import org.junit.Assert;

import java.io.*;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class tls {

    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException {

        if(args.length != 1){
            System.out.println("Vous devez entrer en paramètre le chemin d'accès d'un dossier qui contient" +
                    "du code test java organisé en paquet.");
            System.exit(0);
        }

        String filePath = args[0];
        File javaDirectory = new File(filePath);


        if(javaDirectory.exists() && javaDirectory.isDirectory()){

            for(File javaFile : javaDirectory.listFiles()){
                String csvInfo = getInfoFromFile(javaFile);
                System.out.println(csvInfo);

            }

        }

    }

    public static String getInfoFromFile(File javaFile) throws ClassNotFoundException, FileNotFoundException {

        String csvInfo = "";


        String relativePath = "./" +javaFile.getName();
        System.out.println(relativePath);

        String packageName = getPackageName(javaFile);
        System.out.println(packageName);

        String className = javaFile.getName().replace(".java","");
        System.out.println(className);

        int tloc = 0;
        try(BufferedReader buffReader = new BufferedReader(new FileReader(javaFile))){
            tloc = numberCodeLines(buffReader);
            System.out.println(tloc);

        } catch (IOException e){
            e.printStackTrace();
        }

        int tassert = 0;
        try(BufferedReader buffReader = new BufferedReader(new FileReader(javaFile))){
            tassert = calculateTassert(buffReader);
            System.out.println(tassert);

        } catch (IOException e){
            e.printStackTrace();
        }

        float tcmp = calculateTcmp(tloc, tassert);
        String tcmpFormat = String.format("%.2f", tcmp);
        System.out.println(tcmp);

        csvInfo = relativePath + ", " + packageName + ", " + className + ", " + tloc + ", " + tassert +", " + tcmpFormat;



        return csvInfo;

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

        tcmp = (float) tloc / tassert;

        return tcmp;
    }
}
