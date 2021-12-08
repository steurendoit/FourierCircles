package com.steurendo.fouriercircles.types;

public class RotationDude
{
    private float startPhase;
    private float frequency;
    private float magnitude;
    private float phase;

    public RotationDude(float startPhase, float frequency, float magnitude)
    {
        this.startPhase = startPhase;
        this.frequency = frequency;
        this.magnitude = magnitude;
        phase = startPhase;
    }

    public void reset() { phase = startPhase; }
    public float getX() { return magnitude * (float)Math.cos(phase); }
    public float getY() { return magnitude * (float)Math.sin(phase); }
    public Vector2f getVector() { return new Vector2f(getX(), getY()); }
    public void slideTime(double time)
    { phase += 2 * Math.PI * frequency * time; }
    public void setTime(float time)
    { phase = 2 * (float)Math.PI * frequency * time + startPhase; }
    public float getMagnitude() { return magnitude; }
    public void scaleMagnitude(float factor) { magnitude *= factor; }

    @Override
    public String toString()
    {
        return "Starting phase: " + startPhase + "; Frequency: " + frequency + "; Magnitude: " + magnitude;
    }
}
