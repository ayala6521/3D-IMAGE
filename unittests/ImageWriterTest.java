package unittests;

import renderer.ImageWriter;
import primitives.*;
import org.junit.Test;
/**
 * test for image writer class
 * @author Ayala Cohen && Shira Shevkes
 *
 */
public class ImageWriterTest
{

	/**
	 * test for create image in resolution 500X800 with grid 10X16 
	 * the background color is blue and the grid's color is red 
	 */
	@Test
	public void WriteToImageTest() 
	{
		ImageWriter imageWriter = new ImageWriter("image_1", 800, 500);
        int Nx = imageWriter.getNx();//Nx= how many columns
        int Ny = imageWriter.getNy();//Ny= how many rows
        Color b=new Color(0, 0, 255);//blue color-the background color
        Color r=new Color(255, 0, 0);//red color-the grid's color
        for (int i = 0; i < Ny; i++) 
        {
            for (int j = 0; j < Nx; j++) 
            {
                if (i % 50 == 0 || j % 50 == 0)
                {
                    imageWriter.writePixel(j, i, r);//the grid
                }
                else
                {
                    imageWriter.writePixel(j, i,b);//the background
                }
            }
        }
        imageWriter.writeToImage();	
        }

}