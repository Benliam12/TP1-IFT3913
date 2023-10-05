package main;

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
        if(file.exists()) {
            System.out.println("Scanning the files...");
            ArrayList<FileData> fileData = new ArrayList<FileData>();
            tls(file,fileData);
            if(outputPath != null){
                writeToFile(fileData, outputPath);
            }
            else{
                for(FileData data: fileData)
                    System.out.println(data.toString());
            }
        }
        else{
            System.out.println("File or directory does not exists!");
            exit(0);
        }
    }

    public static void tls(File f,ArrayList<FileData> fileDataOutput){
        tls(f, fileDataOutput,new ArrayList<>(),new ArrayList<>());
    }

    public static void tls(File f, ArrayList<FileData> fileData, ArrayList<Integer> tloc, ArrayList<Float> tcmp){
        if(f.isDirectory()){
            for(String s: f.list()){
                tls(new File(f.getPath() + "/" + s),fileData, tloc, tcmp);
            }
        }
        else{
            //Process each file that was found.
            if(f.getName().matches("^(.)*\\.java$")){
                FileData info = getFileInfo(f);
                if(info.getTassert() > 0){
                    tloc.add(info.getTloc());
                    tcmp.add(info.getTcmp());
                    fileData.add(info);
                }
            }
        }
    }

    public static FileData getFileInfo(File f){
        ArrayList<String> cleanCode = Tloc.commentRemover(f);
        String relativePath = f.getPath();
        String packageName = getPackageName(cleanCode);
        String className = f.getName().replace(".java","");

        int tloc = cleanCode.size();
        int tassert = Tassert.countAssert(cleanCode);
        float tcmp = (float) Math.floor(100f * tloc/tassert) / 100f;
        return new FileData(relativePath,packageName,className,tloc,tassert,tcmp);
    }

    public static boolean writeToFile(ArrayList<FileData> data, String path, boolean display){
        File f = new File(path);
        if(f.isDirectory()){
            if(display)
                System.out.println("The output file cannot be a directory!");
            return false;
        }
        else if(!f.exists()) {
            try{
                f.createNewFile();
            } catch (IOException e){
                if(display)
                    System.out.println("Error, cannot create the output file!");
                return false;
            }
        }

        try{
            FileWriter fileWriter = new FileWriter(f);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (FileData filedata : data) {
                bufferedWriter.write(filedata.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileWriter.close();
            if(display)
                System.out.println("Data exported!");
            return true;
        } catch (IOException e){
            if(display)
                System.out.println("Error while exporting data in the output file! " + e.getMessage());
            return false;
        }
    }

    public static void writeToFile(ArrayList<FileData> data, String path){
       writeToFile(data,path,true);
    }

    public static String getPackageName(ArrayList<String> fileCleanData){
        for(String s: fileCleanData){
            if(s.trim().matches("package (.*)")){
                return s.replace("package","").
                        replace(";", "").trim();
            }
        }
        return null;
    }


}
