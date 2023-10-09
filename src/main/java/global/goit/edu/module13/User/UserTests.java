package global.goit.edu.module13.User;

import global.goit.edu.module13.HttpUserUtils;
import global.goit.edu.module13.UserService.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class UserTests {

    private static final String GET_USER_BY_ID = "https://jsonplaceholder.typicode.com/users/";
    private static final String GET_USER_BY_NAME = "https://jsonplaceholder.typicode.com/users/?username=";
    private static final String DELETE_USER_URI = "https://jsonplaceholder.typicode.com/users/";
    private static final String UPDATE_USER_URI = "https://jsonplaceholder.typicode.com/users/";
    private static final String POST_USER_URI = "https://jsonplaceholder.typicode.com/users/";
    private static final String COMMENT_USER_URI = "https://jsonplaceholder.typicode.com/posts/";
    private static final String TODOS_USER_URI = "https://jsonplaceholder.typicode.com/users/";

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {

        URI uri = new URI("https://jsonplaceholder.typicode.com/users");
        GoePosition geo = new GoePosition(1.55, 2.11);
        Address address = new Address("Vozdvizhenskay", "Apt.6", "Chernigiv", "16000", geo);
        Company company = new Company("FOP", "Survear", "Hello World");
        User userDenis = new User(1, "Denis", "xisi926@gmail.com", address, "0664988869", "goit.com", company);

        //Tas1
        //створення нового об'єкта
        User createdUser = HttpUserUtils.sendPost(uri, userDenis);
        System.out.println("Створення нового об'єкта - ");
        System.out.println(createdUser);
        System.out.println("------------------------------------------------------------------");

        //оновлення об'єкту
        User updatedUser = HttpUserUtils.updateUserById(UPDATE_USER_URI, userDenis, 8);
        System.out.println("Оновлення об'єкту");
        System.out.println(updatedUser);
        System.out.println("------------------------------------------------------------------");

        //видалення об'єкта
        System.out.println("Видалення юзера");
        HttpUserUtils.deleteUserById(DELETE_USER_URI, 7);
        System.out.println("------------------------------------------------------------------");

        //отримання інформації про всіх користувачів
        System.out.println("Отримання інформації про всіх користувачів");
        List<User> users = HttpUserUtils.getUsers(uri);
        for (User user : users) {
            System.out.println(user);
        }
        System.out.println("------------------------------------------------------------------");

        //отримання інформації про користувача за id
        User userOfId = HttpUserUtils.getUserById(GET_USER_BY_ID, 7);
        System.out.println("Отримання інформації про користувача за id");
        System.out.println(userOfId);
        System.out.println("------------------------------------------------------------------");

        //отримання інформації про користувача за username
        List<User> listSearchingByName = HttpUserUtils.getUserByName(GET_USER_BY_NAME, "Antonette");
        System.out.println("Отримання інформації про користувача за username");
        for (User user : listSearchingByName) {
            System.out.println(user);
        }
        System.out.println("------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------");


        //Task 2
        //1) С помощью get запроса получаем коллекцию постов юзера
        //2) С помощью get запроса получаем коллекцию коментов последнего поста
        //3) Записать в файл коментарии

        List<UserPost> user7Posts = HttpUserUtils.getUserPosts(POST_USER_URI, userOfId);
        System.out.println("Task 2");
        System.out.println("Коментарі користувача " + userOfId.getUsername());
        int maxIdPost = 0;
        for (UserPost user7Post : user7Posts) {
            System.out.println(user7Post);

            if (maxIdPost < user7Post.getId()) {
                maxIdPost = user7Post.getId();
            }
        }
        System.out.println("------------------------------------------------------------------");

        List<UserComment> user7Comments = HttpUserUtils.getUserComments(COMMENT_USER_URI, maxIdPost);

        for (UserComment user7Comment : user7Comments) {
            System.out.println(user7Comment);
        }
        UserCommentsFileWriter.write(user7Comments, 7);
        System.out.println("------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------");

        //TASK 3
        System.out.println("Task 3");
        List<UserTask> user7Tasks = HttpUserUtils.getUserTasks(TODOS_USER_URI, userOfId);

        for (UserTask user7Task : user7Tasks) {
            System.out.println(user7Task);
        }

    }
}
