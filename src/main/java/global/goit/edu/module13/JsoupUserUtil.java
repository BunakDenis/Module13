package global.goit.edu.module13;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import global.goit.edu.module13.User.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsoupUserUtil {

    private static final Gson GSON = new Gson();

    public static List<User> sendGet (String uri) throws IOException {
        String json = Jsoup.connect(uri).ignoreContentType(true).execute().body();

        Type type = TypeToken.getParameterized(ArrayList.class, User.class).getType();

        List<User> result = GSON.fromJson(json, type);

        return result;

    }

    public static void main(String[] args) throws IOException {

        String uri = "https://jsonplaceholder.typicode.com/users";

        List<User> users = JsoupUserUtil.sendGet(uri);

        for (User user : users) {
            System.out.println(user);
        }

    }

}

