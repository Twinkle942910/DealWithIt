package com.example.twinkle94.dealwithit.events;

import com.example.twinkle94.dealwithit.events.notes.Comment;
import com.example.twinkle94.dealwithit.events.notes.Note;
import com.example.twinkle94.dealwithit.events.notes.Sub_task;

import java.util.List;

public abstract class ComplexEvent extends Event {
    private Note notes;

    public ComplexEvent() {
        notes = new Note();
    }

    public ComplexEvent(EventBuilder eventBuilder) {
        super(eventBuilder);
        notes = new Note();
    }

    public void addComment(Comment comment){
        notes.addComment(comment);
    }

    public void addSubtask(Sub_task sub_task){
        notes.addSubtask(sub_task);
    }

    public void removeComment(Comment comment){
        notes.removeComment(comment);
    }

    public void removeSubtask(Sub_task sub_task){
        notes.removeSubtask(sub_task);
    }

    public int commentsCount(){
        return notes.commentsCount();
    }

    public int subtasksCount(){
        return notes.subtasksCount();
    }

    public int doneSubtasksCount(){
        return notes.doneSubtasksCount();
    }

    public List<Sub_task> getListSubTasks()
    {
        return notes.getListSubTasks();
    }

    public List<Comment> getListComments()
    {
        return notes.getListComments();
    }

    public void setListComments(List<Comment> comments) {
        this.notes.setListComments(comments);
    }

    public void setListSubTasks(List<Sub_task> subTasks) {
        this.notes.setListSubTasks(subTasks);
    }
}
