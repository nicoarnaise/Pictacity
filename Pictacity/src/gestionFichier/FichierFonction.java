package gestionFichier;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
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
		if(!path.equals("")){
			compiler(path);
		}	
		Class[] mesFonctions = traiterCompilation();
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
		Traitement[] a = {};
		mesTraitements = traitements.toArray(a);
		return mesTraitements;
	}
	
	public void compiler(String cheminAbsolu) {
		// TODO Auto-generated method stub
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		File dir = new File(cheminAbsolu);
		//System.out.println(cheminAbsolu);
		ArrayList<File> files;
		if(dir.isDirectory()){
			files = new ArrayList<File>(Arrays.asList(dir.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(".java");
		    }
		})));
		}else{
			files = new ArrayList<File>();
			files.add(dir);
		}
		
		for(File f : files){
			if (!f.getName().equals("Traitement.java")) {
				// System.out.println(f.getName());
				String[] parts = f.getName().split("\\.");
				System.out.println(new File("functions").getAbsolutePath());
				String[] args = { f.getAbsolutePath(),"-d",new File("functions").getAbsolutePath()};
				int ret = compiler.run(System.in, System.out, System.err, args);
				String name = "";
				for (int i = 0; i < parts.length - 1; i++) {
					name += parts[i];
				}
				System.out.println(name);
			}
		}
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
	public Class[] traiterCompilation() {
		ArrayList<Class> liste = new ArrayList<Class>();
		File dir = new File("functions","fonctions");
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(dir.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(".class");
		    }
		})));
		for(File f : files){
				URLClassLoader classLoader = null;
				try {
					classLoader = new URLClassLoader(
							new URL[] { new File("functions").toURI().toURL() });
					System.out.println(new File("functions").toURI().toURL().toString());
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Load the class from the classloader by name....
				Class loadedClass = null;
				try {
					String[] parts = f.getName().split("\\.");
					String name = "";
					for (int i = 0; i < parts.length - 1; i++) {
						name += parts[i];
					}
					loadedClass = classLoader.loadClass("fonctions." + name);
					liste.add(loadedClass);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		Class[] a = {};
		return liste.toArray(a);
	}
}
