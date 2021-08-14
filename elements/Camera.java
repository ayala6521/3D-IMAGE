package elements;
import geometries.*;
import primitives.*;
import static primitives.Util.*;
import java.util.List;
import java.util.LinkedList;



/**
 * class of Camera
 * 
 * @author Shira Shevkes & Ayala Cohen
 *
 */

public class Camera 
{
	private Point3D p0;
	private Vector vUp;
	private Vector vTo;
	private Vector vRight;
	private double width;
	private double height; 
	private double distance;
	
	
	//getters
	/**
	 * @return the p0
	 */
	public Point3D getP0()
	{
		return p0;
	}
	/**
	 * @return the vUp
	 */
	public Vector getvUp()
	{
		return vUp;
	}
	/**
	 * @return the vTo
	 */
	public Vector getvTo()
	{
		return vTo;
	}
	/**
	 * @return the vRight
	 */
	public Vector getvRight()
	{
		return vRight;
	}
	/**
	 * @return the width
	 */
	public double getWidth()
	{
		return width;
	}
	/**
	 * @return the height
	 */
	public double getHeight()
	{
		return height;
	}
	/**
	 * @return the distance
	 */
	public double getDistance()
	{
		return distance;
	}
	
	
	/** constructor
	 * @param p0
	 * @param vUp
	 * @param vTo
	 */
	public Camera(Point3D p0, Vector vTo,  Vector vUp) throws IllegalArgumentException
	{
		super();
		if(!isZero(vTo.dotProduct(vUp)))
		{
			throw new IllegalArgumentException ("ERROR, cannot create Camera, vUp and vTo are not vertical");
		}
		this.p0 = p0;
		this.vTo = vTo.normalized();
		this.vUp = vUp.normalized();	
		this.vRight= vTo.crossProduct(vUp).normalize();
	}
	
	
	/**
	 * set View Plane Size
	 * 
	 * @param width
	 * @param height
	 * @return Camera
	 */
	public Camera setViewPlaneSize(double width, double height)
	{
		this.width=width;
		this.height=height;
		return this;
	}
	
	/**
	 * set Distance
	 * 
	 * @param distance
	 * @return Camera
	 */

	public Camera setDistance(double distance)
	{
		this.distance=distance;
		return this;
	}
	
	/**
	 * 
	 * @param nX How many pixels in row
	 * @param nY How many pixels in column
	 * @param j  column
	 * @param i  row
	 * @return
	 */
	public Ray constructRayThroughPixel(int nX, int nY, int j, int i) throws IllegalArgumentException
	{
		if(isZero(distance))
			
		{
			throw new IllegalArgumentException ("ERROR, the distance cannot be 0");
		}
		
		Point3D Pc=p0.add(vTo.scale(distance));
		
		double Ry=height/nY; //height of each pixel
		double Rx= width/nX; //width of each pixel
		
		double yi = (i - (nY - 1) / 2d) * Ry;
		double xj = (j - (nX - 1) / 2d) * Rx;
		
		Point3D Pij=Pc;
		
		if(!isZero(xj))
		{
			Pij=Pij.add(vRight.scale(xj));
		}
		
		if (!isZero(yi))
		{
			Pij = Pij.add(vUp.scale(-yi));
		}
		
		Vector Vij = Pij.subtract(p0);

		return new Ray(p0, Vij);
	}
	
	 /**
     * constructRayThroughPixel - find the ray within the camera and pixel
     * @param nX number of pixels in x axis
     * @param nY number of pixels in y axis
     * @param j the wanted pixel coordinate
     * @param i the wanted pixel coordinate
     * @param screenDistance the distance between the camera and the view plane
     * @param screenWidth the view plane width
     * @param screenHeight the view plane height
     * @param numOfRays the number of rays for each pixel
     * @return list of rays for pixel
     */
    public List<Ray> constructRaysThroughPixel (int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight, int numOfRays) throws Exception {
        int numOfRaysInRowCol = (int)Math.ceil(Math.sqrt(numOfRays));
        if(numOfRaysInRowCol == 1)
            return List.of(constructRayThroughPixel(nX, nY, j, i));
        Point3D Pc;
        if (screenDistance != 0)
            Pc = this.p0.add(this.vTo.scale(screenDistance));
        else
            Pc = this.p0;
        Point3D Pij = Pc;
        double Ry = screenHeight/nY;
        double Rx = screenWidth/nX;
        double Yi= (i - (nY/2d))*Ry + Ry/2d;
        double Xj= (j - (nX/2d))*Rx + Rx/2d;
        if(Xj != 0) Pij = Pij.add(this.vRight.scale(-Xj)) ;
        if(Yi != 0) Pij = Pij.add(this.vUp.scale(-Yi)) ;

        double PRy = Ry/numOfRaysInRowCol;
        double PRx = Rx/numOfRaysInRowCol;
        List rays = new LinkedList<Ray>();

        //the center of the pixel
        Point3D tempPij = Pij;

        //Split the pixel to a grid
        for (int r = 0; r < numOfRaysInRowCol; r++){
            double PXj= (r - (numOfRaysInRowCol/2d))*PRx + PRx/2d;
            for(int c = 0; c < numOfRaysInRowCol; c++)
            {
                double PYi= (c - (numOfRaysInRowCol/2d))*PRy + PRy/2d;
                if(PXj != 0) Pij = Pij.add(this.vRight.scale(-PXj)) ;
                if(PYi != 0) Pij = Pij.add(this.vUp.scale(-PYi)) ;
                rays.add(new Ray(this.p0, Pij.subtract(this.p0)));
                Pij = tempPij;
            }
        }
        return rays;
    }
	
	

}
