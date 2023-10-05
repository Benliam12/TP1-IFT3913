public class FileData {
    private String path;
    private String packageName;
    private String className;
    private int tloc;
    private int tassert;
    private float tcmp;

    public FileData(String path, String packageName, String className, int tloc, int tassert, float tcmp){
        this.path = path;
        this.packageName = packageName;
        this.className = className;
        this.tloc = tloc;
        this.tassert = tassert;
        this.tcmp = tcmp;
    }

    public String toString(){
        return path + ", " + packageName + ", " + className + ", " + tloc + ", " + tassert +", " + tcmp;
    }

    public String getPath() {
        return path;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public int getTloc() {
        return tloc;
    }

    public int getTassert() {
        return tassert;
    }

    public float getTcmp() {
        return tcmp;
    }
}
