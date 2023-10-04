import java.io.File;

import static java.lang.System.exit;

public class tls {

    public static void main(String[] args){
        /*
        if(args.length != 1){
            // Should exit the program.
            System.out.println("Please provide a path!");
            exit(0);
        }*/

        File file = new File("./");
        if(file.exists())
        {
            tls(file);
            if(file.isDirectory()){
            }
            else{

            }
        }
        else{
            System.out.println("File or directory does not exists!");
            exit(0);
        }

    }


    public static void tls(File f){
        if(f.isDirectory()){
            for(String s: f.list()){
                tls(new File(f.getPath() + "/" + s));
            }
        }
        else{
            //Process each file that was found.
            System.out.println(f.getPath());
        }
    }
}
