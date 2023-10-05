package main;

import java.io.File;
import java.util.ArrayList;
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

        float seuil = 1.00f;

        if(args.length == 2) {
            path = args[0];
            try{
                seuil = Float.parseFloat(args[1]);
                seuil /=100;
            } catch(NumberFormatException e){
                System.out.println("Invalid threshold number!");
                exit(0);
            }
        }else if(args.length == 4){
            if(args[0].equalsIgnoreCase("-o")){
                path = args[2];
                outputPath = args[1];
                try{
                    seuil = Float.parseFloat(args[3]);
                    seuil /=100;
                } catch(NumberFormatException e){
                    System.out.println("Invalid threshold number!");
                    exit(0);
                }
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
        processTropComp(file, outputPath, seuil);
    }


    public static void processTropComp(File file, String outputPath, float threshold){
        if(file.exists()) {
            ArrayList<Integer> tloc = new ArrayList<>();
            ArrayList<Float> tcmp = new ArrayList<>();
            ArrayList<FileData> files = new ArrayList<>();

            Tls.tls(file,files,tloc,tcmp);

            Collections.sort(tloc);
            Collections.sort(tcmp);

            int seuilTloc = tloc.get((int) Math.floor((1-threshold)*tloc.size()));
            float seuilTcmp = tcmp.get((int) Math.floor((1-threshold)*tcmp.size()));

            System.out.println("TLOC : "+ seuilTloc + " - TCMP: "+ seuilTcmp);

            ArrayList<FileData> tropcompFile = GetComp(files,seuilTloc,seuilTcmp);

            if(outputPath != null){
                Tls.writeToFile(tropcompFile, outputPath);
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

    /**
     * Get the FileData that are suspicious. Which means, their Tloc and Tcmp are above a certain value
     * @param input Original List of FileData
     * @param tloc Tloc lower threshold
     * @param tcmp Tcmp lower threshold
     * @return New list with FileData with Tloc and Tcmp above thresholds
     */
    public static ArrayList<FileData> GetComp(ArrayList<FileData> input, int tloc, float tcmp){
        ArrayList<FileData> output = new ArrayList<>();
        for(FileData file: input){
            if(file.getTloc() >= tloc && file.getTcmp() >= tcmp){
                output.add(file);
            }
        }
        return output;
    }
}
