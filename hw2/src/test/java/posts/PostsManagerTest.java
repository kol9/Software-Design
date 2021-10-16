package posts;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Nikolay Yarlychenko
 */
public class PostsManagerTest {
    private PostsManager postsManager;

    @Mock
    private VKClient client;

    @Mock
    private PostsResponseParser parser;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        postsManager = new PostsManager(client, parser);
    }

    @Test
    public void testPostsOnePerHour() {
        when(client.execute(any(String.class))).thenReturn("Some response");
        when(parser.parseResponse(any(String.class))).thenReturn(getPostsOneByHourOfSizeFromDateTime(LocalDateTime.now(), 10));
        List<Integer> result = postsManager.getPostsNumberPerHour(10, "#test");
        Assert.assertEquals(result, List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
    }

    @Test
    public void testPostsCountInOneHour() {
        when(client.execute(any(String.class))).thenReturn("Some response");
        when(parser.parseResponse(any(String.class))).thenReturn(getPostsInOneHourOfSizeFromDateTime(LocalDateTime.now(), 20));
        List<Integer> result = postsManager.getPostsNumberPerHour(3, "#test");
        Assert.assertEquals(result, List.of(20, 0, 0));
    }

    @Test
    public void testPostsNotInWindow() {
        LocalDateTime fourHoursBeforeNow = LocalDateTime.now().minusHours(4);

        when(client.execute(any(String.class))).thenReturn("Some response");
        when(parser.parseResponse(any(String.class))).thenReturn(getPostsOneByHourOfSizeFromDateTime(fourHoursBeforeNow, 5));

        List<Integer> result = postsManager.getPostsNumberPerHour(3, "#test");
        Assert.assertEquals(result, List.of(0, 0, 0));
    }

    // Helpers

    private List<PostInfo> getPostsOneByHourOfSizeFromDateTime(LocalDateTime dateTime, int size) {
        List<PostInfo> posts = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            long timestamp = timestampByHoursDiffFromDateTime(dateTime, i);
            posts.add(new PostInfo(i, timestamp, "#test 123"));
        }

        return posts;
    }

    private List<PostInfo> getPostsInOneHourOfSizeFromDateTime(LocalDateTime dateTime, int size) {
        List<PostInfo> posts = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            long timestamp = timestampByMinutesDiffInDateTime(dateTime,i);
            posts.add(new PostInfo(i, timestamp, "#test 123"));
        }

        return posts;
    }

    private long timestampByHoursDiffFromDateTime(LocalDateTime dateTime, long hoursDiff) {
        LocalDateTime newTime = dateTime.minusHours(hoursDiff);
        return Timestamp.valueOf(newTime).getTime() / 1000;
    }

    private long timestampByMinutesDiffInDateTime(LocalDateTime dateTime, long minutesDiff) {
        LocalDateTime newTime = dateTime.minusMinutes(minutesDiff);
        return Timestamp.valueOf(newTime).getTime() / 1000;
    }
}