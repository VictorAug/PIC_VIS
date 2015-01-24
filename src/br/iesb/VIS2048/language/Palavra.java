package br.iesb.VIS2048.language;

/**
 * Class Palavra.
 */
public class Palavra {

    /** Atributo portugues. */
    private String portugues;

    /** Atributo ingles. */
    private String ingles;

    /**
     * Instancia um novo palavra.
     *
     * @param portugues the portugues
     * @param ingles the ingles
     */
    public Palavra(String portugues, String ingles) {
	this.portugues = portugues;
	this.ingles = ingles;
    }

    /**
     * Retorna portugues.
     *
     * @return portugues
     */
    public String getPortugues() {
	return portugues;
    }

    /**
     * Atribui o valor portugues.
     *
     * @param portugues novo portugues
     */
    public void setPortugues(String portugues) {
	this.portugues = portugues;
    }

    /**
     * Retorna ingles.
     *
     * @return ingles
     */
    public String getIngles() {
	return ingles;
    }

    /**
     * Atribui o valor ingles.
     *
     * @param ingles novo ingles
     */
    public void setIngles(String ingles) {
	this.ingles = ingles;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
}
