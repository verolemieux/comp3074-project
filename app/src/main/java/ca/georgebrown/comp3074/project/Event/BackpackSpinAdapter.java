package ca.georgebrown.comp3074.project.Event;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.Backpack.Backpack;
import ca.georgebrown.comp3074.project.R;

public class BackpackSpinAdapter extends ArrayAdapter<Backpack> {
    private Context context;
    private ArrayList<Backpack> values;

    public BackpackSpinAdapter(Context context, int textViewResourceId, ArrayList<Backpack> values){
        super(context,textViewResourceId,values);
        this.context = context;
        this.values = values;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        return initView(position,convertView,parent);
    }
    private View initView(int position, View convertView, ViewGroup parent){
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.backpack_row_layout, parent, false
            );
        }
        TextView text = convertView.findViewById(R.id.backpack_text);
        Backpack backpack = getItem(position);
        text.setText(backpack.getBackpack_Name());

        return convertView;

    }

}
