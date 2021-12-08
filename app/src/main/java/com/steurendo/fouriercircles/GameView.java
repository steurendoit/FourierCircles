package com.steurendo.fouriercircles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

import com.steurendo.fouriercircles.types.RotationDude;
import com.steurendo.fouriercircles.types.Vector2f;

public class GameView extends SurfaceView implements Runnable
{
    private Thread thread;
    private Paint paint;
    private Model model;

    //GAME PART
    private boolean isPaused;
    private Vector2f[] pathPoints;
    private int indexPoint;
    private float timeSlider;
    private boolean circlesVisible;

    public GameView(Context context, Model model)
    {
        super(context);

        isPaused = false;
        paint = new Paint();
        paint.setTextSize(10);
        paint.setColor(Color.GREEN);
        this.model = model;
        reinit();
        circlesVisible = true;
    }

    @Override
    public void run()
    {
        while (!isPaused)
        {
            update();
            draw();
            sleep();
        }
    }

    public void reinit()
    {
        model.scale((float)Math.pow(0.9f, 11));
        isPaused = false;
        pathPoints = new Vector2f[CommandBoard.PATH_POINTS_NUMBER];
        initPathPoints();
        indexPoint = 0;
        timeSlider = 0;
    }
    private void initPathPoints()
    {
        float time;

        time = 0;
        for (int i = 0; i < CommandBoard.PATH_POINTS_NUMBER; i++)
        {
            model.evaluateAllAtTime(time);
            pathPoints[i] = model.getPoint();
            time += CommandBoard.ROTATION_SPEED;
        }
        model.evaluateAllAtTime(timeSlider);
    }

    private void update()
    {
        if (!isPaused)
        {
            timeSlider += CommandBoard.ROTATION_SPEED;
            if (timeSlider == 1) timeSlider = 0;
            indexPoint++;
            if (indexPoint == CommandBoard.PATH_POINTS_NUMBER) indexPoint = 0;
            model.evaluateAllAtTime(timeSlider);
        }
    }
    private void draw()
    {
        if (getHolder().getSurface().isValid())
        {
            Canvas canvas = getHolder().lockCanvas();
            int maxVisibility;
            float visibility;
            RotationDude[] dudes;
            Vector2f p0;
            float theta, per;

            dudes = model.getRotations();
            if (dudes.length == 0) return;

            maxVisibility = (int)(CommandBoard.TRAIL_LENGTH * CommandBoard.PATH_POINTS_NUMBER);
            for (int i = maxVisibility, j = indexPoint; i > 0; i--, j--)
            {
                visibility = (float)i / maxVisibility + CommandBoard.VISIBILITY_RANGE_MIN;
                if (j < 0) j += CommandBoard.PATH_POINTS_NUMBER;
                paint.setColor((int)(0xFF * visibility) * 0x000100);
                canvas.drawLine(pathPoints[j].x, pathPoints[j].y,
                                pathPoints[j - 1].x, pathPoints[j - 1].y,
                                paint);
            }

            getHolder().unlockCanvasAndPost(canvas);
        }
    }
    private void sleep()
    {
        try
        {
            Thread.sleep(17);
        } catch (InterruptedException e) { e.printStackTrace(); }
    }

    public void resume()
    {
        isPaused = false;
        thread = new Thread(this);
        thread.start();
    }
    public void pause()
    {
        try
        {
            isPaused = true;
            thread.join();
        } catch (InterruptedException e) { e.printStackTrace(); }
    }
}
