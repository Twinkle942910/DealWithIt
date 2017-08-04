package com.example.twinkle94.dealwithit.events.task_types;

import com.example.twinkle94.dealwithit.events.Comment;
import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.Note;
import com.example.twinkle94.dealwithit.events.Sub_task;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;

import java.util.List;

public class ToDo extends Event
{
    //TODO: refactor this to take same code from tree classes(Birthday, TODO, Work Task).
    private Note notes;

    public ToDo(EventBuilder eventBuilder)
    {
        super(eventBuilder);
        notes = new Note();
    }

    public ToDo()
    {
        super();
        notes = new Note();
    }

    @Override
    protected void setType() {
        type = EventType.TODO;
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

    public static final class Builder extends EventBuilder<ToDo, Builder> {
        @Override
        protected ToDo getEvent() {
            return new ToDo(thisObject());
        }

        @Override
        protected Builder thisObject() {
            return this;
        }

        public Builder(String title) {
            super(title);
        }
    }

    private static class ToDoTest {
        public static void main(String... args) {
            Event event = new ToDo.Builder("Clean the room")
                    .setStartTime("17:31")
                    .setDate("23/07/2017")
                    .setId(1)
                    .setImportance(19)
                    .setEndTime("14:29")
                    .build();

            System.out.println(event.getStartTime());
            System.out.println(event.getEndTime());
            System.out.println(event.getStartDate());
            System.out.println(event.getEndDate());
            System.out.println(event.getId());
            System.out.println(event.getImportance());
            System.out.println(event.getTitle());
        }
    }

}
