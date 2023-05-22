package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;

import java.io.IOException;

public class MediaDeserializer extends StdDeserializer<Media> {

    public MediaDeserializer() {
        this(null);
    }

    public MediaDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Media deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String type = node.get("type").asText();
        String title = node.get("title").asText();
        int releaseYear = node.get("releaseYear").asInt();
        double rating = node.get("rating").asDouble();

        try {
            if ("Movie".equals(type)) {
                int duration = node.get("duration").asInt();
                return new Movie(title, releaseYear, rating, duration);
            } else if ("TvShow".equals(type)) {
                int numberOfSeasons = node.get("numberOfSeasons").asInt();
                int numberOfEpisodes = node.get("numberOfEpisodes").asInt();
                return new TvShow(title, releaseYear, rating, numberOfSeasons, numberOfEpisodes);
            }
        } catch (InvalidTitleException | InvalidYearException | InvalidRatingException | InvalidDurationException |
                 InvalidNumberOfEpisodesException | InvalidNumberOfSeasonsException e) {
            throw new IOException("Failed to deserialize JSON to Media object: " + e.getMessage(), e);
        }

        throw new IOException("Unknown type: " + type);
    }
}
