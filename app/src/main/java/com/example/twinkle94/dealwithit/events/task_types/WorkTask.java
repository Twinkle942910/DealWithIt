package com.example.twinkle94.dealwithit.events.task_types;

import com.example.twinkle94.dealwithit.events.ComplexEvent;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;

public class WorkTask extends ComplexEvent
{
    public WorkTask(EventBuilder eventBuilder)
    {
      super(eventBuilder);
    }

    public WorkTask()
    {
        super();
    }

    @Override
    protected void setType() {
        type = EventType.WORKTASK;
    }

    public static final class Builder extends EventBuilder<WorkTask, WorkTask.Builder> {
        @Override
        protected WorkTask getEvent() {
            return new WorkTask(this);
        }

        @Override
        protected WorkTask.Builder thisObject() {
            return this;
        }

        public Builder(String title) {
            super(title);
        }
    }

    private static class WorkTaskTest {
        public static void main(String... args) {
            ComplexEvent event = new WorkTask.Builder("Clean the room")
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
