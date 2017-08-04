package com.example.twinkle94.dealwithit.events.event_types;

import com.example.twinkle94.dealwithit.events.ComplexEvent;
import com.example.twinkle94.dealwithit.events.notes.Location;

public class Birthday extends ComplexEvent
{
    private Location location;

    public Birthday(EventBuilder eventBuilder)
    {
        super(eventBuilder);
        this.location = ((Builder)eventBuilder).location;
    }

    public Birthday()
    {
        super();
    }

    @Override
    protected void setType() {
        type = EventType.BIRTHDAY;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public static final class Builder extends EventBuilder<Birthday, Birthday.Builder> {
        private Location location;

        @Override
        protected Birthday getEvent() {
            return new Birthday(this);
        }

        @Override
        protected Birthday.Builder thisObject() {
            return this;
        }

        public Builder(String title) {
            super(title);
        }

        public Builder setLocation(Location location){
            this.location = location;
            return this;
        }
    }

    private static class BirthdayTest {
        public static void main(String... args) {
            ComplexEvent event = new Birthday.Builder("Clean the room")
                    .setStartTime("17:31")
                    .setDate("23/07/2017")
                    .setId(1)
                    .setLocation(new Location(1, 1, "Roksolany", "Lviv", "Ukraine"))
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
