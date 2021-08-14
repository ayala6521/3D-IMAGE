package unittests;

import static org.junit.Assert.*;
import org.junit.Test;
import geometries.Geometries;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**Tests for Geometries class
 * @author Ayala Cohen 212224638 & Shira Shevkes 212433841
 *
 */
public class GeometriesTests 
{	
	/**
	 * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() 
	{
		Plane plane = new Plane(new Point3D(0,0,0),new Vector(1,0,0));
		Triangle triangle = new Triangle(new Point3D(2,0,1),new Point3D(2,0,-1),new Point3D(2,1,0));
		Sphere sphere = new Sphere(new Point3D(4,0,0),1d);
		Polygon polygon = new Polygon(new Point3D(9,0,1),new Point3D(9,3,1),new Point3D(9,3,-1),new Point3D(9,0,-1));
		Geometries _geometries = new Geometries(plane, triangle, sphere, polygon);
		Geometries emptyGeometries = new Geometries();//an empty list of geometries
		   
		   
		    // ============ Equivalence Partitions Tests ==============

		    //TC01: a few geometries have intersection points
		    //the ray cross the plane and polygon
		    assertEquals("Some geometries have intersection points",2,
		      _geometries.findIntersections(new Ray( new Point3D(-1,2,0),new Vector(1, 0, 0))).size());

		     
		    // =============== Boundary Values Tests ==================
		       
		    //TC11: empty list of geometries
		    assertEquals("An empty list",null,
		       emptyGeometries.findIntersections(new Ray(new Point3D(1,1,0), new Vector(1, 0, 0))));
		    //TC12: no geometry has intersection points
		    assertEquals("Non geometry has intersection points",null,
		       _geometries.findIntersections(new Ray(new Point3D(-1,0,0), new Vector(0,1, 0))));
		    //TC13: one geometry has intersection points with the sphere
		    assertEquals("one geometry has intersection points", 2,_geometries.findIntersections(new Ray(new Point3D(2.5,0,0), new Vector(4,0,0))).size());
			//TC14: all geometries has intersection points
		    assertEquals("All geometries has intersection points", 5,
		       _geometries.findIntersections(new Ray(new Point3D(-1,0.5,0), new Vector(1,0,0))).size());
	}

}
