package br.iesb.VIS2048.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * The Class Zipper.
 */
public class Zipper {

    List<String> fileList;
    private static final String OUTPUT_ZIP_FILE = "./data.zip";
    private static final String SOURCE_FOLDER = "./data";

    /**
     * Instantiates a new fileList.
     */
    public Zipper() {
	fileList = new ArrayList<String>();
    }

    /**
     * Zip it.
     *
     * @param zipFile
     *            output ZIP file location
     */
    public void zipIt(String zipFile) {
	if (zipFile == null)
	    zipFile = OUTPUT_ZIP_FILE;
	byte[] buffer = new byte[1024];
	try {
	    FileOutputStream fileOutput = new FileOutputStream(zipFile);
	    ZipOutputStream zipOutput = new ZipOutputStream(fileOutput);
	    System.out.println("Output to Zip: " + zipFile);

	    for (String file : this.fileList) {

		System.out.println("File Added: " + file);
		ZipEntry zipEntry = new ZipEntry(file);
		zipOutput.putNextEntry(zipEntry);

		FileInputStream fileInput = new FileInputStream(SOURCE_FOLDER + File.separator + file);
		int len;
		while ((len = fileInput.read(buffer)) > 0)
		    zipOutput.write(buffer, 0, len);

		fileInput.close();
	    }
	    zipOutput.closeEntry();
	    zipOutput.close();
	    System.out.println("Done");
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Generate file list. Traverse a directory and get all files, and add the
     * file into fileList
     * 
     * @param node
     *            the node
     */
    public void generateFileList(File node) {
	if (node == null)
	    node = new File(SOURCE_FOLDER);
	if (node.isFile())
	    fileList.add(generateZipEntry(node.toString()));
	if (node.isDirectory())
	    for (String fileName : node.list())
		generateFileList(new File(node, fileName));
    }

    /**
     * Generate zip entry. Format the file path for zip
     *
     * @param file
     *            the file path
     * @return Formatted file path
     */
    private String generateZipEntry(String file) {
	return file.substring(SOURCE_FOLDER.length() + 1, file.length());
    }

}
