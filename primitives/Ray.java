package primitives;
import java.util.Objects;
import static primitives.Util.*;
import java.util.List;
import geometries.Intersectable.GeoPoint;

public class Ray 
{
	private static final double DELTA = 0.1;
	private Point3D p0;
	private Vector dir; //direction
	
	/**  Ray constructor receiving a point and normalized vector
	 * @param p0 p0 value
	 * @param dir normalized dir value
	 */
	public Ray(Point3D p0, Vector dir) throws IllegalArgumentException //constructor
	{
		super();
		this.p0 = p0;
		this.dir = dir.normalized();
	}
	/**
	 * C-TOR that gets point and tow vectors
	 * 
	 * @param p0     - start point
	 * @param dir    - direction vector
	 * @param normal - the vector on which to move the start point in delta
	 */
	public Ray(Point3D p0, Vector dir, Vector normal) 
	{
		this.dir = dir.normalized();
		Vector delta = normal.scale(normal.dotProduct(dir) > 0 ? DELTA : -DELTA);// where we need to move the point
		this.p0 = p0.add(delta);// moving the point
	}
	/** getter
	 * @return the p0
	 */
	public Point3D getP0()
	{
		return p0;
	}
	/**getter
	 * @return the dir
	 */
	public Vector getDir()
	{
		return dir;
	}
	
	//equals
	@Override
	public boolean equals(Object obj)
	{
	      if (this == obj) return true;
	      if (obj == null) return false;
	      if (!(obj instanceof Ray)) return false;
	      Ray other = (Ray)obj;
	      return p0.equals(other.p0)&& dir.equals(other.dir);
    }
	
	//toString
	@Override
	public String toString()
	{
		return "Ray ["+ (p0!=null ? "p0="+ p0 +", " : "")
				+ (dir!=null ? "dir=" + dir : "") + "]";
	}
	
	/**
	 * 
	 * @param t - scalar
	 * @return p0 +t*v
	 */
	public Point3D getPoint(double t)
	{
		return isZero(t)?p0 : p0.add(dir.scale(t));
	}
	
	/**
	 * find the closest point to the start of the ray
	 * @param points
	 * @return the closest point
	 */
	public Point3D findClosestPoint(List<Point3D> points)
	{
		if(points==null)
			return null;
		double minDis=p0.distance(points.get(0));//the smallest distance
		Point3D closestPoint=points.get(0);//the closest point
		double currentDis;//save the current distance-for the loop
		for (int i = 1; i < points.size(); i++)
		{
			currentDis=p0.distance(points.get(i));
			if(currentDis<minDis)
			{
				minDis=currentDis;
				closestPoint=points.get(i);
			}
		}
		return closestPoint;
	}
	
	public GeoPoint findClosestGeoPoint(List<GeoPoint> points)
	{
		if(points==null || points.size()==0)
			return null;
		double minDis=p0.distance(points.get(0).point);//the smallest distance
		GeoPoint closestPoint=points.get(0);//the closest point
		double currentDis;//save the current distance-for the loop
		for (int i = 1; i < points.size(); i++)
		{
			currentDis=p0.distance(points.get(i).point);
			if(currentDis<minDis)
			{
				minDis=currentDis;
				closestPoint=points.get(i);
			}
		}
		return closestPoint;
	}

}
