package geometries;
import primitives.Point3D;
import static primitives.Util.*;
import java.util.List;
import primitives.Ray;
import primitives.Vector; 

public class Tube extends Geometry
{
	protected Ray axisRay;
	protected double radius;
	
	/**
	 * Tube constructor
	 * @param axisRay
	 * @param radius
	 */
	public Tube(Ray axisRay, double radius) 
	{
		super();
		this.axisRay = axisRay;
		this.radius = radius;
	}
	
	//interface Geometry
	@Override
	public Vector get_Normal(Point3D point)
	{
		Point3D o=axisRay.getP0();
	    Vector v=axisRay.getDir();
	    double t=point.subtract(o).dotProduct(v);
	    if(!isZero(t))
	    {
	       o=o.add(v.scale(t));
	       return point.subtract(o).normalize();
	    }
	  //if the point is on the same level then return normal
        return point.subtract(axisRay.getP0()).normalize();
	}

	/** getter
	 * @return the axisRay
	 */
	public Ray getAxisRay() 
	{
		return axisRay;
	}

	/** getter
	 * @return the radius
	 */
	public double getRadius() 
	{
		return radius;
	}
	
	//toString
	@Override
	public String toString()
	{
		return "Tube [axisRay=" + axisRay + ", radius=" + radius + "]";
	}
	
	//interface Intersectable
	@Override
	public List<GeoPoint> findGeoIntersections(Ray r) 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
