package test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ExecutionTest {
    @Test
    public void RepoTest(){
        File f = new File("haha.txt");
        System.out.println(f.getAbsolutePath());

        f = new File("../jarfiles/tloc.jar");
        System.out.println(f.getAbsolutePath() + " - " + f.exists());

        ArrayList<String> ok = executeJar("cd ../../ && ls");
        for(String s: ok){
            System.out.println(s);
        }
    }
    @Test
    public void ExecuteTloc() throws IOException {
        ArrayList<String> data = executeJar("java -jar ../jarfiles/tloc.jar ../../jfreechart-master/src/test/java/org/jfree/chart/title/TitleTest.java");
        Assert.assertFalse(data.isEmpty());
        try{
            Assert.assertEquals(39,Integer.parseInt(data.get(0)));
        } catch(NumberFormatException e){
            Assert.fail();
        }
    }

    @Test
    public void ExecuteTlocInvalidPath() throws IOException {
        ArrayList<String> data = executeJar("java -jar ../jarfiles/tloc.jar e");
        Assert.assertFalse(data.isEmpty());
        Assert.assertEquals("File or directory does not exists!", data.get(0));
    }

    @Test
    public void ExecuteTlocNoPath() throws IOException {
        ArrayList<String> data = executeJar("java -jar ../jarfiles/tloc.jar");
        Assert.assertFalse(data.isEmpty());
        Assert.assertEquals("Please provide a path!", data.get(0));
    }

    @Test
    public void ExecuteTAssert() throws IOException {
        ArrayList<String> data = executeJar("java -jar ../jarfiles/tassert.jar ../../jfreechart-master/src/test/java/org/jfree/chart/title/TitleTest.java");
        Assert.assertFalse(data.isEmpty());
        try{
            Assert.assertEquals(11,Integer.parseInt(data.get(0)));
        } catch(NumberFormatException e){
            Assert.fail();
        }
    }
    @Test
    public void ExecuteTassertInvalidPath() throws IOException {
        ArrayList<String> data = executeJar("java -jar ../jarfiles/tassert.jar e");
        Assert.assertFalse(data.isEmpty());
        Assert.assertEquals("File or directory does not exists!", data.get(0));
    }
    @Test
    public void ExecuteTAssertNoPath() throws IOException {
        ArrayList<String> data = executeJar("java -jar ../jarfiles/tassert.jar");
        Assert.assertFalse(data.isEmpty());
        Assert.assertEquals("Please provide a path!", data.get(0));
    }

    @Test
    public void ExecuteTls() throws IOException {
        ArrayList<String> lineData = executeJar("java -jar ../jarfiles/tls.jar ../../jfreechart-master/src/test/");
        Assert.assertEquals(350, lineData.size());

    }

    @Test
    public void ExecuteTlsFileOut() throws IOException {
        executeJar("java -jar ../jarfiles/tls.jar -o output.csv ../../jfreechart-master/src/test/");
        File f = new File("output.csv");
        Assert.assertTrue(f.exists());
        Assert.assertTrue(Files.size(Path.of(f.getAbsolutePath())) > 0);
    }

    @Test
    public void ExecuteTropcomp() throws IOException {
        ArrayList<String> s = executeJar("java -jar ../jarfiles/tropcomp.jar ../../jfreechart-master/src/test/ 20");
        Assert.assertTrue(s.size() > 1);
    }
    @Test
    public void ExecuteTropcompFileOut() throws IOException {
        executeJar("java -jar ../jarfiles/tropcomp.jar -o output.csv ../../jfreechart-master/src/test/ 20");
        File f = new File("output.csv");
        Assert.assertTrue(f.exists());
        Assert.assertTrue(Files.size(Path.of(f.getAbsolutePath())) > 0);
    }

    private ArrayList<String> executeJar(String command){
        ArrayList<String> output = new ArrayList<>();
        try{
            Process process = Runtime.getRuntime().exec(command);
            InputStream inputStream = process.getInputStream();
            // Create a BufferedReader to read lines from the input stream
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                output.add(line.trim());
            }
        } catch (IOException e){
        }
        return output;
    }

    @After
    public void Cleanup(){
        File f = new File("output.csv");
        if(f.exists())
            f.delete();
    }
}
