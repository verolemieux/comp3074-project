package ca.georgebrown.comp3074.project.User;

import java.io.Serializable;
import java.util.ArrayList;

import ca.georgebrown.comp3074.project.Backpack.Backpack;
import ca.georgebrown.comp3074.project.Item.Item;
import ca.georgebrown.comp3074.project.Route.Route;

public class User implements Serializable {

    private String email;
    private String name;
    private String password;
    private String enroll_date;
    private String phone_number;
    public ArrayList<Backpack> Backpack_List;
    public ArrayList<Route> Route_List;
    public ArrayList<Item> Item_List;

    public User(String email, String name, String password, String enroll_date, String phone_number)
    {
        this.email = email;
        this.name = name;
        this.password = password;
        this.enroll_date = enroll_date;
        this.phone_number = phone_number;
        Backpack_List = new ArrayList<Backpack>();
        Route_List = new ArrayList<Route>();
        Item_List = new ArrayList<Item>();
        Item_List.add(new Item(1, "Apple"));
        Item_List.add(new Item(2, "Banana"));
        Item_List.add(new Item(3, "Orange"));
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnroll_date() {
        return enroll_date;
    }

    public void setEnroll_date(String enroll_date) {
        this.enroll_date = enroll_date;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public ArrayList<Backpack> getBackpack_List() {
        return Backpack_List;
    }

    public void setBackpack_List(ArrayList<Backpack> backpack_List) {
        Backpack_List = backpack_List;
    }

    public ArrayList<Route> getRoute_List() {
        return Route_List;
    }

    public void setRoute_List(ArrayList<Route> route_List) {
        Route_List = route_List;
    }
}