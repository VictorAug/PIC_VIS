package br.iesb.VIS2048.comm;

public class Protocol {

	private static int BEGIN_PACKET = 0xAA;
	private static int END_PACKET = 0xBB;
	private static int QTDE_PROMEDIACOES = 0x01;
	private static int TEMPO_INTEGRACAO = 0x02;
	private static int LED_STATUS = 0x03;
	private static int LEITURA_CCD = 0x04;
	private static int CRC = 0x00;
	
	public static String getParameter(int qtPro, int tmInt, int led, int ccd){
		String protocolString = "";
		String data = "";
		protocolString += Integer.toHexString(BEGIN_PACKET);
		
		int i = 0;
		
		if(qtPro >= 0){
			data += " "+QTDE_PROMEDIACOES;
			data += Integer.toHexString(qtPro).length()/2 + 1;
			data += Integer.toHexString(qtPro);
			i++;
		}	
		if(tmInt >= 0){
			data += " "+TEMPO_INTEGRACAO;
			data += Integer.toHexString(tmInt).length()/2 + 1;
			data += Integer.toHexString(tmInt);
			i++;
		}	
		if(led >= 0){
			data += " "+LED_STATUS;
			data += Integer.toHexString(led).length()/2 + 1;
			data += Integer.toHexString(led);
			i++;
		}	
		if(ccd >= 0){
			data += " "+LEITURA_CCD;
			data += Integer.toHexString(ccd).length()/2 + 1;
			data += Integer.toHexString(ccd);
			i++;
		}	
		
		protocolString += " "+Integer.toHexString(i);
		protocolString += data;		
		protocolString += " "+Integer.toHexString(CRC);
		protocolString += " "+Integer.toHexString(END_PACKET);
		return protocolString.toUpperCase();
	}

}
