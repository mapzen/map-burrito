package com.mapzen.mapburrito;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.oscim.layers.Layer;
import org.oscim.layers.tile.buildings.BuildingLayer;
import org.oscim.layers.tile.vector.VectorTileLayer;
import org.oscim.layers.tile.vector.labeling.LabelLayer;
import org.oscim.map.Map;
import org.oscim.tiling.source.oscimap4.OSciMap4TileSource;
import org.robolectric.annotation.Config;

import android.location.Location;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(BurritoTestRunner.class)
@Config(constants = BuildConfig.class)
public class MapControllerTest {
    private MapController mapController;
    private Map map;

    @Before
    public void setUp() throws Exception {
        map = new TestMap();
        mapController = new MapController(map);
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertThat(mapController).isNotNull();
    }

    @Test
    public void setTileSource_shouldAddVectorTileLayerFromUrl() throws Exception {
        mapController.setTileSource("http://vector.example.com/");
        assertThat(map.layers().get(1)).isInstanceOf(VectorTileLayer.class);
    }

    @Test
    public void setTileSource_shouldAddVectorTileLayerFromTileSource() throws Exception {
        mapController.setTileSource(new OSciMap4TileSource());
        assertThat(map.layers().get(1)).isInstanceOf(VectorTileLayer.class);
    }

    @Test
    public void getBaseLayer_shouldReturnVectorTileLayer() throws Exception {
        mapController.setTileSource(new OSciMap4TileSource());
        assertThat(mapController.getBaseLayer()).isInstanceOf(VectorTileLayer.class);
    }

    @Test
    public void addLayer_shouldAddLayerToMap() throws Exception {
        Layer layer = new TestLayer(map);
        mapController.addLayer(layer);
        assertThat(map.layers()).contains(layer);
    }

    @Test
    public void addBuildingLayer_shouldAddBuildingLayerToMap() throws Exception {
        mapController.setTileSource("http://vector.example.com/").addBuildingLayer();
        assertThat(map.layers().get(2)).isInstanceOf(BuildingLayer.class);
    }

    @Test
    public void addLabelLayer_shouldAddLabelLayerToMap() throws Exception {
        mapController.setTileSource("http://vector.example.com/").addLabelLayer();
        assertThat(map.layers().get(2)).isInstanceOf(LabelLayer.class);
    }

    @Test
    public void setTheme_shouldApplyThemeFromPath() throws Exception {
        String path = "path/to/theme.xml";
        mapController.setTheme(path);
        assertThat(((MapController.Theme) ((TestMap) map).getTheme()).getPath()).isEqualTo(path);
    }

    @Test
    public void setTheme_shouldApplyThemeFromTheme() throws Exception {
        MapController.Theme theme = new MapController.Theme("path/to/theme.xml");
        mapController.setTheme(theme);
        assertThat(((TestMap) map).getTheme()).isEqualTo(theme);
    }

    @Test
    public void centerOn_shouldAnimateMapToLocation() throws Exception {
        Location location = new Location("test");
        location.setLatitude(1.0);
        location.setLongitude(2.0);
        mapController.centerOn(location);
        assertThat(TestMap.TestAnimator.getLastGeoPointAnimatedTo().getLatitude()).isEqualTo(1.0);
        assertThat(TestMap.TestAnimator.getLastGeoPointAnimatedTo().getLongitude()).isEqualTo(2.0);
    }

    private class TestLayer extends Layer {
        public TestLayer(Map map) {
            super(map);
        }
    }
}
