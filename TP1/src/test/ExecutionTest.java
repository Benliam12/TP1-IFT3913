package test;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

public class ExecutionTest {
    @Test
    public void ExecuteTloc() throws IOException {
        File f = new File("../jarfiles/Tloc.jar");
        System.out.println(f.getAbsolutePath() + "-" + f.exists());
        Process process = Runtime.getRuntime().exec("java -jar ../jarfiles/Tloc.jar ../../jfreechart-master/src/test/java/org/jfree/chart/title/TitleTest.java");
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
        Process process = Runtime.getRuntime().exec("java -jar ../jarfiles/Tassert.jar ../../jfreechart-master/src/test/java/org/jfree/chart/title/TitleTest.java");
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
        Process process = Runtime.getRuntime().exec("java -jar ../jarfiles/Tls.jar ../../jfreechart-master/src/test/");
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
}
