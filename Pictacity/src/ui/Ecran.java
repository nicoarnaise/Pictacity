package ui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import fonctions.Traitement;
import gestionFichier.FichierFonction;
import gestionFichier.FichierImage;

public class Ecran extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DefaultListModel<String> list = new DefaultListModel<String>();
	HashMap<String,Traitement> lesTraitements;
	
	JMenuBar menu = new JMenuBar();
	JMenu fichier = new JMenu("Fichier");
	JMenu fonctions = new JMenu("Fonctions");
	//JMenuItem newProj = new JMenuItem("Nouveau Projet");
	//JMenuItem ouvProj = new JMenuItem("Ouvrir Projet");
	//JMenuItem enrProj = new JMenuItem("Enregistrer Projet");
	JMenuItem quitter = new JMenuItem("Quitter");
	JMenuItem newFnct = new JMenuItem("Nouvelle Fonction");
	
	GroupLayout layout;
	JButton up = new JButton("↑");
	JButton down = new JButton("↓");
	JButton sup = new JButton("x");
	JPanel imgAct = new JPanel();
	JList<String> listFnct;
	ScalablePane imgOri = new ScalablePane();
	ScalablePane imgMod = new ScalablePane();
	JButton ouvrir = new JButton("OUVRIR");
	JLabel nomFich = new JLabel("nom de l'image d'origine");
	JButton enregistrer = new JButton("ENREGISTRER");
	FichierFonction ff;
	private String pathImg = "";
	
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
		
		lesTraitements = new HashMap<String, Traitement>();
		ff = new FichierFonction();
		Traitement[] mesTraitements=ff.load("");
		for (int i=0;i<mesTraitements.length;i++){
			lesTraitements.put(mesTraitements[i].getClass().getSimpleName(), mesTraitements[i]);
			JMenuItem monTraitement = new JMenuItem(mesTraitements[i].getClass().getSimpleName());
			monTraitement.addActionListener(this);
			fonctions.add(monTraitement);
		}
		
		menu.add(fonctions);
		this.setJMenuBar(menu);
		
		enregistrer.addActionListener(this);
		ouvrir.addActionListener(this);
		
		newFnct.addActionListener(this);
		up.addActionListener(this);
		sup.addActionListener(this);
		down.addActionListener(this);
		
		list.addElement("Aucun traitement");
		listFnct = new JList<String>(list);
		listFnct.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED), "Liste des fonctions appliquées :"));
		listFnct.setFixedCellWidth(200);
		imgOri.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		imgMod.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		nomFich.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				  layout.createSequentialGroup()
				      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				    	   .addComponent(imgOri)
				    	   .addGroup(layout.createSequentialGroup()
				    		   .addComponent(ouvrir)
				    	       .addComponent(nomFich)))
				      .addComponent(listFnct)
				      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				    		  .addComponent(up)
				    		  .addComponent(sup)
				    		  .addComponent(down))
				      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				           .addComponent(imgMod)
				           .addComponent(enregistrer))
				);
		layout.setVerticalGroup(
				  layout.createSequentialGroup()
				      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				    	   .addComponent(imgOri)
				       	   .addComponent(listFnct)
				           .addComponent(imgMod)
				           .addGroup(layout.createSequentialGroup()
				        		   .addComponent(up)
				        		   .addComponent(sup)
				        		   .addComponent(down)))
				      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    	   .addComponent(ouvrir)
				    	   .addComponent(nomFich)
				    	   .addComponent(enregistrer))
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
		else if (source==newFnct){
			Traitement[] mesTraitements=ff.load(ouvrir());
			fonctions.removeAll();
			fonctions.add(newFnct);
			for (int i=0;i<mesTraitements.length;i++){
				lesTraitements.put(mesTraitements[i].getClass().getSimpleName(), mesTraitements[i]);
				JMenuItem monTraitement = new JMenuItem(mesTraitements[i].getClass().getSimpleName());
				monTraitement.addActionListener(this);
				fonctions.add(monTraitement);
			}
		}
		else if (source==ouvrir){
			ouvrirImage();
		}
		else if (source==enregistrer){
			enregistrer();
		}
		else if (source==up){
			int index = listFnct.getSelectedIndex();
			if (index>0){
				PileTraitement.getInstance().upQueue(index);
				majListe();
				faireTraitements();
			}
		}
		else if (source==sup){
			int index = listFnct.getSelectedIndex();
			if (index>-1){
				PileTraitement.getInstance().removeQueue(index);
				majListe();
				faireTraitements();
			}
		}
		else if (source==down){
			int index = listFnct.getSelectedIndex();
			if (index>-1 && index<list.size()-1){
				PileTraitement.getInstance().downQueue(index);
				majListe();
				faireTraitements();
			}
		}
		else{
			PileTraitement.getInstance().addQueue(lesTraitements.get(((JMenuItem)source).getText()));
			majListe();
			faireTraitements();
		}
	}
	
	public void majListe(){
		list.clear();
		for(Traitement t : PileTraitement.getInstance().getQueue()){
			list.addElement(t.getClass().getSimpleName());
		}
		if(list.isEmpty()){
			list.addElement("Aucun traitement");
		}
		listFnct.setModel(list);
		this.invalidate();
		this.validate();
		this.repaint();
	}
	
	public void faireTraitements(){

		LinkedList<Traitement> traitements = (LinkedList<Traitement>)PileTraitement.getInstance().getQueue().clone();
		if(traitements.isEmpty()){
			FichierImage fi = new FichierImage();
			imgMod.setPane(fi.load(pathImg));
		}else{
		try {
			Image icon = imgOri.getImage();
			BufferedImage bi = (BufferedImage)icon;
			BufferedImage bi2 = traitements.poll().applique(bi);
			imgMod.setPane(new ScalablePane(bi2));
		} catch (ClassCastException execpt) {
		}
		while (!traitements.isEmpty()) {
			try {
				Image icon = imgMod.getImage();
				BufferedImage bi = (BufferedImage)icon;
				BufferedImage bi2 = traitements.poll().applique(bi);
				imgMod.setPane(new ScalablePane(bi2));
			} catch (ClassCastException execpt) {
			}
		}}
		this.invalidate();
		this.validate();
		this.repaint();
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
		pathImg=ouvrir();
		if (pathImg!=""){
			nomFich.setText(pathImg);
		};
		FichierImage fi=new FichierImage();
		imgOri.setPane(fi.load(pathImg));
		imgMod.setPane(fi.load(pathImg));
	}
	
	public boolean enregistrer(){
		JFileChooser dialog = new JFileChooser();
		if (dialog.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			String path = dialog.getSelectedFile().getAbsolutePath();
			FichierImage fi=new FichierImage();
			fi.save(imgMod, path);
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
