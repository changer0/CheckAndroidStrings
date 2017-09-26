import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckStrings {
    private static Map<String, String[]> stringsMap = new HashMap<String, String[]>();
    public static void main(String[] args) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("strings.xml"));
            StringBuilder sb = new StringBuilder();
            String tempStr = "";
            while ((tempStr = br.readLine()) != null) {
                readToStringsMap(tempStr, 0);
                sb.append(tempStr + "\n");
            }
            writeToFile();
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

        System.out.println("输出完成");
    }


    /**
     * 将字符串保存到stringsMap
     * 正则关系: <string name="(.*)">(.*)</string>
     * @param str 需要保证每次都是正确的数据
     * @param index
     */
    private static void readToStringsMap(String str, int index) {
        Pattern pattern = Pattern.compile("<string name=\"([a-zA-Z_].*)\">(.*)</string>");
        Matcher mat = pattern.matcher(str);
        if (mat.find()) {
            String key = mat.group(1);
            String value = mat.group(2);
            if (!isTextEmpty(key)) {
                String[] values = stringsMap.get(key);
                if (values == null) {
                    stringsMap.put(key,  new String[]{value, "", "", "", "", ""});
                } else {
                    values[index] = value;
                }
            }
        }
    }


    /**
     * 写入文件
     */
    private static void writeToFile() {
        Set<Map.Entry<String, String[]>> entries = stringsMap.entrySet();
        Iterator<Map.Entry<String, String[]>> iterator = entries.iterator();
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("荣耀阅读字符串检查结果.csv"), "gbk"));
            bw.write("String Name," + "Default," + "en-rGB," + "zh-rCN," + "zh-rTW," + "zh-rHK," + "zh_rBo\n" );
            while (iterator.hasNext()) {
                Map.Entry<String, String[]> entry = iterator.next();
                bw.write(entry.getKey() + ",");
                for (int i = 0; i < 6; i++) {
//                    if (i == 5) {
//                        bw.write(new String(("\"" + entry.getValue()[i] + "\"" + "\n").getBytes("utf-8"), "gbk"));
//                    } else {
//                        bw.write(new String(("\"" + entry.getValue()[i] + "\"" + ",").getBytes("utf-8"), "gbk"));
//                    }


                    if (i == 5) {
                        bw.write("\"" + entry.getValue()[i] + "\"" + "\n");
                    } else {
                        bw.write("\"" + entry.getValue()[i] + "\"" + ",");
                    }
                }
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 打印测试
     * @return
     */
    private static void printTest() {
        Set<Map.Entry<String, String[]>> entries = stringsMap.entrySet();
        Iterator<Map.Entry<String, String[]>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String[]> entry = iterator.next();
            System.out.println("Key => " + entry.getKey() + " Value => " + entry.getValue()[0]);
        }
    }

    /**
     * 判空操作
     * @param str
     * @return
     */
    public static boolean isTextEmpty(String str) {
        boolean ret = true;
        if (str != null && !"".equals(str.trim())) {
            ret = false;
        }
        return ret;
    }

}
