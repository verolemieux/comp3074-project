package ca.georgebrown.comp3074.project.Event;

import java.io.Serializable;

import ca.georgebrown.comp3074.project.Backpack.Backpack;
import ca.georgebrown.comp3074.project.Route.Route;

public class Event implements Serializable {
    private long Event_Id;
    private String Event_Name;
    private String Date;
    private String Description;
    private long backpack;
    private long route;

    public Event(long id, String name, String date, String desc, long bp_id, long r_id)
    {
        Event_Id = id;
        Event_Name = name;
        Date = date;
        Description = desc;
        backpack = bp_id;
        route = r_id;
    }
    public long getEvent_Id() {
        return Event_Id;
    }

    public void setEvent_Id(long event_Id) {
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

    public long getBackpack() {
        return backpack;
    }

    public void setBackpack(long backpack) {
        this.backpack = backpack;
    }

    public long getRoute() {
        return route;
    }

    public void setRoute(long route) {
        this.route = route;
    }
}
