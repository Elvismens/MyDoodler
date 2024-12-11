package com.example.mydoodler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DrawingView extends View {

    private class PathWithPaint {
        Path path;
        Paint paint;

        PathWithPaint(Path path, Paint paint) {
            this.path = path;
            this.paint = paint;
        }
    }

    private List<PathWithPaint> paths = new ArrayList<>();
    private List<PathWithPaint> undonePaths = new ArrayList<>();
    private Paint currentPaint;
    private Path currentPath;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        currentPaint = new Paint();
        currentPaint.setAntiAlias(true);
        currentPaint.setDither(true);
        currentPaint.setColor(0xFF000000); // default color
        currentPaint.setStyle(Paint.Style.STROKE);
        currentPaint.setStrokeJoin(Paint.Join.ROUND);
        currentPaint.setStrokeCap(Paint.Cap.ROUND);
        currentPaint.setStrokeWidth(10); // default width
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (PathWithPaint pwp : paths) {
            canvas.drawPath(pwp.path, pwp.paint);
        }
        if (currentPath != null) {
            canvas.drawPath(currentPath, currentPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentPath = new Path();
                currentPath.moveTo(event.getX(), event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentPath != null) {
                    currentPath.lineTo(event.getX(), event.getY());
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (currentPath != null) {
                    paths.add(new PathWithPaint(currentPath, new Paint(currentPaint)));
                    currentPath = null;
                }
                invalidate();
                break;
        }
        return true;
    }

    public void undo() {
        if (paths.size() > 0) {
            undonePaths.add(paths.remove(paths.size() - 1));
            invalidate();
        }
    }

    public void redo() {
        if (undonePaths.size() > 0) {
            paths.add(undonePaths.remove(undonePaths.size() - 1));
            invalidate();
        }
    }

    public void erase() {
        paths.clear();
        undonePaths.clear();
        invalidate();
    }

    public void addPath(Path path) {
        paths.add(new PathWithPaint(path, new Paint(currentPaint)));
        undonePaths.clear(); // Clear the undone paths when a new path is added
    }

    public void setCurrentWidth(float width) {
        currentPaint.setStrokeWidth(width);
    }

    public void setCurrentOpacity(int opacity) {
        currentPaint.setAlpha(opacity);
    }

    public void setCurrentColor(int color) {
        currentPaint.setColor(color);
    }
}
