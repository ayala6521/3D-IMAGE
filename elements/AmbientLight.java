package elements;

import primitives.*;

/**
 * class of Ambient light
 *
 */
public class AmbientLight extends Light
{
	//private Color intensity;
	
	/* ********* Constructors ***********/
	/**
	 * CTOR that gets IA color parameter and scale KA- discount factor
	 * 
	 * @param IA color
	 * @param KA a discount factor
	 */
	public AmbientLight(Color IA, double KA)
	{
		super(IA.scale(KA));

	}
	
	
	/**
	 * A default C-TOR, the intensity color is Black
	 */
	public AmbientLight()
	{
		super(Color.BLACK);
	}

	/**
	 * get intensity
	 * @return intensity value
	 */
	//public Color getIntensity() 
	//{
		//return intensity;
	//}

}

