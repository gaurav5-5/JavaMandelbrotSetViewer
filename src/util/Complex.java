// util/Complex.java
package util;

public class Complex extends Vector2<Double> {

    private double real() {
        return super.getFirst();
    }

    private double imaginary() {
        return super.getSecond();
    }
    
    /**
     * @return real part of Complex number
     */
    public double getReal() {
        return super.getFirst();
    }
    
    /**
     * @param real real part of Complex number
     */
    public void setReal(double real) {
        super.setFirst(real);
    }
    
    public Complex() {
        super(0.0, 0.0);
    }
    
    public Complex(Vector2<Double> vec) {
        super(vec);
    }
    
    public Complex(double real, double imaginary) {
        super(real, imaginary);
    }
    
    public void set(Vector2<Double> vec) {
        setReal(vec.getFirst());
        setImaginary(vec.getSecond());
    }
    
    
    /**
     * @return imaginary part of Complex number
     */
    public double getImaginary() {
        return super.getSecond();
    }
    
    /**
     * @param imaginary imaginary part of Complex number
     */
    public void setImaginary(double imaginary) {
        super.setSecond(imaginary);
    }

    

    public Complex add(Complex c) {
        return new Complex(
            this.real() + c.real(),
            this.imaginary() + c.imaginary()
        );
    }

    public Complex subtract(Complex c) {
        return new Complex(
            this.real() - c.real(),
            this.imaginary() - c.imaginary()
        );
    }

    public Complex multiply(Complex c) {
        return new Complex(
            (this.real()*c.real()) - (this.imaginary()*c.imaginary()),
            (this.real()*c.imaginary()) + (this.imaginary()*c.real())
        );
    }


    public Complex square() {
        double realPart = this.real() * this.real() - this.imaginary() * this.imaginary();
        double imaginaryPart = 2 * this.real() * this.imaginary();
        return new Complex(realPart, imaginaryPart);
    }

    public Complex multiply(double k) {
        return new Complex(this.real()*k,this.imaginary()*k);
    }

    private double rational() {
        return (this.real()*this.real()) + (this.imaginary()*this.imaginary());
    }

    public Complex divide(Complex c) {
        return (
            this.multiply(c.conjugate(c))
        ).multiply(1/c.rational());
    }

    public Complex conjugate(Complex c) {
        return new Complex(
            this.real(),
            (-1*this.imaginary())
        );
    }

    public double modulus() {
        return Math.sqrt(rational());
    }

    @Override
    public boolean equals(Object obj) {
        Complex c = (Complex) obj;
        if (c.real() == this.real() && c.imaginary() == this.imaginary())
            return true;
        return false;
    }

    @Override
    public String toString() {
        return (
            new StringBuffer(
                (this.real() > 0 ? "" : "-")
            )
            .append(Math.abs(this.real()))
            .append(
                (this.imaginary() > 0 ? " + " : " - ")
            )
            .append(Math.abs(this.imaginary()))
            .append("i")
        ).toString();
    }

}
