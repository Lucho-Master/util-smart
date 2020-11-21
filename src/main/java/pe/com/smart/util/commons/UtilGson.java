package pe.com.smart.util.commons;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;


@Component
public class UtilGson {

    private static UtilGson utilGson;
    private static Gson gson = null;

    private UtilGson() {

    }

    public static UtilGson getInstance() {
        if(utilGson == null) {
            utilGson = new UtilGson();
            gson = new Gson();
        }
        return utilGson;
    }

    public static String objectToJson(Object obj) {
        return gson.toJson(obj);
    }
}
