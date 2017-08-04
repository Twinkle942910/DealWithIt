package com.example.twinkle94.dealwithit.events.event_types;

import com.example.twinkle94.dealwithit.events.notes.Comment;
import com.example.twinkle94.dealwithit.events.ComplexEvent;

public class ToDo extends ComplexEvent
{
    public ToDo(EventBuilder eventBuilder)
    {
        super(eventBuilder);
    }

    public ToDo()
    {
        super();
    }

    @Override
    protected void setType() {
        type = EventType.TODO;
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
            ComplexEvent event = new ToDo.Builder("Clean the room")
                    .setStartTime("17:31")
                    .setDate("23/07/2017")
                    .setId(1)
                    .setImportance(19)
                    .setEndTime("14:29")
                    .build();

            event.addComment(new Comment(1, 1, "Lol"));

            System.out.println(event.getStartTime());
            System.out.println(event.getEndTime());
            System.out.println(event.getStartDate());
            System.out.println(event.getEndDate());
            System.out.println(event.getId());
            System.out.println(event.getImportance());
            System.out.println(event.getTitle());

            System.out.println(event.getListComments().get(0).getContent());
        }
    }

}
