package br.iesb.VIS2048.comm;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class Protocol {

    private static final int BEGIN_PACKET = 0xAA;
    private static final int END_PACKET = 0xBB;
    private static final int QTDE_PROMEDIACOES = 0x01;
    private static final int TEMPO_INTEGRACAO = 0x02;
    private static final int LED_STATUS = 0x03;
    private static final int LEITURA_CCD = 0x04;
    private static final int CRC = 0x00;

    public static String getParameter(int qtPro, int tmInt, int led, int ccd) {
	StringBuilder data = new StringBuilder();
	String buffer = " ";
	StringBuilder protocolString = new StringBuilder();
	protocolString.append(Integer.toHexString(BEGIN_PACKET));

	int i = 0;

	if (qtPro >= 0) {
	    buffer = littleEndian(qtPro);
	    data.append(fillZeros(Integer.toString(QTDE_PROMEDIACOES)));
	    data.append(fillZeros(Integer.toString(buffer.length() / 2)));
	    data.append(buffer);
	    i++;
	}
	if (tmInt >= 0) {
	    buffer = littleEndian(tmInt);
	    data.append(fillZeros(Integer.toString(TEMPO_INTEGRACAO)));
	    data.append(fillZeros(Integer.toString(buffer.length() / 2)));
	    data.append(buffer);
	    i++;
	}
	if (led >= 0) {
	    buffer = littleEndian(led);
	    data.append(fillZeros(Integer.toString(LED_STATUS)));
	    data.append(fillZeros(Integer.toString(buffer.length() / 2)));
	    data.append(buffer);
	    i++;
	}
	if (ccd > 0) {
	    buffer = littleEndian(ccd);
	    data.append(fillZeros(Integer.toString(LEITURA_CCD)));
	    data.append(fillZeros(Integer.toString(buffer.length() / 2)));
	    data.append(buffer);
	    i++;
	}

	protocolString.append(fillZeros(Integer.toHexString(i)));
	protocolString.append(data);
	protocolString.append(fillZeros(Integer.toHexString(CRC)));
	protocolString.append(Integer.toHexString(END_PACKET));
	return protocolString.toString().toUpperCase();
    }

    private static String fillZeros(String a) {
	return (a.length() % 2 != 0 ? "0" + a : a);
    }

    public static String littleEndian(int a) {
	String b = fillZeros(Integer.toHexString(a));
	String c = "";
	int j = b.length();
	for (int i = j; i > 0; i--) {
	    if (i % 2 == 1) {
		c += b.charAt(i - 1) + "" + b.charAt(i);
	    }
	}
	return c;
    }

    // TODO Método inútil.
    public static int bigToLittleEndian(int bigEndian) {
	ByteBuffer buf = ByteBuffer.allocate(4);
	buf.putInt(bigEndian);
	buf.order(ByteOrder.LITTLE_ENDIAN);
	return buf.getInt(0);
    }
}
