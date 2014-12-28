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
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipException;

/**
 * The Class FileDataBase.
 */
public class DBHandler {

	/** The app path string. */
	private final static String appPathString = "./data";
	
	/** The DB path string. */
	private final static String DBPathString = appPathString + "/DB";
	
	/** The DB file. */
	private static String DBFile = DBPathString + "/DB1.txt";
	
	/** The file. */
	private static File file;
	
	/** To count files created. */
	private Integer count;

	/**
	 * Instantiates a new file data base.
	 */
	public DBHandler() {
		createDirectory(appPathString);
		createDirectory(DBPathString);
		try {
			if (createFile(DBFile))
				System.out.println("DBFile: " + DBFile);
		} catch (ZipException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reload title for DBFile name.
	 */
	public void reloadTitle() {
		DBFile.concat("/DB"+(count++)+".txt");
	}
	
	/**
	 * Reset count for DBFile name.
	 */
	public void resetCount() {
		count = 0;
	}

	/**
	 * Creates the directory.
	 *
	 * @param path the path
	 * @return true, if successful
	 */
	private static boolean createDirectory(String path) {
		if (Files.exists(Paths.get(path)))
			return true;// Diretório já existe
		else {
			File dir = new File(path);
			if (dir.mkdir())
				return true;// Diretório criado com sucesso
			else
				return false;// Diretório não pôde ser criado
		}
	}

	/**
	 * Creates the file.
	 *
	 * @param path the path
	 * @return true, if successful
	 * @throws ZipException the zip exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static boolean createFile(String path) throws ZipException, IOException {
		file = new File(path);
		if (file.isFile()) {
			return true;// Arquivo já existe
		} else {
			try {
				if (file.createNewFile()) {
					return true;// Arquivo criado
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Fill random vector.
	 *
	 * @param vet the vet
	 * @param size the size
	 * @return the double[]
	 */
	public double[] fillRandomVector(double[] vet, int size) {
		vet = new double[size];
		for (int i = 0; i < size; i++)
			vet[i] = Math.random() * 1000;
		return vet;
	}

	/**
	 * Log vector.
	 *
	 * @param leitura the vet
	 * @return true, if successful
	 */
	public static void saveGZipObject(Object vlo, String fileName) {
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
		} catch(IOException ioe) {
			ioe.printStackTrace();
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
	/**
	 * Write to file.
	 *
	 * @param text the text
	 * @return true, if successful
	 */
	private static boolean writeToFile(String text) {
		try {
			createDirectory(appPathString);
			createDirectory(DBPathString);
			try {
				createFile(DBFile);
				//if (createFile(DBFile))
					//System.out.println("DBFile: " + DBFile);
			} catch (ZipException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			File f = new File(DBFile);
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
        //System.out.println("Output String lenght : " + outStr.length());
        return outStr;
     }	

}
