package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class MediaSerializer extends StdSerializer<Media> {

    public MediaSerializer() {
        this(null);
    }

    public MediaSerializer(Class<Media> t) {
        super(t);
    }

    @Override
    public void serialize(Media value, JsonGenerator gen, SerializerProvider arg2) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("type", value.getClass().getSimpleName());
        gen.writeStringField("title", value.getTitle());
        gen.writeNumberField("releaseYear", value.getReleaseYear());
        gen.writeNumberField("rating", value.getRating());

        if (value instanceof Movie) {
            gen.writeNumberField("duration", ((Movie) value).getDuration());
        } else if (value instanceof TvShow) {
            gen.writeNumberField("numberOfSeasons", ((TvShow) value).getNumberOfSeasons());
            gen.writeNumberField("numberOfEpisodes", ((TvShow) value).getNumberOfEpisodes());
        }

        gen.writeEndObject();
    }
}
