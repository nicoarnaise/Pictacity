package gestionFichier;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ui.ScalablePane;


public class FichierImage implements GestionFichier<ScalablePane> {

	@Override
	public ScalablePane load(String path) {

		ScalablePane img = null;
		try {
			BufferedImage image = ImageIO.read(new File(path));
			img = new ScalablePane(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return img;
	}

	@Override
	public void save(ScalablePane object, String path) {
		try {			
		    BufferedImage bi = (BufferedImage)object.getImage();
		    File outputfile = new File(path);
		    String extension = path.split("\\.")[path.split("\\.").length-1];
		    ImageIO.write(bi, extension, outputfile);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}

}
