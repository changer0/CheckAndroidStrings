package TestSax;

import java.util.ArrayList;
import java.util.List;

public class Note {
    private int level;
    private Heading heading;
    private Body body;
    private List<String> to = new ArrayList<>();
    private String from;

    @Override
    public String toString() {
        return "Note{" +
                "level=" + level +
                ", heading=" + heading +
                ", body=" + body +
                ", to=" + to +
                ", from='" + from + '\'' +
                '}';
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }
    static class Heading {
        @Override
        public String toString() {
            return "Heading{" +
                    "font='" + font + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }

        private String font;
        private String title;

        public String getFont() {
            return font;
        }

        public void setFont(String font) {
            this.font = font;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
    static class Body {
        @Override
        public String toString() {
            return "Body{" +
                    "font='" + font + '\'' +
                    ", title='" + title + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }

        private String font;
        private String title;
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getFont() {
            return font;
        }

        public void setFont(String font) {
            this.font = font;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
