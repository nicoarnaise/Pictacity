package fonctions;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Flou implements Traitement {

	@Override
	public BufferedImage applique(BufferedImage img) {
		
		int l=img.getWidth();
		int h=img.getHeight();
		
		BufferedImage imfl = new BufferedImage(l,h,BufferedImage.TYPE_INT_RGB);
		
		for (int j=2;j<h-2;j++)
			for (int i=2;i<l-2;i++)
			{
			    int r = 0;
			    int g = 0;
			    int b = 0;
			    
			    for (int m=j-2;m<=j+2;m++)
			    for (int k=i-2;k<=i+2;k++)
			    {
			        Color c = new Color(img.getRGB(k,m));
			        r += c.getRed();
			        g += c.getGreen();
			        b += c.getBlue();
			    }
			    
			    imfl.setRGB(i,j,new Color(r/25,g/25,b/25).getRGB());
			}
		
		return imfl;
	}

}
