package ca.georgebrown.comp3074.project.Event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ca.georgebrown.comp3074.project.Backpack.Backpack;
import ca.georgebrown.comp3074.project.R;
import ca.georgebrown.comp3074.project.Route.Route;

public class RouteSpinAdapter extends ArrayAdapter<Route> {
    private Context context;
    private ArrayList<Route> values;

    public RouteSpinAdapter(Context context, int textViewResourceId, ArrayList<Route> values){
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
                    R.layout.route_row_layout, parent, false
            );
        }
        TextView text = convertView.findViewById(R.id.route_text);
        Route route = getItem(position);
        text.setText(route.getRoute_Name());

        return convertView;

    }
}
