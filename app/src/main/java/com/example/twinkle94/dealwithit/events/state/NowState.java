package com.example.twinkle94.dealwithit.events.state;

import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.EventAction;

public class NowState extends State {
    public NowState() {
        super(StateType.NOW, StateType.NOW_COLOR, StateType.NOW_IMAGE);
    }

    @Override
    public void changeState(Event event, EventAction eventAction) {
        switch (eventAction){
            case DO:
                event.setState(new InProcessState());
                break;

            case CANCEL:
                event.setState(new CanceledState());
                break;

            case TIME:
                //Time passed.
                event.setState(new MissedState());
                break;
        }
    }
}
