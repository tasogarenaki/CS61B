/**
 * Highway
 *
 * Constructs a "highway" element of "way" as described in OSM.
 *
 * @author Terry
 */
public class Highway extends Way{
    final String type;

    Highway(long id, int maxSpeed, String name, String type){
        super(id, maxSpeed, name);
        this.type = type;
    }
}
