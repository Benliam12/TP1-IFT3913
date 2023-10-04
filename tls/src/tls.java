import java.io.File;
import java.util.ArrayList;

import static java.lang.System.exit;

public class tls {

    public static void main(String[] args){
        if(args.length != 1){
            // Should exit the program.
            System.out.println("Please provide a path!");
            exit(0);
        }

        File file = new File(args[0]);
        if(file.exists()) {
            tls(file);
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
            if(f.getName().matches("^(.)*\\.java$")){
                System.out.println(getFileInfo(f));
            }
        }
    }

    public static String getFileInfo(File f){

        ArrayList<String> cleanCode = Tloc.commentRemover(f);

        String csvInfo = "";
        String relativePath = f.getPath();
        String packageName = getPackageName(cleanCode);
        String className = f.getName().replace(".java","");

        int tloc = cleanCode.size();
        int tassert = Tassert.countAssert(cleanCode);
        float tcmp = (float) Math.floor(100f * tloc/tassert) / 100f;
        csvInfo = relativePath + ", " + packageName + ", " + className + ", " + tloc + ", " + tassert +", " + tcmp;
        return csvInfo;
    }

    public static String getPackageName(ArrayList<String> fileCleanData){
        boolean isMultiLine = false;

        for(String s: fileCleanData){
            if(s.trim().matches("package (.*)")){
                return s.replace("package","").
                        replace(";", "").trim();
            }
        }
        return null;
    }
}
