import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Map;
import java.util.Stack;

public class SaxHandler extends DefaultHandler {

    private Map<String, String[]> mStringsMap;
    private Stack<String> mNameStack;
    private int mIndex;
    private String temKey = "";
    private StringBuffer temValue;


    public SaxHandler(Map<String, String[]> map, int index) {
        mStringsMap = map;
        mIndex = index;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        mNameStack = new Stack<>();
    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        temValue = new StringBuffer();
        mNameStack.push(qName);
        switch (qName) {
            case "string" :
                temKey = attributes.getValue("name");
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
       temValue.append(ch, start, length);

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (mNameStack.pop().equals("string")) {

            String[] values = mStringsMap.get(temKey);
            if (values == null) {
                mStringsMap.put(temKey, createStringValue(temKey, temValue.toString().trim(), mIndex ));
            } else {
                values[mIndex] = temValue.toString().trim();
                mStringsMap.remove(temKey);
                mStringsMap.put(temKey, values);
            }
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }


    /**
     *
     * @param key
     * @param value
     * @param index
     * @return
     */
    private static String[] createStringValue(String key, String value, int index) {
        String[] values = null;
        switch (index) {
            case 0:
                values = new String[]{value, null, null, null, null, null};
                break;
            case 1:
                values = new String[]{null, value, null, null, null, null};
                break;
            case 2:
                values = new String[]{null, null, value, null, null, null};
                break;
            case 3:
                values = new String[]{null, null, null, value, null, null};
                break;
            case 4:
                values = new String[]{null, null, null, null, value, null};
                break;
            case 5:
                values = new String[]{null, null, null, null, null, value};
                break;
        }
       return values;
    }


}
