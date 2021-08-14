package unittests;

import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
import static org.junit.Assert.*;

/** Unit tests for geometries.Triangle class
 * @author Ayala Cohen 212224638 & Shira Shevkes 212433841
 *
 */
public class TriangleTests {

	/**checks getNormal function
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		Triangle tri = new Triangle(new Point3D(2,0,0),new Point3D(0,2,0),new Point3D(0,0,0));
        assertEquals(new Vector(0,0,1),tri.get_Normal(null));
	}

	/**
	 * checks findIntersections
	 * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Triangle tr = new Triangle(new Point3D(0, 3, -3),new Point3D(3, 0, -3),new Point3D(-3, 0, -3));
        Ray r;
        // ============ Equivalence Partitions Tests ==============
        //TC01: the ray goes through the triangle
        r = new Ray(new Point3D(1, 1, -2), new Vector(-2, 0.5, -1));
        assertEquals("the ray goes through the triangle", List.of(new Point3D(-1, 1.5, -3)), tr.findIntersections(r));
        //TC02: the ray is outside the triangle between 2 far sides
        r = new Ray(new Point3D(4, 4, -2), new Vector(1, 1, -4));
        assertEquals("the ray is outside the triangle between 2 far sides", null, tr.findIntersections(r));
        //TC03: the ray is outside the triangle between 2 close sides
        r = new Ray(new Point3D(-4, -1, -2), new Vector(-1, -1, -1));
        assertEquals("the ray is outside the triangle between 2 close sides", null, tr.findIntersections(r));


        // =============== Boundary Values Tests ==================
        //TC11: ray goes through the continuation of side 1
        r = new Ray(new Point3D(-1, 4, -2), new Vector(0, 0, -1));
        assertEquals("ray goes through the continuation of side 1", null, tr.findIntersections(r));
        //TC12: ray through edge
        r = new Ray(new Point3D(-2, 1, -1), new Vector(0, 0, -1));
        assertEquals("ray through edge", null, tr.findIntersections(r));
        //TC13: ray through vertex kodkod ]][[[
        r = new Ray(new Point3D(0, 3, -2), new Vector(0, 0, -1));
        assertEquals("ray through vertex", null, tr.findIntersections(r));
	}

}
