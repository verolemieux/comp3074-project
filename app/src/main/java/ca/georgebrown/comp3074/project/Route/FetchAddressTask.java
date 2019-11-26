package ca.georgebrown.comp3074.project.Route;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FetchAddressTask extends AsyncTask<Location, Void, String> {
    private Context context;
    private OnTaskCompleted listener;

    FetchAddressTask(Context context, OnTaskCompleted listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Location... locations){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        Location location = locations[0];
        List<Address> addresses = null;
        String result = "";

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),1);
        } catch (IOException e){
            result = "Service not available";
            Log.e("LOCATION", result);
        } catch (IllegalArgumentException ex){
            result = "Invalid position";
            Log.e("LOCATION", result);
        }

        if (addresses == null || addresses.size() == 0){
            result = "No address found for location";
            Log.d("LOCATION", result);
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressParts = new ArrayList<>();
            for(int i = 0; i <= address.getMaxAddressLineIndex(); i++){
                addressParts.add(address.getAddressLine(i));
            }
            result = TextUtils.join("\n", addressParts);
        }

        return result;
    }

    @Override
    protected void onPostExecute(String s){
        listener.onTaskCompleted(s);
        super.onPostExecute(s);
    }

    public interface OnTaskCompleted{
        void onTaskCompleted(String result);
    }
}
