/**
 * 
 */
package unittests;
import static org.junit.Assert.*;
import org.junit.Test;
//import primitives.Vector;
import static primitives.Util.*;
import primitives.*;

/**
 * @author Ayala Cohen 212224638 Shira Shevkes 212433841
 *
 */
public class VectorTests
{

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	public void testAdd()
	{
		// ============ Equivalence Partitions Tests ==============
		Vector v1=new Vector (1,2,3);
		Vector v2=new Vector (1,-4,3);
		assertEquals("Wrong add",new Vector(2,-2,6),v1.add(v2));
		
		// =============== Boundary Values Tests ==================
		try
		{
			Vector v3=new Vector(-1,-1,-1);
			Vector v4=new Vector(1,1,1);
			v3.add(v4);
			fail("Vector (0,0,0) shouldnt be valid");
			
		}
		
		catch (IllegalArgumentException e)
		{
			assertTrue(e.getMessage()!=null);
		}
			
	}
				
	/**
	 * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
	 */
	@Test
	public void testSubtract()
	{
		// ============ Equivalence Partitions Tests ==============
		Vector v1=new Vector(1,2,3);
		Vector v2=new Vector(1,-4,3);
		assertEquals("Wrong Subtract", v1.subtract(v2), new Vector(0, 6, 0));
		
		// =============== Boundary Values Tests ==================
		try
		{
			Vector v3=new Vector (1,1,1);
			Vector v4=new Vector (1,1,1);
			v3.subtract(v4);
			fail("Vector (0,0,0) shouldnt be valid");
		}
		
		catch (IllegalArgumentException e)
		{
			assertTrue(e.getMessage()!=null);
			
		}
    }

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	public void testScale()
	{
		// ============ Equivalence Partitions Tests ==============
		Vector v1=new Vector(1,2,3);
		assertEquals("Wrong Scale",new Vector(2,4,6),v1.scale(2));
		// =============== Boundary Values Tests ==================
		try 
		{
			v1.scale(0);
			fail("Vector (0,0,0) shouldnt be valid");
		}
		catch (IllegalArgumentException e)
		{
			assertTrue(e.getMessage()!=null);
			
		}
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	public void testDotProduct() 
	{
		// ============ Equivalence Partitions Tests ==============
		Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        assertTrue("The dotProduct for orthogonal vectors is not zero",isZero(v1.dotProduct(v3)));
        assertTrue("dotProduct wrong value",isZero(v1.dotProduct(v2)+28));	
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	public void testCrossProduct()
	{
		// ============ Equivalence Partitions Tests ==============
		Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);
        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("CrossProduct() wrong result length",v1.length()*v3.length(),vr.length(),0.00001);
        // Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to its operands",isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to its operands",isZero(vr.dotProduct(v3)));
        // =============== Boundary Values Tests ==================
        // test zero vector from cross-productof co-lined vectors
        try
        {
        	 v1.crossProduct(v2);
        	 fail("crossProduct() for parallel vectors does not throw an exception");
        }
        catch(Exception e) {}	
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	public void testLengthSquared() 
	{
		// ============ Equivalence Partitions Tests ==============
		Vector v1 = new Vector(1, 2, 3);
		assertTrue("lengthSquared() wrong value",isZero(v1.lengthSquared() - 14));
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	public void testLength()
	{
		// ============ Equivalence Partitions Tests ==============
		Vector v1 = new Vector(0, 3, 4);
		assertTrue("length() wrong value",isZero(v1.length() - 5));
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	public void testNormalize() 
	{
		// ============ Equivalence Partitions Tests ==============
		Vector v = new Vector(1, 2, 3);
		Vector vNormalize = v.normalize();
		assertTrue("normalize() function creates a new vector", v == vNormalize);
		assertTrue("normalize() result is not a unit vector", isZero(vNormalize.length() - 1));
	}

	/**
	 * Test method for {@link primitives.Vector#normalized()}.
	 */
	@Test
	public void testNormalized() 
	{
		// ============ Equivalence Partitions Tests ==============
		Vector v = new Vector(1, 2, 3);
		Vector u = v.normalized();
		assertTrue("normalizated() function did not create a new vector", v != u);
	}
}
