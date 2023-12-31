package main;

import org.junit.Assert;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.exit;

public class Tassert {
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
                System.out.println(countAssert(DoubleQuoteStringRemover(Tloc.commentRemover(file))));
            }
        }
        else{
            System.out.println("File or directory does not exists!");
            exit(0);
        }
    }
    public static List<String> getAllAssertMethodName(){
        List<String> listAssertMethodName = new ArrayList<String>();
        Method[] assertMethodsList = Assert.class.getMethods();
        for (Method assertMethod : assertMethodsList) {
            if(!listAssertMethodName.contains(assertMethod.getName())){
                listAssertMethodName.add(assertMethod.getName());
            }
        }

        return listAssertMethodName;
    }

    public static int countAssert(ArrayList<String> target){
        List<String> targetFun = getAllAssertMethodName();
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
}
