package br.iesb.VIS2048.comm;

public class Protocol {

	private static int BEGIN_PACKET = 0xAA;
	private static int END_PACKET = 0xBB;
	private static int QTDE_PROMEDIACOES = 0x01;
	private static int TEMPO_INTEGRACAO = 0x02;
	private static int LED_STATUS = 0x03;
	private static int LEITURA_CCD = 0x04;
	private static int CRC = 0x00;
	
	public static String getParameter(){
		String protocolString = "";
		protocolString += BEGIN_PACKET;
		
		
		protocolString += CRC;
		protocolString += END_PACKET;
		return protocolString;
	}

}
