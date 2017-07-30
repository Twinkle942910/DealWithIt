package com.example.twinkle94.dealwithit.events.state;

import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.type_enums.EventAction;
import com.example.twinkle94.dealwithit.events.type_enums.StateType;

public abstract class State {
    private StateType mType;
    private int mColor;
    private int mImage;

    public State(StateType mType, int mColor, int mImage) {
        this.mType = mType;
        this.mColor = mColor;
        this.mImage = mImage;
    }

    public StateType getType() {
        return mType;
    }

    public int getColor() {
        return mColor;
    }

    public int getImage() {
        return mImage;
    }

    @Override
    public String toString() {
        return mType.toString();
    }

    public abstract void changeState(Event event, EventAction eventAction);
}
