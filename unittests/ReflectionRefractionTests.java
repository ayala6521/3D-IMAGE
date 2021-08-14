package unittests;

import org.junit.Test;

import elements.*;
import geometries.Sphere;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests {
	private Scene scene = new Scene("Test scene");

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheres() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(150, 150).setDistance(1000);

		scene.geometries.add( //
				new Sphere(new Point3D(0, 0, -50), 50) //
						.setEmmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
				new Sphere(new Point3D(0, 0, -50), 25) //
						.setEmmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));
		scene.lights.add( //
				new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
						.setkL(0.0004).setkQ(0.0000006));

		Render render = new Render() //
				.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point3D(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(2500, 2500).setDistance(10000); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.geometries.add( //
				new Sphere(new Point3D(-950, -900, -1000), 400) //
						.setEmmission(new Color(0, 0, 100)) //
						.setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20).setkT(0.5)),
				new Sphere(new Point3D(-950, -900, -1000), 200) //
						.setEmmission(new Color(100, 20, 20)) //
						.setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)),
				new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
						new Point3D(670, 670, 3000)) //
								.setEmmission(new Color(20, 20, 20)) //
								.setMaterial(new Material().setkR(1)),
				new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
						new Point3D(-1500, -1500, -2000)) //
								.setEmmission(new Color(20, 20, 20)) ///
								.setMaterial(new Material().setkR(0.5)));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, -750, -150), new Vector(-1, -1, -4)) //
				.setkL(0.00001).setkQ(0.000005));

		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setViewPlaneSize(200, 200).setDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.geometries.add( //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
				new Sphere(new Point3D(60, 50, -50), 30) //
						.setEmmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) //
				.setkL(4E-5).setkQ(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}
	
	@Test
	//our pic sun and house
	public void ourPicture()
	{
		Camera camera = new Camera(new Point3D(0, 0, 1500), new Vector(0, 0, -1), new Vector(0, 1, 0)) 
				.setViewPlaneSize(200, 200).setDistance(1000);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));
		scene.geometries.add(
				new Polygon(new Point3D(-150, -160, -10), new Point3D(-150, 90, -400), new Point3D(165, 90, -400),
						new Point3D(165, -160, -10)).setMaterial(new Material().setkR(1).setkD(0.5).setDiffusedAndGlossy(5))
								.setEmmission(new Color(0, 0, 0)),
				new Sphere(new Point3D(100, 40, -150), 25).setEmmission(new Color(java.awt.Color.YELLOW))// sun
					.setMaterial(new Material().setkR(3).setkD(0.5).setnShininess(30).setkS(0.5)),
				new Polygon(new Point3D(-90, -90, -50), new Point3D(-30, -90, -50), new Point3D(-30, -35, -50), 
							new Point3D(-90, -35, -50)).setMaterial(new Material().setkR(1).setkD(0.5))
							.setEmmission(new Color(0,0,255)),
				new Triangle(new Point3D(-100, -35, -50), new Point3D(-60, 10, -50), new Point3D(-20, -35, -50)) //roof
							.setEmmission(new Color(255, 0, 0))
							.setMaterial(new Material().setkR(1).setkD(1).setnShininess(300).setkS(0.5)));
		
		//scene.setBackground(new Color(200,100,70));
		scene.lights.add(new PointLight(new Color(50, 50, 50), new Point3D(-50, -50, 50))//
				.setkL(0.00001).setkQ(0.00001));
		ImageWriter imageWriter = new ImageWriter("pic", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();		
	}
	
	//our picture project 1
	@Test
	public void projPicture1() throws Exception
	{
	
        
        Camera camera = new Camera(new Point3D(50, 100, -11000), new Vector(0, 0, 1), new Vector(0, -1, 0)) 
				.setViewPlaneSize(2500, 2500).setDistance(9000);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK), 0.1));
        

		scene.geometries.add(
                
                new Sphere(new Point3D(-1000, -50, 1600), 200).setEmmission(new Color(0, 25, 51))
				.setMaterial(new Material().setkR(0.7).setkD(0.8).setnShininess(200).setkS(0.8)),
              
				new Sphere(new Point3D(1000, -50, 1600), 200).setEmmission(new Color(0, 25, 51))
				.setMaterial(new Material().setkR(0.7).setkD(0.8).setnShininess(200).setkS(0.8)),
              
				new Sphere(new Point3D(0, -50, 1600), 200).setEmmission(new Color(0, 25, 51))
				.setMaterial(new Material().setkR(0.7).setkD(0.8).setnShininess(200).setkS(0.8)),
             
				new Triangle(new Point3D(40, -15, -8200), new Point3D(350, -15, -8200), new Point3D(350, 185, -8200)) 
				.setEmmission(new Color(java.awt.Color.WHITE).reduce(10))
				.setMaterial(new Material().setkT(1)),
				            
				new Triangle(new Point3D(40, -15, -8200), new Point3D(350, 185, -8200), new Point3D(-270, 185, -8200)) 
				.setEmmission(new Color(java.awt.Color.WHITE).reduce(10))
				.setMaterial(new Material().setkT(1).setDiffusedAndGlossy(15)),
				
				new Triangle(new Point3D(40, -15, -8200), new Point3D(-270, -15, -8200), new Point3D(-270, 185, -8200)) 
				.setEmmission(new Color(java.awt.Color.WHITE).reduce(10))
				.setMaterial(new Material().setkT(1).setDiffusedAndGlossy(30)),
				
                new Plane(new Point3D(1500, 1500, 0),new Point3D(-1500, -1500, 3850), new Point3D(-1500, 1500, 0))
				.setEmmission(new Color(java.awt.Color.BLACK).reduce(5))
				.setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(2000)));
				

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(0, 300, -400), new Vector(-1, 1, 4))
				.setkL(0.00001).setkQ(0.000005));
		scene.lights.add(new SpotLight(new Color(20, 40, 0), new Point3D(800, 100, -300), new Vector(-1, 1, 4))
				.setkL(0.00001).setkQ(0.000005));
		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-800, 100, -300), new Vector(-1, 1, 4))
				.setkL(0.00001).setkQ(0.000005));
		scene.lights.add(new DirectionalLight(new Color(java.awt.Color.darkGray), new Vector(-0.5, 0.5, 0)));
		

     
        ImageWriter imageWriter = new ImageWriter("projPicture1",1000,1000);
        RayTracerBasic rayTracer=new RayTracerBasic(scene);
		rayTracer.setDistanceForDiffusedAndGlossy(5000);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(rayTracer);
				

		render.renderImage();
		render.writeToImage();
		
	}
	
	//another picture for Diffused And Glossy
	@Test
	public void ourProjPicture() throws Exception
	{
		 Camera camera = new Camera(new Point3D(50, 100, -11000), new Vector(0, 0, 1), new Vector(0, -1, 0)) 
					.setViewPlaneSize(2500, 2500).setDistance(9000);
			scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK), 0.1));
		scene.geometries.add(
	                
	                new Sphere(new Point3D(-1000, -50, 1600), 200).setEmmission(new Color(63, 120, 77))
					.setMaterial(new Material().setkR(0.7).setkD(0.8).setnShininess(200).setkS(0.8)),
	              
					new Sphere(new Point3D(1000, -50, 1600), 200).setEmmission(new Color(102, 32, 20))
					.setMaterial(new Material().setkR(0.7).setkD(0.8).setnShininess(200).setkS(0.8)),
	              
					new Sphere(new Point3D(0, -50, 1600), 200).setEmmission(new Color(51, 0, 51))
					.setMaterial(new Material().setkR(0.7).setkD(0.8).setnShininess(200).setkS(0.8)),
					
					 new Plane(new Point3D(1500, 1500, 0),new Point3D(-1500, -1500, 3850), new Point3D(-1500, 1500, 0))
						.setEmmission(new Color(java.awt.Color.BLACK).reduce(5))
						.setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(2000).setkR(0.4)));
		
		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(0, 300, -400), new Vector(-1, 1, 4))
				.setkL(0.00001).setkQ(0.000005));
		scene.lights.add(new SpotLight(new Color(20, 40, 0), new Point3D(800, 100, -300), new Vector(-1, 1, 4))
				.setkL(0.00001).setkQ(0.000005));
		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-800, 100, -300), new Vector(-1, 1, 4))
				.setkL(0.00001).setkQ(0.000005));
		scene.lights.add(new DirectionalLight(new Color(java.awt.Color.darkGray), new Vector(-0.5, 0.5, 0)));
			
			
		ImageWriter imageWriter = new ImageWriter("anotherPic",1000,1000);
        RayTracerBasic rayTracer=new RayTracerBasic(scene);
		rayTracer.setDistanceForDiffusedAndGlossy(5000);
		Render render = new Render() //
				.setImageWriter(imageWriter) 
				.setCamera(camera) //
				.setRayTracer(rayTracer);
				

		render.renderImage();
		render.writeToImage();
	}
	
	/**
     * test multi shapes
     */
    @Test
    public void test()throws Exception {
        
        Camera camera = new Camera(new Point3D(50, 100, -11000), new Vector(0, 0, 1), new Vector(0, -1, 0)) 
				.setViewPlaneSize(2500, 2500).setDistance(9000);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK), 0.1));

        scene.geometries.add(
                new Sphere(new Point3D(-950, 0, 1100),400).setEmmission(new Color(21,0, 81).scale(1.5))
				.setMaterial(new Material().setkR(0.7).setkD(0.8).setnShininess(200).setkS(0.8)),
               
				new Sphere(new Point3D(0, 500, 700),300).setEmmission(new Color(102, 32, 20))
				.setMaterial(new Material().setkR(0.7).setkD(0.8).setnShininess(200).setkS(0.8)),
				
				new Sphere(new Point3D(1000, 50, 1100),450).setEmmission(new Color(51, 0, 51))
				.setMaterial(new Material().setkR(0.7).setkD(0.8).setnShininess(120).setkS(0.25)),
				
				new Sphere(new Point3D(0, -500, 1700),600).setEmmission(new Color(63, 120, 77))
				.setMaterial(new Material().setkR(0.7).setkD(0.85).setnShininess(200).setkS(0.25)),	
				
				new Triangle(new Point3D(50, -215, -8200), new Point3D(380, -215, -8200), new Point3D(380, 285, -8200)) 
				.setEmmission(new Color(java.awt.Color.WHITE).reduce(10))
				.setMaterial(new Material().setkT(1).setDiffusedAndGlossy(24)),
				
				new Triangle(new Point3D(40, -215, -8200), new Point3D(370, 285, -8200), new Point3D(-270, 285, -8200)) 
				.setEmmission(new Color(java.awt.Color.WHITE).reduce(10))
				.setMaterial(new Material().setkT(1)),
				
				new Triangle(new Point3D(30, -215, -8200), new Point3D(-280, -215, -8200), new Point3D(-280, 285, -8200)) 
				.setEmmission(new Color(java.awt.Color.WHITE).reduce(10))
				.setMaterial(new Material().setkT(1).setDiffusedAndGlossy(24)),
				
				new Plane(new Point3D(1500, 1500, 0),new Point3D(-1500, -1500, 3850), new Point3D(-1500, 1500, 0))
				.setEmmission(new Color(java.awt.Color.BLACK).reduce(5))
				.setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(2000).setkR(0.4).setDiffusedAndGlossy(200)));



      
        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(0, 300, -400), new Vector(-1, 1, 4))
				.setkL(0.00001).setkQ(0.000005));
		scene.lights.add(new SpotLight(new Color(20, 40, 0), new Point3D(800, 100, -300), new Vector(-1, 1, 4))
				.setkL(0.00001).setkQ(0.000005));
		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-800, 100, -300), new Vector(-1, 1, 4))
				.setkL(0.00001).setkQ(0.000005));
		scene.lights.add(new DirectionalLight(new Color(java.awt.Color.darkGray), new Vector(-0.5, 0.5, 0)));
        
        
        ImageWriter imageWriter = new ImageWriter("Pro2Pic",1000,1000);
        RayTracerBasic rayTracer=new RayTracerBasic(scene);
		rayTracer.setDistanceForDiffusedAndGlossy(5000);
		rayTracer.setAdaptiveDiffusedAndGlossyFlag(true);
		rayTracer.setMaxRaysForDiffusedAndGlossy(100);
		Render render = new Render() //
				.setImageWriter(imageWriter) 
				.setCamera(camera) //
				.setMultithreading(4)
				.setRayTracer(rayTracer);

		render.renderImage();
		render.writeToImage();
    }
	
}
