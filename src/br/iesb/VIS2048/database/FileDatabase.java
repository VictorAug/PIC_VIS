package br.iesb.VIS2048.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileDatabase {
	private String appPathString = "./data";
	private String DBPathString = appPathString+"/DB";
	private String DBFile = DBPathString + "/DB1.txt";
	
	private boolean createDirectory(String path){
		if(Files.exists(Paths.get(path))){
			System.out.println("Diretório já existe!");
			return true;
		}else{
			System.out.println("Diretório já existe!");
			File dir = new File(path); 
			if(dir.mkdir()){
				System.out.println("Diretório criado com sucesso");
				return true;
			}else{
				System.out.println("Diretório não pôde ser criado");
				return false;
			}
		}
	}
	
	private boolean createFile(String path){
		File f = new File(path);
		if(f.isFile()){ 
			System.out.println("Arquivo já existe!");
			return true;
		}
		else{
			System.out.println("Arquivo não existe");
			try {
				if(f.createNewFile()){
					System.out.println("Arquivo criado");
					return true;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				return false;
			}
			
		}
		return false;
	}

//	private boolean readFromFile(){
//		FileInputStream in = null;
//		try {
//			in = new FileInputStream(DBFile);
//			int c; 
//			while( (c=in.read()) != -1 )
//				System.out.print((char) c);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally{
//			if(in != null){
//				try {
//					in.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return false;
//	}
	
	private double[] fillRandomVector(double[] vet, int size){
		vet = new double[size];
		for (int i=0;i<size;i++)
			vet[i] = Math.random() * 1000;
		return vet;
	}
	
	private boolean logVector(long timestamp, double[] vet){
		String text = String.valueOf(timestamp);
		for (int i=0;i<10;i++)
			text = text + ',' + String.valueOf(vet[i]);
		writeToFile(text);
		return true;
	}
	
	private boolean writeToFile(String text){
		try {
			File f = new File(DBFile);
			FileWriter fw = new FileWriter(f,true);
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
	
	FileDatabase(){
		if(createDirectory(appPathString)) System.out.println("Podemos continuar");
		if(createDirectory(DBPathString)) System.out.println("Podemos continuar");
		if(createFile(DBFile)) System.out.println("Podemos continuar");
		int vetSize = 10;
		double vet[] = fillRandomVector(new double[vetSize], vetSize); 
		System.out.println(System.currentTimeMillis());
		//for(double i = 0; i<1000; i++)
			logVector(System.currentTimeMillis(), vet);
		//readFromFile();
		//System.out.println(System.currentTimeMillis() / 1000L);
		
		try(BufferedReader br = new BufferedReader(new FileReader(DBFile))) {
			String[] dados = new String[vetSize];
			double lineNumber = 1;
			String line;
			Pattern pattern = Pattern.compile("^\\d+");
		    Matcher matcher;
		    while((line = br.readLine()) != null) {
		    	matcher = pattern.matcher(line);
		    	while (matcher.find()) {
		    		Date date = new Date( Long.parseLong(matcher.group(), 10) );
		    		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss aa",Locale.getDefault());
		    		System.out.println("Linha: "+lineNumber + " - Data: " + format.format(date));
		    	}
		    	Pattern replace = Pattern.compile("^\\d+,");
		        Matcher matcher2 = replace.matcher(line);
		        System.out.println("Dados: " + matcher2.replaceAll(""));
		        
		        dados = matcher2.replaceAll("").split(",");
		        for(String a : dados)
		        	System.out.println(Double.parseDouble(a));
		    	System.out.println();
		    	lineNumber++;
		    }
		    // line is not visible here.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
