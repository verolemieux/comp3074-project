package ca.georgebrown.comp3074.project.Item;

public class Item {
    private int Item_Id;
    private String Item_Name;
    private String Item_Picture;
    private String Item_QR_Code;

    public Item(int Id, String name, String item_pic, String item_code)
    {
        Item_Id = Id;
        Item_Name = name;
        Item_Picture = item_pic;
        Item_QR_Code = item_code;
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

    public String getItem_Picture() {
        return Item_Picture;
    }

    public void setItem_Picture(String item_Picture) {
        Item_Picture = item_Picture;
    }

    public String getItem_QR_Code() {
        return Item_QR_Code;
    }

    public void setItem_QR_Code(String item_QR_Code) {
        Item_QR_Code = item_QR_Code;
    }


}
