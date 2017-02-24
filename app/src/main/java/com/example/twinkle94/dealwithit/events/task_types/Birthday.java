package com.example.twinkle94.dealwithit.events.task_types;

import com.example.twinkle94.dealwithit.events.Comment;
import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.Interest;
import com.example.twinkle94.dealwithit.events.Location;
import com.example.twinkle94.dealwithit.events.Sub_task;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;

import java.util.ArrayList;
import java.util.List;

public class Birthday extends Event
{
    private Location location;

    private List<Sub_task> subTasks;
    private List<Comment> comments;

    public Birthday(int id, String title, String time_start, String time_end, String date,
                    EventType type, String state, int importance, Location location)
    {
        super(id, title, time_start, time_end, date, type, state, importance);

        this.location = location;

        subTasks = new ArrayList<>();
        comments = new ArrayList<>();
    }

    public void addSubTask(Sub_task sub_task)
    {
        subTasks.add(sub_task);
    }

    public void addComment(Comment comment)
    {
        comments.add(comment);
    }
}
