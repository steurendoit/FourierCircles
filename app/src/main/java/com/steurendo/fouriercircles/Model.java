package com.steurendo.fouriercircles;

import com.steurendo.fouriercircles.types.RotationDude;
import com.steurendo.fouriercircles.types.Vector2f;

import java.io.File;

public class Model
{
    private RotationDude[] rotations;
    private int nRotations;

    public Model(File src)
    {
        if (src != null)
            loadRotationsFile(src);
        else
        {
            nRotations = 0;
            rotations = new RotationDude[0];
        }
    }
    public Model() { this(null); }

    public void loadRotationsFile(File src)
    {
        rotations = RotationsReader.buildRotationsFromFile(src);
        nRotations = rotations.length;
    }
    public RotationDude[] getRotations()
    {
        RotationDude[] dudes;

        dudes = new RotationDude[nRotations];
        if (dudes.length > 0)
            System.arraycopy(rotations, 0, dudes, 0, dudes.length);

        return dudes;
    }
    public void reset()
    {
        for (RotationDude rotation : rotations)
            rotation.reset();
    }
    public void rotateAll(float time)
    {
        for (RotationDude rotation : rotations)
            rotation.slideTime(time);
    }
    public void evaluateAllAtTime(float time)
    {
        for (RotationDude rotation : rotations)
            rotation.setTime(time);
    }
    public Vector2f getPoint()
    {
        float x, y;

        x = 0;
        y = 0;
        for (int i = 0; i < nRotations; i++)
        {
            x += rotations[i].getX();
            y += rotations[i].getY();
        }

        return new Vector2f(x, y);
    }
    public int getNRotations() { return nRotations; }
    public void setNRotations(int nRotations) { this.nRotations = nRotations; }
    public void shiftRotationsBy(int nShift)
    {
        int newVal;

        newVal = nRotations + nShift;
        if (newVal > 0 && newVal <= rotations.length)
            nRotations = newVal;
    }
    public void scale(float factor)
    {
        for (RotationDude dude : rotations)
            dude.scaleMagnitude(factor);
    }
}