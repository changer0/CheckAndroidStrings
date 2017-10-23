package TestSax;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * SAX只能解析不能序列化
 *
 * 1. 基本解析
 * 2. 带嵌套的解析
 * 3. 属性名重名解析
 * 4.
 *
 * Project: Day11_SAXXml
 * Created: Lulu
 * Date: 2016/8/9
 */
public class Test {
//    public static void main(String[] args) {
//        try {
//            //1. 生成SAX工厂类
//            SAXParserFactory factory = SAXParserFactory.newInstance();
//            //2. 通过工厂类生成SAXParser对象
//            SAXParser parser = factory.newSAXParser();
//
//            Note note = new Note();
//            parser.parse(new FileInputStream("test.xml"), new MyHandler(note));
//
//            System.out.println(note.toString());
//            System.out.println(note.getLevel());
//
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }


    public static void main(String[] args) {
        System.out.println("result => " + func("Tencent"));
        System.out.println("Test => " + "Tencent".substring(1));
    }
    public static String func(String s) {
        return s.length() > 0 ? func(s.substring(1)) + s.charAt(0) : "";
    }
}
