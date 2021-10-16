package posts;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Nikolay Yarlychenko
 */
public class VKApiRequestBuilder {

    private static final String VK_API = "https://api.vk.com/";
    private final StringBuilder request;
    private final HashMap<String, Object> parameters;
    private String method;

    private VKApiRequestBuilder() {
        request = new StringBuilder(VK_API);
        parameters = new HashMap<>();
    }

    public VKApiRequestBuilder(String http) {
        request = new StringBuilder(http);
        parameters = new HashMap<>();
    }

    public static VKApiRequestBuilder builder() {
        return new VKApiRequestBuilder();
    }

    public void method(String name) {
        method = name;
    }

    public void param(String key, Object value) {
        parameters.put(key, value);
    }

    public String build() {
        request.append("method/");
        if (method == null) {
            return request.toString();
        }
        request.append(method);


        if (!parameters.isEmpty()) {
            request.append("?");
        }

        List<String> params = new ArrayList<>();

        parameters.forEach((key, value) -> {
            params.add(key + "=" + URLEncoder.encode(value.toString(), StandardCharsets.UTF_8));
        });

        request.append(String.join("&", params));

        return request.toString();
    }
}

