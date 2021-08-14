package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;
/**
 * class of RayTracerBase
 * @author Ayala Cohen 212224638 && Shira Shevkes 212433841
 *
 */
public abstract class RayTracerBase 
{
	
	protected Scene scene;//scene
	
	/**
	 * constructor that gets scene
	 * @param scene
	 */
	public RayTracerBase(Scene scene)
	{
		this.scene=scene;
	}
	
	/**
	 * The function looks for intersections between the ray and the 3D model of the scene, 
	 * searches for the point closest to the ray and returns the color of the point
	 * @param ray a ray
	 * @return the color of the closest point to the ray
	 */
	public abstract Color traceRay(Ray ray); 

}