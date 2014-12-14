package poly;

public final class RealRing implements Ring<Double> {

    @Override
    public Double add(Double x, Double y) {
        return x + y;
    }

    @Override
    public Double multiply(Double x, Double y) {
        return x * y;
    }

    @Override
    public Double fromInteger(int n) {
        return (double) n;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RealRing;
    }

    @Override
    public String toString() {
        return "R";
    }
}
