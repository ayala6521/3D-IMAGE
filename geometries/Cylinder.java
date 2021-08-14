package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

public class Cylinder extends Tube 
{
	private double height;

	/** Cylinder constructor
	 * @param axisRay
	 * @param radius
	 * @param height
	 */
	public Cylinder(Ray axisRay, double radius,double height) 
	{
		super(axisRay, radius);
		this.height=height;
		// TODO Auto-generated constructor stub
	}
	
	//interface
	@Override
	public Vector get_Normal(Point3D point)
	{
		if(point.equals(axisRay.getP0()))
			return axisRay.getDir().scale(-1).normalize();
		Point3D centerSecondBase=axisRay.getP0().add(axisRay.getDir().scale(height));
		if(point.equals(centerSecondBase))
			return axisRay.getDir().normalize();
		if(point.subtract(axisRay.getP0()).length()<radius && isZero(point.subtract(axisRay.getP0()).dotProduct(axisRay.getDir())))
			return axisRay.getDir().scale(-1).normalize();
		Vector v=point.subtract(axisRay.getP0().add(axisRay.getDir().scale(height)));
		if(v.length()<radius && isZero(v.dotProduct(axisRay.getDir())))
			return axisRay.getDir().normalize();
		if(point.subtract(axisRay.getP0()).length()==radius && isZero(point.subtract(axisRay.getP0()).dotProduct(axisRay.getDir())))
			return point.subtract(axisRay.getP0()).normalize();
		return super.get_Normal(point);
	}

	/** getter
	 * @return the height
	 */
	public double getHeight() 
	{
		return height;
	}

	//toString
	@Override
	public String toString()
	{
		return "Cylinder [height=" + height + ", axisRay=" + axisRay + ", radius=" + radius + "]";
	}
	

}
