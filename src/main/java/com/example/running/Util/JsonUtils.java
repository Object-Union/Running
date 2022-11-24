package com.example.running.Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    // {"senderId":1,"recipientId":2,"content":"确实"}
    public static ObjectMapper mapper = new ObjectMapper();

    public static <T> T ReadToObject(String json, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(json, clazz);
    }

//    public static String ReadToJSON(Object obj) throws JsonProcessingException {
//        return mapper.writeValueAsString(obj);
//    }
}
