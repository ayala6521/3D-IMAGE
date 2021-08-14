/**
 * 
 */
package unittests;

import static org.junit.Assert.*;
import org.junit.Test;
import static primitives.Util.*;
import primitives.*;
import geometries.Tube;
/**
 * @author Ayala Cohen 212224638 Shira Shevkes 212433841
 *
 */
public class TubeTests 
{

	/**
	 * Test method for {@link geometries.Tube#get_Normal(primitives.Point3D)}.
	 */
	@Test
	public void testGet_Normal()
	{
		// ============ Equivalence Partitions Tests ==============
		Tube tube = new Tube(new Ray(Point3D.ZERO, new Vector(1, 0, 0)), 5);
		assertEquals("Bad normal to tube", new Vector(0, 0, 1), tube.get_Normal(new Point3D(1, 0, 5)));
		// =============== Boundary Values Tests ==================
		// if the point is parallel to the head of the ray
		assertEquals("bad normal to tube", new Vector(0, 0, 1), tube.get_Normal(new Point3D(0, 0, 5)));
				
	}

}
