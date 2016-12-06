package gestionFichier;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class FichierImage implements GestionFichier<JLabel> {

	@Override
	public JLabel load(String path) {

		ImageIcon icon = new ImageIcon(path);
		JLabel img = new JLabel(icon);

		return img;
	}

	@Override
	public void save(JLabel object, String path) {
		try {
			Icon icon = object.getIcon();
		    BufferedImage bi = new BufferedImage(icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_INT_RGB);
		    Graphics g = bi.createGraphics();
		    icon.paintIcon(null, g, 0, 0);
		    g.dispose();
		    File outputfile = new File(path);
		    String extension = path.split("\\.")[path.split("\\.").length-1];
		    ImageIO.write(bi, extension, outputfile);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}

}
