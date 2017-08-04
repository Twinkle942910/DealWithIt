package com.example.twinkle94.dealwithit.events;

import java.util.ArrayList;
import java.util.List;

public class Note {
    private List<Sub_task> subTasks;
    private List<Comment> comments;

    public Note() {
        this.subTasks = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void addSubtask(Sub_task sub_task){
        subTasks.add(sub_task);
    }

    public void removeComment(Comment comment){
        comments.remove(comment);
    }

    public void removeSubtask(Sub_task sub_task){
        subTasks.remove(sub_task);
    }

    public int commentsCount(){
        return comments.size();
    }

    public int subtasksCount(){
        return subTasks.size();
    }

    public int doneSubtasksCount(){
        int doneCounter = 0;

        for (Sub_task sub_task : subTasks){
            if(sub_task.isChecked()) doneCounter++;
        }

        return doneCounter;
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
