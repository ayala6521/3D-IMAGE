package unittests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.Geometry;
import geometries.Intersectable.GeoPoint;
import primitives.*;

/**
 * @author Ayala Cohen && Shira Shevkes
 *
 */
public class RayTests {

	/**
	 * Test method for {@link primitives.Ray#findClosestPoint(java.util.List)}.
	 */
	@Test
	public void testFindClosestPoint() {
		Ray ray = new Ray(Point3D.ZERO, new Vector(0, 0, 1));
		// ============ Equivalence Partitions Tests ==============
		// TC01:A point in the middle of the list is closest to the beginning of the ray
		List<Point3D> points = List.of(new Point3D(0, 0, 5), new Point3D(0, 0, 0.5), new Point3D(0, 0, 1));
		assertEquals("bad ClosestPoint :point in the middle of the list", new Point3D(0, 0, 0.5),
				ray.findClosestPoint(points));

		// =============== Boundary Values Tests ==================
		// TC11:Empty list (method should return null value)
		assertEquals("bad ClosestPoint :there ais no closest point", null, ray.findClosestPoint(null));
		// TC12:The first point is closest to the beginning of the foundation
		points = List.of(new Point3D(0, 0, 0.5), new Point3D(0, 0, 5), new Point3D(0, 0, 1));
		assertEquals("bad ClosestPoint :point in the beginning of the list", new Point3D(0, 0, 0.5),
				ray.findClosestPoint(points));
		// TC13: The last point is closest to the beginning of the foundation
		points = List.of(new Point3D(0, 0, 5), new Point3D(0, 0, 1), new Point3D(0, 0, 0.5));
		assertEquals("bad ClosestPoint :point in the end of the list", new Point3D(0, 0, 0.5),
				ray.findClosestPoint(points));

	}

	/**
	 * Test method for {@link primitives.Ray#findClosestGeoPoint(java.util.List)}.
	 */
	@Test
	public void testFindClosestGeoPoint() 
	{
		Ray ray = new Ray(Point3D.ZERO, new Vector(0, 0, 1));
		// ============ Equivalence Partitions Tests ==============
		// TC01:A point in the middle of the list is closest to the beginning of the ray
		Geometry g=null;
		List<GeoPoint> points = List.of(new GeoPoint(g, new Point3D(0, 0, 5)),
				new GeoPoint(g, new Point3D(0, 0,0.5)), new GeoPoint(g, new Point3D(0, 0, 1)));
		assertEquals("bad ClosestPoint :point in the middle of the list", new GeoPoint(g, new Point3D(0, 0, 0.5)),
				ray.findClosestGeoPoint(points));

		// =============== Boundary Values Tests ==================
		// TC11:Empty list (method should return null value)
		assertEquals("bad ClosestPoint :there ais no closest point", null, ray.findClosestGeoPoint(null));
		// TC12:The first point is closest to the beginning of the foundation
		points = List.of(new GeoPoint(g, new Point3D(0, 0, 0.5)), new GeoPoint(g, new Point3D(0, 0, 5)),
				new GeoPoint(g, new Point3D(0, 0, 1)));
		assertEquals("bad ClosestPoint :point in the beginning of the list", new GeoPoint(g, new Point3D(0, 0, 0.5)),
				ray.findClosestGeoPoint(points));
		// TC13: The last point is closest to the beginning of the foundation
		points = List.of(new GeoPoint(g, new Point3D(0, 0, 5)), new GeoPoint(g, new Point3D(0, 0, 1)),
				new GeoPoint(g, new Point3D(0, 0, 0.5)));
		assertEquals("bad ClosestPoint :point in the end of the list",new GeoPoint(g, new Point3D(0, 0, 0.5)),
				ray.findClosestGeoPoint(points));

	}

}
