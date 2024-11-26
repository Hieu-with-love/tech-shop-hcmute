package com.hcmute.tech_shop.repositories;

import com.hcmute.tech_shop.entities.Rating;
import com.hcmute.tech_shop.entities.composites.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingId> {
    List<Rating> findByProductId(Long productId);
}
