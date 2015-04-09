package com.mapzen.mapburrito;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MapControllerTest {
    private MapController builder;

    @Before
    public void setUp() throws Exception {
        builder = new MapController();
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertThat(builder).isNotNull();
    }
}
