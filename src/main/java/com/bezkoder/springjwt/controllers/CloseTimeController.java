package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.models.CloseTime;
import com.bezkoder.springjwt.service.CloseTimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/close-times")
public class CloseTimeController {
    private final CloseTimeService closeTimeService;

    public CloseTimeController(CloseTimeService closeTimeService) {
        this.closeTimeService = closeTimeService;
    }

    @PostMapping("/create")
    public ResponseEntity<CloseTime> createCloseTime(@RequestBody CloseTime closeTime) {
        CloseTime savedCloseTime = closeTimeService.saveCloseTime(closeTime);
        return ResponseEntity.ok(savedCloseTime);
    }

    @GetMapping("/{date}")
    public ResponseEntity<List<String>> getClosedTimesForDate(@PathVariable String date) {
        List<String> closedTimes = closeTimeService.getClosedTimesForDate(date);
        return ResponseEntity.ok(closedTimes);
    }

    @GetMapping("/{date}/{time}")
    public ResponseEntity<CloseTime> getClosedTime(@PathVariable String date, @PathVariable String time) {
        CloseTime closedTime = closeTimeService.getClosedTime(date, time);
        return closedTime != null ? ResponseEntity.ok(closedTime) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCloseTime(@PathVariable Long id) {
        closeTimeService.deleteCloseTime(id);
        return ResponseEntity.noContent().build();
    }
}
