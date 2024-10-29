package com.bezkoder.springjwt.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "close_time")
public class CloseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;

    @ElementCollection
    @CollectionTable(name = "close_time_slots", joinColumns = @JoinColumn(name = "close_time_id"))
    @Column(name = "time")
    private List<String> time;

    private boolean isClosed;

    public CloseTime() {
    }

    public CloseTime(Long id, String date, List<String> time, boolean isClosed) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.isClosed = isClosed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }
}
