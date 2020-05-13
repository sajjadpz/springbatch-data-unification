package com.example.springbatchdataunification.domain.internal;

import com.example.springbatchdataunification.domain.Rating;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class RatingSetFileMapper implements FieldSetMapper<Rating> {
    @Override
    public Rating mapFieldSet(FieldSet fieldSet) throws BindException {

        Rating rating = new Rating();

        rating.setUserId(fieldSet.readLong("userId"));
        rating.setMovieId(fieldSet.readLong("movieId"));
        rating.setRating(fieldSet.readInt("rating"));
        rating.setRatingTimestamp(fieldSet.readString("ratingTimestamp"));

        return rating;
    }
}
