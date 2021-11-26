package com.steurendo.fouriercircles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable
{
    private Thread thread;
    private boolean isPaused;
    private Paint paint;

    public GameView(Context context)
    {
        super(context);

        isPaused = false;
        paint = new Paint();
        paint.setTextSize(10);
        paint.setColor(Color.GREEN);
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

    private void update()
    {
    }
    private void draw()
    {
        if (getHolder().getSurface().isValid())
        {
            Canvas canvas = getHolder().lockCanvas();

            canvas.drawCircle(1000, 1000, 300, paint);

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
