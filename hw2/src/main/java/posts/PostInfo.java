package posts;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Objects;

public class PostInfo {
    private static final String ID_KEY = "id";
    private static final String DATE_KEY = "date";
    private static final String TEXT_KEY = "text";

    public Date date;
    public Integer id;
    public String text;

    public PostInfo(int id, long date, String text) {
        this.id = id;
        this.date = new Date(date * 1000);
        this.text = text;
    }

    public static PostInfo parsedFrom(JSONObject obj) throws JSONException {
        int id = obj.getInt(ID_KEY);
        long date = obj.getLong(DATE_KEY);
        String text = obj.getString(TEXT_KEY);

        return new PostInfo(id, date, text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostInfo postInfo = (PostInfo) o;
        return date.equals(postInfo.date) && id.equals(postInfo.id) && text.equals(postInfo.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, id, text);
    }

    public String toJsonString() {
        return "{" +
                "date:" + date.getTime()/1000 +
                ", id:" + id +
                ", text:\"" + text + '\"' +
                '}';
    }
}
