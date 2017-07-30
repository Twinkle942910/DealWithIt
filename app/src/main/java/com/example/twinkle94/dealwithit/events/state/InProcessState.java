package com.example.twinkle94.dealwithit.events.state;

import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.type_enums.EventAction;
import com.example.twinkle94.dealwithit.events.type_enums.StateType;

public class InProcessState extends State {
    public InProcessState() {
        super(StateType.IN_PROCESS, StateType.IN_PROCESS_COLOR, StateType.IN_PROCESS_IMAGE);
    }

    @Override
    public void changeState(Event event, EventAction eventAction) {

    }
}
