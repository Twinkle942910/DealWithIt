package com.example.twinkle94.dealwithit.events.event_types;

import com.example.twinkle94.dealwithit.events.Event;

public class Schedule extends Event
{
    private ScheduleType scheduleType;

    public Schedule(EventBuilder eventBuilder)
    {
        super(eventBuilder);
        this.scheduleType = ((Builder)eventBuilder).scheduleType;
    }

    public Schedule()
    {
        super();
        this.scheduleType = ScheduleType.NO_TYPE;
    }

    @Override
    protected void setType() {
        type = EventType.SCHEDULE;
    }

    public ScheduleType getScheduleType()
    {
        return scheduleType;
    }

    public static final class Builder extends EventBuilder<Schedule, Schedule.Builder> {
        private ScheduleType scheduleType;

        @Override
        protected Schedule getEvent() {
            return new Schedule(this);
        }

        @Override
        protected Schedule.Builder thisObject() {
            return this;
        }

        public Builder(String title) {
            super(title);
        }

        public Schedule.Builder setScheduleType(ScheduleType scheduleType){
            this.scheduleType = scheduleType;
            return this;
        }
    }

    private static class BirthdayTest {
        public static void main(String... args) {
            Event event = new Schedule.Builder("Clean the room")
                    .setStartTime("17:31")
                    .setDate("23/07/2017")
                    .setId(1)
                    .setScheduleType(ScheduleType.LESSON)
                    .setImportance(19)
                    .setEndTime("14:29")
                    .build();

            System.out.println(event.getStartTime());
            System.out.println(event.getEndTime());
            System.out.println(event.getStartDate());
            System.out.println(event.getEndDate());
            System.out.println(((Schedule)event).getScheduleType().toString());
            System.out.println(event.getId());
            System.out.println(event.getImportance());
            System.out.println(event.getTitle());
        }
    }
}
