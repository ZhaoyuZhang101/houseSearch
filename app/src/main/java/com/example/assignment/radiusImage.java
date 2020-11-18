package com.example.assignment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;

public class radiusImage extends androidx.appcompat.widget.AppCompatImageView {
    private final RectF roundRect = new RectF();
    private final Paint maskPaint = new Paint();
    private final Paint zonePaint = new Paint();
    private float rect_radius = 20;

    public radiusImage(Context context) {
        super(context);
        init(context);
    }

    public radiusImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public radiusImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        maskPaint.setAntiAlias(true);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        zonePaint.setAntiAlias(true);
        zonePaint.setColor(Color.WHITE);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = getWidth();
        int height = getHeight();
        roundRectSet(width, height);
    }

    @Override
    public void draw(Canvas canvas) {
        canvasSetLayer(canvas);
        super.draw(canvas);
        canvas.restore();
    }


    public void setRectRadius(float radius) {
        rect_radius = radius;
        invalidate();
    }

    private void roundRectSet(int width, int height) {
        roundRect.set(0, 0, width, height);
    }

    private void canvasSetLayer(Canvas canvas) {
        canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawRoundRect(roundRect, rect_radius, rect_radius, zonePaint);
        canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);
    }
}