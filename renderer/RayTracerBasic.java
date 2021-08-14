package renderer;
import geometries.Intersectable.GeoPoint;
import java.util.List;
import primitives.*;
import elements.*;
import geometries.*;
import scene.Scene;
import java.util.LinkedList;
import static primitives.Util.*;

/**
 * class of RayTracerBasic
 * @author Ayala Cohen && Shira Shevkes
 *
 */
public class RayTracerBasic extends RayTracerBase 
{
	private static final int MAX_CALC_COLOR_LEVEL = 5;
	private static final double MIN_CALC_COLOR_K = 0.001;
    //private int numOfRaysForDiffusedAndGlossy = 36;

   
    /**
     * Multiple reflection and refraction rays
     * distanceOfGrid - the distance between the object and the grid for reflection and refraction
     * numOfRaysForDiffusedAndGlossy - number of rays for reflection and refraction
     * AdaptiveDiffusedAndGlossy - Flag to choose whether to apply the Adaptive Diffused And Glossy
     */
    private double distanceForDiffusedAndGlossy = 100;
    private int maxRaysForDiffusedAndGlossy = 500;
    private boolean AdaptiveDiffusedAndGlossyFlag = true;
    
	/**
	 * @param distanceForDiffusedAndGlossy the distanceForDiffusedAndGlossy to set
	 */
	public void setDistanceForDiffusedAndGlossy(double distanceForDiffusedAndGlossy) {
		this.distanceForDiffusedAndGlossy = distanceForDiffusedAndGlossy;
	}

	///**
	 //* @param numOfRaysForDiffusedAndGlossy the numOfRaysForDiffusedAndGlossy to set
	 //*/
	//public void setNumOfRaysForDiffusedAndGlossy(int numOfRaysForDiffusedAndGlossy) {
		//this.numOfRaysForDiffusedAndGlossy = numOfRaysForDiffusedAndGlossy;
	//}
	
    /**
     * set Max Rays For Diffused And Glossy
     * the maximum rays for reflection and refraction
     */
    public void setMaxRaysForDiffusedAndGlossy(int maxRaysForDiffusedAndGlossy) {
        this.maxRaysForDiffusedAndGlossy = maxRaysForDiffusedAndGlossy;
    }
	
    /**
     * set Adaptive Diffused And Glossy Flag
     * Flag to choose whether to apply the Adaptive Diffused And Glossy
     */
    public void setAdaptiveDiffusedAndGlossyFlag(boolean adaptiveDiffusedAndGlossyFlag) {
        AdaptiveDiffusedAndGlossyFlag = adaptiveDiffusedAndGlossyFlag;
    }

    
	/**
	 * constructor that gets a scene
	 * @param scene
	 */
	public RayTracerBasic(Scene scene)
	{
		super(scene);
	}
	
	/**
	 * calculate the color of a point
	 * 
	 * @param geopoint - a point of intersection
	 * @param ray      - ray of a center of pixel
	 * @return the color of the point by phong model + reflacted and refracted
	 */
	private Color calcColor(GeoPoint geopoint, Ray ray) 
	{
		return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, 1.0).add(scene.ambientLight.getIntensity());
	}
	
	
	/**
	 * calculate the color of a point, recursion
	 * 
	 * @param intersection - a point of intersection
	 * @param ray          - ray of a center of pixel
	 * @param level        - level of the recursion
	 * @param k            - The multiplication factor of kR and kT
	 * @return local effect + global effect
	 */
	private Color calcColor(GeoPoint intersection, Ray ray, int level, double k)
	{
		Color color = intersection.geometry.getEmmission();
		color = color.add(calcLocalEffects(intersection, ray,k));
		return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
	}
	
	/**
	 * calc Local Effects - diffusive and Specular
	 * 
	 * @param intersection - GeoPoint intersection
	 * @param ray          - Ray of a center of pixel
	 * @return calculate the part of phong model :( kd*|l*n| +ks*(-v*r)^nsh)*IL
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray,double k) 
	{
		Vector v = ray.getDir();// ray direction
		Vector n = intersection.geometry.get_Normal(intersection.point);
		double nv = Util.alignZero(n.dotProduct(v));
		if (Util.isZero(nv))// there is no diffusive and Specular
			return Color.BLACK;
		int nShininess = intersection.geometry.getMaterial().nShininess;
		double kd = intersection.geometry.getMaterial().kD;
		double ks = intersection.geometry.getMaterial().kS;
		Color color = Color.BLACK;
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(intersection.point);
			double nl = Util.alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sing(nv)
				double ktr = transparency( l, n, intersection,lightSource);
				if (ktr * k > MIN_CALC_COLOR_K) {
					Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
					color = color.add(calcDiffusive(kd, nl, lightIntensity),
							calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
				}
			}
		}
		return color;
	}
	
	/**
	 * calc Global Effects - reflected and refracted
	 * 
	 * @param geopoint - geopoint
	 * @param ray      - Ray of a center of pixel
	 * @param level    - level of the recursion
	 * @param k        - The multiplication factor of kR and kT
	 * @return color after global effect
	 */
	private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, double k) {
		if (level == 1 || k < MIN_CALC_COLOR_K) {
			return Color.BLACK;
		}
		Color color = Color.BLACK;
		Vector n = geopoint.geometry.get_Normal(geopoint.point);
		Material material = geopoint.geometry.getMaterial();
		// calculate reflectedd השתקפות
		double kr = material.kR;
		double kkr = k * kr;
		if (kkr > MIN_CALC_COLOR_K) {
			try {
				if(AdaptiveDiffusedAndGlossyFlag)//if adaptive Diffused And Glossy is on
	            {
	                // calc the reflected vector
	                double nv1 = ray.getDir().dotProduct(n);
	                Vector v1 = ray.getDir();
	                Vector ref = v1.add(n.scale(-2*nv1));
	                //calc the color
	                color = color.add(AdaptiveDiffusedAndGlossy(n,geopoint.point,ref,-1, material.get_DiffusedAndGlossy(),level,kkr,kr).scale(kr));
	            }
	            else
	            {
	                List<Ray> reflectedRays = constructReflectedRays(n, geopoint.point,ray, material.get_DiffusedAndGlossy());
	                primitives.Color tempColor1 = primitives.Color.BLACK;
	                //calculate for each ray
	                for(Ray reflectedRay: reflectedRays)
	                {
	                    GeoPoint gp = findClosestIntersection(reflectedRay);
	                    tempColor1 = tempColor1.add(gp == null ? primitives.Color.BLACK : calcColor(gp, reflectedRay, level - 1, kkr).scale(kr));
	                }
	                color = color.add(tempColor1.reduce(reflectedRays.size()));
	            }
			}
			catch(Exception e)
			{
				return geopoint.geometry.getEmmission();
			}
			
			
		}
		// calculate refracted שקיפות
		double kt = material.kT;
		double kkt = k * kt;
		if (kkt > MIN_CALC_COLOR_K) {
			try {
				if(AdaptiveDiffusedAndGlossyFlag)//if adaptive Diffused And Glossy is on
				{
					color = color.add(AdaptiveDiffusedAndGlossy(n,geopoint.point,ray.getDir(),1, material.get_DiffusedAndGlossy(),level,kkt,kt).scale(kt));
				}
				else
				{
					List<Ray> refractedRays = constructRefractedRays(n, geopoint.point,ray, material.get_DiffusedAndGlossy());
					primitives.Color tempColor2 = primitives.Color.BLACK;
					//calculate for each ray
					for(Ray refractedRay: refractedRays)
					{
						GeoPoint gp = findClosestIntersection(refractedRay);
						tempColor2 = tempColor2.add(gp == null? primitives.Color.BLACK : calcColor(gp, refractedRay, level -1, kkt).scale(kt));
					}
					color = color.add(tempColor2.reduce(refractedRays.size()));
				}
			}
			catch(Exception e)
			{return geopoint.geometry.getEmmission();}
	
		}
		return color;
	}
	
	 /**
     * calcColor - return the  color for ray
     * @param ray ray throw pixel
     * @return the color in the point
     */
    private primitives.Color calcColor(Ray ray) throws Exception {
        GeoPoint gp;
            gp = findClosestIntersection(ray);
            if(gp == null)
                return this.scene.background;
            else
                return calcColor(gp, ray);
    }
	
	/**
	 * Calculate Specular component of light reflection.
	 *
	 * @param ks         specular component coef
	 * @param l          direction from light to point
	 * @param n          normal to surface at the point
	 * @param nl         dot-product n*l
	 * @param v          direction from point of view to point
	 * @param nShininess shininess level
	 * @param ip         light intensity at the point
	 * @return specular component light effect at the point
	 *         <p>
	 *         Finally, the Phong model has a provision for a highlight, or
	 *         specular, component, which reflects light in a shiny way. This is
	 *         defined by [rs,gs,bs](-V.R)^p, where R is the mirror reflection
	 *         direction vector we discussed in class (and also used for ray
	 *         tracing), and where p is a specular power. The higher the value of p,
	 *         the shinier the surface.
	 */
	private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color ip) {
		double p = nShininess;

		Vector R = l.add(n.scale(-2 * nl)); // nl must not be zero!
		double minusVR = -Util.alignZero(R.dotProduct(v));
		if (minusVR <= 0) {
			return Color.BLACK; // view from direction opposite to r vector
		}
		return ip.scale(ks * Math.pow(minusVR, p));
	}

	/**
	 * Calculate Diffusive component of light reflection.
	 *
	 * @param kd diffusive component coef
	 * @param nl dot-product n*l
	 * @param ip light intensity at the point
	 * @return diffusive component of light reflection
	 *         <p>
	 *         The diffuse component is that dot product n•L that we discussed in
	 *         class. It approximates light, originally from light source L,
	 *         reflecting from a surface which is diffuse, or non-glossy. One
	 *         example of a non-glossy surface is paper. In general, you'll also
	 *         want this to have a non-gray color value, so this term would in
	 *         general be a color defined as: [rd,gd,bd](n•L)
	 */
	private Color calcDiffusive(double kd, double nl, Color ip) {
		if (nl < 0)
			nl = -nl;
		return ip.scale(nl * kd);
	}

	
	@Override
	public Color traceRay(Ray ray)
	{
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		if (intersections == null)
			return scene.background;
		GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
		return calcColor(closestPoint, ray);
	}
	
	/**
	 * check if there is shadow
	 * 
	 * @param l  vector l - light direction
	 * @param n  normal
	 * @param gp geo point
	 * @return
	 */
	private boolean unshaded(Vector l, Vector n, GeoPoint gp, LightSource lightSource) 
	{
		Vector lightDirection = l.scale(-1); // from point to light source
//		Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);// where we need to move the point
//		Point3D point = gp.point.add(delta);// moving the point
//		Ray lightRay = new Ray(point, lightDirection);// the new ray after the moving
		Ray lightRay = new Ray(gp.point, lightDirection, n);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		if (intersections == null)
			return true;// if there are not intersection points - unshadow
		double lightDistance = lightSource.getDistance(gp.point);
		for (GeoPoint g : intersections) {
			if (Util.alignZero(g.point.distance(lightRay.getP0()) - lightDistance) <= 0
					&& g.geometry.getMaterial().kT == 0)
				return false;
		}
		return true;

	}
	/**
	 * construct refracted ray
	 * 
	 * @param pointGeo - intersection point
	 * @param inRay    - ray v from the camera
	 * @return refracted ray
	 */
	private Ray constructRefractedRay(Point3D pointGeo, Ray inRay, Vector n)
	{
		Vector v = inRay.getDir();
		return new Ray(pointGeo, v, n);
	}

	/**
	 * construct reflected ray
	 * 
	 * @param pointGeo - intersection point
	 * @param inRay    - ray v from the camera
	 * @param n        - normal from the geometry in the intersection point
	 * @return reflected ray
	 */
		private Ray constructReflectedRay(Point3D pointGeo, Ray inRay, Vector n) 
		{
			
			Vector v = inRay.getDir();
			double vn = v.dotProduct(n);
			
			if (Util.isZero(vn)) {
				return null;
			}
			Vector r = (v.subtract(n.scale(2 * vn))).normalized();
			return new Ray(pointGeo, r, n);

		}

		private List<Ray> constructReflectedRays(Vector n, Point3D point, Ray ray, double DiffusedAndGlossy) throws Exception
	    {
	        double nv = ray.getDir().dotProduct(n);
	        Vector v = ray.getDir();
	        Vector ref = v.add(n.scale(-2*nv));
	        return RaysOfGrid(n, point, ref, -1, DiffusedAndGlossy);
	    }

	
	private List<Ray> constructRefractedRays(Vector n, Point3D point, Ray ray, double DiffusedAndGlossy) throws Exception
    {
        return RaysOfGrid(n, point, ray.getDir(), 1, DiffusedAndGlossy);
    }

	private List<Ray> RaysOfGrid(Vector n, Point3D point, Vector Vto, int direction, double DiffusedAndGlossy) throws Exception {
        if(direction != 1 && direction != -1)
            throw new IllegalArgumentException("direction must be 1 or -1");
        double gridSize = DiffusedAndGlossy; //from the Material 
        int numOfRowCol = isZero(gridSize)? 1: (int)Math.ceil(Math.sqrt(maxRaysForDiffusedAndGlossy));
        Vector Vup = Vto.findRandomOrthogonal();//vector in the grid
        Vector Vright = Vto.crossProduct(Vup);//vector in the grid
        Point3D centerOfGrid = point.add(Vto.scale(distanceForDiffusedAndGlossy)); // center point of the grid
        double sizeOfCube = gridSize/numOfRowCol;//size of each cube in the grid
        List rays = new LinkedList<Ray>();
        n = n.dotProduct(Vto) > 0 ? n.scale(-direction) : n.scale(direction);//fix the normal direction
        Point3D tempcenterOfGrid = centerOfGrid;//save the center of the grid
        Vector tempRayVector;
        for (int row = 0; row < numOfRowCol; row++)
        {
        	double xAsixChange= (row - (numOfRowCol/2d))*sizeOfCube + sizeOfCube/2d;
        		for(int col = 0; col < numOfRowCol; col++)
        		{
        			double yAsixChange= (col - (numOfRowCol/2d))*sizeOfCube + sizeOfCube/2d;
        			if(xAsixChange != 0) centerOfGrid = centerOfGrid.add(Vright.scale(-xAsixChange)) ;
        			if(yAsixChange != 0) centerOfGrid = centerOfGrid.add(Vup.scale(-yAsixChange)) ;
        			tempRayVector = centerOfGrid.subtract(point);
                	if(n.dotProduct(tempRayVector) < 0 && direction == 1) //refraction
                		rays.add(new Ray(point, tempRayVector, n));
                	if(n.dotProduct(tempRayVector) > 0 && direction == -1) //reflection
                		rays.add(new Ray(point, tempRayVector, n));
                	centerOfGrid = tempcenterOfGrid;
        		}
        }
        return rays;
    }

	
	/**
	 * calculate intersection points with the ray and return the closest point
	 * 
	 * @param ray - ray
	 * @return Closest intersection point
	 */
	private GeoPoint findClosestIntersection(Ray ray)
	{

		if (ray == null) {
			return null;
		}

		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		return ray.findClosestGeoPoint(intersections);
	}
	
	/**
	 * 
	 * @param l  vector l - light direction
	 * @param n  normal
	 * @param gp geo point
	 * @return
	 */
	private double transparency(Vector l, Vector n, GeoPoint gp, LightSource lightSource) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(gp.point, lightDirection, n);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
		if (intersections == null)
			return 1.0; // if there are not intersection points - unshadow
		double ktr = 1.0;
		double lightDistance = lightSource.getDistance(gp.point);
		for (GeoPoint g : intersections) {
			if (Util.alignZero(g.point.distance(gp.point) - lightDistance) <= 0) {
				ktr *= g.geometry.getMaterial().kT;
				if (ktr < MIN_CALC_COLOR_K)
					return 0.0;
			}
		}
		return ktr;
	}
	
    /**
     *Adaptive Diffused And Glossy
     *@param n normal vector
     *@param point the intersection point
     *@param Vto the light direction
     *@param direction Diffused or Glossy
     *@param DiffusedAndGlossy the grid size
     *@param level the recursive level for calcColor
     *@param k min ktr
     *@param ktr the kt or thr kr of the geometry
     *@return color for intersection point
     */
    private primitives.Color AdaptiveDiffusedAndGlossy(Vector n, Point3D point, Vector Vto, int direction, double DiffusedAndGlossy, int level , double k, double ktr) throws Exception {

           if (direction != 1 && direction != -1)
               throw new IllegalArgumentException("direction must be 1 or -1");
           double gridSize = DiffusedAndGlossy;
           int numOfRowCol = isZero(gridSize) ? 1 : (int) Math.floor(Math.sqrt(maxRaysForDiffusedAndGlossy));
           Vector Vup = Vto.findRandomOrthogonal();//vector in the grid
           Vector Vright = Vto.crossProduct(Vup);//vector in the grid
           Point3D centerOfGrid = point.add(Vto.scale(distanceForDiffusedAndGlossy)); // center point of the grid
           double sizeOfCube = gridSize / numOfRowCol;//size of each cube in the grid
           n = n.dotProduct(Vto) > 0 ? n.scale(-direction) : n.scale(direction);//fix the normal direction

           if(isZero(gridSize))
           {
               Ray tempRay = new Ray(point, centerOfGrid.subtract(point), n);
               GeoPoint gp = findClosestIntersection(tempRay);
               if (gp == null)
                   return scene.background;
               else
                   return calcColor(gp, tempRay, level - 1, k);
           }

           return AdaptiveDiffusedAndGlossyRec(centerOfGrid, gridSize, sizeOfCube, point, Vright, Vup, n, direction, level, k, ktr, null);
    }
    
    /**
     *Adaptive Diffused And Glossy
     *@param centerP the center point of grid
     *@param WidthAndHeight the screen width and height
     *@param minCubeSize the min cube size
     *@param pIntersection the intersection point
     *@param Vright a random orthogonal vector to Vup
     *@param Vup an orthogonal vector to Vup and Vright
     *@param normal normal vector
     *@param direction Diffused or Glossy
     *@param level the recursive level for calcColor
     *@param k min ktr
     *@param ktr the kt or thr kr of the geometry
     *@return color for intersection point
     */
    private primitives.Color AdaptiveDiffusedAndGlossyRec(Point3D centerP, double WidthAndHeight, double minCubeSize, Point3D pIntersection,Vector Vright,Vector Vup , Vector normal, int direction, int level , double k, double ktr, List<Point3D> prePoints) throws Exception {
        List<Point3D> nextCenterPList = new LinkedList<Point3D>();
        List<Point3D> cornersList = new LinkedList<Point3D>();
        List<primitives.Color> colorList = new LinkedList<primitives.Color>();
        Point3D tempCorner;
        GeoPoint gp;
        Ray tempRay;
        for (int i = -1; i <= 1; i += 2)
            for (int j = -1; j <= 1; j += 2) {
                tempCorner = centerP.add(Vright.scale(i * WidthAndHeight / 2)).add(Vup.scale(j * WidthAndHeight / 2));
                cornersList.add(tempCorner);
                if (prePoints == null || !isInList(prePoints, tempCorner)) {
                    tempRay = new Ray(pIntersection, tempCorner.subtract(pIntersection), normal);
                    if ((normal.dotProduct(tempRay.getDir()) < 0 && direction == 1) || (normal.dotProduct(tempRay.getDir()) > 0 && direction == -1)) {
                        nextCenterPList.add(centerP.add(Vright.scale(i * WidthAndHeight / 4)).add(Vup.scale(j * WidthAndHeight / 4)));
                        gp = findClosestIntersection(tempRay);
                        if (gp == null)
                            colorList.add(scene.background);
                        else {
                            colorList.add(calcColor(gp, tempRay, level - 1, k));
                        }                                        
                    }
                    
                }
            }

        if (nextCenterPList == null || nextCenterPList.size() == 0) {
            return primitives.Color.BLACK;
        }


        if (WidthAndHeight < minCubeSize * 2) {
            primitives.Color sumColor = primitives.Color.BLACK;
            for (primitives.Color color : colorList) {
                sumColor = sumColor.add(color);
            }
            return sumColor.reduce(colorList.size());
        }


        boolean isAllEquals = true;
        primitives.Color tempColor = colorList.get(0);
        for (primitives.Color color : colorList) {
            if (!tempColor.isAlmostEquals(color))
                isAllEquals = false;
        }
        if (isAllEquals && colorList.size() > 1)
            return tempColor;


        tempColor = primitives.Color.BLACK;
        for (Point3D center : nextCenterPList) {
            tempColor = tempColor.add(AdaptiveDiffusedAndGlossyRec(center, WidthAndHeight / 2, minCubeSize, pIntersection, Vright, Vup, normal, direction, level, k, ktr, cornersList));
        }
        return tempColor.reduce(nextCenterPList.size());
    }
	
	
	/**
     * is In List - Checks whether a point is in a points list
     * @param point the point we want to check
     * @param pointsList where we search the point
     * @return true if the point is in the list, false otherwise
     */
    private boolean isInList(List<Point3D> pointsList, Point3D point) {
        for (Point3D tempPoint : pointsList) {
            if(point.isAlmostEquals(tempPoint))
                return true;
        }
        return false;
    }
	
	
}
