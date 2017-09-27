package TestSax;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Stack;

/**
 * Project: Day11_SAXXml
 * Created: Lulu
 * Date: 2016/8/9
 */
public class MyHandler extends DefaultHandler {
    private Note note;
    //定义一个栈
    private Stack<String> nameStack;
    public MyHandler(Note note) {
        this.note = note;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        //初始化实体类
        nameStack = new Stack<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        nameStack.push(qName);
//        System.out.println(qName);
        switch (qName){
            case "note":
                note.setLevel(Integer.parseInt(attributes.getValue("level")));
                break;
            case "heading":
                note.setHeading(new Note.Heading());
                break;
            case "body":
                note.setBody(new Note.Body());
                break;
        }
    }
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String s = new String(ch, start, length).trim();
        if (!s.isEmpty()) {
//            System.out.println(s);
            switch (nameStack.peek()) {
                case "to" :
                    note.getTo().add(s);
                    break;
                case "from" :
                    note.setFrom(s);
                    break;
                case "font" :
                    switch (nameStack.get(nameStack.size() - 2)) {
                        case "heading":
                            note.getHeading().setFont(s);
                            break;
                        case "body":
                            note.getBody().setFont(s);
                            break;
                    }
                    break;
                case "title" :
                    note.getHeading().setTitle(s);
                    break;
                case "message" :
                    note.getBody().setMessage(s);
                    break;



            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        nameStack.pop();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
}