package com.example.twinkle94.dealwithit.events;

import com.example.twinkle94.dealwithit.adapter.today_page_adapter.Item;
import com.example.twinkle94.dealwithit.events.notes.Interest;
import com.example.twinkle94.dealwithit.events.state.CanceledState;
import com.example.twinkle94.dealwithit.events.state.FinishedState;
import com.example.twinkle94.dealwithit.events.state.InProcessState;
import com.example.twinkle94.dealwithit.events.state.MissedState;
import com.example.twinkle94.dealwithit.events.state.NowState;
import com.example.twinkle94.dealwithit.events.state.State;
import com.example.twinkle94.dealwithit.events.state.StateType;
import com.example.twinkle94.dealwithit.events.state.WaitingState;
import com.example.twinkle94.dealwithit.events.event_types.ToDo;
import com.example.twinkle94.dealwithit.events.event_types.EventType;
import com.example.twinkle94.dealwithit.util.DateTimeValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//TODO: Does it have to somehow know about current time?
public abstract class Event implements Item {
    public static final String DATE_FORMAT = "EEEE, MMMM dd";
    public static final String TIME_FORMAT_24_HOURS = "kk:mm";
    public static final String TIME_FORMAT_12_HOURS = "hh:mm a";

    private int id; //optional
    private String title; //required
    private Calendar dateStart; //required
    private Calendar dateEnd; //required
    private int importance; //optional

    protected EventType type; //required (set trough this(child) constructor)
    private State state; //required (set trough this constructor)
    private List<Interest> interestList; //optional

    //12 hours - false, 24 hours - true.
    private boolean is24Hours;

    private int interests;
    private int duration;

    public Event(EventBuilder eventBuilder) {
        this.id = eventBuilder.id;
        this.title = eventBuilder.title;
        this.state = eventBuilder.state;
        this.importance = eventBuilder.importance;
        this.dateStart = eventBuilder.dateStart;
        this.dateEnd = eventBuilder.dateEnd;
        this.interestList = new ArrayList<>();

        setType();
    }

    public Event() {
        this.id = -1;
        this.title = "No_title";
        this.dateStart = Calendar.getInstance();
        this.dateEnd = Calendar.getInstance();
        this.importance = -1;
        this.state = new WaitingState();
        interestList = new ArrayList<>();

        setType();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDateTime(String date, String startTime, String endTime) {
        is24Hours = EventBuilder.getTimeFormat(startTime.toLowerCase());

        setDate(date);
        setStartTime(startTime);
        setEndTime(endTime);

        toNextDay();
    }

    public void setDate(String date) {
        if (DateTimeValidator.validateDate(date)) {
            Calendar givenDate = EventBuilder.convertDate(date);

            EventBuilder.copyDate(dateStart, givenDate);
            EventBuilder.copyDate(dateEnd, givenDate);
        }
        toNextDay();
    }

    public void setStartTime(String startTime) {
        is24Hours = EventBuilder.getTimeFormat(startTime.toLowerCase());

        if (DateTimeValidator.validateTime(is24Hours, startTime)) {
            EventBuilder.copyTime(dateStart, EventBuilder.convertTime(is24Hours, startTime));
        }
        toNextDay();
    }

    public void setEndTime(String endTime) {
        is24Hours = EventBuilder.getTimeFormat(endTime.toLowerCase());

        if (DateTimeValidator.validateTime(is24Hours, endTime)) {
            EventBuilder.copyTime(dateEnd, EventBuilder.convertTime(is24Hours, endTime));
        }
        toNextDay();
    }

    public String getStartTime() {
        return EventBuilder.getTime(is24Hours, dateStart.getTime());
    }

    public String getEndTime() {
        return EventBuilder.getTime(is24Hours, dateEnd.getTime());
    }

    public String getStartDate() {
        return EventBuilder.getDate(dateStart.getTime());
    }

    public String getEndDate() {
        return EventBuilder.getDate(dateEnd.getTime());
    }

    @Override
    public EventType getType() {
        return type;
    }

    protected abstract void setType();

    public State getState() {
        return state;
    }

    public String getStateName() {
        return state.toString();
    }

    public void changeState(EventAction eventAction) {
        state.changeState(this, eventAction);
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public int getInterests() {
        return interests;
    }

    public List<Interest> getListInterests() {
        return interestList;
    }

    public void setListInterests(List<Interest> list_interests) {
        this.interestList = list_interests;
    }

    public void setState(State state) {
        this.state = state;
    }

    //TODO: refactor this)
    public void setStateName(StateType state){
        this.state = EventBuilder.pickState(state);
    }

    public void addInterest(Interest interest) {
        interestList.add(interest);
    }

    public boolean is24Hours() {
        return is24Hours;
    }

    public void set24Hours(boolean is24Hours) {
        this.is24Hours = is24Hours;
    }

    private void toNextDay() {
        if (EventBuilder.isEndingNextDay(dateStart, dateEnd)) dateEnd.add(Calendar.DAY_OF_MONTH, 1);
    }

    private void countInterest() {
        int sum = 0;

        for (Interest interest : interestList) {
            sum += interest.getValue();

            interests = (sum / interestList.size());
        }
    }

    //TODO: Can we do it without generics? (Debug trough code).
    protected abstract static class EventBuilder<E extends Event, B extends EventBuilder<E, B>> {
        private int id;
        private final String title;
        private State state;
        private final Calendar dateStart;
        private final Calendar dateEnd;
        private int importance;

        //12 hours - false, 24 hours - true.
        private boolean is24Hours;

        protected E event;
        protected B thisObject;

        protected abstract E getEvent(); //Each concrete implementing subclass overrides this so that T becomes an object of the concrete subclass

        protected abstract B thisObject(); //Each concrete implementing subclass builder overrides this for the same reason, but for B for the builder

        public EventBuilder(String title) {
            this.title = title;

            this.dateStart = Calendar.getInstance();
            this.dateEnd = Calendar.getInstance();

            this.state = new WaitingState();

            //TODO: doesn't have all the fields this way.
            //event = getEvent();
            thisObject = thisObject();
        }

        public B setId(int id) {
            this.id = id;
            return thisObject;
        }

        public B setDate(String date) {
            if (DateTimeValidator.validateDate(date)) {
                Calendar givenDate = convertDate(date);

                copyDate(dateStart, givenDate);
                copyDate(dateEnd, givenDate);
            }
            toNextDay();
            return thisObject;
        }

        public B setStartTime(String startTime) {
            is24Hours = getTimeFormat(startTime);

            if (DateTimeValidator.validateTime(is24Hours, startTime)) {
                copyTime(dateStart, convertTime(is24Hours, startTime));
            }
            toNextDay();
            return thisObject;
        }

        public B setEndTime(String endTime) {
            is24Hours = getTimeFormat(endTime);

            if (DateTimeValidator.validateTime(is24Hours, endTime)) {
                copyTime(dateEnd, convertTime(is24Hours, endTime));
            }
            toNextDay();
            return thisObject;
        }

        public B setImportance(int importance) {
            this.importance = importance;
            return thisObject;
        }

        //TODO: refactor this)
        public B setState(StateType state) {
            this.state = pickState(state);
            return thisObject;
        }

        public E build() {
            event = getEvent();
            return event;
        }

        private static boolean getTimeFormat(String time) {
            if (time != null && time.length() > 2) {
                String am_pm = time.substring(time.length() - 2);

                if (am_pm.equals("am") || am_pm.equals("pm")) return false;
            }
            return true;
        }

        private static boolean isEndingNextDay(Calendar startDate, Calendar endDate) {
            long startMilliseconds = startDate.getTimeInMillis();
            long endMilliseconds = endDate.getTimeInMillis();

            if (endMilliseconds < startMilliseconds) return true;
            return false;
        }

        private void toNextDay() {
            if (isEndingNextDay(dateStart, dateEnd)) dateEnd.add(Calendar.DAY_OF_MONTH, 1);
        }

        private static String getTime(boolean is24Hours, Date time) {
            SimpleDateFormat endTime = null;

            if (is24Hours)
                endTime = new SimpleDateFormat(TIME_FORMAT_24_HOURS);
            else endTime = new SimpleDateFormat(TIME_FORMAT_12_HOURS);

            if (endTime != null) return endTime.format(time);
            return null;
        }

        private static String getDate(Date date) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            return dateFormat.format(date);
        }

        private static Calendar convertDate(String date) {
            Calendar givenDate = Calendar.getInstance();

            try {
                givenDate.setTime(new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return givenDate;
        }

        private static Calendar convertTime(boolean is24Hours, String startTime) {
            Calendar time = Calendar.getInstance();

            String hourFormat24 = "kk:mm";
            String hourFormat12 = "hh:mm a";

            try {
                if (is24Hours) time.setTime(new SimpleDateFormat(hourFormat24).parse(startTime));
                else time.setTime(new SimpleDateFormat(hourFormat12).parse(startTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return time;
        }

        private static void copyDate(Calendar date, Calendar sourceDate) {
            date.set(Calendar.YEAR, sourceDate.get(Calendar.YEAR));
            date.set(Calendar.MONTH, sourceDate.get(Calendar.MONTH));
            date.set(Calendar.DAY_OF_MONTH, sourceDate.get(Calendar.DAY_OF_MONTH));
        }

        private static void copyTime(Calendar time, Calendar sourceTime) {
            time.set(Calendar.HOUR_OF_DAY, sourceTime.get(Calendar.HOUR_OF_DAY));
            time.set(Calendar.MINUTE, sourceTime.get(Calendar.MINUTE));
        }

        private static State pickState(StateType state) {
            final State pickedState;

            switch (state){
                case NOW:
                    pickedState = new NowState();
                    break;

                case CANCELED:
                    pickedState = new CanceledState();
                    break;

                case FINISHED:
                    pickedState = new FinishedState();
                    break;

                case MISSED:
                    pickedState = new MissedState();
                    break;

                case IN_PROCESS:
                    pickedState = new InProcessState();
                    break;

                default:
                    pickedState = new WaitingState();
                    break;
            }

            return pickedState;
        }
    }

    private static class EventTest {
        public static void main(String... args) {
            Event event = new ToDo();

            //event.setDateTime("23/07/2017", "13:31", "01:29");

            event.setStartTime("17:31");
            event.setEndTime("14:29");
            event.setDate("23/07/2017");

            System.out.println(event.getStartTime());
            System.out.println(event.getEndTime());
            System.out.println(event.getStartDate());
            System.out.println(event.getEndDate());
        }
    }
}
