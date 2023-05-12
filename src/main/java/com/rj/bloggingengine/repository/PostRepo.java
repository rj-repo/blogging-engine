package com.rj.bloggingengine.repository;

import com.rj.bloggingengine.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

    @Query(value = "SELECT AVG(review.rating) FROM review GROUP BY review.post_id", nativeQuery = true)
    Double averageRating();

}
