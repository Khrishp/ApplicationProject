package com.example.applicationproject;

import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CalendarActivity extends AppCompatActivity implements WeekView.EventClickListener,
        MonthLoader.MonthChangeListener, WeekView.EventLongPressListener {
    WeekView mWeekView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MonthLoader.MonthChangeListener mMonthChangeListener = new MonthLoader.MonthChangeListener() {
            @Override
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                // Populate the week view with some events.
                List<WeekViewEvent> events = getEvents(newYear, newMonth);
                return events;
            }
        };
        // Get a reference for the week view in the layout.
        mWeekView = findViewById(R.id.weekView);

// Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(this);

// The week view has infinite scrolling horizontally. We have to provide the events of a
// month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(mMonthChangeListener);

// Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        return null;
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {

    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

    }

    private List<WeekViewEvent> getEvents(int newYear, int newMonth) {
        List<WeekViewEvent> eventList = null;
        WeekViewEvent newEvent = new WeekViewEvent();
        Calendar calendar = Calendar.getInstance();
        newEvent.setStartTime(calendar);
        calendar.set(Calendar.HOUR, (calendar.HOUR + 1));
        newEvent.setEndTime(calendar);
        eventList.add(newEvent);
        return eventList;
    }
}


