package com.example.twinkle94.dealwithit.events.task_types;

import com.example.twinkle94.dealwithit.events.Comment;
import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.Interest;
import com.example.twinkle94.dealwithit.events.Sub_task;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;

import java.util.ArrayList;
import java.util.List;

public class WorkTask extends Event
{
    private List<Sub_task> subTasks;
    private List<Comment> comments;
    private List<Interest> interests;

    public WorkTask(int id, String title, String time_start, String time_end, String date,
                    EventType type, String state, int importance)
    {
        super(id, title, time_start, time_end, date, type, state, importance);

        subTasks = new ArrayList<>();
        comments = new ArrayList<>();
        interests = new ArrayList<>();
    }

    public void addSubTask(Sub_task sub_task)
    {
        subTasks.add(sub_task);
    }

    public void addComment(Comment comment)
    {
        comments.add(comment);
    }

    public void addInterest(Interest interest)
    {
        interests.add(interest);
    }

    private void countInterest()
    {
        int sum = 0;

        for (Interest interest: interests)
        {
            sum += interest.getValue();

            setInterests(sum / interests.size());
        }
    }
}
