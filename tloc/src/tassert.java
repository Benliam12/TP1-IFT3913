import org.junit.Assert;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class tassert {

    public static void main(String[] args) throws FileNotFoundException {

        if(args.length != 1){
            System.out.println("Vous devez entrer en paramètre un fichier source d'une classe de test java.");
        }
        String filePath = args[0];
        File javaFile = new File(filePath);

        if(javaFile.exists() && !javaFile.isDirectory()){
            try(BufferedReader buffReader = new BufferedReader(new FileReader(javaFile))){

                List<String> liste = getAllAssertMethodName();
                int nbCodeLine = calculateTassert(buffReader);
                System.out.println(nbCodeLine);

            } catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    public static List<String> getAllAssertMethodName(){
        List<String> listAssertMethodName = new ArrayList<String>();

        Method[] assertMethodsList = Assert.class.getMethods();

        for (Method assertMethod : assertMethodsList) {
            if(!listAssertMethodName.contains(assertMethod.getName())){
                listAssertMethodName.add(assertMethod.getName());
            }
        }

//        for(String elem : listAssertMethodName){
//            System.out.println(elem);
//        }

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
}
