package gestionFichier;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FichierImage implements GestionFichier<Image> {

	@Override
	public Image load(String path) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("strawberry.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	@Override
	public void save(Image object, String path) {
		try {
		    BufferedImage bi = (BufferedImage) object;
		    File outputfile = new File(path);
		    String extension = path.split(".")[path.split(".").length-1];
		    ImageIO.write(bi, extension, outputfile);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}

}
