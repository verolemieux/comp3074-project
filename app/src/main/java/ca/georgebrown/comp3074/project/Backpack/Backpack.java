package ca.georgebrown.comp3074.project.Backpack;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

import ca.georgebrown.comp3074.project.Item.Item;

public class Backpack implements Serializable{
    private long Backpack_Id;
    private String Backpack_Name;
    private ArrayList<Item> Item_List;
    private int Size;
    private int numItems;
    private String user_email;

    public Backpack(long Id, String name, String user_email)
    {
        Backpack_Id = Id;
        Backpack_Name = name;
        Item_List = new ArrayList<Item>();
        Size = 20;
        this.user_email = user_email;
        numItems = 0;
    }
    public Backpack(String name, String user_email){
        this.user_email = user_email;
        this.Backpack_Name = name;
    }
    public String getUser_email(){return user_email;}
    public long getBackpack_Id() {
        return Backpack_Id;
    }

    public void setBackpack_Id(long backpack_Id) {
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

    public int getNumItems()
    {
        return numItems;
    }

    public void setNumItem(int i)
    {
        numItems = i;
    }

    public boolean addItem(Item i)
    {
        if(Size >= 20)
        {
            return false;
        }
        Item_List.add(i);
        return true;
    }
}
