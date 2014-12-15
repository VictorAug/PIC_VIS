package br.iesb.VIS2048.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.zip.ZipException;

/**
 * The Class FileDataBase.
 */
public class FileDataBase {

	/** The app path string. */
	private final static String appPathString = "./data";
	
	/** The DB path string. */
	private final static String DBPathString = appPathString + "/DB";
	
	/** The DB file. */
	private static String DBFile = DBPathString + "/DB1.txt";
	
	/** The file. */
	private File file;
	
	/** To count files created. */
	private Integer count;

	/**
	 * Instantiates a new file data base.
	 */
	public FileDataBase() {
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
	private boolean createDirectory(String path) {
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
	private boolean createFile(String path) throws ZipException, IOException {
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
	public static boolean logVector(Double[] leitura) {
		String text = String.valueOf(Calendar.getInstance().getTime());
		for (int i = 0; i < 2048; i++)
			text += ", " + String.valueOf(leitura[i]);
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

}
