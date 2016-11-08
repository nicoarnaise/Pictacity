package gestionFichier;

public interface GestionFichier<T> {
	public T load(String path);
	public void save(T object, String path);
}
