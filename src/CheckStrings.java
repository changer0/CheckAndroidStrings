import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.*;

public class CheckStrings {
    private static Map<String, String[]> stringsMap = new HashMap<String, String[]>();
    //Default,en-rGB,zh-rCN,zh-rTW,zh-rHK,zh_rBo 按照这个顺序排列
    private static int mBaseIndex = 2;
    public static void main(String[] args) {
        try {
            System.out.println("!请将该Jar文件放在项目根目录!");
            System.out.println("请输入编号用来判断以哪种语言作为对照, 序号对照表:");
            System.out.println("Default 0 en-rGB 1 zh-rCN 2 zh-rTW 3 zh-rHK 4 zh_rBo 5");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNext()) {
                String temIndex = scanner.nextLine();
                try {
                    mBaseIndex = Integer.parseInt(temIndex.trim());
                }catch (Exception e) {
                    System.out.println("输入非法");
                }
                if (mBaseIndex > 5 || mBaseIndex < 0) {
                    System.out.println("输入非法");
                    return;
                }
            }
            //1. 生成SAX工厂类
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //2. 通过工厂类生成SAXParser对象
            SAXParser parser = factory.newSAXParser();
            for (int i = mBaseIndex; i < mBaseIndex + 6; i++) {
                parseStringsFile(parser, i % 6);
            }
            writeToFileIsContain();
            writeToFileValues();
            System.out.println("文档输出完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析字符串文件
     * @param parser
     * @param index
     */
    private static void parseStringsFile(SAXParser parser, int index) {
        try {
            System.out.println("正在读取 => " + getFilePathForIndex(index));
            parser.parse(new FileInputStream(getFilePathForIndex(index)), new SaxHandler(stringsMap, index));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据下列顺序
     * Default,en-rGB,zh-rCN,zh-rTW,zh-rHK,zh_rBo 按照这个顺序排列
     * @param index
     * @return
     */
    private static String getFilePathForIndex(int index) {
        String ret = "";
        switch (index) {
            case 0:
                ret = "res/values/strings.xml";
                break;
             case 1:
                 ret = "res/values-en/strings.xml";
                break;
             case 2:
                 ret = "res/values-zh-rCN/strings.xml";
                break;
             case 3:
                 ret = "res/values-zh-rTW/strings.xml";
                break;
             case 4:
                 ret = "res/values-zh-rHK/strings.xml";
                break;
             case 5:
                 ret = "res/values-bo-rCN/strings.xml";
                break;

        }
        return ret;
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
            System.out.println("Key => " + entry.getKey() + " Value => " + entry.getValue()[mBaseIndex]);
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
