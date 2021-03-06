package com.example.twinkle94.dealwithit.events.state;

import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.EventAction;

public class MissedState extends State {
    public MissedState() {
        super(StateType.MISSED, StateType.MISSED_COLOR, StateType.MISSED_IMAGE);
    }

    @Override
    public void changeState(Event event, EventAction eventAction) {
        event.setState(new WaitingState());
    }
}
