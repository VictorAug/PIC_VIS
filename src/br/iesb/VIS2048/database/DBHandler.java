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
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * The Class FileDataBase.
 */
public class DBHandler {

	private final static String appPathString = "./Vis/";
	private final static String DBPathString = appPathString + "DB/";
	private static String DBFileConfig = DBPathString + "config/";
	private static String DBFileCollection = DBPathString + "collection/";
	private ArrayList<String> collection = new ArrayList<String>();
	private String mainDB;
	DBChartCollection DBChart;
	
	public DBHandler() {
		createDir(appPathString);
		createDir(DBPathString);
		createDir(DBFileConfig);
		createDir(getDBFileCollection());
		
		for (File file : getFilesList(getDBFileCollection()))
			if(file.getName().endsWith(".vis")){
				collection.add(file.getName().toLowerCase());
			}		
		//System.out.println(collection.size());
		
		for(int i = 1; i<collection.size()+2; i++){
			if(!collection.contains(("solucao"+i+".vis").toLowerCase())){
				setMainDB("solucao"+i+".vis");
				break;
			}
		}
		System.err.print(getDBFileCollection()+getMainDB());
		DBChart = new DBChartCollection();
		//saveChartCollection();
		System.out.println(" Ready");
	}
	
	public ArrayList<String> getCollectionList(){
		return collection;		
	}	
	
	public void saveChartCollection(){
		saveGZipObject(DBChart, getDBFileCollection()+getMainDB());
	}
	
	private static void createDir(String path){
		if(!Files.exists(Paths.get(path))){
			new File(path).mkdir();
		}
	}
//	private static void createFile(String path){
//		if (!new File(path).isFile()){
//			try {
//				new File(path).createNewFile();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
	public static File[] getFilesList(String directoryName){
        return new File(directoryName).listFiles();
    }
	public void listFilesAndFolders(String directoryName){
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        for (File file : fList){
            System.out.println(file.getName());
        }
    }
	public void listFiles(String directoryName){
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        for (File file : fList){
            if (file.isFile()){
                System.out.println(file.getName());
            }
        }
    }
	public void listFolders(String directoryName){
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        for (File file : fList){
            if (file.isDirectory()){
                System.out.println(file.getName());
            }
        }
    }
//	public double[] fillRandomVector(double[] vet, int size) {
//		vet = new double[size];
//		for (int i = 0; i < size; i++)
//			vet[i] = Math.random() * 1000;
//		return vet;
//	}

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
		} catch(IOException ioe) {
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
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		return obj;
	}
	
	public static boolean logVector(Double[] leitura, String name, String description, int numberOfSamples, long timestamp) {
		String reading = "";		
		//String text = String.valueOf(Calendar.getInstance().getTime());
		String text = String.valueOf(timestamp);
		text += ", "+name;
		text += ", "+description;
		text += ", "+numberOfSamples;
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
	
	public static String compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        //System.out.println("String length : " + str.length());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        String outStr = out.toString("ISO-8859-1");
        //System.out.println("Output String lenght : " + outStr.length());
        return outStr;
     }
    public static String decompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        //System.out.println("Input String length : " + str.length());
        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(str.getBytes("ISO-8859-1")));
        BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "ISO-8859-1"));
        String outStr = "";
        String line;
        while ((line=bf.readLine())!=null) {
          outStr += line;
        }
        //System.out.println("Output String length : " + outStr.length());
        return outStr;
     }

	public String getMainDB() {
		return mainDB;
	}
	public String getMainDBFileName() {
		return getDBFileCollection()+mainDB;
	}
	public void setMainDB(String mainDB) {
		this.mainDB = mainDB;
	}

	public static String getDBFileCollection() {
		return DBFileCollection;
	}

	public static void setDBFileCollection(String dBFileCollection) {
		DBFileCollection = dBFileCollection;
	}

	public static void saveChartCollection(DBChartCollection chartCollection,String mainDBFileName) {
		System.out.println(chartCollection.count());		
		//DBZip.fecharZip();
	}	

}
