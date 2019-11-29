package ca.georgebrown.comp3074.project.Route;

import java.io.Serializable;

public class Route implements Serializable {

    private long Route_Id;
    private String Route_Name;
    private float Route_Length;
    private int Route_Rating;
    private int Route_Difficulty;
    private String Route_Content;

<<<<<<< Updated upstream
    public Route (int id, String name, float length, int rating, int difficulty, String content)
=======
    public Route (long id, String name, float length, int rating, int difficulty, String start_loc, String end_loc)
>>>>>>> Stashed changes
    {
        Route_Id = id;
        Route_Name = name;
        Route_Length = length;
        Route_Rating = rating;
        Route_Difficulty = difficulty;
<<<<<<< Updated upstream
        Route_Content = content;
=======
        Route_Start_Location = start_loc;
        Route_End_Location = end_loc;
        //Route_Content = content;
>>>>>>> Stashed changes
    }
    public long getRoute_Id() {
        return Route_Id;
    }

    public void setRoute_Id(long route_Id) {
        Route_Id = route_Id;
    }

    public String getRoute_Name() {
        return Route_Name;
    }

    public void setRoute_Name(String route_Name) {
        Route_Name = route_Name;
    }

    public float getRoute_Length() {
        return Route_Length;
    }

    public void setRoute_Length(float route_Length) {
        Route_Length = route_Length;
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

    public String getRoute_Content() {
        return Route_Content;
    }

    public void setRoute_Content(String route_Content) {
        Route_Content = route_Content;
    }



}
