package com.example.springbatchdataunification.domain.internal;

import com.example.springbatchdataunification.domain.Rating;
import com.example.springbatchdataunification.domain.RatingDao;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class RatingItemWriter implements ItemWriter<Rating> {
    private RatingDao ratingDao;

    @Override
    public void write(List<? extends Rating> ratings) throws Exception {
        for (Rating rating : ratings) {
            ratingDao.saveRating(rating);
        }
    }

    public RatingItemWriter setRatingDao(RatingDao ratingDao) {
        this.ratingDao = ratingDao;
        return this;
    }
}
