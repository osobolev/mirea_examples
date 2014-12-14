package poly;

public interface Ring<N> {

    N add(N x, N y);

    N multiply(N x, N y);

    N fromInteger(int n);
}
