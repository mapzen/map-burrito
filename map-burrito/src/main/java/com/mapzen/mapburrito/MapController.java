package com.mapzen.mapburrito;

import org.oscim.android.canvas.AndroidGraphics;
import org.oscim.backend.AssetAdapter;
import org.oscim.core.GeoPoint;
import org.oscim.core.MapPosition;
import org.oscim.layers.Layer;
import org.oscim.layers.marker.ItemizedLayer;
import org.oscim.layers.marker.MarkerItem;
import org.oscim.layers.marker.MarkerSymbol;
import org.oscim.layers.tile.buildings.BuildingLayer;
import org.oscim.layers.tile.vector.OsmTileLayer;
import org.oscim.layers.tile.vector.VectorTileLayer;
import org.oscim.layers.tile.vector.labeling.LabelLayer;
import org.oscim.map.Map;
import org.oscim.theme.ThemeFile;
import org.oscim.tiling.source.oscimap4.OSciMap4TileSource;

import android.graphics.drawable.Drawable;
import android.location.Location;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MapController {
    public static final int DEFAULT_DURATION = 1000;
    public static final int DEFAULT_ZOOM = 17;
    public static final String LOCATION_MARKER_TITLE = "Current location";
    public static final String LOCATION_MARKER_DESCRIPTION = "You are here";

    private Map map;
    private VectorTileLayer baseLayer;
    private ItemizedLayer<MarkerItem> currentLocationLayer;
    private MarkerSymbol currentLocationSymbol;

    public MapController(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public VectorTileLayer getBaseLayer() {
        return baseLayer;
    }

    /**
     * Set tile source via URL string.
     */
    public MapController setTileSource(String url) {
        setTileSource(new OSciMap4TileSource(url));
        return this;
    }

    /**
     * Set tile source via {@link OSciMap4TileSource}
     */
    public MapController setTileSource(OSciMap4TileSource tileSource) {
        baseLayer = new OsmTileLayer(map);
        baseLayer.setTileSource(tileSource);
        map.setBaseMap(baseLayer);
        return this;
    }

    /**
     * Add generic map layer.
     */
    public MapController addLayer(Layer layer) {
        map.layers().add(layer);
        return this;
    }

    /**
     * Convenience method to add building layer using current base layer.
     */
    public MapController addBuildingLayer() {
        addLayer(new BuildingLayer(map, baseLayer));
        return this;
    }

    /**
     * Convenience method to add label layer using current base layer.
     */
    public MapController addLabelLayer() {
        addLayer(new LabelLayer(map, baseLayer));
        return this;
    }

    /**
     * Set {@link Drawable} resource to be used for current location marker.
     */
    public MapController setCurrentLocationDrawable(Drawable currentLocationDrawable) {
        currentLocationSymbol = AndroidGraphics.makeMarker(currentLocationDrawable,
                MarkerItem.HotspotPlace.CENTER);
        return this;
    }

    /**
     * Display a marker for the current location.
     */
    public MapController showCurrentLocation(Location location) {
        if (currentLocationLayer == null) {
            currentLocationLayer = new ItemizedLayer<>(map, currentLocationSymbol);
            addLayer(currentLocationLayer);
        }

        final GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
        final MarkerItem markerItem = new MarkerItem(LOCATION_MARKER_TITLE,
                LOCATION_MARKER_DESCRIPTION, geoPoint);
        currentLocationLayer.removeAllItems();
        currentLocationLayer.addItem(markerItem);
        return this;
    }

    /**
     * Set map theme using path to xml style declaration (usually found in assets folder).
     */
    public MapController setTheme(String path) {
        setTheme(new Theme(path));
        return this;
    }

    /**
     * Set map theme using {@link Theme} object.
     */
    public MapController setTheme(Theme theme) {
        map.setTheme(theme);
        return this;
    }

    /**
     * Animate map to given location.
     */
    public MapController centerOn(Location location) {
        map.animator().animateTo(new GeoPoint(location.getLatitude(), location.getLongitude()));
        return this;
    }

    /**
     * Animate map to given location while resetting zoom, tilt, and bearing.
     */
    public MapController resetMapAndCenterOn(Location location) {
        MapPosition position = map.getMapPosition();
        position.setPosition(location.getLatitude(), location.getLongitude());
        position.setZoomLevel(DEFAULT_ZOOM);
        position.setTilt(0);
        position.setBearing(0);
        map.animator().animateTo(DEFAULT_DURATION, position);
        return this;
    }

    /**
     * Update all map layers.
     */
    public MapController update() {
        map.updateMap(true);
        return this;
    }

    /**
     * Implementation of {@link ThemeFile} that loads xml style declaration from the given path.
     */
    public static class Theme implements ThemeFile {
        private final String path;

        public Theme(String path) {
            this.path = path;
        }

        @Override
        public InputStream getRenderThemeAsStream() throws FileNotFoundException {
            return AssetAdapter.readFileAsStream(path);
        }

        public String getPath() {
            return path;
        }
    }
}
