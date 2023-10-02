import java.util.Objects;

/**
 * Coordinate
 * It represents a coordinate point on the surface of the Earth,
 * completely determined by its longitude and latitude coordinates.
 *
 * @author Terry
 */
public class Coordinate {
    /* Longitude and Latitude coordinates. */
    double lon, lat;

    Coordinate (double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Node other = (Node) o;
        return this.lon == other.lon && this.lat == other.lat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lon, lat);
    }
}
