package ca.georgebrown.comp3074.project.Item;

import java.io.Serializable;

public class Item implements Serializable {
    private int Item_Id;
    private String Item_Name;
    private byte[] Item_Picture;
    private byte[] Item_QR_Code;
    private String Description;

    public Item(int Id, String name, String description)
    {
        Item_Id = Id;
        Item_Name = name;
        Description = description;
        //implement later with string for path to picture/ QR code
        Item_Picture = null;
        Item_QR_Code = null;
    }
    public int getItem_Id() {
        return Item_Id;
    }

    public void setItem_Id(int item_Id) {
        Item_Id = item_Id;
    }

    public String getItem_Name() {
        return Item_Name;
    }

    public void setItem_Name(String item_Name) {
        Item_Name = item_Name;
    }

    public byte[] getItem_Picture() {
        return Item_Picture;
    }

    public void setItem_Picture(byte[] item_Picture) {
        Item_Picture = item_Picture;
    }

    public byte[] getItem_QR_Code() {
        return Item_QR_Code;
    }

    public void setItem_QR_Code(byte[] item_QR_Code) {
        Item_QR_Code = item_QR_Code;
    }

    public String getDescription() {return Description;}

    public void setDescription(String desc){Description = desc;}

    public int maxValue(int current, int largest)
    {
        if(current > largest)
        {
            return current;
        }
        return largest;
    }

}
