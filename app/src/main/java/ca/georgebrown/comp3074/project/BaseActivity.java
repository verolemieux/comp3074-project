package ca.georgebrown.comp3074.project;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import ca.georgebrown.comp3074.project.Backpack.BackpacksActivity;
import ca.georgebrown.comp3074.project.Event.EventsActivity;
import ca.georgebrown.comp3074.project.Item.ItemsActivity;
import ca.georgebrown.comp3074.project.Route.RoutesActivity;
import ca.georgebrown.comp3074.project.User.User;

public class BaseActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout drawer;
    public FloatingActionButton fab;
    public ActionBarDrawerToggle toggle;
    public NavigationView navView;
    public User validatedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.Open, R.string.Close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        validatedUser = (User)getIntent().getSerializableExtra("ValidatedUser");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent();
        switch(id)
        {
            case R.id.home:
                intent = new Intent(getApplicationContext(), HomeActivity.class);
                break;
            case R.id.backpacks:
                intent = new Intent(getApplicationContext(), BackpacksActivity.class);
                break;
            case R.id.items:
                intent = new Intent(getApplicationContext(), ItemsActivity.class);
                break;
            case R.id.events:
                intent = new Intent(getApplicationContext(), EventsActivity.class);
                break;
            case R.id.routes:
                intent = new Intent(getApplicationContext(), RoutesActivity.class);
                break;
        }
        intent.putExtra("ValidatedUser", validatedUser);
        startActivity(intent);

        drawer = findViewById(R.id.drawer_layout);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
