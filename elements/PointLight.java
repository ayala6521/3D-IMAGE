package elements;
import geometries.*;
import primitives.*;

public class PointLight extends Light implements LightSource
{
	private Point3D position;
	private double kC=1;
	private double kL=0;
	private double kQ=0;
	
	/* ********* Constructor ***********/
	/**
	 * C-TOR that gets the value of all the fields
	 * @param intensity intensity color
	 * @param position position point
	 */
	public PointLight(Color intensity, Point3D position) 
	{
		super(intensity);
		this.position = position;
	}
	
	/* ************* Getters/Setters *******/
	@Override
	public Color getIntensity(Point3D p) {
		double distance2 = p.distanceSquared(position);// d^2
		double distance = p.distance(position);
		return getIntensity().scale(1 / (kC + kL * distance + kQ * distance2));
	}

	@Override
	public Vector getL(Point3D p) {
		if (p.equals(position)) {//if p==position because vector zero is not good!
			return null;
		}
		return p.subtract(position).normalized();
	}
	
	/**
	 * set kQ
	 * @param kQ kQ value
	 * @return the point light object
	 */
	public PointLight setkQ(double kQ)
	{
		this.kQ = kQ;
		return this;
	}

	/**
	 * set kL
	 * @param kL kL value
	 * @return the point light object
	 */
	public PointLight setkL(double kL) 
	{
		this.kL = kL;
		return this;
	}

	/**
	 * set kC
	 * @param kC kC value
	 * @return the point light object
	 */
	public PointLight setkC(double kC)
	{
		this.kC = kC;
		return this;
	}
	
	/* ************* Administration *******/
	@Override
	public String toString()
	{
		return "PointLight [position=" + position + ", kQ=" + kQ + ", kL=" + kL + ", kC=" + kC + ", getIntensity()="
				+ getIntensity() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	@Override
	public double getDistance(Point3D point)
	{
		return position.distance(point);
	}
	
}
