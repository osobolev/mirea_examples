package poly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Polynomial<N> {

    private final Ring<N> ring;
    private final List<N> coefficients;

    public Polynomial(Ring<N> ring, List<N> coefficients) {
        this.ring = ring;
        this.coefficients = coefficients;
    }

    @SafeVarargs
    public static <N> Polynomial<N> create(Ring<N> ring, N... coefficients) {
        return new Polynomial<>(ring, Arrays.asList(coefficients));
    }

    private Polynomial(Ring<N> ring, int n) {
        this.ring = ring;
        this.coefficients = new ArrayList<>(Collections.nCopies(n, ring.fromInteger(0)));
    }

    public int getDegree() {
        return coefficients.size() - 1;
    }

    public Polynomial<N> add(Polynomial<N> that) {
        if (!this.ring.equals(that.ring))
            throw new IllegalArgumentException("Операция над многочленами разных колец: " + this.ring + " и " + that.ring);
        int n = Math.max(this.coefficients.size(), that.coefficients.size());
        Polynomial<N> result = new Polynomial<>(ring, n);
        for (int i = 0; i < n; i++) {
            N a;
            if (i < this.coefficients.size() && i < that.coefficients.size()) {
                N a1 = this.coefficients.get(i);
                N a2 = that.coefficients.get(i);
                a = ring.add(a1, a2);
            } else if (i < this.coefficients.size()) {
                a = this.coefficients.get(i);
            } else {
                a = that.coefficients.get(i);
            }
            result.coefficients.set(i, a);
        }
        return result;
    }

    public Polynomial<N> multiply(Polynomial<N> that) {
        if (!this.ring.equals(that.ring))
            throw new IllegalArgumentException("Операция над многочленами разных колец: " + this.ring + " и " + that.ring);
        int n = this.coefficients.size() + that.coefficients.size() - 1;
        Polynomial<N> result = new Polynomial<>(ring, n);
        for (int i = 0; i < this.coefficients.size(); i++) {
            N a1 = this.coefficients.get(i);
            for (int j = 0; j < that.coefficients.size(); j++) {
                N a2 = that.coefficients.get(j);
                N a = ring.multiply(a1, a2);
                int k = i + j;
                result.coefficients.set(k, ring.add(result.coefficients.get(k), a));
            }
        }
        return result;
    }

    public N calculate(N x) {
        N t = this.coefficients.get(this.coefficients.size() - 1);
        for (int i = this.coefficients.size() - 2; i >= 0; i--) {
            N tx = ring.multiply(t, x);
            t = ring.add(this.coefficients.get(i), tx);
        }
        return t;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        N zero = ring.fromInteger(0);
        N one = ring.fromInteger(1);
        for (int i = coefficients.size() - 1; i >= 0; i--) {
            N coefficient = coefficients.get(i);
            if (coefficient.equals(zero))
                continue;
            if (buf.length() > 0) {
                buf.append(" + ");
            }
            if (i == 0 || !coefficient.equals(one)) {
                buf.append(coefficient.toString());
            }
            if (i > 0) {
                buf.append("x^" + i);
            }
        }
        if (buf.length() <= 0)
            return zero.toString();
        return buf.toString();
    }
}
