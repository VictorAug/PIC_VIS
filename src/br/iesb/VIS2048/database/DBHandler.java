package br.iesb.VIS2048.database;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * The Class FileDataBase.
 */
public class DBHandler {

    /** Constante appPathString. */
    private final static String appPathString = "./Vis/";

    /** Constante DBPathString. */
    private final static String DBPathString = appPathString + "DB/";

    /** Atributo DB file config. */
    private static String DBFileConfig = DBPathString + "config/";

    /** Atributo DB file collection. */
    private static String DBFileCollection = DBPathString + "collection/";

    /** Atributo collection. */
    private ArrayList<String> collection = new ArrayList<String>();

    /** Atributo main db. */
    private String mainDB;

    /** Atributo DB chart. */
    private DBChartCollection DBChart;

    /**
     * Instancia um novo DB handler.
     */
    public DBHandler() {
	createDir(appPathString);
	createDir(DBPathString);
	createDir(DBFileConfig);
	createDir(getDBFileCollection());

	for (File file : getFilesList(getDBFileCollection()))
	    if (file.getName().endsWith(".vis")) {
		collection.add(file.getName().toLowerCase());
	    }
	// System.out.println(collection.size());

	for (int i = 1; i < collection.size() + 2; i++) {
	    if (!collection.contains(("solucao" + i + ".vis").toLowerCase())) {
		mainDB = "solucao" + i + ".vis";
		break;
	    }
	}
	System.err.print(getDBFileCollection() + getMainDB());
	DBChart = new DBChartCollection();
	// saveChartCollection();
	System.out.println(" Ready");
    }

    /**
     * Retorna collection list.
     *
     * @return collection list
     */
    public ArrayList<String> getCollectionList() {
	return new ArrayList<>(collection);
    }

    /**
     * Save chart collection.
     */
    public void saveChartCollection() {
	saveGZipObject(DBChart, getDBFileCollection() + getMainDB());
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

    // private static void createFile(String path){
    // if (!new File(path).isFile()){
    // try {
    // new File(path).createNewFile();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }
    // }
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
     * List files and folders.
     *
     * @param directoryName
     *            the directory name
     */
    public void listFilesAndFolders(String directoryName) {
	File directory = new File(directoryName);
	File[] fList = directory.listFiles();
	for (File file : fList) {
	    System.out.println(file.getName());
	}
    }

    /**
     * List files.
     *
     * @param directoryName
     *            the directory name
     */
    public void listFiles(String directoryName) {
	File directory = new File(directoryName);
	File[] fList = directory.listFiles();
	Arrays.asList(fList).stream().filter(file -> file.isFile()).forEach(file -> System.out.println(file.getName()));
    }

    /**
     * List folders.
     *
     * @param directoryName
     *            the directory name
     */
    public void listFolders(String directoryName) {
	File directory = new File(directoryName);
	File[] fList = directory.listFiles();
	Arrays.asList(fList).stream().filter(file -> file.isFile()).forEach(file -> System.out.println(file.getName()));
    }

    // public double[] fillRandomVector(double[] vet, int size) {
    // vet = new double[size];
    // for (int i = 0; i < size; i++)
    // vet[i] = Math.random() * 1000;
    // return vet;
    // }

    /**
     * Save g zip object.
     *
     * @param vlo
     *            the vlo
     * @param fileName
     *            the file name
     * @return true, se bem-sucedido
     */
    public static boolean saveGZipObject(Object vlo, String fileName) {
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

    /**
     * Log vector.
     *
     * @param leitura
     *            the leitura
     * @param name
     *            the name
     * @param description
     *            the description
     * @param numberOfSamples
     *            the number of samples
     * @param timestamp
     *            the timestamp
     * @return true, se bem-sucedido
     */
    public static boolean logVector(Double[] leitura, String name, String description, int numberOfSamples, long timestamp) {
	String reading = "";
	// String text = String.valueOf(Calendar.getInstance().getTime());
	String text = String.valueOf(timestamp);
	text += ", " + name;
	text += ", " + description;
	text += ", " + numberOfSamples;
	reading = String.valueOf(leitura[0]);
	for (int i = 1; i < 2048; i++)
	    reading += "," + String.valueOf(leitura[i]);
	try {
	    text += compress(reading);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	writeToFile(text);
	return true;
    }

    /**
     * Write to file.
     *
     * @param text
     *            the text
     * @return true, se bem-sucedido
     */
    private static boolean writeToFile(String text) {
	try {
	    createDir(appPathString);
	    createDir(DBPathString);
	    File f = new File(getDBFileCollection());
	    FileWriter fw = new FileWriter(f, true);
	    PrintWriter output = new PrintWriter(fw);
	    output.println(text);
	    output.close();
	    fw.close();
	    return true;
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return false;
    }

    /**
     * Compress.
     *
     * @param str
     *            the str
     * @return string
     * @throws IOException
     *             Sinaliza que uma I/O exception ocorreu.
     */
    public static String compress(String str) throws IOException {
	if (str == null || str.length() == 0) {
	    return str;
	}
	// System.out.println("String length : " + str.length());
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	GZIPOutputStream gzip = new GZIPOutputStream(out);
	gzip.write(str.getBytes());
	gzip.close();
	String outStr = out.toString("ISO-8859-1");
	// System.out.println("Output String lenght : " + outStr.length());
	return outStr;
    }

    /**
     * Decompress.
     *
     * @param str
     *            the str
     * @return string
     * @throws IOException
     *             Sinaliza que uma I/O exception ocorreu.
     */
    public static String decompress(String str) throws IOException {
	if (str == null || str.length() == 0) {
	    return str;
	}
	// System.out.println("Input String length : " + str.length());
	GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(str.getBytes("ISO-8859-1")));
	BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "ISO-8859-1"));
	String outStr = "";
	String line;
	while ((line = bf.readLine()) != null) {
	    outStr += line;
	}
	// System.out.println("Output String length : " + outStr.length());
	return outStr;
    }

    /**
     * Retorna main db.
     *
     * @return main db
     */
    public String getMainDB() {
	return mainDB == null ? null : new String(mainDB);
    }

    /**
     * Retorna main db file name.
     *
     * @return main db file name
     */
    public String getMainDBFileName() {
	return DBFileCollection.concat(mainDB);
    }

    /**
     * Atribui o valor main db.
     *
     * @param mainDB
     *            novo main db
     */
    public void setMainDB(String mainDB) {
	if (mainDB == null) {
	    this.mainDB = null;
	} else {
	    this.mainDB = new String(mainDB);
	}
    }

    /**
     * Retorna DB file collection.
     *
     * @return DB file collection
     */
    public static String getDBFileCollection() {
	return DBFileCollection == null ? null : new String(DBFileCollection);
    }

    /**
     * Atribui o valor DB file collection.
     *
     * @param dBFileCollection
     *            novo DB file collection
     */
    public static void setDBFileCollection(String dBFileCollection) {
	if (dBFileCollection == null) {
	    DBFileCollection = null;
	} else {
	    DBFileCollection = new String(dBFileCollection);
	}
    }

    /**
     * Save chart collection.
     *
     * @param chartCollection
     *            the chart collection
     * @param mainDBFileName
     *            the main db file name
     */
    public static void saveChartCollection(DBChartCollection chartCollection, String mainDBFileName) {
	System.out.println(chartCollection.count());
	// DBZip.fecharZip();
    }

}
