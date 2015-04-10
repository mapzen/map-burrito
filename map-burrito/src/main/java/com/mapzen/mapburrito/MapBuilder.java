package com.mapzen.mapburrito;

import org.oscim.backend.AssetAdapter;
import org.oscim.layers.Layer;
import org.oscim.layers.tile.buildings.BuildingLayer;
import org.oscim.layers.tile.vector.OsmTileLayer;
import org.oscim.layers.tile.vector.VectorTileLayer;
import org.oscim.layers.tile.vector.labeling.LabelLayer;
import org.oscim.map.Map;
import org.oscim.theme.ThemeFile;
import org.oscim.tiling.source.oscimap4.OSciMap4TileSource;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MapBuilder {
    private Map map;
    private VectorTileLayer baseLayer;
    private ArrayList<Layer> layers = new ArrayList<>();
    private ThemeFile theme;

    public MapBuilder(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public MapBuilder setTileSource(String url) {
        setTileSource(new OSciMap4TileSource(url));
        return this;
    }

    public MapBuilder setTileSource(OSciMap4TileSource tileSource) {
        baseLayer = new OsmTileLayer(map);
        baseLayer.setTileSource(tileSource);
        return this;
    }

    public MapBuilder addLayer(Layer layer) {
        layers.add(layer);
        return this;
    }

    public MapBuilder addBuildingLayer() {
        addLayer(new BuildingLayer(map, baseLayer));
        return this;
    }

    public MapBuilder addLabelLayer() {
        addLayer(new LabelLayer(map, baseLayer));
        return this;
    }

    public MapBuilder setTheme(String path) {
        setTheme(new Theme(path));
        return this;
    }

    public MapBuilder setTheme(Theme theme) {
        this.theme = theme;
        return this;
    }

    public void build() {
        map.setBaseMap(baseLayer);
        for (Layer layer : layers) {
            map.layers().add(layer);
        }

        map.setTheme(theme);
    }

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
