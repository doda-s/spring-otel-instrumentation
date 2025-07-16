package me.phgs.application.util;

import java.util.concurrent.atomic.AtomicLong;

public class Generator {
    private static final AtomicLong atLon = new AtomicLong();

    public static long generateId() {
        return atLon.incrementAndGet();
    }
}