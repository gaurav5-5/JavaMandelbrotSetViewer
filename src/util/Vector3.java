// util/Vector3.java
package util;

public class Vector3<X> extends Vector2<X> {
    private X third;

    public Vector3(X x, X y, X z) {
        super(x,y);
        third = z;
    }

    public X getThird() {
        return third;
    }


    public X getZ() {
        return getThird();
    }

}
