package edu.miracosta.cs112.moviedatabaseproject_v3.model;

import edu.miracosta.cs112.moviedatabaseproject_v3.exceptions.*;

public class MediaTestStub extends Media {

    public MediaTestStub() throws InvalidTitleException, InvalidYearException, InvalidRatingException {
        super();
    }

    public MediaTestStub(String title, int releaseYear, double rating) throws InvalidTitleException, InvalidYearException, InvalidRatingException {
        super(title, releaseYear, rating);
    }

    public MediaTestStub(MediaTestStub mediaTestStub) {
        super(mediaTestStub);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        MediaTestStub media = (MediaTestStub) obj;

        if (releaseYear.get() != media.releaseYear.get()) {
            return false;
        }

        if (Double.compare(media.rating.get(), rating.get()) != 0) {
            return false;
        }

        return title.get() != null ? title.get().equals(media.title.get()) : media.title.get() == null;
    }

}
