package posts;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author Nikolay Yarlychenko
 */

public class PostsResponseParserTest {

    private final static String testResponse = "{\n" +
            "  \"response\": {\n" +
            "    \"items\": [\n" +
            "      {\n" +
            "        \"id\": 1873,\n" +
            "        \"date\": 1633872509,\n" +
            "        \"text\": \"Приветик, Котаны. #lol\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 5845,\n" +
            "        \"date\": 1633872180,\n" +
            "        \"text\": \"\uD83D\uDE0F\uD83D\uDE0F#leagueoflegends #riot #lolmeme #lol #meme #yasuo\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"id\": 7843,\n" +
            "        \"date\": 1633871264,\n" +
            "        \"text\": \"Работа мечты #прикол #lol #лол #мем #xaxaxa #баян #топ\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";


    @Test
    public void parseResponse() {
        PostsResponseParser parser = new PostsResponseParser();
        List<PostInfo> info = parser.parseResponse(testResponse);
        Assert.assertEquals(info.size(), 3);

        Assert.assertEquals(info.get(0), new PostInfo(1873, 1633872509, "Приветик, Котаны. #lol"));
        Assert.assertEquals(info.get(1), new PostInfo(5845, 1633872180, "\uD83D\uDE0F\uD83D\uDE0F#leagueoflegends #riot #lolmeme #lol #meme #yasuo"));
        Assert.assertEquals(info.get(2), new PostInfo(7843, 1633871264, "Работа мечты #прикол #lol #лол #мем #xaxaxa #баян #топ"));
    }
}
