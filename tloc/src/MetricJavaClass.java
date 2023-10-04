public class MetricJavaClass {
    int tloc = 0;
    float tcmp = 0;
    String className = "";

    public MetricJavaClass(int tlocValue, float tcmpValue, String classNameValue){
        this.tloc = tlocValue;
        this.tcmp = tcmpValue;
        this.className = classNameValue;
    }

    public String getClassName(){
        return this.className;
    }

    public int getTloc(){
        return this.tloc;
    }

    public float getTcmp(){
        return this.tcmp;
    }
}
