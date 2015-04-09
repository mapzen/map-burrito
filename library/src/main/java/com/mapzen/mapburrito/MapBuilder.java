package com.mapzen.mapburrito;

import org.oscim.layers.Layer;
import org.oscim.layers.tile.buildings.BuildingLayer;
import org.oscim.layers.tile.vector.OsmTileLayer;
import org.oscim.layers.tile.vector.VectorTileLayer;
import org.oscim.layers.tile.vector.labeling.LabelLayer;
import org.oscim.map.Map;
import org.oscim.theme.ThemeFile;
import org.oscim.tiling.source.oscimap4.OSciMap4TileSource;

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

    public MapBuilder setBaseMap(OSciMap4TileSource tileSource) {
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

    public MapBuilder setTheme(ThemeFile theme) {
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
}
