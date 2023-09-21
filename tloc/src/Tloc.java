import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.System.exit;

public class Tloc {
    public static void main(String[] args){

        if(args.length != 1){
            // Should exit the program.
            System.out.println("Vous devez fournir un chemin d'accès!");
            exit(0);
        }

        File file = new File(args[0]);

        if(file.exists())
        {
            if(file.isDirectory()){
                for(File f : file.listFiles()){
                    System.out.println(f.getName());
                }
            }
            else{
                System.out.println(file.getAbsolutePath());
                System.out.println("SIZE: " + countLine(file));
            }
        }
        else{
            System.out.println("File or directory does not exists!");
            exit(0);
        }


    }

    public static int countLine(File f){
        try {
            int total = 0;
            Scanner scanner = new Scanner(f);

            //If this is true, it means we are in a multiline comment.
            boolean multiline = false;

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                //Check if line it not just spaces.
                if(line.trim().length() > 0){
                    //Check normal comment
                    if(line.length() >=2)
                    {
                        if(line.trim().matches("^\\/\\**")){
                            System.out.println("Start comment! - " +line.trim());
                            multiline=true;
                        }
                        else if(line.trim().matches("\\*\\/")){
                            System.out.println("Closing comment! - " + line.trim());
                            multiline=false;
                        }
                        else
                        {
                            if(!multiline)
                                total++;
                        }
                    }
                    else
                    {
                        total++;
                    }
                }
            }
            return total;
        } catch (FileNotFoundException e) {
            return 0;
        }
    }
}