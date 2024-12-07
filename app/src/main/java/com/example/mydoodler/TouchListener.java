package com.example.mydoodler;

import android.view.MotionEvent;
import android.view.View;
import android.graphics.Path;

public class TouchListener implements View.OnTouchListener {

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        DrawingView drawingView = (DrawingView) view;
        Path path;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                path = new Path();
                path.moveTo(x, y);
                drawingView.addPath(path);
                break;
            case MotionEvent.ACTION_MOVE:
                path = drawingView.getLastPath();
                if (path != null) {
                    path.lineTo(x, y);
                }
                break;
        }

        drawingView.invalidate();

        return true;
    }
}

