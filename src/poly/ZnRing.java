package poly;

public final class ZnRing implements Ring<Integer> {

    private final int n;

    public ZnRing(int n) {
        this.n = n;
    }

    @Override
    public Integer add(Integer x, Integer y) {
        return (x + y) % n;
    }

    @Override
    public Integer multiply(Integer x, Integer y) {
        return (x * y) % n;
    }

    @Override
    public Integer fromInteger(int n) {
        return n % this.n;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ZnRing) {
            ZnRing that = (ZnRing) obj;
            return this.n == that.n;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Z_" + n;
    }
}
