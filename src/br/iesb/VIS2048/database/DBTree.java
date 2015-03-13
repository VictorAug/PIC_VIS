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

public class DBTree {
	private final static String appPathString = "./Vis/";
	private final static String DBPathString = appPathString + "NewDB/";
	private static String DBFileConfig = DBPathString + "config/";
	private static String DBFileCollection = DBPathString + "collection/";
	private ArrayList<String> collection = new ArrayList<String>();
	private String mainDB = null;
	
	public DBTree(String mainDB) {
		this.mainDB = mainDB;
		createDir(appPathString);
		createDir(DBPathString);
		createDir(DBFileConfig);
		createDir(DBFileCollection);

		for (File file : getFilesList(DBFileCollection))
			if (file.getName().endsWith(".zip")) {
				collection.add(file.getName().toLowerCase());
			}
		if(this.mainDB == null){
			for (int i = 1; i < collection.size() + 2; i++) {
				if (!collection.contains(("solucao" + i + ".zip").toLowerCase())) {
					this.mainDB = "solucao" + i + ".zip";
					break;
				}
			}
		}
		try {
			ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(
					DBFileCollection + mainDB));
			zip.putNextEntry(new ZipEntry("index.txt"));
			zip.closeEntry();
			zip.flush();
			zip.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public DBTree() {
		createDir(appPathString);
		createDir(DBPathString);
		createDir(DBFileConfig);
		createDir(DBFileCollection);

		for (File file : getFilesList(DBFileCollection))
			if (!file.getName().endsWith(".zip")) {
				collection.add(file.getName().toLowerCase());
			}
		if(this.mainDB == null){
			for (int i = 1; i < collection.size() + 2; i++) {
				if (!collection.contains(("solucao" + i).toLowerCase())) {
					this.mainDB = "solucao" + i;
					break;
				}
			}
		}
		
	}
	public static File[] getFilesList(String directoryName) {
		return new File(directoryName).listFiles();
	}
	public DBChartCollection select() {

		return null;
	}

	public boolean insert(DBChartCollection col) {
		saveGZipObject(col, "teste");
		return false;
	}

	public boolean update() {
		return false;
	}

	public boolean remove() {
		return false;
	}

	private static void createDir(String path) {
		if (!Files.exists(Paths.get(path))) {
			new File(path).mkdir();
		}
	}

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
