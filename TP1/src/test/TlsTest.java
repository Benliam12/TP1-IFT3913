package test;

import main.FileData;
import main.Tls;
import org.junit.*;

import java.io.File;
import java.util.ArrayList;

import static java.lang.System.exit;

public class TlsTest {

    private static ArrayList<FileData> fileData = new ArrayList<>();
    @BeforeClass
    public static void init(){
        File f = new File("output.csv");
        if(f.exists()){
            if(!f.delete()){
                System.out.println("Error on Tls init method!");
                exit(0);
            }
        }

        File target = new File("../../jfreechart-master/src/test/");
        Tls.tls(target, fileData);
    }

    @Test
    public void PDFExampleTest(){
        File f = new File("../../jfreechart-master/src/test/java/org/jfree/chart/title/TitleTest.java");
        FileData fileData = Tls.getFileInfo(f);
        Assert.assertEquals(39, fileData.getTloc());
        Assert.assertEquals(11, fileData.getTassert());
        Assert.assertEquals(3.54f, fileData.getTcmp(), 0.01f);
        Assert.assertEquals("org.jfree.chart.title", fileData.getPackageName());
    }

    @Test
    public void ExportTestFile(){
        File target = new File("../../jfreechart-master/src/test/java/org/jfree/chart/title/TitleTest.java");
        ArrayList<FileData> data = new ArrayList<>();
        Tls.tls(target, data);
        Tls.writeToFile(data, "output.csv",false);
        File f = new File("output.csv");
        Assert.assertTrue(f.exists());
    }

    @Test
    public void FileDetectionTest(){
        Assert.assertEquals(349, fileData.size());
    }

    @Test
    public void ExtensionTest(){
        for(FileData file : fileData){
            Assert.assertTrue(file.getPath().matches("(.*)\\.java"));
        }
    }

    @AfterClass
    public static void clean(){
        File f = new File("output.csv");
        if(f.exists()){
            if(!f.delete()){
                System.out.println("Error on Tls clean method!");
            }
        }
    }
}
