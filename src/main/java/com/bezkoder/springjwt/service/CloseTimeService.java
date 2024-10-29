package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.CloseTime;

import java.util.List;

public interface CloseTimeService {
    CloseTime saveCloseTime(CloseTime closeTime);
    List<String> getClosedTimesForDate(String date);
    CloseTime getClosedTime(String date, String time);
    void deleteCloseTime(Long id);
}
