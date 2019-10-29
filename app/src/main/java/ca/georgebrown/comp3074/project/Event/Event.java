package ca.georgebrown.comp3074.project.Event;

import ca.georgebrown.comp3074.project.Backpack.Backpack;
import ca.georgebrown.comp3074.project.Route.Route;

public class Event {
    private int Event_Id;
    private String Event_Name;
    private String Date;
    private String Description;
    private Backpack backpack;
    private Route route;

    public Event(int id, String name, String date, String desc, Backpack bp, Route r)
    {
        Event_Id = id;
        Event_Name = name;
        Date = date;
        Description = desc;
        backpack = bp;
        route = r;
    }
    public int getEvent_Id() {
        return Event_Id;
    }

    public void setEvent_Id(int event_Id) {
        Event_Id = event_Id;
    }

    public String getEvent_Name() {
        return Event_Name;
    }

    public void setEvent_Name(String event_Name) {
        Event_Name = event_Name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public void setBackpack(Backpack backpack) {
        this.backpack = backpack;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
