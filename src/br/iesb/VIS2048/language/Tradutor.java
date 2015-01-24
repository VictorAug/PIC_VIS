package br.iesb.VIS2048.language;

public class Tradutor {

    private Dicionario dicionario;
    
    public Tradutor(Dicionario dicionario) {
	this.dicionario = dicionario;
    }

    public String traduzirParaIngles(String palavra) {
	Palavra p = dicionario.consultar(palavra);
	if (p != null) {
	    return p.getIngles();
	}
	return null;
    }
    
    public String traduzirParaPortugues(String palavra) {
	Palavra p = dicionario.consultar(palavra);
	if (p != null) {
	    return p.getPortugues();
	}
	return null;
    }
    
    public Dicionario getDicionario() {
	return dicionario;
    }

    public void setDicionario(Dicionario dicionario) {
	this.dicionario = dicionario;
    }
    
}
