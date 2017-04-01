package com.example.twinkle94.dealwithit.fragments;

import com.example.twinkle94.dealwithit.events.type_enums.EventType;

public interface OnTypePickListener
{
   void onScheduleTypePick(EventType type);
   void onTaskTypePick(EventType type);
}
