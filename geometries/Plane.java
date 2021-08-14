package geometries;
import java.util.List;
import static primitives.Util.*;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;


public class Plane extends Geometry
{
	protected Point3D q0;
	protected Vector normal;
	
	/** Plane constructor receiving Point3D and Vector
	 * @param q0 q0 value
	 * @param normal normal value
	 */
	public Plane(Point3D q0, Vector normal)
	{
		super();
		this.q0 = q0;
		this.normal = normal;
	}
	
	/** Plane constructor receiving three Point3D 
	 * @param q0 q0 value
	 * @param q1 q1 value
	 * @param q2 q2 value
	 */
	public Plane(Point3D q0, Point3D q1,Point3D q2)
	{
		//try {
			this.q0=q0;
			Vector v1=q0.subtract(q1);
			Vector v2=q0.subtract(q2);	
			normal=v1.crossProduct(v2).normalized();
		//	}
	       // catch (IllegalArgumentException exc)
		//	{
	       // throw new IllegalArgumentException("This is not a Plane/Triangle");
			//}
		
	}

	/** getter
	 * @return the q0
	 */
	public Point3D getQ0()
	{
		return q0;
	}
	
	/** getter
	 * @return the normal
	 */
	public Vector getNormal()
	{
		return normal;
	}

	//toString
	@Override
	public String toString() 
	{
		return "Plane ["+(q0!=null? "q0=" + q0 + ", " :"")
				+(normal!=null?"normal=" +normal :"")+ "]";
	}
	
	//interface
	@Override
	public Vector get_Normal(Point3D point)
	{		
		return normal;
	}
	
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray)
	{
		//get ray point and vector
        Point3D rayP = ray.getP0();
        Vector rayV = ray.getDir();

        // check if the ray is parallel to the plane
        if (isZero(normal.dotProduct(rayV))) // dotProduct = 0 => parallel
            return null;

        try {
            double t = (normal.dotProduct(q0.subtract(rayP))) / (normal.dotProduct(rayV));

            if(isZero(t))
            	// the ray starts on the plane
               return null;
            else if(t > 0.0) // the ray crosses the plane
            	return List.of(new GeoPoint(this, (ray.getPoint(t))));
            else // the ray doesn't cross the plane
            	return null;

        } catch(IllegalArgumentException ex){
            // _p.subtract(rayP) is vector zero, which means the ray point is equal to the plane point (ray start on plane)
        	return null;
	}

	}

}
