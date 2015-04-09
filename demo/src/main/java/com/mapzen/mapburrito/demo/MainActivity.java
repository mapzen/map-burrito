package com.mapzen.mapburrito.demo;

import com.mapzen.mapburrito.MapBuilder;

import org.oscim.android.MapView;
import org.oscim.backend.AssetAdapter;
import org.oscim.theme.ThemeFile;
import org.oscim.tiling.source.oscimap4.OSciMap4TileSource;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MapView mapView = (MapView) findViewById(R.id.map);
        new MapBuilder(mapView.map())
                .setBaseMap(new OSciMap4TileSource())
                .addBuildingLayer()
                .addLabelLayer()
                .setTheme(new DefaultTheme())
                .build();
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

    public static class DefaultTheme implements ThemeFile {
        @Override
        public InputStream getRenderThemeAsStream() throws FileNotFoundException {
            return AssetAdapter.readFileAsStream("styles/default.xml");
        }
    }
}
