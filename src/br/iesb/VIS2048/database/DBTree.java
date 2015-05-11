package br.iesb.VIS2048.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Classe DBTree.
 */
public class DBTree {

    private final static String APP_PATH_STRING = "./Vis/";
    private final static String DB_PATH_STRING = APP_PATH_STRING + "NewDB/";
    private static String DB_FILE_CONFIG = DB_PATH_STRING + "config/";
    private static String DB_FILE_COLLECTION = DB_PATH_STRING + "collection/";
    private String mainDB = null;

    private ArrayList<String> collection = new ArrayList<String>();

    /**
     * Instancia um novo objeto DBTree.
     *
     * @param mainDB
     *            the main db
     */
    // TODO Método inútil.
    public DBTree(String mainDB) {
	this.mainDB = mainDB;
	createDir(APP_PATH_STRING);
	createDir(DB_PATH_STRING);
	createDir(DB_FILE_CONFIG);
	createDir(DB_FILE_COLLECTION);

	for (File file : getFilesList(DB_FILE_COLLECTION))
	    if (file.getName().endsWith(".zip")) {
		collection.add(file.getName().toLowerCase());
	    }
	if (this.mainDB == null) {
	    for (int i = 1; i < collection.size() + 2; i++) {
		if (!collection.contains(("solucao" + i + ".zip").toLowerCase())) {
		    this.mainDB = "solucao" + i + ".zip";
		    break;
		}
	    }
	}
	try {
	    ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(DB_FILE_COLLECTION + mainDB));
	    zip.putNextEntry(new ZipEntry("index.txt"));
	    zip.closeEntry();
	    zip.flush();
	    zip.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Instancia um novo DB tree.
     */
    // TODO Método inútil.
    public DBTree() {
	createDir(APP_PATH_STRING);
	createDir(DB_PATH_STRING);
	createDir(DB_FILE_CONFIG);
	createDir(DB_FILE_COLLECTION);

	for (File file : getFilesList(DB_FILE_COLLECTION))
	    if (!file.getName().endsWith(".zip")) {
		collection.add(file.getName().toLowerCase());
	    }
	if (this.mainDB == null) {
	    for (int i = 1; i < collection.size() + 2; i++) {
		if (!collection.contains(("solucao" + i).toLowerCase())) {
		    this.mainDB = "solucao" + i;
		    break;
		}
	    }
	}

    }

    /**
     * Retorna files list.
     *
     * @param directoryName
     *            the directory name
     * @return files list
     */
    public static File[] getFilesList(String directoryName) {
	return new File(directoryName).listFiles();
    }

    /**
     * Select.
     *
     * @return DB chart collection
     */
    // TODO Método inútil.
    public DBChartCollection select() {
	return null;
    }

    /**
     * Insert.
     *
     * @param col
     *            the col
     * @return true, se bem-sucedido
     */
    // TODO Método inútil.
    public boolean insert(DBChartCollection col) {
	saveGZipObject(col, "teste");
	return false;
    }

    /**
     * Update.
     *
     * @return true, se bem-sucedido
     */
    // TODO Método inútil.
    public boolean update() {
	return false;
    }

    /**
     * Removes the.
     *
     * @return true, se bem-sucedido
     */
    // TODO Método inútil.
    public boolean remove() {
	return false;
    }

    /**
     * Creates the dir.
     *
     * @param path
     *            the path
     */
    private static void createDir(String path) {
	if (!Files.exists(Paths.get(path))) {
	    new File(path).mkdir();
	}
    }

    /**
     * Save g zip object.
     *
     * @param vlo
     *            the vlo
     * @param fileName
     *            the file name
     * @return true, se bem-sucedido
     */
    private static boolean saveGZipObject(Object vlo, String fileName) {
	FileOutputStream fos = null;
	GZIPOutputStream gos = null;
	ObjectOutputStream oos = null;
	try {
	    fos = new FileOutputStream(fileName);
	    gos = new GZIPOutputStream(fos);
	    oos = new ObjectOutputStream(gos);
	    oos.writeObject(vlo);
	    oos.flush();
	    oos.close();
	    gos.close();
	    fos.close();
	    return true;
	} catch (IOException ioe) {
	    ioe.printStackTrace();
	    return false;
	}
    }

    /**
     * Save zip object.
     *
     * @param vlo
     *            the vlo
     * @param fileName
     *            the file name
     * @return true, se bem-sucedido
     */
    // TODO Método inútil.
    public static boolean saveZipObject(Object vlo, String fileName) {
	FileOutputStream fos = null;
	GZIPOutputStream gos = null;
	ObjectOutputStream oos = null;
	try {
	    fos = new FileOutputStream(fileName);
	    gos = new GZIPOutputStream(fos);
	    oos = new ObjectOutputStream(gos);
	    oos.writeObject(vlo);
	    oos.flush();
	    oos.close();
	    gos.close();
	    fos.close();
	    return true;
	} catch (IOException ioe) {
	    ioe.printStackTrace();
	    return false;
	}
    }

    /**
     * Load g zip object.
     *
     * @param fileName
     *            the file name
     * @return object
     */
    // TODO Método inútil.
    public static Object loadGZipObject(String fileName) {
	Object obj = null;
	FileInputStream fis = null;
	GZIPInputStream gis = null;
	ObjectInputStream ois = null;
	try {
	    fis = new FileInputStream(fileName);
	    gis = new GZIPInputStream(fis);
	    ois = new ObjectInputStream(gis);
	    obj = ois.readObject();
	    ois.close();
	    gis.close();
	    fis.close();
	} catch (IOException ioe) {
	    ioe.printStackTrace();
	} catch (ClassNotFoundException cnfe) {
	    cnfe.printStackTrace();
	}
	return obj;
    }
}
