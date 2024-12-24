package ru.BouH_.audio.data;

public class SoundSourceData {
    private float x;
    private float y;
    private float z;
    private float vx;
    private float vy;
    private float vz;

    public SoundSourceData() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;

        this.vx = 0.0f;
        this.vy = 0.0f;
        this.vz = 0.0f;
    }

    public void setPos(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setVel(float x, float y, float z) {
        this.vx = x;
        this.vy = y;
        this.vz = z;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getZ() {
        return this.z;
    }

    public float getVx() {
        return this.vx;
    }

    public float getVy() {
        return this.vy;
    }

    public float getVz() {
        return this.vz;
    }
}