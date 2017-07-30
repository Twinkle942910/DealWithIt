package com.example.twinkle94.dealwithit.events.state;

import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.type_enums.EventAction;
import com.example.twinkle94.dealwithit.events.type_enums.StateType;

public class WaitingState extends State {
    public WaitingState() {
        super(StateType.WAITING, StateType.WAITING_COLOR, StateType.WAITING_IMAGE);
    }

    @Override
    public void changeState(Event event, EventAction eventAction) {
        //TODO: implement time checking.
        if(event.getStartTime() == "Current time") event.setState(new NowState());
        else event.setState(new InProcessState());
    }
}
