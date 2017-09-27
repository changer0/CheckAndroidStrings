import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckStrings {
    private static Map<String, String[]> stringsMap = new HashMap<String, String[]>();
    //Default,en-rGB,zh-rCN,zh-rTW,zh-rHK,zh_rBo 按照这个顺序排列
    private static final int BASE_INDEX = 2;
    public static void main(String[] args) {
        BufferedReader br = null;
        try {

            //1. 生成SAX工厂类
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //2. 通过工厂类生成SAXParser对象
            SAXParser parser = factory.newSAXParser();
            parser.parse(new FileInputStream("strings.xml"), new SaxHandler(stringsMap, BASE_INDEX));
            writeToFileIsContain();
            writeToFileValues();
            printTest();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
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
        System.out.println("文档输出完成");
    }


    /**
     * 将搜索values写入文件
     */
    private static void writeToFileValues() {
        Set<Map.Entry<String, String[]>> entries = stringsMap.entrySet();
        Iterator<Map.Entry<String, String[]>> iterator = entries.iterator();
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("荣耀阅读字符串检查结果[包含字符串].csv"), "gbk"));
            bw.write("String Name," + "Default," + "en-rGB," + "zh-rCN," + "zh-rTW," + "zh-rHK," + "zh_rBo\n" );
            while (iterator.hasNext()) {
                Map.Entry<String, String[]> entry = iterator.next();
                bw.write(entry.getKey() + ",");
                for (int i = 0; i < 6; i++) {
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
     * 将搜索values写入文件
     */
    private static void writeToFileIsContain() {
        Set<Map.Entry<String, String[]>> entries = stringsMap.entrySet();
        Iterator<Map.Entry<String, String[]>> iterator = entries.iterator();
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("荣耀阅读字符串检查结果[是否缺失].csv"), "gbk"));
            bw.write("String Name," + "Default," + "en-rGB," + "zh-rCN," + "zh-rTW," + "zh-rHK," + "zh_rBo\n" );
            while (iterator.hasNext()) {
                Map.Entry<String, String[]> entry = iterator.next();
                bw.write(entry.getKey() + ",");
                for (int i = 0; i < 6; i++) {
                    if (i == 5) {
                        if (entry.getValue()[i] == null) {
                            bw.write("\"" + "无" + "\"" + "\n");
                        } else {
                            bw.write("\"" + "有" + "\"" + "\n");
                        }
                    } else {
                        if (entry.getValue()[i] == null) {
                            bw.write("\"" + "无" + "\"" + ",");
                        } else {
                            bw.write("\"" + "有" + "\"" + ",");
                        }
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
            System.out.println("Key => " + entry.getKey() + " Value => " + entry.getValue()[BASE_INDEX]);
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
