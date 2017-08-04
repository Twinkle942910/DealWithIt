package com.example.twinkle94.dealwithit.events.state;

import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.EventAction;

public class InProcessState extends State {
    public InProcessState() {
        super(StateType.IN_PROCESS, StateType.IN_PROCESS_COLOR, StateType.IN_PROCESS_IMAGE);
    }

    @Override
    public void changeState(Event event, EventAction eventAction) {
        switch (eventAction){
            case FINISH:
                event.setState(new FinishedState());
                break;

            case CANCEL:
                event.setState(new CanceledState());
                break;

            case TIME:
                event.setState(new FinishedState());
                break;
        }
    }
}
