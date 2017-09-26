import javax.swing.plaf.TextUI;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

public class CheckStrings {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("text.xml"));
            StringBuilder sb = new StringBuilder();
            String tempStr = "";
            while ((tempStr = br.readLine()) != null) {
                sb.append(tempStr + "\n");
            }
            System.out.println(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("这是一个测试");
    }
}
