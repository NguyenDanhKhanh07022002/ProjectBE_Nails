package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.CloseTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CloseTimeRepository extends JpaRepository<CloseTime, Long> {
    List<CloseTime> findByDate(String date);
    CloseTime findByDateAndTime(String date, String time);

    void deleteByDate(@Param("date") LocalDate date);
}
