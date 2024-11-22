package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.Rating;
import com.hcmute.tech_shop.entities.composites.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, RatingId> {
    List<Rating> findByProductId(Long productId);
}
