import posts.PostsManager;
import posts.VKClient;

/**
 * @author Nikolay Yarlychenko
 */
public class Main {

    public static void main(String[] args) {
        VKClient client = new VKClient();
        PostsManager manager = new PostsManager(client);
        System.out.println(manager.getPostsNumberPerHour(24, "#lol"));
    }
}

