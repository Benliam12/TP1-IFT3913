import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static java.lang.System.exit;

public class Tropcomp {

    public static void main(String[] args){
        if(args.length < 1){
            // Should exit the program.
            System.out.println("Please provide a path!");
            exit(0);
        }
        String path = ".";
        String outputPath = null;

        float seuil = 0.10f;

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
            System.out.println("Scanning the files...");

            ArrayList<Integer> tloc = new ArrayList<>();
            ArrayList<Float> tcmp = new ArrayList<>();
            ArrayList<FileData> files = new ArrayList<>();
            Tls.tls(file,data,files,tloc,tcmp);

            Collections.sort(tloc);
            Collections.sort(tcmp);

            int seulTloc = tloc.get((int) Math.floor((1-seuil)*tloc.size()));
            float seultcmp = tcmp.get((int) Math.floor((1-seuil)*tcmp.size()));

            System.out.println("TLOC : "+ seulTloc + " - TCMP: "+ seultcmp);

            ArrayList<FileData> tropcompFile = GetComp(files,seulTloc,seultcmp);

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

                    for (FileData fileData : tropcompFile) {
                        bufferedWriter.write(fileData.toString());
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
                for (FileData fileData : tropcompFile)
                    System.out.println(fileData.toString());
            }
        }
        else{
            System.out.println("File or directory does not exists!");
            exit(0);
        }
    }

    public static ArrayList<FileData> GetComp(ArrayList<FileData> input, int tloc, float tcmp){

        ArrayList<FileData> output = new ArrayList<>();

        for(FileData file: input){
            if(file.getTloc() > tloc && file.getTcmp() > tcmp){
                output.add(file);
            }
        }

        return output;
    }
}
