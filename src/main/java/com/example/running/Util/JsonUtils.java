package com.example.running.Util;

import com.example.running.Bean.Chat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    public static ObjectMapper mapper = new ObjectMapper();

    public static <T> T ReadToObject(String json, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(json, clazz);
    }

    public static String ReadToJSON(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    public static void main(String[] args) throws JsonProcessingException {
        Chat chat = new Chat(null, null, 1, 2, "Hello World");
        String s = JsonUtils.ReadToJSON(chat);
        // {"senderId":1,"recipientId":2,"content":"确实"}
        System.out.println(s);
        System.out.println(ReadToObject(s, Chat.class));
    }
}
