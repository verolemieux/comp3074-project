package ca.georgebrown.comp3074.project.Route;

import java.io.Serializable;

public class Route implements Serializable {

    private int Route_Id;
    private String Route_Name;
    private float Route_Distance;
    private int Route_Rating;
    private int Route_Difficulty;
    private String Route_Start_Location;
    private String Route_End_Location;

    public Route (int id, String name, float length, int rating, int difficulty, String start_loc, String end_loc)
    {
        Route_Id = id;
        Route_Name = name;
        Route_Distance = length;
        Route_Rating = rating;
        Route_Difficulty = difficulty;
        Route_Start_Location = start_loc;
        Route_End_Location = end_loc;
        //Route_Content = content;
    }
    public int getRoute_Id() {
        return Route_Id;
    }

    public void setRoute_Id(int route_Id) {
        Route_Id = route_Id;
    }

    public String getRoute_Name() {
        return Route_Name;
    }

    public void setRoute_Name(String route_Name) {
        Route_Name = route_Name;
    }

    public int getRoute_Rating() {
        return Route_Rating;
    }

    public void setRoute_Rating(int route_Rating) {
        Route_Rating = route_Rating;
    }

    public int getRoute_Difficulty() {
        return Route_Difficulty;
    }

    public void setRoute_Difficulty(int route_Difficulty) {
        Route_Difficulty = route_Difficulty;
    }

    public float getRoute_Distance() {
        return Route_Distance;
    }

    public void setRoute_Distance(float route_Distance) {
        Route_Distance = route_Distance;
    }

    public String getRoute_Start_Location() {
        return Route_Start_Location;
    }

    public void setRoute_Start_Location(String route_Start_Location) {
        Route_Start_Location = route_Start_Location;
    }

    public String getRoute_End_Location() {
        return Route_End_Location;
    }

    public void setRoute_End_Location(String route_End_Location) {
        Route_End_Location = route_End_Location;
    }



}
