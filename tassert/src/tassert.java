import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.exit;

/*
* assert("assert(\"haha\")
*
* */
public class tassert {
    public static void main(String[] args){
        if(args.length != 1){

            // Should exit the program.
            System.out.println("Please provide a path!");
            exit(0);
        }

        File file = new File(args[0]);
        if(file.exists())
        {
            if(file.isDirectory()){
                System.out.println("Programs only supports single file.");
            }
            else{
                System.out.println(countAssert(DoubleQuoteStringRemover(commentRemover(new File(args[0])))));
            }
        }
        else{
            System.out.println("File or directory does not exists!");
            exit(0);
        }
    }

    public static int countAssert(ArrayList<String> target){
        String[] targetFun = {"assertArrayEquals", "assertEquals", "assertNotEquals", "assertFalse", "assertNotNull", "assertNotSame", "assertNull", "assertSame", "assertThat", "assertThrows", "assertTrue", "fail"};
        int count = 0;
        for(String s: target){
            //find match
            boolean found = false;
            for(String s2 : targetFun){
                String regex = "( *(.+)[A-z]+\\.| +|)" + s2 + "\\((.*)";
                if(s.matches(regex)){
                    found=true;
                    break;
                }
            }
            if(found)
                count++;

        }



        return count;
    }

    public static ArrayList<String> DoubleQuoteStringRemover(ArrayList<String> target){
        Pattern pattern = Pattern.compile("\"((?:\\\\.|[^\"\\\\])*)\"");
        ArrayList<String> output = new ArrayList<>();
        for(String s : target){
            Matcher matcher = pattern.matcher(s);

            int index = 0;
            StringBuilder builder = new StringBuilder();
            while (matcher.find()) {
                String matchedString = matcher.group(1); // Extract the string between double quotes
                builder.append(s, index, matcher.start());
                builder.append("\"\"");
                index = matcher.end();
            }

            builder.append(s.substring(index));
            output.add(builder.toString());
        }
        return output;
    }

    /**
     * Removes comments from the file.
     * @param f
     */
    public static ArrayList<String> commentRemover(File f){

        ArrayList<String> strings = new ArrayList<String>();

        try {
            Scanner scanner = new Scanner(f);

            //If this is true, it means we are in a multiline comment.
            boolean multiline = false;

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                //Check if line it not just spaces.
                if(line.trim().length() > 0){
                    //Check normal comment
                    if(line.trim().matches("^\\/\\**")){
                        multiline=true;
                    }
                    else if(line.trim().matches("\\*\\/")){
                        multiline=false;
                    }
                    else {
                        if(!multiline && !line.trim().matches("^\\/\\/") && !line.trim().matches("\\/\\*(.)*\\*\\/"))
                            strings.add(line);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            return strings;
        }
        return strings;
    }
}
