package br.iesb.VIS2048.language;

import java.util.Set;

/**
 * Class Dicionario.
 */
public class Dicionario {

    /** Atributo palavras. */
    private Set<Palavra> palavras;

    /**
     * Instancia um novo dicionario.
     *
     * @param palavras the palavras
     */
    public Dicionario(Set<Palavra> palavras) {
	this.palavras = palavras;
    }

    
    /**
     * Adicionar.
     *
     * @param palavra the palavra
     */
    public void adicionar(Palavra palavra) {
	this.palavras.add(palavra);
    }

    /**
     * Remover.
     *
     * @param palavra the palavra
     * @return boolean
     */
    public Boolean remover(String palavra) {
	return palavras.remove(palavra);
    }
    
    /**
     * Consultar.
     *
     * @param palavra the palavra
     * @return palavra
     */
    public Palavra consultar(String palavra) {
	for (Palavra p : palavras) {
	    if (p.getPortugues().equals(palavra) || p.getIngles().equals(palavra)) {
		return p;
	    }
	}
	return null;
    }

    /**
     * Retorna palavras.
     *
     * @return palavras
     */
    public Set<Palavra> getPalavras() {
	return palavras;
    }


    /**
     * Atribui o valor palavras.
     *
     * @param palavras novo palavras
     */
    public void setPalavras(Set<Palavra> palavras) {
	this.palavras = palavras;
    }
    
}
