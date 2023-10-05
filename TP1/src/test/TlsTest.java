package test;

import main.FileData;
import main.Tloc;
import main.Tls;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class TlsTest {
    @Before
    public void init(){
        File f = new File("output.csv");
        if(f.exists()){
            if(!f.delete()){
                System.out.println("Error on Tls init method!");
            }
        }
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

    @After
    public void clean(){
        File f = new File("output.csv");
        if(f.exists()){
            if(!f.delete()){
                System.out.println("Error on Tls clean method!");
            }
        }
    }
}
