package ru.BouH_.lwjgl_vector;

import java.io.Serializable;
import java.nio.FloatBuffer;

public abstract class VectorLWJGL implements Serializable, ReadableVectorLWJGL {
    protected VectorLWJGL() {
    }

    public final float length() {
        return (float) Math.sqrt(this.lengthSquared());
    }

    public abstract float lengthSquared();

    public abstract VectorLWJGL load(FloatBuffer var1);

    public abstract VectorLWJGL negate();

    public final VectorLWJGL normalise() {
        float len = this.length();
        if (len != 0.0F) {
            float l = 1.0F / len;
            return this.scale(l);
        } else {
            throw new IllegalStateException("Zero length vector");
        }
    }

    public abstract VectorLWJGL store(FloatBuffer var1);

    public abstract VectorLWJGL scale(float var1);
}
