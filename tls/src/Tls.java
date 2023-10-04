import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.System.exit;

public class Tls {

    public static void main(String[] args){
        if(args.length < 1){
            // Should exit the program.
            System.out.println("Please provide a path!");
            exit(0);
        }
        String path = ".";
        String outputPath = null;
        if(args.length == 1) {
            path = args[0];
        }else if(args.length == 3){
            if(args[0].equalsIgnoreCase("-o")){
                path = args[2];
                outputPath = args[1];
            }
            else{
                System.out.println("Incorrect arguments!");
                exit(0);
            }
        }
        else{
            System.out.println("Incorrect arguments!");
            exit(0);
        }


        File file = new File(path);
        ArrayList<String> data = new ArrayList<>();
        if(file.exists()) {
            tls(file,data);
            if(outputPath != null){
                File f = new File(outputPath);
                if(f.isDirectory()){
                    System.out.println("The output file cannot be a directory!");
                    exit(0);
                }
                else if(!f.exists()) {
                    try{
                        f.createNewFile();
                    } catch (IOException e){
                        System.out.println("Error, cannot create the output file!");
                        exit(0);
                    }
                }

                try{
                    FileWriter fileWriter = new FileWriter(f);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                    for (String line : data) {
                        bufferedWriter.write(line);
                        bufferedWriter.newLine();
                    }
                    bufferedWriter.close();
                    fileWriter.close();
                    System.out.println("Data exported!");
                    exit(0);
                } catch (IOException e){
                    System.out.println("Error while exporting data in the output file! " + e.getMessage());
                    exit(0);
                }

            }
            else{
                for(String s: data)
                    System.out.println(s);
            }
        }
        else{
            System.out.println("File or directory does not exists!");
            exit(0);
        }

    }

    public static void tls(File f, ArrayList<String> output){
        if(f.isDirectory()){
            for(String s: f.list()){
                tls(new File(f.getPath() + "/" + s), output);
            }
        }
        else{
            //Process each file that was found.
            if(f.getName().matches("^(.)*\\.java$")){
                output.add(getFileInfo(f));
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
