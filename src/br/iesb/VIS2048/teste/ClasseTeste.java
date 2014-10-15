package br.iesb.VIS2048.teste;

import java.awt.EventQueue;

import br.iesb.VIS2048.frame.BaseFrame;

public class ClasseTeste {
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					BaseFrame baseFrame = new BaseFrame();
					baseFrame.getFrame().setVisible(true);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}			
}
