import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Assert;


public class Tassert {
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

    public static int countAssert(ArrayList<String> target){
        List<String> targetFun = getAllAssertMethodName();
        int count = 0;
        for(String s: target){
            //find match
            boolean found = false;
            for(String s2 : targetFun){
                String regex = "( *(.+)[A-z]+\\.| +|)" + s2 + "\\((.*)";
                if(s.matches(regex)){
                    found=true;
                    break;
                }
            }
            if(found)
                count++;

        }

        return count;
    }

    public static ArrayList<String> DoubleQuoteStringRemover(ArrayList<String> target){
        Pattern pattern = Pattern.compile("\"((?:\\\\.|[^\"\\\\])*)\"");
        ArrayList<String> output = new ArrayList<>();
        for(String s : target){
            Matcher matcher = pattern.matcher(s);

            int index = 0;
            StringBuilder builder = new StringBuilder();
            while (matcher.find()) {
                String matchedString = matcher.group(1); // Extract the string between double quotes
                builder.append(s, index, matcher.start());
                builder.append("\"\"");
                index = matcher.end();
            }

            builder.append(s.substring(index));
            output.add(builder.toString());
        }
        return output;
    }
}
