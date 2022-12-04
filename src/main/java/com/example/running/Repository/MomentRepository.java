package com.example.running.Repository;

import com.example.running.Bean.Moment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository(value = "MomentRepository")
public interface MomentRepository extends JpaRepository<Moment, Integer> {
    Page<Moment> findByUserId(Integer userId, Pageable pageable);

    Page<Moment> findByUserIdIn(Collection<Integer> userId, Pageable pageable);
}
