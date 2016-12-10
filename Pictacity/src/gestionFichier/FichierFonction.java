package gestionFichier;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import fonctions.Traitement;

public class FichierFonction implements GestionFichier<Traitement[]> {
	
	private Traitement[] mesTraitements;
	
	@Override
	public Traitement[] load(String path) {
		// TODO Auto-generated method stub
		Class[] mesFonctions = traiterCompilation(path);
		ArrayList<Traitement> traitements = new ArrayList<Traitement>();
		for(Class c : mesFonctions){
			try {
				Traitement t = (Traitement)c.newInstance();
				traitements.add(t);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				System.out.println(c.getName()+" n\'a pas pu être chargée: err1");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				System.out.println(c.getName()+" n\'a pas pu être chargée: err2");
			}
		}
		Traitement[] a = null;
		mesTraitements = traitements.toArray(a);
		return mesTraitements;
	}
	
	public Traitement[] getTraitements(){
		return mesTraitements;
	}

	@Override
	public void save(Traitement[] object, String path) {
		// TODO Auto-generated method stub

	}

	/**
	 * traiterCompilation : traite la compilation d’un fichier source java.
	 * Confirme au client que la compilation s’est faite correctement. Le
	 * fichier source est donné par son chemin relatif par rapport au chemin des
	 * fichiers sources.
	 */
	public Class[] traiterCompilation(String cheminAbsolu) {
		ArrayList<Class> liste = new ArrayList<Class>();
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		File dir = new File(cheminAbsolu);
		//System.out.println(cheminAbsolu);
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(dir.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(".java");
		    }
		})));
		for(File f : files){
			//System.out.println(f.getName());
			String[] parts = f.getName().split("\\.");
			String[] args = {f.getAbsolutePath()};
			int ret = compiler.run(System.in, System.out, System.err, args);
			String name = "";
			for (int i=0;i<parts.length-1;i++){
				name+=parts[i];
			}
			//System.out.println(name);
			ClassLoader cl = this.getClass().getClassLoader();
			try {
				Class c = cl.loadClass(name+".class");
				liste.add(c);
			} catch (ClassNotFoundException e) {
				System.out.println(f.getName()+" n\'a pas pu être compilée.");
			}
		}
		Class[] a = null;
		return liste.toArray(a);
	}
}
