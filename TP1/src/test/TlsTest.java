package test;

import main.FileData;
import main.Tloc;
import main.Tls;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class TlsTest {
    @Test
    public void PDFExampleTest(){
        File f = new File("../../jfreechart-master/src/test/java/org/jfree/chart/title/TitleTest.java");
        FileData fileData = Tls.getFileInfo(f);
        Assert.assertEquals(39, fileData.getTloc());
        Assert.assertEquals(11, fileData.getTassert());
        Assert.assertEquals(3.54f, fileData.getTcmp(), 0.01f);
        Assert.assertEquals("org.jfree.chart.title", fileData.getPackageName());
    }
}
