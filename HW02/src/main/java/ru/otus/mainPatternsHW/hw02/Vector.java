package ru.otus.mainPatternsHW.hw02;

import java.util.Arrays;

public class Vector {
    int[] coords;

    public Vector(int... coords) {
        this.coords = coords;
    }

    public static Vector sum(Vector v1, Vector v2) {
        int[] resultCoords = new int[v1.coords.length];
        for (int i = 0; i < resultCoords.length; i++)
            resultCoords[i] = v1.coords[i] + v2.coords[i];
        return new Vector(resultCoords);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Arrays.equals(coords, vector.coords);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coords);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "coords=" + Arrays.toString(coords) +
                '}';
    }
}
