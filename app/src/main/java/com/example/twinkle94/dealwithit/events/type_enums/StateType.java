package com.example.twinkle94.dealwithit.events.type_enums;

import com.example.twinkle94.dealwithit.R;

public enum StateType {
    WAITING("Waiting"),
    IN_PROCESS("In process"),
    NOW("Now"),
    FINISHED("Finished"),
    MISSED("Missed"),
    CANCELED("Canceled");

    public static final int WAITING_COLOR = R.color.waitingStateColor;
    public static final int IN_PROCESS_COLOR = R.color.processStateColor;
    public static final int NOW_COLOR = R.color.nowStateColor;
    public static final int FINISHED_COLOR = R.color.finishedStateColor;
    public static final int MISSED_COLOR = R.color.missedStateColor;
    public static final int CANCELED_COLOR = R.color.canceledStateColor;

    public static final int WAITING_IMAGE = R.drawable.ic_state_waiting;
    public static final int IN_PROCESS_IMAGE = R.drawable.ic_state_process;
    public static final int NOW_IMAGE = R.drawable.ic_state_now;
    public static final int FINISHED_IMAGE = R.drawable.ic_state_finished;
    public static final int MISSED_IMAGE = R.drawable.ic_state_missed;
    public static final int CANCELED_IMAGE = R.drawable.ic_state_canceled;

    private final String name;

    StateType(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
