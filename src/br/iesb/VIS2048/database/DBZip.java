package br.iesb.VIS2048.database;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Class DBZip.
 */
public class DBZip {

    /** Atributo arquivo zip atual. */
    private File arquivoZipAtual;

    /** Constante TAMANHO_BUFFER. */
    private static final int TAMANHO_BUFFER = 2048; // 2 Kb
    
    /**
     * Listar entradas zip.
     *
     * @param arquivo
     *            the arquivo
     * @return list
     * @throws ZipException
     *             the zip exception
     * @throws IOException
     *             Sinaliza que uma I/O exception ocorreu.
     */
    public List<ZipEntry> listarEntradasZip(File arquivo) throws ZipException, IOException {
	List<ZipEntry> entradasDoZip = new ArrayList<ZipEntry>();
	ZipFile zip = null;
	try {
	    zip = new ZipFile(arquivo);
	    Enumeration<? extends ZipEntry> e = zip.entries();
	    ZipEntry entrada = null;
	    while (e.hasMoreElements()) {
		entrada = (ZipEntry) e.nextElement();
		entradasDoZip.add(entrada);
	    }
	    setArquivoZipAtual(arquivo);
	} finally {
	    if (zip != null) {
		zip.close();
	    }
	}
	return entradasDoZip;
    }

    /**
     * Extrair zip.
     *
     * @param diretorio
     *            the diretorio
     * @throws ZipException
     *             the zip exception
     * @throws IOException
     *             Sinaliza que uma I/O exception ocorreu.
     */
    public void extrairZip(File diretorio) throws ZipException, IOException {
	extrairZip(this.getArquivoZipAtual(), diretorio);
    }

    /**
     * Extrair zip.
     *
     * @param arquivoZip
     *            the arquivo zip
     * @param diretorio
     *            the diretorio
     * @throws ZipException
     *             the zip exception
     * @throws IOException
     *             Sinaliza que uma I/O exception ocorreu.
     */
    private void extrairZip(File arquivoZip, File diretorio) throws ZipException, IOException {
	ZipFile zip = null;
	File arquivo = null;
	InputStream is = null;
	OutputStream os = null;
	byte[] buffer = new byte[TAMANHO_BUFFER];
	try {
	    // cria diretório informado, caso não exista
	    if (!diretorio.exists()) {
		diretorio.mkdirs();
	    }
	    if (!diretorio.exists() || !diretorio.isDirectory()) {
		throw new IOException("Informe um diretório válido");
	    }
	    zip = new ZipFile(arquivoZip);
	    Enumeration<? extends ZipEntry> e = zip.entries();
	    while (e.hasMoreElements()) {
		ZipEntry entrada = (ZipEntry) e.nextElement();
		arquivo = new File(diretorio, entrada.getName());
		// se for diretório inexistente, cria a estrutura
		// e pula pra próxima entrada
		if (entrada.isDirectory() && !arquivo.exists()) {
		    arquivo.mkdirs();
		    continue;
		}
		// se a estrutura de diretórios não existe, cria
		if (!arquivo.getParentFile().exists()) {
		    arquivo.getParentFile().mkdirs();
		}
		try {
		    // lê o arquivo do zip e grava em disco
		    is = zip.getInputStream(entrada);
		    os = new FileOutputStream(arquivo);
		    int bytesLidos = 0;
		    if (is == null) {
			throw new ZipException("Erro ao ler a entrada do zip: " + entrada.getName());
		    }
		    while ((bytesLidos = is.read(buffer)) > 0) {
			os.write(buffer, 0, bytesLidos);
		    }
		} finally {
		    if (is != null) {
			try {
			    is.close();
			} catch (Exception ex) {
			}
		    }
		    if (os != null) {
			try {
			    os.close();
			} catch (Exception ex) {
			}
		    }
		}
	    }
	} finally {
	    if (zip != null) {
		try {
		    zip.close();
		} catch (Exception e) {
		}
	    }
	}
    }

    /**
     * Criar zip.
     *
     * @param arquivoZip
     *            the arquivo zip
     * @param arquivos
     *            the arquivos
     * @return list
     * @throws ZipException
     *             the zip exception
     * @throws IOException
     *             Sinaliza que uma I/O exception ocorreu.
     */
    public List<ZipEntry> criarZip(File arquivoZip, File[] arquivos) throws ZipException, IOException {
	FileOutputStream fos = null;
	BufferedOutputStream bos = null;
	setArquivoZipAtual(null);
	try {
	    // adiciona a extensão .zip no arquivo, caso não exista
	    if (!arquivoZip.getName().toLowerCase().endsWith(".zip")) {
		arquivoZip = new File(arquivoZip.getAbsolutePath() + ".zip");
	    }
	    fos = new FileOutputStream(arquivoZip);
	    bos = new BufferedOutputStream(fos, TAMANHO_BUFFER);
	    List<ZipEntry> listaEntradasZip = criarZip(bos, arquivos);
	    setArquivoZipAtual(arquivoZip);
	    return listaEntradasZip;
	} finally {
	    if (bos != null) {
		try {
		    bos.close();
		} catch (Exception e) {
		}
	    }
	    if (fos != null) {
		try {
		    fos.close();
		} catch (Exception e) {
		}
	    }
	}
    }

    /**
     * Criar zip.
     *
     * @param os
     *            the os
     * @param arquivos
     *            the arquivos
     * @return list
     * @throws ZipException
     *             the zip exception
     * @throws IOException
     *             Sinaliza que uma I/O exception ocorreu.
     */
    public List<ZipEntry> criarZip(OutputStream os, File[] arquivos) throws ZipException, IOException {
	if (arquivos == null || arquivos.length < 1) {
	    throw new ZipException("Adicione ao menos um arquivo ou diretório");
	}
	List<ZipEntry> listaEntradasZip = new ArrayList<ZipEntry>();
	ZipOutputStream zos = null;
	try {
	    zos = new ZipOutputStream(os);
	    for (int i = 0; i < arquivos.length; i++) {
		String caminhoInicial = arquivos[i].getParent();
		List<ZipEntry> novasEntradas = adicionarArquivoNoZip(zos, arquivos[i], caminhoInicial);
		if (novasEntradas != null) {
		    listaEntradasZip.addAll(novasEntradas);
		}
	    }
	} finally {
	    if (zos != null) {
		try {
		    zos.close();
		} catch (Exception e) {
		}
	    }
	}
	return listaEntradasZip;
    }

    /**
     * Adicionar arquivo no zip.
     *
     * @param zos
     *            the zos
     * @param arquivo
     *            the arquivo
     * @param caminhoInicial
     *            the caminho inicial
     * @return list
     * @throws IOException
     *             Sinaliza que uma I/O exception ocorreu.
     */
    private List<ZipEntry> adicionarArquivoNoZip(ZipOutputStream zos, File arquivo, String caminhoInicial) throws IOException {
	List<ZipEntry> listaEntradasZip = new ArrayList<ZipEntry>();
	FileInputStream fis = null;
	BufferedInputStream bis = null;
	byte buffer[] = new byte[TAMANHO_BUFFER];
	try {
	    // diretórios não são adicionados
	    if (arquivo.isDirectory()) {
		// recursivamente adiciona os arquivos dos diretórios abaixo
		File[] arquivos = arquivo.listFiles();
		for (int i = 0; i < arquivos.length; i++) {
		    List<ZipEntry> novasEntradas = adicionarArquivoNoZip(zos, arquivos[i], caminhoInicial);
		    if (novasEntradas != null) {
			listaEntradasZip.addAll(novasEntradas);
		    }
		}
		return listaEntradasZip;
	    }
	    String caminhoEntradaZip = null;
	    int idx = arquivo.getAbsolutePath().indexOf(caminhoInicial);
	    if (idx >= 0) {
		// calcula os diretórios a partir do diretório inicial
		// isso serve para não colocar uma entrada com o caminho
		// completo
		caminhoEntradaZip = arquivo.getAbsolutePath().substring(idx + caminhoInicial.length() + 1);
	    }
	    ZipEntry entrada = new ZipEntry(caminhoEntradaZip);
	    zos.putNextEntry(entrada);
	    zos.setMethod(ZipOutputStream.DEFLATED);
	    fis = new FileInputStream(arquivo);
	    bis = new BufferedInputStream(fis, TAMANHO_BUFFER);
	    int bytesLidos = 0;
	    while ((bytesLidos = bis.read(buffer, 0, TAMANHO_BUFFER)) != -1) {
		zos.write(buffer, 0, bytesLidos);
	    }
	    listaEntradasZip.add(entrada);
	} finally {
	    if (bis != null) {
		try {
		    bis.close();
		} catch (Exception e) {
		}
	    }
	    if (fis != null) {
		try {
		    fis.close();
		} catch (Exception e) {
		}
	    }
	}
	return listaEntradasZip;
    }

    /**
     * Fechar zip.
     */
    public void fecharZip() {
	setArquivoZipAtual(null);
    }

    /**
     * Retorna arquivo zip atual.
     *
     * @return arquivo zip atual
     */
    public File getArquivoZipAtual() {
	return arquivoZipAtual == null ? null : new File(arquivoZipAtual.getAbsolutePath());
    }

    /**
     * Atribui o valor arquivo zip atual.
     *
     * @param arquivoZipAtual
     *            novo arquivo zip atual
     */
    private void setArquivoZipAtual(File arquivoZipAtual) {
	if (arquivoZipAtual == null) {
	    this.arquivoZipAtual = null;
	} else {
	    this.arquivoZipAtual = new File(arquivoZipAtual.getAbsolutePath());
	}
    }

}