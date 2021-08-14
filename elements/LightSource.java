package elements;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface of light source
 * @author Shira Shevkes and Ayala Cohen
 *
 */
public interface LightSource
{
	/**
	 * get intensity in a point
	 * @param p a point
	 * @return intensity color in the point
	 */
	public Color getIntensity(Point3D p);
	
	/**
	 * get vector L of the lighting direction on a point
	 * @param p a point
	 * @return the lighting direction on a point
	 */
	public Vector getL(Point3D p);
	
	/**
	 * calculate the distance between the light and the point
	 * @param point - point3D
	 * @return
	 */
	double getDistance(Point3D point);
}