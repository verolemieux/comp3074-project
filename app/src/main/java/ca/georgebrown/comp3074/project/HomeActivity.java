package ca.georgebrown.comp3074.project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import ca.georgebrown.comp3074.project.User.User;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_home, null, false);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.addView(contentView, 0);

        TextView welcome = findViewById(R.id.txtWelcome);
        final User validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
        welcome.setText("Welcome " + validatedUser.getName());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(toggle.onOptionsItemSelected(item)){
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
