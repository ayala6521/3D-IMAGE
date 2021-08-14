/**
 * 
 */
package unittests;

import static org.junit.Assert.*;
import geometries.*;
import primitives.*;
import org.junit.Test;
import java.util.List;
import java.util.LinkedList;
/**
 * @author Ayala Cohen 212224638 Shira Shevkes 212433841
 *
 */
public class SphereTests 
{

	/**
	 * Test method for {@link geometries.Sphere#get_Normal(primitives.Point3D)}.
	 */
	@Test
	public void testGet_Normal()
	{
		// ============ Equivalence Partitions Tests ==============
		Point3D p=new Point3D(1,1,8);
		Point3D o=new Point3D(1,1,2);
		Sphere s=new Sphere(o,6);
		Vector v=p.subtract(o).normalize();
		assertEquals("Bad normal to sphere",v,s.get_Normal(p));		
	}
	
	/**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(new Point3D(1, 0, 0), 1d);;

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertEquals("Ray's line out of sphere", null,sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Point3D> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),new Vector(3, 1, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(p1, p2), result);

        // TC03: Ray starts inside the sphere (1 point)
        Sphere sphere2=new Sphere(new Point3D(0,-1,0),2d);
        Ray ray = new Ray (new Point3D(-1,0,0),new Vector(3,-1,0));
        result=(List<Point3D>) sphere2.findIntersections(ray);
        List<Point3D> exepted = new LinkedList<Point3D>();
        exepted.add(new Point3D(2,-1,0));
        assertEquals(exepted,result);
        
        // TC04: Ray starts after the sphere (0 points)
    	ray= new Ray (new Point3D(-3,0,0),new Vector(-1,0,0));
    	result= sphere2.findIntersections(ray);
    	assertEquals(null,result);

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
    	
    	ray = new Ray (new Point3D(0,-3,0),new Vector(0,2,2));
    	exepted.clear();
    	exepted.add(new Point3D(0,-1,2));
    	result=sphere2.findIntersections(ray);
    	assertEquals(exepted,result);
    	
        // TC12: Ray starts at sphere and goes outside (0 points)
    	ray = new Ray (new Point3D(0,-3,0),new Vector(0,-2,-2));
    	result=sphere2.findIntersections(ray);
    	assertEquals(null,result);
    	
        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
    	ray = new Ray (new Point3D(0,-4,0),new Vector(0,7,0));
    	exepted.clear();
    	exepted.add(new Point3D(0,-3,0));
    	exepted.add(new Point3D(0,1,0));
    	result=sphere2.findIntersections(ray);
    	assertEquals(exepted,result);
        // TC14: Ray starts at sphere and goes inside (1 points)
    	ray = new Ray (new Point3D(0,1,0),new Vector(0,-4,0));
    	exepted.clear();
    	exepted.add(new Point3D(0,-3,0));
    	result=sphere2.findIntersections(ray);
    	assertEquals(exepted,result);

        // TC15: Ray starts inside (1 points)
    	ray = new Ray (new Point3D(0,-2,0),new Vector(0,5,0));
    	exepted.clear();
    	exepted.add(new Point3D(0,1,0));
    	result= sphere2.findIntersections(ray);
    	assertEquals(exepted,result);
    	
        // TC16: Ray starts at the center (1 points)
    	ray=new Ray(new Point3D(1, 0, 0),new Vector(0, 1, 0));
    	exepted.clear();
    	exepted.add(new Point3D(1,1,0));
    	result =sphere.findIntersections(ray);
    	assertEquals(exepted,result);
    	        
        // TC17: Ray starts at sphere and goes outside (0 points)
    	ray = new Ray (new Point3D(0,1,0),new Vector(0,11,0));
    	result= sphere2.findIntersections(ray);
    	assertEquals(null,result);
    	
        // TC18: Ray starts after sphere (0 points)
    	ray= new Ray (new Point3D(0,4,0),new Vector(0,11,0));
    	result= sphere2.findIntersections(ray);
    	assertEquals(null,result);

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
    	ray = new Ray (new Point3D(-1,1,0),new Vector(2,0,0));
    	result=sphere2.findIntersections(ray);
    	assertEquals(null,result);
    	
        // TC20: Ray starts at the tangent point
    	ray = new Ray (new Point3D(0,1,0),new Vector(2,0,0));
    	result=sphere2.findIntersections(ray);
    	assertEquals(null,result);
    	
        // TC21: Ray starts after the tangent point
    	ray = new Ray (new Point3D(1,1,0),new Vector(2,0,0));
    	result=sphere2.findIntersections(ray);
    	assertEquals(null,result);

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
    	ray = new Ray (new Point3D(0,6,0),new Vector(2,0,0));
    	result=sphere2.findIntersections(ray);
    	assertEquals(null,result);
    }

}
