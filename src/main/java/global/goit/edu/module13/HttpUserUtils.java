package global.goit.edu.module13;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import global.goit.edu.module13.User.User;
import global.goit.edu.module13.UserService.UserComment;
import global.goit.edu.module13.UserService.UserPost;
import global.goit.edu.module13.UserService.UserTask;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class HttpUserUtils {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();
    public static List<User> getUsers(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        Type type = TypeToken.getParameterized(ArrayList.class, User.class).getType();
        List<User> result = GSON.fromJson(response.body(), type);

        return result;
    }
    public static User sendPost (URI uri, User user) throws IOException, InterruptedException {
        final String requestBody = GSON.toJson(user);
        //System.out.println(requestBody);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        User result = GSON.fromJson(response.body(), User.class);

        return result;
    }
    public static User getUserById(String uri, int id) throws IOException, InterruptedException, URISyntaxException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%d", uri, id)))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return GSON.fromJson(response.body(), User.class);

    }
    public static List<User> getUserByName (String uri, String name) throws IOException, InterruptedException, URISyntaxException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s", uri, name)))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        Type type = TypeToken.getParameterized(ArrayList.class, User.class).getType();

        return GSON.fromJson(response.body(), type);
    }
    public static void deleteUserById(String uri, int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%d", uri, id)))
                .DELETE()
                .build();
        HttpResponse<String> result = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Code of deleting user = " + result.statusCode());
    }
    public static User updateUserById(String uri, User user, int id) throws IOException, InterruptedException {
        user.setId(id);
        final String requestBody = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%d", uri, id)))
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-type", "application/json")
                .build();

        HttpResponse<String> result = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Code of updating user = " + result.statusCode());

        return GSON.fromJson(result.body(), User.class);
    }

    public static List<UserComment> getUserComments (String uri, int idPost) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/comments", uri, idPost)))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        Type type = TypeToken.getParameterized(ArrayList.class, UserComment.class).getType();

        return GSON.fromJson(response.body(), type);

    }

    public static List<UserPost> getUserPosts (String uri, User user) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/posts", uri, user.getId())))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        Type type = TypeToken.getParameterized(ArrayList.class, UserPost.class).getType();

        return GSON.fromJson(response.body(), type);

    }

    public static List<UserTask> getUserTasks (String uri, User user) throws IOException, InterruptedException {
        List<UserTask> result = new ArrayList<>();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/todos", uri, user.getId())))
                .GET()
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        Type type = TypeToken.getParameterized(ArrayList.class, UserTask.class).getType();

        List<UserTask> userTasks = GSON.fromJson(response.body(), type);

        for (UserTask userTask : userTasks) {
            if (!userTask.isCompleted()) {
                result.add(userTask);
            }
        }

        return result;

    }

}
