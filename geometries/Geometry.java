package geometries;
import primitives.*;

public abstract class Geometry implements Intersectable 
{
	protected Color emmission = Color.BLACK;
	private Material material=new Material();
	
	/** 
	 * @param point
	 * @return Vector normal for point
	 */
	public abstract Vector get_Normal(Point3D point);
	
	
	/**Get emmission 
	 * @return the emmission
	 */
	public Color getEmmission()
	{
		return emmission;
	}
	
	/**
	 * Set emmission 
	 * @param emmission
	 * @return the geometry
	 */
	public Geometry setEmmission(Color emmission)
	{
		 this.emmission = emmission;
		 return this;
	}
	
	/**
	 * get material
	 * @return the material
	 */
	public Material getMaterial()
	{
		return material;
	}

	/**
	 * set material
	 * @param material
	 * @return the geometry
	 */
	public Geometry setMaterial(Material material) 
	{
		this.material = material;
		return this;
	}
	
	
}

