package geometries;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import primitives.Point3D;
import primitives.Ray;
import java.util.LinkedList;


/**Geometries class of collection of intersectables
 * @author Ayala Cohen 212224638 & Shira Shevkes 212433841
 *
 */
public class Geometries implements Intersectable {
	private List<Intersectable> geometries= new LinkedList<>() ;
	   /* ********* Constructors ***********/

 /**
  * A new Container
  * @param geometries the geometries
  * @see Intersectable
  */
 public Geometries(Intersectable ... geometries)
 {
 	this.geometries = new ArrayList<>();
     this.geometries.addAll(Arrays.asList(geometries));
 }
 /**
  * defualt C_tor
  */
 public Geometries() 
 {
 	geometries = new ArrayList<>();
	}
	/* ************* Operations ***************/

 /**
  * get geometries
  * @return geometries list
  */
 public List<Intersectable> getGeometries(){
     return geometries;
 }
 

 /**
  * add geometry
  * @param geometries the geometry
  */
 public void add(Intersectable ... geometries){
     this.geometries.addAll(Arrays.asList(geometries));
 }
	
	  /**
  * 
  *
  * @param ray the ray that intersect the geometries
  * @return list of Point3D that intersect the collection
  */
 @Override
 public List<Point3D> findIntersections(Ray ray) {
     List<Point3D> intersections = null;

     for (Intersectable geo : geometries) 
     {
         List<Point3D> tempIntersections = geo.findIntersections(ray);//list of single geometry
         if (tempIntersections != null) 
         {
             if (intersections == null)//for the first time
                 intersections = new ArrayList<>();
             intersections.addAll(tempIntersections);
         }
     }
     return intersections;
 }

 @Override
 public List<GeoPoint> findGeoIntersections (Ray ray)
 {
     List<GeoPoint> intersections = null;

     for (Intersectable geo : geometries) 
     {
         List<GeoPoint> tempIntersections = geo.findGeoIntersections(ray);//list of single geometry
         if (tempIntersections != null) 
         {
             if (intersections == null)//for the first time
                 intersections = new ArrayList<>();
             intersections.addAll(tempIntersections);
         }
     }
     return intersections;

 }
 
}
