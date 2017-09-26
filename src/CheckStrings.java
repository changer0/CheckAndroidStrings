import javax.swing.plaf.TextUI;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class CheckStrings {
    private static Map<String, String[]> stringsMap = new HashMap<String, String[]>();
    public static void main(String[] args) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("text.xml"));
            StringBuilder sb = new StringBuilder();
            String tempStr = "";
            while ((tempStr = br.readLine()) != null) {
                saveToStringsMap(tempStr, 0);
                sb.append(tempStr + "\n");
            }
            System.out.println(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("这是一个测试");
    }


    /**
     * 将字符串保存到stringsMap
     * 正则关系: <string name="(.*)">(.*)</string>
     * @param str 需要保证每次都是正确的数据
     * @param index
     */
    private static void saveToStringsMap(String str, int index) {
        Pattern pattern = Pattern.compile("<string name=\"(.*)\">(.*)</string>");
        Matcher mat = pattern.matcher(str);
        if (mat.find()) {
            String key = mat.group(1);
            String value = mat.group(2);
            if (!isTextEmpty(key)) {

            }
        }
    }

    public static boolean isTextEmpty(String str) {
        boolean ret = false;
        if (str != null && !"".equals(str.trim())) {
            ret = true;
        }
        return ret;
    }

}
