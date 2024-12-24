/*
 * Copyright (c) 2002-2008 LWJGL Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'LWJGL' nor the names of
 *   its contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package ru.BouH_.lwjgl_vector;

import org.lwjgl.util.vector.ReadableVector3f;

import java.io.Serializable;
import java.nio.FloatBuffer;

public class Vector3fLWJGL extends VectorLWJGL implements Serializable {

    private static final long serialVersionUID = 1L;

    public float x, y, z;

    public Vector3fLWJGL() {
        super();
    }

    public Vector3fLWJGL(ReadableVector3f src) {
        set(src);
    }

    public Vector3fLWJGL(float x, float y, float z) {
        set(x, y, z);
    }

    public static Vector3fLWJGL add(Vector3fLWJGL left, Vector3fLWJGL right, Vector3fLWJGL dest) {
        if (dest == null)
            return new Vector3fLWJGL(left.x + right.x, left.y + right.y, left.z + right.z);
        else {
            dest.set(left.x + right.x, left.y + right.y, left.z + right.z);
            return dest;
        }
    }

    public static Vector3fLWJGL sub(Vector3fLWJGL left, Vector3fLWJGL right, Vector3fLWJGL dest) {
        if (dest == null)
            return new Vector3fLWJGL(left.x - right.x, left.y - right.y, left.z - right.z);
        else {
            dest.set(left.x - right.x, left.y - right.y, left.z - right.z);
            return dest;
        }
    }

    public static Vector3fLWJGL cross(
            Vector3fLWJGL left,
            Vector3fLWJGL right,
            Vector3fLWJGL dest) {

        if (dest == null)
            dest = new Vector3fLWJGL();

        dest.set(
                left.y * right.z - left.z * right.y,
                right.x * left.z - right.z * left.x,
                left.x * right.y - left.y * right.x
        );

        return dest;
    }

    public static float dot(Vector3fLWJGL left, Vector3fLWJGL right) {
        return left.x * right.x + left.y * right.y + left.z * right.z;
    }

    public static float angle(Vector3fLWJGL a, Vector3fLWJGL b) {
        float dls = dot(a, b) / (a.length() * b.length());
        if (dls < -1f)
            dls = -1f;
        else if (dls > 1.0f)
            dls = 1.0f;
        return (float) Math.acos(dls);
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3fLWJGL set(ReadableVector3f src) {
        x = src.getX();
        y = src.getY();
        z = src.getZ();
        return this;
    }

    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    public Vector3fLWJGL translate(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public VectorLWJGL negate() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    public Vector3fLWJGL negate(Vector3fLWJGL dest) {
        if (dest == null)
            dest = new Vector3fLWJGL();
        dest.x = -x;
        dest.y = -y;
        dest.z = -z;
        return dest;
    }

    public Vector3fLWJGL normalise(Vector3fLWJGL dest) {
        float l = length();

        if (dest == null)
            dest = new Vector3fLWJGL(x / l, y / l, z / l);
        else
            dest.set(x / l, y / l, z / l);

        return dest;
    }

    public VectorLWJGL load(FloatBuffer buf) {
        x = buf.get();
        y = buf.get();
        z = buf.get();
        return this;
    }

    public VectorLWJGL scale(float scale) {

        x *= scale;
        y *= scale;
        z *= scale;

        return this;

    }

    public VectorLWJGL store(FloatBuffer buf) {

        buf.put(x);
        buf.put(y);
        buf.put(z);

        return this;
    }

    public String toString() {

        String sb = "Vector3f[" +
                x +
                ", " +
                y +
                ", " +
                z +
                ']';
        return sb;
    }

    public final float getX() {
        return x;
    }

    public final void setX(float x) {
        this.x = x;
    }

    public final float getY() {
        return y;
    }

    public final void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Vector3fLWJGL other = (Vector3fLWJGL) obj;
        return x == other.x && y == other.y && z == other.z;
    }
}
