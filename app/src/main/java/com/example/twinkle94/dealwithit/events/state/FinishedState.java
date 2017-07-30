package com.example.twinkle94.dealwithit.events.state;

import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.type_enums.EventAction;
import com.example.twinkle94.dealwithit.events.type_enums.StateType;

/**
 * Created by Twinkle on 6/27/2017.
 */

public class FinishedState extends State {
    public FinishedState() {
        super(StateType.FINISHED, StateType.FINISHED_COLOR, StateType.FINISHED_IMAGE);
    }

    @Override
    public void changeState(Event event, EventAction eventAction) {

    }
}
