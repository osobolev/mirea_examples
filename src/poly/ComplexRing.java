package poly;

public final class ComplexRing implements Ring<Complex> {

    @Override
    public Complex add(Complex x, Complex y) {
        return x.add(y);
    }

    @Override
    public Complex multiply(Complex x, Complex y) {
        return x.multiply(y);
    }

    @Override
    public Complex fromInteger(int n) {
        return new Complex(n, 0);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ComplexRing;
    }

    @Override
    public String toString() {
        return "C";
    }
}
