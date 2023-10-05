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
    public void ExecuteTloc() throws IOException {
        Process process = Runtime.getRuntime().exec("java -jar ../jarfiles/tloc.jar ../../jfreechart-master/src/test/java/org/jfree/chart/title/TitleTest.java");
        InputStream inputStream = process.getInputStream();
        // Create a BufferedReader to read lines from the input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        line = reader.readLine();

        Assert.assertNotNull(line);
        try{
            Assert.assertEquals(39,Integer.parseInt(line));
        } catch(NumberFormatException e){
            Assert.fail();
        }
    }

    @Test
    public void ExecuteTAssert() throws IOException {
        Process process = Runtime.getRuntime().exec("java -jar ../jarfiles/tassert.jar ../../jfreechart-master/src/test/java/org/jfree/chart/title/TitleTest.java");
        InputStream inputStream = process.getInputStream();
        // Create a BufferedReader to read lines from the input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        line = reader.readLine();

        Assert.assertNotNull(line);
        try{
            Assert.assertEquals(11,Integer.parseInt(line));
        } catch(NumberFormatException e){
            Assert.fail();
        }
    }

    @Test
    public void ExecuteTls() throws IOException {
        Process process = Runtime.getRuntime().exec("java -jar ../jarfiles/tls.jar ../../jfreechart-master/src/test/");
        InputStream inputStream = process.getInputStream();
        // Create a BufferedReader to read lines from the input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        ArrayList<String> lineData = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            lineData.add(line);
        }

        Assert.assertEquals(350, lineData.size());

    }

    @Test
    public void ExecuteTlsFileOut() throws IOException {
        Process process = Runtime.getRuntime().exec("java -jar ../jarfiles/tls.jar -o output.csv ../../jfreechart-master/src/test/");
        InputStream inputStream = process.getInputStream();
        // Create a BufferedReader to read lines from the input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
        }
        File f = new File("output.csv");

        Assert.assertTrue(f.exists());
        Assert.assertTrue(Files.size(Path.of(f.getAbsolutePath())) > 0);
    }

    @Test
    public void ExecuteTropcomp() throws IOException {
        Process process = Runtime.getRuntime().exec("java -jar ../jarfiles/tropcomp.jar ../../jfreechart-master/src/test/ 20");
        InputStream inputStream = process.getInputStream();
        // Create a BufferedReader to read lines from the input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        ArrayList<String> s = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            s.add(line);
        }
        Assert.assertTrue(s.size() > 1);
    }
    @Test
    public void ExecuteTropcompFileOut() throws IOException {
        Process process = Runtime.getRuntime().exec("java -jar ../jarfiles/tropcomp.jar -o output.csv ../../jfreechart-master/src/test/ 20");
        InputStream inputStream = process.getInputStream();
        // Create a BufferedReader to read lines from the input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
        }
        File f = new File("output.csv");

        Assert.assertTrue(f.exists());
        Assert.assertTrue(Files.size(Path.of(f.getAbsolutePath())) > 0);
    }

    @After
    public void Cleanup(){
        File f = new File("output.csv");
        if(f.exists())
            f.delete();
    }
}
