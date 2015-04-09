package com.mapzen.mapburrito.shadows;

import org.oscim.backend.CanvasAdapter;
import org.oscim.backend.canvas.Bitmap;
import org.oscim.backend.canvas.Canvas;
import org.oscim.backend.canvas.Paint;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(CanvasAdapter.class)
public class ShadowCanvasAdapter {
    @Implementation
    public static Canvas newCanvas() {
        return new Canvas() {
            @Override
            public void setBitmap(Bitmap bitmap) {
            }

            @Override
            public void drawText(String string, float x, float y, Paint stroke) {
            }

            @Override
            public void drawBitmap(Bitmap bitmap, float x, float y) {
            }
        };
    }
}
