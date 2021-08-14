package elements;
import primitives.*;

public class SpotLight extends PointLight
{
	private Vector direction;
	
	/* ********* Constructors ***********/
	/**
	 * C-TOR that gets the value of all the fields
	 * @param intensity intensity color
	 * @param position position point
	 * @param direction direction vector of the spot
	 */
	public SpotLight(Color intensity, Point3D position, Vector direction) 
	{
		super(intensity, position);
		this.direction = direction.normalized();
	}
	
	/* ************* Get *******/
	@Override
    public Color getIntensity(Point3D p) 
	{
    	double pl =direction.dotProduct(getL(p));//dir*L
    	//if(pl<0) pl=0;     
        if (pl <= 0)
            return Color.BLACK;
        return super.getIntensity(p).scale(pl);
    }

}
