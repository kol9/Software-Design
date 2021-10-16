package posts;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class PostsManager {
    private static final String METHOD = "newsfeed.search";

    private final VKClient client;
    private final PostsResponseParser parser;

    public PostsManager(VKClient client) {
        this.client = client;
        this.parser = new PostsResponseParser();
    }

    public PostsManager(VKClient client, PostsResponseParser parser) {
        this.client = client;
        this.parser = parser;
    }

    public List<Integer> getPostsNumberPerHour(int hours, String hashtag) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < hours; ++i) {
            result.add(0);
        }

        String response = client.execute(getMethod(hashtag));
        List<PostInfo> postInfoList = parser.parseResponse(response);

        LocalDateTime now = LocalDateTime.now();
        postInfoList.stream()
                .map(postInfo -> postInfo.date)
                .map(date -> {
                    Duration duration = Duration.between(
                            date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                            now
                    );
                    return Math.abs(duration.toHours());
                })
                .filter(h -> h < hours)
                .forEach(h -> result.set(h.intValue(), result.get(h.intValue()) + 1));

        return result;
    }

    private String getMethod(String hashtag) {
        VKApiRequestBuilder builder = VKApiRequestBuilder.builder();
        builder.method(METHOD);
        builder.param("q", hashtag);
        builder.param("count", 200);

        return builder.build();
    }
}
