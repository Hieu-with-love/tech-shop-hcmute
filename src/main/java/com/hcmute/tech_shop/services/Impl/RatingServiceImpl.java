package com.hcmute.tech_shop.services.Impl;

import com.hcmute.tech_shop.entities.Rating;
import com.hcmute.tech_shop.entities.composites.RatingId;
import com.hcmute.tech_shop.repositories.RatingRepository;
import com.hcmute.tech_shop.services.interfaces.IRatingService;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements IRatingService {
    private final RatingRepository ratingRepository;

    @Override
    public int countUser(Long productId) {
        return ratingRepository.findByProductId(productId).size();
    }

    @Override
    public int countRatingStar(Long productId) {
        List<Rating> ratings = ratingRepository.findByProductId(productId);
        if (ratings.isEmpty())
            return 0;
        int sum = 0;
        for (Rating rating : ratings) {
            sum += rating.getStar();
        }
        return sum / ratings.size();
    }

    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public <S extends Rating> S save(S entity) {
        return ratingRepository.save(entity);
    }

    @Override
    public Optional<Rating> findById(RatingId ratingId) {
        return ratingRepository.findById(ratingId);
    }

    @Override
    public boolean existsById(RatingId ratingId) {
        return ratingRepository.existsById(ratingId);
    }

    @Override
    public long count() {
        return ratingRepository.count();
    }

    @Override
    public void deleteById(RatingId ratingId) {
        ratingRepository.deleteById(ratingId);
    }

    @Override
    public void deleteAll() {
        ratingRepository.deleteAll();
    }
}
