import java.io.*;

public class TlocLilou {
    public static void main(String[] args) throws FileNotFoundException {

        if(args.length != 1){
            System.out.println("Vous devez entrer en paramètre un fichier source d'une classe de test java.");
        }
        String filePath = args[0];
        File javaFile = new File(filePath);

        if(javaFile.exists() && !javaFile.isDirectory()){
            try(BufferedReader buffReader = new BufferedReader(new FileReader(javaFile))){
                int nbCodeLine = numberCodeLines(buffReader);
                System.out.println(nbCodeLine);
                
            } catch (IOException e){
                e.printStackTrace();
            }
        }

    }
    
    public static int numberCodeLines(BufferedReader buffReader) throws IOException {
        String line = buffReader.readLine();
        int nbLines = 0;
        boolean isCommented = false;
        boolean isMultiLine = false;

        while(line != null){

            if(!line.contains("//") && !line.isEmpty()){
                if(!line.contains("/*")){
                    if(!line.contains("*/")){
                        if(!isMultiLine){
                            nbLines++;
                        }
                    }
                    else{
                        isMultiLine = false;
                    }
                }
                else{
                    isMultiLine = true;
                }
            }
            line = buffReader.readLine();

        }
        return nbLines;
    }
}
