package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import main.Action;
import main.Plan;

public class ButtonText extends Button{

	public static final Font font = new Font("Tw Cen MT",Font.BOLD,26);
	
	private String txt;
	private Color color;
	private boolean circle;
	
	public ButtonText(Plan plan,String txt, int x, int y, int sx, int sy,Color color, Action act) {
		super(plan, x, y, sx, sy, act);
		this.color=color;
		this.txt=txt;
	}

	@Override
	public void render(Graphics g) {
		if(alph==0)return;
		g.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),alph));
		
		if(circle)g.drawOval(bounds.x, bounds.y, bounds.width, bounds.height);
		else g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
		
		g.setFont(font);
		Graphics2D g2=(Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		int strWid=g2.getFontMetrics().stringWidth(txt);
		g.drawString(txt, bounds.x+bounds.width/2-strWid/2, bounds.y+bounds.height/2+9);
		
		if(hover && visible) {
			g.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),100));
			if(circle)g.fillOval(bounds.x, bounds.y, bounds.width, bounds.height);
			else g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		}
	}
	/*public void render(Graphics g,int x,int y) {
		if(alph==0)return;
		g.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),alph));
		
		if(circle)g.drawOval(bounds.x+x, bounds.y+y, bounds.width, bounds.height);
		else g.drawRect(bounds.x+x, bounds.y+y, bounds.width, bounds.height);
		
		g.setFont(font);
		Graphics2D g2=(Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		int strWid=g2.getFontMetrics().stringWidth(txt);
		g.drawString(txt, bounds.x+x+bounds.width/2-strWid/2, bounds.y+y+bounds.height/2+9);
		
		if(hover && visible) {
			g.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),60));
			if(circle)g.fillOval(bounds.x+x, bounds.y+y, bounds.width, bounds.height);
			else g.fillRect(bounds.x+x, bounds.y+y, bounds.width, bounds.height);
		}
		g.setColor(Color.red);
		g.fillRect(bounds.x+x-1, bounds.y+y-1, 2, 2);
	}*/
	@Override
	public void setCircle() {this.circle=true;}
	@Override
	public void setColor(Color to) {this.color=to;}
}
