package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import fonctions.Flou;
import fonctions.Traitement;
import gestionFichier.FichierImage;

public class Ecran extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JMenuBar menu = new JMenuBar();
	JMenu fichier = new JMenu("Fichier");
	JMenu fonctions = new JMenu("Fonctions");
	//JMenuItem newProj = new JMenuItem("Nouveau Projet");
	//JMenuItem ouvProj = new JMenuItem("Ouvrir Projet");
	//JMenuItem enrProj = new JMenuItem("Enregistrer Projet");
	JMenuItem quitter = new JMenuItem("Quitter");
	JMenuItem newFnct = new JMenuItem("Nouvelle Fonction");
	
	GroupLayout layout;
	JPanel imgAct = new JPanel();
	JList<String> listFnct;
	JPanel imgOri = new JPanel();
	JPanel imgMod = new JPanel();
	JButton ouvrir = new JButton("OUVRIR");
	JLabel nomFich = new JLabel("nom de l'image d'origine");
	JButton enregistrer = new JButton("ENREGISTRER");
	JProgressBar progress = new JProgressBar();
	
	
	public Ecran(){
		this.setDefaultCloseOperation(Ecran.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent arg0) {
            	quitter();
            }
        });
		/*newProj.addActionListener(this);
		fichier.add(newProj);
		ouvProj.addActionListener(this);
		fichier.add(ouvProj);
		enrProj.addActionListener(this);
		fichier.add(enrProj);*/
		quitter.addActionListener(this);
		fichier.add(quitter);
		menu.add(fichier);
		fonctions.add(newFnct);
		menu.add(fonctions);
		this.setJMenuBar(menu);
		
		enregistrer.addActionListener(this);
		ouvrir.addActionListener(this);
		
		String[] list = {"Fonction 1", "Fonction 2", "Fonction 3"};
		listFnct = new JList<String>(list);
		listFnct.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED), "Liste des fonctions appliquées :"));
		listFnct.setFixedCellWidth(300);
		imgOri.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		imgMod.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		nomFich.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		progress.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		progress.setString("import");
		progress.setValue(50);
		progress.setStringPainted(true);
		
		layout.setHorizontalGroup(
				  layout.createSequentialGroup()
				      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				    	   .addComponent(imgOri)
				    	   .addGroup(layout.createSequentialGroup()
				    		   .addComponent(ouvrir)
				    	       .addComponent(nomFich)))
				      .addComponent(listFnct)
				      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				           .addComponent(imgMod)
				           .addGroup(layout.createSequentialGroup()
				               .addComponent(enregistrer)
				        	   .addComponent(progress)))
				);
		layout.setVerticalGroup(
				  layout.createSequentialGroup()
				      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				    	   .addComponent(imgOri)
				       	   .addComponent(listFnct)
				           .addComponent(imgMod))
				      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    	   .addComponent(ouvrir)
				    	   .addComponent(nomFich)
				    	   .addComponent(enregistrer))
				      .addComponent(progress)
				);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == quitter){			
			quitter();
		}
		/*else if (source==enrProj){
			enregistrer();
		}
		else if (source==ouvProj){
			ouvrirProjet();
		}
		else if (source==newProj){
			nouveau();
		}*/
		else if (source==ouvrir){
			ouvrirImage();
		}
		else if (source==enregistrer){
			enregistrer();
		}
	}
	
	public void nouveau(){
	    String nomProjet = JOptionPane.showInputDialog(null, "Saisir le nom du projet", "Nouveau projet", JOptionPane.QUESTION_MESSAGE);
	    while (nomProjet != null && ("".equals(nomProjet))){
	    	nomProjet = JOptionPane.showInputDialog(null, "Saisir le nom du projet", "Nouveau projet", JOptionPane.QUESTION_MESSAGE);
	    }
	    if (nomProjet != null){
	    	System.out.println(nomProjet);
	    	// ..............
	    	enregistrer();
	    	// ...............
	    }
	}
	
	public String ouvrir(){
		JFileChooser dialog = new JFileChooser();
		if (dialog.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = dialog.getSelectedFile(); 
			String name = file.getName();
			String path = file.getAbsolutePath();
			// ................
			System.out.println("Fichier ouvert "+name);
			return path;
			
		}
		return "";
	}
	
	public void ouvrirProjet(){
		String path=ouvrir();
		if (path!=""){
			this.setTitle(path);
		};
	}
	
	public void ouvrirImage(){
		String path=ouvrir();
		if (path!=""){
			nomFich.setText(path);
		};
		FichierImage fi=new FichierImage();
		JLabel img = fi.load(path);
		imgOri.add(img);
		
		
		/*Traitement trait = new Flou();
		Icon icon = img.getIcon();
	    BufferedImage bi = new BufferedImage(icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_INT_RGB);
	    Graphics g = bi.createGraphics();
	    icon.paintIcon(null, g, 0, 0);
	    g.dispose();
		BufferedImage bi2 = trait.applique(bi);
		JLabel img2 = new JLabel();
		img2.setIcon(new ImageIcon(bi2));
		imgMod.add(img2);*/
	}
	
	public boolean enregistrer(){
		JFileChooser dialog = new JFileChooser();
		if (dialog.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			String path = dialog.getSelectedFile().getAbsolutePath();
			FichierImage fi=new FichierImage();
			fi.save((JLabel)imgMod.getComponents()[0], path);
			System.out.println("Fichier sauvegardé "+path);
			return true;
		}else{
			return false;
		}
	}
	
	public void quitter(){
		int option = JOptionPane.showConfirmDialog(null, "Voulez vous sauvgarder ?", "Sauvegarde", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (option == JOptionPane.OK_OPTION) {
			if(enregistrer()){
				this.dispose();
			}
		}
		else if (option == JOptionPane.NO_OPTION){
			this.dispose();
		}
	}
	
	public static void main(String[] args) {
		Ecran e = new Ecran();
		e.setBounds(100, 100, 1200, 700);
		e.setVisible(true);
	}
}
