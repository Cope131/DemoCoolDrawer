package com.myapplicationdev.android.democooldrawer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    // Drawer Components
    // -> Navigation Items
    private String[] drawerItems;
    ArrayAdapter<String> aa;
    // -> Views
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    // ->
    String currentTitle;
    ActionBar ab;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Views
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerList = findViewById(R.id.left_drawer);
        drawerList.setOnItemClickListener(this::onItemClick);

        // Navigation Items
        drawerItems = new String[]{"Bio", "Vaccination", "Anniversary"};
        aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, drawerItems);
        drawerList.setAdapter(aa);

        //
        ab = getSupportActionBar();
        currentTitle = getTitle().toString();

        // Shows hamburger icon when the drawer is closed
        // Shows back arrow icon when drawer is open
        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                ab.setTitle(currentTitle);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                ab.setTitle("Make a selection");
            }
        };

        // set drawer toggle as the drawer listener of drawer layout
        drawerLayout.addDrawerListener(drawerToggle);

        // Enable the hamburger and arrow icon in the action bar
        ab.setDisplayHomeAsUpEnabled(true);

        // sets state of the drawer indicator
        // hamburger menu or up button
        drawerToggle.syncState();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new BioFragment();
                break;
            case 1:
                fragment = new VaccinationFragment();
                break;
            case 2:
                fragment = new AnniversaryFragment();
        }
        // Add and Show Selected Item Fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        // Check the Selected Drawer Item
        drawerList.setItemChecked(position, true);
        // Set the Title of App Bar
        currentTitle = drawerItems[position];
        ab.setTitle(currentTitle);
        // Close the Drawer
        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}