package node;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import ui.ButtonText;

public class Slot {
	
	private int[] loc;
	private String player;
	private Color color;
	
	public Slot(String player,Color color,int x,int y) {
		this.player=player;
		this.color=color;
		this.loc=new int[] {x,y};
	}
	
	public void render(Graphics g,int x,int y) {
		g.setColor(color);
		g.drawRect(loc[0]+x, loc[1]+y, Tree.slotX, Tree.slotY);
		
		g.setFont(ButtonText.font);
		Graphics2D g2=(Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		int strWid=g2.getFontMetrics().stringWidth(player);
		g.drawString(player, x+loc[0]+Tree.slotX/2-strWid/2,y+ loc[1]+Tree.slotY/2+9);
	}
	public void render(Graphics g,int atx,int aty,int x,int y) {
		g.setColor(color);
		g.drawRect(atx+x, aty+y, Tree.slotX, Tree.slotY);
		
		g.setFont(ButtonText.font);
		Graphics2D g2=(Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		int strWid=g2.getFontMetrics().stringWidth(player);
		g.drawString(player, atx+x+Tree.slotX/2-strWid/2, aty+y+Tree.slotY/2+9);
	}
	
	public void setPlayer(String to) {this.player=to;}
	public String getPlayer() {return this.player;}
	public void setLoc(int x,int y) {this.loc=new int[] {x,y};}
	public int[] getLoc() {return this.loc;}
	public void setColor(Color to) {this.color=to;}
	public Color getColor() {return this.color;}
}
