package ru.BouH_.lwjgl_vector;

import java.nio.FloatBuffer;

public interface ReadableVectorLWJGL {
    float length();

    float lengthSquared();

    VectorLWJGL store(FloatBuffer var1);
}
