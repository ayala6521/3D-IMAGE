package primitives;
import java.util.Objects;


public class Vector 
{
	Point3D head;

	/** 
	 * c-tor from Point3D
	 * @param head Point3D
	 */
	public Vector(Point3D head) throws IllegalArgumentException 
	{
		super();
		this.head = head;
		if(head.equals(Point3D.ZERO))
			throw new IllegalArgumentException("Cannot create zero vector");
	}

	/**
	 * c-tor from 3 coordinate	
	 * @param x coord x
	 * @param y coord y
	 * @param z coord z
	 * @throws IllegalArgumentException
	 */
	public Vector(Coordinate x,Coordinate y,Coordinate z) throws IllegalArgumentException 
	{
		super();
		Point3D point=new Point3D(x,y,z);
		if(point.equals(Point3D.ZERO))
			throw new IllegalArgumentException("Cannot create zero vector");
		this.head = point;
	}
	
	/**
	 * c-tor that gets 3 double value
	 * @param x x value
	 * @param y y value
	 * @param z z value
	 * @throws IllegalArgumentException
	 */
	public Vector(double x,double y,double z) throws IllegalArgumentException 
	{
		super();
		Point3D point=new Point3D(x,y,z);
		if(point.equals(Point3D.ZERO))
			throw new IllegalArgumentException("Cannot create zero vector");
		this.head = point;
	}
	
	/**
	 * getter
	 * @return the head point
	 */
	public Point3D getHead()
	{
		return head;
	}
	
	//equals
	@Override
	public boolean equals(Object obj)
	{
	      if (this == obj) return true;
	      if (obj == null) return false;
	      if (!(obj instanceof Vector)) return false;
	      Vector other = (Vector)obj;
	      return head.equals(other.head);
    }
	
	/**
	 * adding two vectors
	 * @param otherVector other vector
	 * @return new vector after adding
	 * @throws IllegalArgumentException
	 */
	public Vector add(Vector otherVector) throws IllegalArgumentException
	{
		return new Vector(head.add(otherVector));
	}
	
	/**
	 * subtract between two vectors	
	 * @param otherVector other vector
	 * @return new vector after subtract
	 * @throws IllegalArgumentException
	 */
	public Vector subtract(Vector otherVector) throws IllegalArgumentException
	{
		return head.subtract(otherVector.head);
	}
	
	/**
	 *  scale product
	 * @param scaleNum a scale number
	 * @return new vector after scale product
	 * @throws IllegalArgumentException
	 */
	public Vector scale(double scaleNum) throws IllegalArgumentException
	{
		return new Vector(scaleNum*head.x.coord, scaleNum*head.y.coord, scaleNum*head.z.coord);
	}
	
	/**
	 * dot product  
	 * @param otherVec other vector
	 * @return new vector after dot product
	 */
	public double dotProduct(Vector otherVec)
	{
		return head.x.coord*otherVec.getHead().x.coord+head.y.coord*otherVec.getHead().y.coord+head.z.coord*otherVec.getHead().z.coord;
	}
	
	/**
	 * cross product	
	 * @param otherVec other vector
	 * @return new vector after cross product
	 * @throws IllegalArgumentException
	 */
	public Vector crossProduct(Vector otherVec) throws IllegalArgumentException
	{
		return new Vector(head.y.coord*otherVec.head.z.coord - head.z.coord*otherVec.head.y.coord,
				head.z.coord*otherVec.head.x.coord - head.x.coord*otherVec.head.z.coord,
				head.x.coord*otherVec.head.y.coord - head.y.coord*otherVec.head.x.coord);
	}
	
	/**
	 * length square
	 * @param 
	 * @return the length squared of the vector
	 */
	public double lengthSquared()
	{
		return this.dotProduct(this);
	}
	
	/**
	 * length 
	 * @param 
	 * @return the length of the vector
	 */
	public double length()
	{
		return Math.sqrt(this.lengthSquared());
	}
	
	/**
	 * normalize
	 * @param 
	 * @return 
	 */
	public Vector normalize()
	{
		//return scale(1/length());
		double length=this.length();
		head=new Point3D(head.x.coord/length,head.y.coord/length,head.z.coord/length);
		return this;
	}
	
	public Vector normalized()
	{
		return new Vector(this.head).normalize();
	}

	//toString
	@Override
	public String toString()
	{
		return "Vector [" + head!=null ? "head=" + head : "" + "]";
	}
	
	/**
     * find Random Orthogonal func
     * return an orthogonal vector
     */
    public Vector findRandomOrthogonal() throws Exception
    {
        double x = this.getHead().getX();
        double y = this.getHead().getY();
        double z = this.getHead().getZ();
        double Ax= Math.abs(x), Ay= Math.abs(y), Az= Math.abs(z);
        if (Ax < Ay)
            return Ax < Az ?  new Vector(0, -z, y) : new Vector(-y, x, 0);
        else
            return Ay < Az ?  new Vector(z, 0, -x) : new Vector(-y, x, 0);
    }

	

	
	
	

	
	
	
}


