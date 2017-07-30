package com.example.twinkle94.dealwithit.events.state;

import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.type_enums.EventAction;
import com.example.twinkle94.dealwithit.events.type_enums.StateType;

public class NowState extends State {
    public NowState() {
        super(StateType.NOW, StateType.NOW_COLOR, StateType.NOW_IMAGE);
    }

    @Override
    public void changeState(Event event, EventAction eventAction) {

    }
}
