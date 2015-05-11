package br.iesb.VIS2048.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
 * Classe DBHandler.
 */
public class DBHandler {

    private final static String APP_PATH_STRING = "./Vis/";
    private final static String DB_PATH_STRING = APP_PATH_STRING + "NewDB/";
    private final static String DB_FILE_CONFIG = DB_PATH_STRING + "config/";
    private static String DB_FILE_COLLECTION = DB_PATH_STRING + "collection/";
    private String mainDB;

    private ArrayList<String> collection = new ArrayList<String>();
    private DBChartCollection dbChart;

    public DBHandler() {
	createDir(APP_PATH_STRING);
	createDir(DB_PATH_STRING);
	createDir(DB_FILE_CONFIG);
	createDir(DB_FILE_COLLECTION);
	for (File file : getFilesList(DB_FILE_COLLECTION))
	    collection.add(file.getName().toLowerCase());

	for (int i = 1; i < collection.size() + 2; i++) {
	    if (!collection.contains(("solucao" + i).toLowerCase())) {
		mainDB = "solucao" + i;
		break;
	    }
	}
	System.err.print(mainDB);
	// DBChart = new DBChartCollection();
	System.out.println("Ready");
    }

    // TODO Método inútil.
    public void saveChartCollection() {
	saveGZipObject(dbChart, mainDB);
    }

    private static void createDir(String path) {
	if (!Files.exists(Paths.get(path))) {
	    new File(path).mkdir();
	}
    }

    public static File[] getFilesList(String directoryName) {
	return new File(directoryName).listFiles();
    }

    public void updateCollectionList() {
	collection.clear();
	for (File file : getFilesList(DB_FILE_COLLECTION))
	    collection.add(file.getName().toLowerCase());
    }

    // TODO Método inútil.
    public void listFilesAndFolders(String directoryName) {
	File directory = new File(directoryName);
	File[] fList = directory.listFiles();
	for (File file : fList) {
	    System.out.println(file.getName());
	}
    }

    // TODO Método inútil.
    public void listFiles(String directoryName) {
	File directory = new File(directoryName);
	File[] fList = directory.listFiles();
	Arrays.asList(fList).stream().filter(file -> file.isFile()).forEach(file -> System.out.println(file.getName()));
    }

    // TODO Método inútil.
    public void listFolders(String directoryName) {
	File directory = new File(directoryName);
	File[] fList = directory.listFiles();
	Arrays.asList(fList).stream().filter(file -> file.isFile()).forEach(file -> System.out.println(file.getName()));
    }

    public boolean insert(DBChartCollection col) {
	createDir(DB_FILE_COLLECTION + mainDB);
	String name = col.getChart(0).getTimestamp() + "-" + col.getChart(col.count() - 1).getTimestamp();

	try {
	    // OutputStream outputStream = new FileOutputStream(new File(mainDB
	    // + "/" + "index.txt"));
	    // outputStream.write(name.getBytes(), 0, name.length());
	    // outputStream.close();
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(DB_FILE_COLLECTION + mainDB + "/" + "index.txt", true)));
	    out.println(name);
	    out.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	saveGZipObject(col, DB_FILE_COLLECTION + mainDB + "/" + name + ".vis");
	return false;
    }

    // TODO Método inútil.
    public DBChartCollection select() {
	File directory = new File(DB_FILE_COLLECTION + mainDB);
	File[] fList = directory.listFiles();
	Arrays.asList(fList).stream().filter(file -> file.isFile()).forEach(file -> System.out.println(file.getName()));
	return null;
    }

    // TODO Método inútil
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

    // TODO Método inútil.
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

    private static boolean writeToFile(String text) {
	try {
	    createDir(APP_PATH_STRING);
	    createDir(DB_PATH_STRING);
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

    private static String compress(String str) throws IOException {
	if (str == null || str.length() == 0) {
	    return str;
	}
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	GZIPOutputStream gzip = new GZIPOutputStream(out);
	gzip.write(str.getBytes());
	gzip.close();
	String outStr = out.toString("ISO-8859-1");
	return outStr;
    }

    // TODO Método inútil.
    public static String decompress(String str) throws IOException {
	if (str == null || str.length() == 0) {
	    return str;
	}
	GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(str.getBytes("ISO-8859-1")));
	BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "ISO-8859-1"));
	String outStr = "";
	String line;
	while ((line = bf.readLine()) != null) {
	    outStr += line;
	}
	return outStr;
    }

    // TODO Método inútil.
    public static void saveChartCollection(DBChartCollection chartCollection, String mainDBFileName) {
	System.out.println(chartCollection.count());
    }

    // TODO Método inútil.
    public boolean update() {
	return false;
    }

    // TODO Método inútil.
    public boolean remove() {
	return false;
    }

    public String getMainDB() {
	return mainDB == null ? null : new String(mainDB);
    }

    // TODO Método inútil.
    public String getMainDBFileName() {
	return DB_FILE_COLLECTION + mainDB;
    }

    public void setMainDB(String mainDB) {
	if (mainDB == null) {
	    this.mainDB = null;
	} else {
	    this.mainDB = new String(mainDB);
	}
    }

    public static String getDBFileCollection() {
	return DB_FILE_COLLECTION == null ? null : new String(DB_FILE_COLLECTION);
    }

    // TODO Método inútil.
    public static void setDBFileCollection(String dBFileCollection) {
	if (dBFileCollection == null) {
	    DB_FILE_COLLECTION = null;
	} else {
	    DB_FILE_COLLECTION = new String(dBFileCollection);
	}
    }

    public ArrayList<String> getCollectionList() {
	return collection == null ? null : new ArrayList<>(collection);
    }

}
