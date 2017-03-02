package com.example.twinkle94.dealwithit.events.task_types;

import com.example.twinkle94.dealwithit.events.Comment;
import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.Sub_task;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;

import java.util.ArrayList;
import java.util.List;

public class ToDo extends Event
{
    private List<Sub_task> subTasks;
    private List<Comment> comments;

    public ToDo(int id, String title, String time_start, String time_end, String date,
                EventType type, String state, int importance)
    {
        super(id, title, time_start, time_end, date, type, state, importance);

        subTasks = new ArrayList<>();
        comments = new ArrayList<>();
    }

    public List<Sub_task> getListSubTasks()
    {
        return subTasks;
    }

    public List<Comment> getListComments()
    {
        return comments;
    }

    public void setListComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setListSubTasks(List<Sub_task> subTasks) {
        this.subTasks = subTasks;
    }
}
