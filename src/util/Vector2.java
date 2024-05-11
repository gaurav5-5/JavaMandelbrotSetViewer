// util/Vector2.java
package util;

public class Vector2<X> {
    private X first;
    private X second;

    public X getSecond() {
        return second;
    }

    public X getY() {
        return second;
    }

    public void setSecond(X second) {
        this.second = second;
    }

    public X getFirst() {
        return first;
    }

    public X getX() {
        return first;
    }

    public void setFirst(X first) {
        this.first = first;
    }


    public Vector2(X x, X y) {
        first = x;
        second = y;
    }

    public Vector2(Vector2<X> x) {
        this(x.first, x.second);
    } 

    public void set(Vector2<X> x) {
        this.first = x.first;
        this.second = x. second;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof Vector2)){
            return false;
        }
        @SuppressWarnings("unchecked")
        Vector2<X> vec  = (Vector2<X>) other;
        return vec.first == this.first && vec.second == this.second;
    }

    public String toString() {
        return "(" + first + "," + second + ")";
    }
}
