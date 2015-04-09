package com.mapzen.mapburrito;

import org.oscim.map.Map;
import org.oscim.theme.ThemeFile;

public class TestMap extends Map {
    private ThemeFile theme;

    @Override
    public void updateMap(boolean b) {
    }

    @Override
    public void render() {
    }

    @Override
    public boolean post(Runnable runnable) {
        return false;
    }

    @Override
    public boolean postDelayed(Runnable runnable, long l) {
        return false;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void setTheme(ThemeFile theme) {
        this.theme = theme;
    }

    public ThemeFile getTheme() {
        return theme;
    }
}
