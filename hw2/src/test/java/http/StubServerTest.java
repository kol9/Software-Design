package http;

import com.xebialabs.restito.semantics.Action;
import com.xebialabs.restito.server.StubServer;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import posts.PostInfo;
import posts.VKApiRequestBuilder;
import posts.VKClient;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.contentType;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.startsWithUri;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StubServerTest {

    StubServer stubServer;

    @BeforeEach
    public void start() {
        stubServer = new StubServer().run();
    }

    @Test
    public void testClientWithStubServer() {
        PostInfo expected = new PostInfo(1, 1634400744, "Test Post");

        whenHttp(stubServer).
                match(startsWithUri("/method")).
                then(jsonContent(expected), contentType("application/json"));

        VKClient client = new VKClient();
        VKApiRequestBuilder builder = new VKApiRequestBuilder(baseUrl(stubServer));

        String response = client.rawExecute(builder.build());
        PostInfo actual = PostInfo.parsedFrom(new JSONObject(response));

        assertEquals(actual, expected);
    }

    private String baseUrl(StubServer server) {
        return format("http://localhost:%d/", server.getPort());
    }

    private Action jsonContent(PostInfo obj) {
        return stringContent(obj.toJsonString());
    }
}
