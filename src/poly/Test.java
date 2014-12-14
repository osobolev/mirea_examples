package poly;

public final class Test {

    public static void main(String[] args) {
        {
            RealRing ring = new RealRing();
            Polynomial<Double> poly1 = Polynomial.create(ring, 1.0, 1.0);
            Polynomial<Double> poly2 = Polynomial.create(ring, 1.0, 1.0);
            System.out.println("(" + poly1 + ") * (" + poly2 + ") = " + poly1.multiply(poly2));
        }
        {
            ZnRing z3 = new ZnRing(3);
            Polynomial<Integer> poly1 = Polynomial.create(z3, 1, 1);
            ZnRing z5 = new ZnRing(5);
            Polynomial<Integer> poly2 = Polynomial.create(z5, 1, 1);
            System.out.println("(" + poly1 + ") * (" + poly2 + ") = " + poly1.multiply(poly2));
        }
    }
}
