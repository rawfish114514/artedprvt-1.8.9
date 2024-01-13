package rawfish.artedprvt.std.math;

import rawfish.artedprvt.api.Solvable;

@Solvable
public class Vector {
    private final double[] doubles;

    @Solvable
    public Vector(double... doubles) {
        this.doubles = doubles;
    }

    @Solvable
    public double get(int index) {
        return doubles[index];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < doubles.length; i++) {
            sb.append(doubles[i]);
            if (i + 1 < doubles.length) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }
}
