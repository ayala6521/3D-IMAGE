/**
 * 
 */
package unittests;

import static org.junit.Assert.*;
import geometries.*;
import primitives.*;
import org.junit.Test;

/**
 * @author Ayala Cohen 212224638 Shira Shevkes 212433841
 *
 */
public class CylinderTests
{
	/**
	 * Test method for {@link geometries.Cylinder#get_Normal(primitives.Point3D)}.
	 */
	@Test
	public void testGet_Normal() 
	{
		Cylinder cyl=new Cylinder(new Ray(Point3D.ZERO, new Vector(0,0,1)),5,3);
		// ============ Equivalence Partitions Tests ==============
		//TC01: test for points on the cylinder's side
		assertEquals("Bad normal to cylinder-for points to the cylinder's side", new Vector(1,0,0),cyl.get_Normal(new Point3D(5,0,2.5)));
		//TC02: test for points on the FIRST base(where the point P0)
		assertEquals("Bad normal to cylinder-for points to the cylinder's first base", new Vector(0,0,-1),cyl.get_Normal(new Point3D(1,2,0)));
		assertEquals("Bad normal to cylinder-for points to the cylinder's first base", new Vector(0,0,-1),cyl.get_Normal(Point3D.ZERO));
		//TC03: test for points on the SECOND base
		assertEquals("Bad normal to cylinder-for points to the cylinder's second base", new Vector(0,0,1),cyl.get_Normal(new Point3D(1,2,3)));
		assertEquals("Bad normal to cylinder-for points to the cylinder's second base", new Vector(0,0,1),cyl.get_Normal(new Point3D(0,0,3)));
		// =============== Boundary Values Tests ==================
		//TC02: test for points on the border between the cylinde's side and the first base
		assertEquals("Bad normal to cylinder-for points to the cylinder's first base", new Vector(0,1,0),cyl.get_Normal(new Point3D(0,5,0)));
		//TC02: test for points on the border between the cylinde's side and the second base
		assertEquals("Bad normal to cylinder-for points to the cylinder's second base", new Vector(0,1,0),cyl.get_Normal(new Point3D(0,5,3)));		
	}

}
