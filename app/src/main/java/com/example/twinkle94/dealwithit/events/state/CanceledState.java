package com.example.twinkle94.dealwithit.events.state;

import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.type_enums.EventAction;
import com.example.twinkle94.dealwithit.events.type_enums.StateType;

public class CanceledState extends State {
    public CanceledState() {
        super(StateType.CANCELED, StateType.CANCELED_COLOR, StateType.CANCELED_IMAGE);
    }

    @Override
    public void changeState(Event event, EventAction eventAction) {

    }
}
