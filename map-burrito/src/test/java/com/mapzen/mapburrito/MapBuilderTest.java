package com.mapzen.mapburrito;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.oscim.layers.Layer;
import org.oscim.layers.tile.buildings.BuildingLayer;
import org.oscim.layers.tile.vector.VectorTileLayer;
import org.oscim.layers.tile.vector.labeling.LabelLayer;
import org.oscim.map.Map;
import org.oscim.theme.ThemeFile;
import org.oscim.tiling.source.oscimap4.OSciMap4TileSource;
import org.robolectric.annotation.Config;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(BurritoTestRunner.class)
@Config(constants = BuildConfig.class)
public class MapBuilderTest {
    private MapBuilder builder;
    private Map map;

    @Before
    public void setUp() throws Exception {
        map = new TestMap();
        builder = new MapBuilder(map);
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertThat(builder).isNotNull();
    }

    @Test
    public void setTileSource_shouldAddVectorTileLayerFromUrl() throws Exception {
        builder.setTileSource("http://vector.example.com/").build();
        assertThat(map.layers().get(1)).isInstanceOf(VectorTileLayer.class);
    }

    @Test
    public void setTileSource_shouldAddVectorTileLayerFromTileSource() throws Exception {
        builder.setTileSource(new OSciMap4TileSource()).build();
        assertThat(map.layers().get(1)).isInstanceOf(VectorTileLayer.class);
    }

    @Test
    public void addLayer_shouldAddLayerToMap() throws Exception {
        Layer layer = new TestLayer(map);
        builder.addLayer(layer).build();
        assertThat(map.layers()).contains(layer);
    }

    @Test
    public void addBuildingLayer_shouldAddBuildingLayerToMap() throws Exception {
        builder.setTileSource("http://vector.example.com/").addBuildingLayer().build();
        assertThat(map.layers().get(2)).isInstanceOf(BuildingLayer.class);
    }

    @Test
    public void addLabelLayer_shouldAddLabelLayerToMap() throws Exception {
        builder.setTileSource("http://vector.example.com/").addLabelLayer().build();
        assertThat(map.layers().get(2)).isInstanceOf(LabelLayer.class);
    }

    @Test
    public void setTheme_shouldApplyThemeFromPath() throws Exception {
        String path = "path/to/theme.xml";
        builder.setTheme(path).build();
        assertThat(((MapBuilder.Theme) ((TestMap) map).getTheme()).getPath()).isEqualTo(path);
    }

    @Test
    public void setTheme_shouldApplyThemeFromTheme() throws Exception {
        MapBuilder.Theme theme = new MapBuilder.Theme("path/to/theme.xml");
        builder.setTheme(theme).build();
        assertThat(((TestMap) map).getTheme()).isEqualTo(theme);
    }

    private class TestLayer extends Layer {
        public TestLayer(Map map) {
            super(map);
        }
    }
}
