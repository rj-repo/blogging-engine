package com.rj.bloggingengine.repository;

import com.rj.bloggingengine.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Integer> {

    boolean existsByAuthorAndPost_Id(String author, Integer id);

}
