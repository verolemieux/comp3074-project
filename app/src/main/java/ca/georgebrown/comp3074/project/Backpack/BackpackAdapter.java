package ca.georgebrown.comp3074.project.Backpack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

import ca.georgebrown.comp3074.project.R;

public class BackpackAdapter extends ArrayAdapter<Backpack> implements Serializable {
    public BackpackAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Backpack> objects){
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if(convertView==null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.backpack_layout, null);
        }

        TextView txt = convertView.findViewById(R.id.txtBackpackLayout);
        txt.setText(getItem(position).getBackpack_Name());
        return convertView;
    }
}
