package com.example.twinkle94.dealwithit.events.state;

import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.EventAction;

public class CanceledState extends State {
    public CanceledState() {
        super(StateType.CANCELED, StateType.CANCELED_COLOR, StateType.CANCELED_IMAGE);
    }

    @Override
    public void changeState(Event event, EventAction eventAction) {
        event.setState(new WaitingState());
    }
}
