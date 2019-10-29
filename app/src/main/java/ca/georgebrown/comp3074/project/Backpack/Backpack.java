package ca.georgebrown.comp3074.project.Backpack;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

import ca.georgebrown.comp3074.project.Item.Item;

public class Backpack implements Serializable{
    private int Backpack_Id;
    private String Backpack_Name;
    private ArrayList<Item> Item_List;
    private int Size;

    public Backpack(int Id, String name, int user_id)
    {
        Backpack_Id = Id;
        Backpack_Name = name;
        Item_List = new ArrayList<Item>();
        Size = 20;
    }
    public int getBackpack_Id() {
        return Backpack_Id;
    }

    public void setBackpack_Id(int backpack_Id) {
        Backpack_Id = backpack_Id;
    }

    public String getBackpack_Name() {
        return Backpack_Name;
    }

    public void setBackpack_Name(String backpack_Name) {
        Backpack_Name = backpack_Name;
    }

    public ArrayList<Item> getItem_List() {
        return Item_List;
    }

    public void setItem_List(ArrayList<Item> item_List) {
        Item_List = item_List;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int size) {
        Size = size;
    }
}
