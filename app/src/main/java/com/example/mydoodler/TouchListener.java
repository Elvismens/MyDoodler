package com.example.mydoodler;

import android.view.MotionEvent;
import android.view.View;
import android.graphics.Path;

public class TouchListener implements View.OnTouchListener {

    private Path currentPath;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        DrawingView drawingView = (DrawingView) view;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                currentPath = new Path();
                currentPath.moveTo(x, y);
                drawingView.addPath(currentPath);
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentPath != null) {
                    currentPath.lineTo(x, y);
                }
                break;
            case MotionEvent.ACTION_UP:
                currentPath = null;
                break;
        }

        drawingView.invalidate();

        return true;
    }
}
