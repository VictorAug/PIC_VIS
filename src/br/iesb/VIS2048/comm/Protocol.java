package br.iesb.VIS2048.comm;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Protocol {

    private static final int BEGIN_PACKET = 0xAA;
    private static final int END_PACKET = 0xBB;
    private static final int QTDE_PROMEDIACOES = 0x01;
    private static final int TEMPO_INTEGRACAO = 0x02;
    private static final int LED_STATUS = 0x03;
    private static final int LEITURA_CCD = 0x04;
    private static final int CRC = 0x00;

    public static String getParameter(int qtPro, int tmInt, int led, int ccd) {
	StringBuilder protocolString = new StringBuilder();
	StringBuilder data = new StringBuilder();
	protocolString.append(Integer.toHexString(BEGIN_PACKET));

	int i = 0;

	if (qtPro >= 0) {
	    data.append(" " + QTDE_PROMEDIACOES);
	    data.append(Integer.toHexString(qtPro).length() / 2 + 1);
	    data.append(Integer.toHexString(bigToLittleEndian(qtPro)));
	    i++;
	}
	if (tmInt >= 0) {
	    data.append(" " + TEMPO_INTEGRACAO);
	    data.append(Integer.toHexString(tmInt).length() / 2 + 1);
	    data.append(Integer.toHexString(bigToLittleEndian(tmInt)));
	    i++;
	}
	if (led >= 0) {
	    data.append(" " + LED_STATUS);
	    data.append(Integer.toHexString(led).length() / 2 + 1);
	    data.append(Integer.toHexString(bigToLittleEndian(led)));
	    i++;
	}
	if (ccd >= 0) {
	    data.append(" " + LEITURA_CCD);
	    data.append(Integer.toHexString(ccd).length() / 2 + 1);
	    data.append(Integer.toHexString(bigToLittleEndian(ccd)));
	    i++;
	}

	protocolString.append(" " + Integer.toHexString(i));
	protocolString.append(data);
	protocolString.append(" " + Integer.toHexString(CRC));
	protocolString.append(" " + Integer.toHexString(END_PACKET));
	return protocolString.toString().toUpperCase();
    }

    public static int bigToLittleEndian(int bigendian) {
	ByteBuffer buf = ByteBuffer.allocate(4);

	// buf.order(ByteOrder.BIG_ENDIAN);
	buf.putInt(bigendian);

	buf.order(ByteOrder.LITTLE_ENDIAN);
	return buf.getInt(0);
    }
}
