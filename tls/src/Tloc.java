import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tloc {
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
                    if(line.trim().matches("^\\/\\*(.*)$") && !line.trim().matches("(.)*\\*\\/")){
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
