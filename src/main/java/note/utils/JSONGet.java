package note.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 适用于Controller中获取前端数据
 */
public class JSONGet {

    /**
     * 将前端的RequestBody数据中的data中的类数据提取成实例
     * @param jsonString    RequestBody或Results字符串
     * @param key   data中的对象key
     * @param clazz 所需的对象类
     * @param <T>   对象类
     * @return  从data中获取的对象实例，若key错误或不存在对应key对象则返回null
     */
    public static <T> T getValue(String jsonString, String key, Class<T> clazz) {
        return JSON.parseObject(jsonString).getObject(key, clazz);
    }

    public static <T> T getValue(String jsonString, String key) {
        return (T) JSON.parseObject(jsonString).get(key);
    }

    public static <T> ArrayList<T> getList(String jsonString, String key, Class<T> cl) {
        JSONArray array = JSON.parseObject(jsonString).getJSONArray(key);
        ArrayList<T> list = new ArrayList<>();
        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                list.add(array.getObject(i, cl));
            }
        }
        return list;
    }

    /**
     * 将前端的RequestBody数据中的data中的类数据提取成实例
     * @param jsonString    RequestBody或Results字符串
     * @param clazz 所需的对象类
     * @param <T>   对象类
     * @return  从data中获取的对象实例，若key错误或不存在对应key对象则返回null
     */
    public static <T> T getValue(String jsonString, Class<T> clazz) {
        return JSON.parseObject(jsonString).getObject(clazz.getSimpleName().toLowerCase(), clazz);
    }

    /**
     * 从前端的RequestBody数据中的data中的类数据提取成实例
     * @param jsonString    RequestBody或Results字符串
     * @param key   data中的字符串key
     * @return  data中的对应key字符串
     */
    public static String getString(String jsonString, String key) {
        return JSON.parseObject(jsonString).getString(key);
    }

}
