package com.jpdong.easy.punch;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PunchItem {

    private String id;
    public String title;
    public int duration;
    public long lastTime;
    public int frequency;
    public int totalDuration;

    List<Long> punchList;

    public PunchItem(String title, int duration) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.duration = duration;
    }

    public PunchItem(String id, String title, int duration, long lastTime) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.lastTime = lastTime;
    }

    public String getId() {
        return id;
    }
}
