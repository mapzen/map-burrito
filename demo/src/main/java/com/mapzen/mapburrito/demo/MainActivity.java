package com.mapzen.mapburrito.demo;

import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;
import com.mapzen.mapburrito.MapController;

import org.oscim.android.MapView;
import org.oscim.tiling.source.oscimap4.OSciMap4TileSource;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new LostApiClient.Builder(this).build().connect();

        final MapView mapView = (MapView) findViewById(R.id.map);
        final MapController mapController = new MapController(mapView.map())
                .setTileSource(new OSciMap4TileSource())
                .addBuildingLayer()
                .addLabelLayer()
                .setTheme("styles/default.xml");

        final ImageButton findMe = (ImageButton) findViewById(R.id.find_me);
        findMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location location = LocationServices.FusedLocationApi.getLastLocation();
                if (location != null) {
                    mapController.centerOn(location);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
