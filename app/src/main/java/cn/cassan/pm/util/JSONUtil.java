package cn.cassan.pm.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import cn.cassan.pm.api.ApiReturnResult;

/**
 * JSON工具包，解析，生成JSON字符串
 * Created by anqin on 2016/9/16.
 */
public class JSONUtil {

    public static ApiReturnResult parseJSONString(String JSONString) {
        ApiReturnResult result = new ApiReturnResult();
        try {
            // 直接把JSON字符串转化为一个JSONObject对象  
            JSONObject returnresult = new JSONObject(JSONString);
            // 第2个键值对  
            result.setMessage(returnresult.getString("message"));
            // 第3个键值对  
            result.setStatus(returnresult.getString("status"));
            // 第4个键值对  
            result.setData(returnresult.getJSONObject("data").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ApiReturnResult parseArrayString(String JSONString) {
        ApiReturnResult result = new ApiReturnResult();
        try {
            // 直接把JSON字符串转化为一个JSONObject对象  
            JSONObject returnresult = new JSONObject(JSONString);
            // 第2个键值对  
            result.setMessage(returnresult.getString("message"));
            // 第3个键值对  
            result.setStatus(returnresult.getString("status"));
            // 第4个键值对  
            result.setData(returnresult.getJSONArray("data").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ApiReturnResult parseErrorString(String JSONString) {
        ApiReturnResult result = new ApiReturnResult();
        try {
            // 直接把JSON字符串转化为一个JSONObject对象  
            JSONObject returnresult = new JSONObject(JSONString);
            // 第2个键值对  
            if (returnresult.getString("message") != null) {
                result.setMessage(returnresult.getString("message"));
            }

            // 第3个键值对  
            result.setStatus(returnresult.getString("status"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将对象序列化成JSON
     *
     * @param obj
     * @return
     */
    public static String serializeObject(Object obj) {
        if (obj == null)
            return null;
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    /**
     * 将对象数组反序列化
     */
    public static <T> List<T> deSerializeObjects(String json) {
        Type listType = new TypeToken<List<T>>() {
        }.getType();
        Gson gson = new Gson();
        List<T> list = gson.fromJson(json, listType);
        return list;
    }

    /**
     * 反序列化成一个对象
     *
     * @param json
     * @param <T>
     * @return
     */
    public static <T> T deSerializeObject(String json) {
        Type type = new TypeToken<T>() {
        }.getType();
        Gson gson = new Gson();
        T obj = gson.fromJson(json, type);
        return obj;
    }

}
