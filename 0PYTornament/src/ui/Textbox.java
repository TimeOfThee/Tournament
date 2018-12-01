package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

import main.KeyManager;

public class Textbox {
	
	private String txt="";
	private KeyManager kM;
	private boolean open=false;
	
	public Textbox(KeyManager kM) {
		this.kM=kM;
	}
	public void update() {
		if(!open)return;
		input();
	}
	public void render(Graphics g,int x, int y,int sx,int sy,Color color) {
		if(!open)return;
		g.setColor(color);
		g.drawRect(x, y, sx, sy);
		
		g.setFont(ButtonText.font);
		Graphics2D g2=(Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		int strWid=g2.getFontMetrics().stringWidth(txt);
		g.drawString(txt, x+sx/2-strWid/2,y+sy/2+9);
	}
	public void render(Graphics g,int x, int y,int sx,int sy,Color color,Font font) {
		if(!open)return;
		g.setColor(color);
		g.drawRect(x, y, sx, sy);
		
		g.setFont(font);
		Graphics2D g2=(Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		int strWid=g2.getFontMetrics().stringWidth(txt);
		g.drawString(txt, x+sx/2-strWid/2,y+sy/2+9);
	}
	public void open(String txt) {
		this.txt=txt;
		open=true;
	}
	public String close() {
		open=false;
		return txt;
	}
	private void input() {
		for(int a=48;a<58;a++) {
			if(kM.justPressed(a)) {
				txt+=KeyEvent.getKeyText(a);
			}
		}
		for(int a=65;a<91;a++) {
			if(kM.justPressed(a)) {
				txt+=KeyEvent.getKeyText(a);
			}
		}
		if(kM.justPressed(KeyEvent.VK_BACK_SPACE) && txt.length()>0) {
			txt=txt.substring(0,txt.length()-1);
		}else if(kM.justPressed(KeyEvent.VK_SPACE)) {
			txt+=" ";
		}
	}
	public boolean isOpen(){return this.open;}
}
