package com.example.doan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

public class CustomView extends View {

    public Paint paint;
    public Path path;
    private Bitmap iconBitmap;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();
        path.moveTo(0,50);
        path.lineTo(300, 50);
        path.lineTo(300, 100);
        path.lineTo(0, 100);
//        path.lineTo(350, 300);

        float radius = 1.0f; // Giảm bán kính để có góc cong nhỏ hơn
        CornerPathEffect cornerPathEffect = new CornerPathEffect(radius);

        float dashLength = 10.0f; // Điều chỉnh độ dài của đoạn nét đứt
        float gapLength = 10.0f; // Điều chỉnh khoảng trắng giữa các đoạn nét đứt
        float[] intervals = {dashLength, gapLength};

        DashPathEffect dashPathEffect = new DashPathEffect(intervals, 0); // Khoảng trắng là 0

        ComposePathEffect composePathEffect = new ComposePathEffect(cornerPathEffect, dashPathEffect);

        paint.setPathEffect(composePathEffect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);

    }
}

