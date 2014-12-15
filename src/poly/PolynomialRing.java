package poly;

public final class PolynomialRing<N> implements Ring<Polynomial<N>> {

    private final Ring<N> ring;

    public PolynomialRing(Ring<N> ring) {
        this.ring = ring;
    }

    @Override
    public Polynomial<N> add(Polynomial<N> x, Polynomial<N> y) {
        return x.add(y);
    }

    @Override
    public Polynomial<N> multiply(Polynomial<N> x, Polynomial<N> y) {
        return x.multiply(y);
    }

    @Override
    public Polynomial<N> fromInteger(int n) {
        N a0 = ring.fromInteger(n);
        return Polynomial.create(ring, a0);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PolynomialRing) {
            PolynomialRing<?> that = (PolynomialRing<?>) obj;
            return this.ring.equals(that.ring);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return ring.toString() + "[x]";
    }
}
