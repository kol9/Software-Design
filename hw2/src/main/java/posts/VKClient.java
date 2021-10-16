package posts;

import http.UrlReader;

/**
 * @author Nikolay Yarlychenko
 */
public class VKClient {
    private static final String VK_API_VERSION = "5.131";
    private static final String ACCESS_TOKEN = "1d7104301d7104301d710430b51d02e11711d711d710430422f365b468a330690f7e47e";
    private final UrlReader reader = new UrlReader();

    public String execute(String req) {
        String request = req + "&v=" + VK_API_VERSION + "&access_token=" + ACCESS_TOKEN;
        return reader.readAsText(request);
    }

    public String rawExecute(String req) {
        return reader.readAsText(req);
    }
}


