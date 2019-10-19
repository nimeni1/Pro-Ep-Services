package driver.service.serializer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import driver.service.models.Driver;
import driver.service.models.Fare;

import java.util.List;
import java.util.Map;

public class JsonSerializer {

    private static Gson gson = new Gson();

    public static Map<String,Object> jsonToMap(String json) {
        return gson.fromJson(json,new TypeToken<Map<String,Object>>(){}.getType());
    }

    public static String mapToJson(Map<String,Object> map) {
        return gson.toJson(map);
    }

    public static String arrayListToJson(List list) {
        return gson.toJson(list);
    }

    public static Fare toFare(String fareJson) {
        return gson.fromJson(fareJson,Fare.class);
    }
    public static Driver driverFromJson(String json) {return gson.fromJson(json, Driver.class);}
}
