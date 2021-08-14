package scene;

import primitives.*;
import elements.AmbientLight;
import geometries.Geometries;
import java.util.LinkedList;
import java.util.List;
import elements.LightSource;

/**
 * class of Scene
 * @author Ayala Cohen && Shira Shevkes
 *
 */
public class Scene
{

	public String name;
	public Color background = Color.BLACK;
	public AmbientLight ambientLight = new AmbientLight(Color.BLACK, 1);
	public Geometries geometries;
	public List<LightSource> lights=new LinkedList<LightSource>();

	/**
	 * CTOR
	 * @param name
	 */
	public Scene(String name) 
	{
		this.name = name;
		this.geometries = new Geometries();

	}
	 /**
     * set scene background
     * @param background the background color
     */
    public Scene setBackground(Color background)
    {
        this.background = background;
        return this;
    }

    /**
     * set scene light
     * @param light the light
     */
    public Scene setAmbientLight(AmbientLight light)
    {
        this.ambientLight = light;
        return this;

    }
    /**
     * set scene geometries
     * @param light the geometries
     */
    public Scene setGeometries(Geometries geometries)
    {
        this.geometries = geometries;
        return this;

    }
    
    /**
     * set scene lights
     * @param lights list of lights
     * @return the scene
     */
	public Scene setLights(List<LightSource> lights)
	{
		this.lights = lights;
		return this;
	}

}
