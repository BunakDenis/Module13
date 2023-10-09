package global.goit.edu.module13.UserService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class UserCommentsFileWriter {

    public static void write(List<UserComment> comments, int idUser) throws IOException {

        if (!comments.isEmpty()) {

            try (Writer writer = new FileWriter("user-" + idUser + "post-" + comments.get(0).getPostId() + "-comments.json")) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(comments, writer);
            }
        }
    }
}
