import org.junit.Assert;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class tropcomp {

    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException {

        if(args.length != 2){
            System.out.println("Vous devez entrer en paramètre le chemin d'accès d'un dossier qui contient" +
                    "du code test java organisé en paquet ainsi que le seuil.");
            System.exit(0);
        }

        String filePath = args[0];
        File javaDirectory = new File(filePath);


        if(javaDirectory.exists() && javaDirectory.isDirectory()){

            ArrayList<MetricJavaClass> listJavaClassMetrics = readDirectory(javaDirectory);
            findSuspectClass(listJavaClassMetrics, Integer.parseInt(args[1]));

        }

    }

    public static void findSuspectClass(ArrayList<MetricJavaClass> listJavaClassMetrics, int seuil){

    }

    public static ArrayList<MetricJavaClass> readDirectory(File javaDirectory) throws FileNotFoundException, ClassNotFoundException {

        ArrayList<MetricJavaClass> listJavaClassMetrics = new ArrayList<MetricJavaClass>();

        for(File file : javaDirectory.listFiles()){

            if(file.isDirectory()){
                readDirectory(file);
            }
            else if(getFileExtension(file.getAbsolutePath()).equalsIgnoreCase("java")){
                listJavaClassMetrics.add(getInfoFromFile(file));
            }

        }

        return listJavaClassMetrics;
    }

    //https://stackoverflow.com/questions/25298691/how-to-check-the-file-type-in-java
    public static String getFileExtension(String fullName) {
        if(fullName != null){
            String fileName = new File(fullName).getName();
            int dotIndex = fileName.lastIndexOf('.');
            return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
        }
        return "";

    }

    public static MetricJavaClass getInfoFromFile(File javaFile) throws ClassNotFoundException, FileNotFoundException {


        String className = javaFile.getName().replace(".java","");

        int tloc = 0;
        try(BufferedReader buffReader = new BufferedReader(new FileReader(javaFile))){
            tloc = numberCodeLines(buffReader);

        } catch (IOException e){
            e.printStackTrace();
        }

        int tassert = 0;
        try(BufferedReader buffReader = new BufferedReader(new FileReader(javaFile))){
            tassert = calculateTassert(buffReader);

        } catch (IOException e){
            e.printStackTrace();
        }

        float tcmp = calculateTcmp(tloc, tassert);

        MetricJavaClass javaClass = new MetricJavaClass(tloc, tcmp, className);

        return javaClass;

    }

    public static String getPackageName(File javaFile){
        boolean isMultiLine = false;

        try(BufferedReader buffReader = new BufferedReader(new FileReader(javaFile))){

            String line = buffReader.readLine();
            String packageName = "";

            while(line != null){

                if(!line.contains("//")){
                    if(!line.contains("/*")){
                        if(!line.contains("*/")){
                            if(!isMultiLine){

                                //La ligne n'est pas un commentaire
                                if(line.contains("package")){
                                    packageName = line.replace("package","").
                                            replace(";", "").trim();
                                    break;
                                }


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

            return packageName;

        } catch (IOException e){
            e.printStackTrace();
        }
        return "";
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

    public static int calculateTassert(BufferedReader buffReader) throws IOException {
        int nbTassert = 0;

        String line = buffReader.readLine();
        boolean isMultiLine = false;

        List<String> listAssertMethodName = getAllAssertMethodName();

        while(line != null){

            if(!line.contains("//")){
                if(!line.contains("/*")){
                    if(!line.contains("*/")){
                        if(!isMultiLine){

                            //La ligne n'est pas un commentaire
                            // On regarde maintenant si des méthodes d'assertions JUnit sont utilisées.
                            for(String method : listAssertMethodName){
                                if(line.matches("( )*"+method+"\n*( )*\\((.*)")){
                                    nbTassert++;
                                }
                            }

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
        return nbTassert;
    }

    public static float calculateTcmp(int tloc, int tassert){
        float tcmp = 0;

        if(tloc == 0 || tassert == 0){
            return tcmp;
        }

        tcmp = (float) Math.floor(tloc * 100f / tassert) / 100;

        return tcmp;
    }
}
