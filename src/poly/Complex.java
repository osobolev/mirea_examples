package poly;

public final class Complex {

    public final double re;
    public final double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public Complex add(Complex that) {
        return new Complex(this.re + that.re, this.im + that.im);
    }

    public Complex multiply(Complex that) {
        return new Complex(this.re * that.re - this.im * that.im, this.re * that.im + this.im * that.re);
    }

    @Override
    public String toString() {
        if (im < 0) {
            return re + String.valueOf(im) + "i";
        } else if (im > 0) {
            return re + "+" + im + "i";
        } else {
            return String.valueOf(re);
        }
    }
}
