package data;

import annotation.ClassMeta;
import lombok.Getter;

/**
 * Объекты, которые используются в Route
 */
@ClassMeta(name = "locationFrom")
@Getter
public class LocationFrom {
    private Integer x; //Поле не может быть null
    private float y;
    private double z;

    public LocationFrom(Integer x, float y, double z) {
        try {
            if (x == null) {
                throw new NullPointerException();
            } else this.x = x;
            this.y = y;
            this.z = z;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return "LocationFrom x: " + x.toString() + ", LocationFrom y: " + y + ", LocationFrom z: " + z;
    }
}

