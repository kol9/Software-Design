package posts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nikolay Yarlychenko
 */
public class PostsResponseParser {

    public List<PostInfo> parseResponse(String testResponse) {
        List<PostInfo> postInfoList = new ArrayList<>();
        try {
            JSONArray posts = new JSONObject(testResponse).getJSONObject("response").getJSONArray("items");
            for (int i = 0; i < posts.length(); ++i) {
                JSONObject itemObject = posts.getJSONObject(i);
                postInfoList.add(PostInfo.parsedFrom(itemObject));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postInfoList;
    }
}
