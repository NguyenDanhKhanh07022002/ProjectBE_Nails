package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.CloseTime;
import com.bezkoder.springjwt.repository.CloseTimeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CloseTimeServiceImpl implements CloseTimeService {

    private final CloseTimeRepository closeTimeRepository;

    public CloseTimeServiceImpl(CloseTimeRepository closeTimeRepository) {
        this.closeTimeRepository = closeTimeRepository;
    }

    @Override
    public CloseTime saveCloseTime(CloseTime closeTime) {

//        LocalDate yesterday = LocalDate.now().minusDays(1);
//        String yesterdayStr = yesterday.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//        closeTimeRepository.deleteByDate(LocalDate.parse(yesterdayStr));

        List<CloseTime> existingCloseTimes = closeTimeRepository.findByDate(closeTime.getDate());
        List<String> times = closeTime.getTime();

        if (!existingCloseTimes.isEmpty()) {
            for (CloseTime existingCloseTime : existingCloseTimes) {
                List<String> timesdb = existingCloseTime.getTime();
                timesdb.removeIf(time -> !times.contains(time));
                for (String time : times) {
                    if (!timesdb.contains(time)) {
                        timesdb.add(time);
                    }
                }
                existingCloseTime.setTime(timesdb);
                closeTimeRepository.save(existingCloseTime);
            }
            return existingCloseTimes.get(existingCloseTimes.size() - 1);
        } else {
            return closeTimeRepository.save(closeTime);
        }
    }

    @Override
    public List<String> getClosedTimesForDate(String date) {
        List<CloseTime> closeTimes = closeTimeRepository.findByDate(date);
        List<String> closeTimeList = new ArrayList<>();
        for (CloseTime closeTime : closeTimes) {
            closeTimeList.addAll(closeTime.getTime());
        }
        return closeTimeList;
    }

    @Override
    public CloseTime getClosedTime(String date, String time) {
        return closeTimeRepository.findByDateAndTime(date, time);
    }

    @Override
    public void deleteCloseTime(Long id) {
        closeTimeRepository.deleteById(id);
    }
}
