package primitives;



public  class Point3D 
{
	public static Point3D ZERO= new Point3D(0,0,0); //zero static
	final Coordinate x;
	final Coordinate y;
	final Coordinate z;

	/** Point3D constructor receiving a 3 coordinate value of x,y,z values
	 * @param x x value
	 * @param y y value
	 * @param z z value
	 */
	public Point3D(Coordinate x, Coordinate y, Coordinate z) // constructor
	{
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**Point3D constructor receiving a 3 double value of x,y,z values
	 * @param x x value
	 * @param y y value
	 * @param z z value
	 */
	public Point3D(double x, double y, double z) //constructor
	{
		super();
		this.x = new Coordinate(x);
		this.y = new Coordinate(y);
		this.z = new Coordinate(z);
	}

	
	// equals
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Point3D))
			return false;
		Point3D other = (Point3D) obj;
		return x.equals(other.x) && y.equals(other.y) && z.equals(other.z);
	}

	// toString
	@Override
	public String toString()
	{
		return "Point3D [" + (x!=null?"x="+x+",":"")+(y!=null?"y="+y+",":"")+(z!=null?"z="+z+",":"")+"]";
	}
	
	/**
	 * subtract between two points and return vector
	 * @param otherPoint the second point
	 * @return new vector after subtract
	 * @throws IllegalArgumentException
	 */
	public Vector subtract(Point3D otherPoint) throws IllegalArgumentException
	{
		double newX= x.coord-otherPoint.x.coord;
		double newY=y.coord-otherPoint.y.coord;
		double newZ= z.coord-otherPoint.z.coord;
		return new Vector(newX,newY,newZ);
	}
	
	/**
	 * add between vector and point and return point
	 * @param otherVector the vector
	 * @return new Point3D after the add action
	 */
	public Point3D add(Vector otherVector)
	{
		double newX=otherVector.getHead().x.coord+x.coord;
		double newY=otherVector.getHead().y.coord+y.coord;
		double newZ=otherVector.getHead().z.coord+z.coord;
		return new Point3D(newX,newY,newZ);
	}
	
	/**
	 * distance squared
	 * @param otherPoint other point
	 * @return the distance squared
	 */
	public double distanceSquared(Point3D otherPoint)
	{
		double disX=otherPoint.x.coord-x.coord;
		double disY=otherPoint.y.coord-y.coord;
		double disZ=otherPoint.z.coord-z.coord;
		return disX*disX+disY*disY+disZ*disZ;

	}
	/**
	 * distance between two points
	 * @param otherPoint other point
	 * @return the distance between them
	 */
	public double distance(Point3D otherPoint)
	{
		return Math.sqrt(distanceSquared(otherPoint));
	}

	/**
	 * 
	 * @return x double
	 */
	public double getX()
	{
		return x.coord;
	}
	
	/**
	 * 
	 * @return y double
	 */
	public double getY()
	{
		return y.coord;
	}
	
	/**
	 * 
	 * @return z double
	 */
	public double getZ()
	{
		return z.coord;
	}

	 /**
     * Checks whether the different between the points is [almost] zero
     * @param point
     * @return true if the different between the points is zero or almost zero, false otherwise
     */
    public  boolean isAlmostEquals(Point3D point) {

        return  (Util.isZero(this.getX()-point.getX())) &&
                (Util.isZero(this.getY()-point.getY())) &&
                (Util.isZero(this.getZ()-point.getZ()));
    }

}
