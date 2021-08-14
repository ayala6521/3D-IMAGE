package elements;
import primitives.Color;


/**
 * class of light
 * @author Shira Shevkes and Ayala Cohen
 *
 */

abstract class Light
{
	private Color intensity ;
	
	 /**
	  * C-TOR that gets intensity color
	  * @param intensity intensity color
	  */
	 protected Light(Color intensity)
	 {
		this.intensity = intensity;
	}

	 /**
	  * get intensity
	  * @return intensity color
	  */
	public Color getIntensity()
	{
		return intensity;
	}


}
