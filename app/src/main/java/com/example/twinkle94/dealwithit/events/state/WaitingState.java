package com.example.twinkle94.dealwithit.events.state;

import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.EventAction;

public class WaitingState extends State {
    public WaitingState() {
        super(StateType.WAITING, StateType.WAITING_COLOR, StateType.WAITING_IMAGE);
    }

    @Override
    public void changeState(Event event, EventAction eventAction) {
        //TODO: what if none of this cases will work? (If eventAction will be different from them?)
        switch (eventAction){
            case DO:
                event.setState(new InProcessState());
                break;

            case TIME:
                event.setState(new NowState());
                break;
        }
    }
}
