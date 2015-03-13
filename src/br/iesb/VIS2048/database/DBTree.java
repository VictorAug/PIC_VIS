package br.iesb.VIS2048.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import br.iesb.VIS2048.frame.Chart;

public class DBTree {
	private final static String appPathString = "./Vis/";
	private final static String DBPathString = appPathString + "DB/";
	private static String DBFileConfig = DBPathString + "config/";
	private static String DBFileCollection = DBPathString + "collection/";
	
	public DBTree() {
		createDir(appPathString);
		createDir(DBPathString);
		createDir(DBFileConfig);
//		createDir(getDBFileCollection());
//		for (File file : getFilesList(getDBFileCollection()))
//			if (file.getName().endsWith(".vis")) {
//				collection.add(file.getName().toLowerCase());
//			}
//		for (int i = 1; i < collection.size() + 2; i++) {
//			if (!collection.contains(("solucao" + i + ".vis").toLowerCase())) {
//				mainDB = "solucao" + i + ".vis";
//				break;
//			}
//		}
	}

	public Chart select(){
		return null;
	}
	public Chart insert(){
		return null;
	}
	public Chart update(){
		return null;
	}
	public Chart remove(){
		return null;
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
