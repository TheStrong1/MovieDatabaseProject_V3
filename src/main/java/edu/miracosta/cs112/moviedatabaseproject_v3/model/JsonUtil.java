package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    // Configure ObjectMapper with custom serialization/deserialization settings
    static {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Media.class, new MediaDeserializer());
        module.addSerializer(Media.class, new MediaSerializer());
        mapper.registerModule(module);
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // for pretty-printing
    }

    public static List<Media> readMediaFromJson(String filePath) throws IOException {
        byte[] jsonData = Files.readAllBytes(Paths.get(filePath));
        return mapper.readValue(jsonData, new TypeReference<List<Media>>() {});
    }

    public static void writeMediaToJson(List<Media> mediaList, String filePath) throws IOException {
        mapper.writeValue(Paths.get(filePath).toFile(), mediaList);
    }
}
