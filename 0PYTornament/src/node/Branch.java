package node;

import java.awt.Color;
import java.awt.Graphics;

import main.Action;
import main.Plan;
import ui.ButtonText;

public class Branch {

	private Tree mother;
	
	private Slot sTop,sBot,win;
	private Branch bTop,bBot;
	private int[] loc;
	private ButtonText top,bot,mid;
	private Color colorDef=new Color(100,100,100);
	private int winSet=0,flash=0;

	private Action tBtn=new Action() {
		@Override
		public void action() {
			boolean cancel=false;
			if(bTop!=null) {
				if(bTop.getWin()==null) {
					bTop.flash();
					cancel=true;
				}
			}if(bBot!=null) {
				if(bBot.getWin()==null) {
					bBot.flash();
					cancel=true;
				}
			}
			if(!cancel){
				winSet=1;
			}
		}
	},bBtn=new Action() {
		@Override
		public void action() {
			boolean cancel=false;
			if(bTop!=null) {
				if(bTop.getWin()==null) {
					bTop.flash();
					cancel=true;
				}
			}if(bBot!=null) {
				if(bBot.getWin()==null) {
					bBot.flash();
					cancel=true;
				}
			}
			if(!cancel){
				winSet=2;
			}
		}
	},mBtn=new Action() {
		@Override
		public void action() {
			winSet=0;
			flash();
		}
	};
	
	public Branch(Plan plan,Tree mother,Slot top,Slot bot,int x,int y) {
		this.mother=mother;
		this.sBot=bot;
		this.sTop=top;
		this.loc=new int[]{x,y};
		
		this.top=new ButtonText(plan, ">", top.getLoc()[0]+Tree.slotX+5, top.getLoc()[1]+Tree.slotY-Tree.btn/2, Tree.btn, Tree.btn, top.getColor(), tBtn);
		this.bot=new ButtonText(plan, ">", bot.getLoc()[0]+Tree.slotX+5, bot.getLoc()[1]+Tree.slotY-Tree.btn/2, Tree.btn, Tree.btn, bot.getColor(), bBtn);
		this.mid=new ButtonText(plan, "--", loc[0]-5-Tree.btn, loc[1]+Tree.slotY-Tree.btn/2, Tree.btn, Tree.btn, colorDef, mBtn);
		this.top.setCircle();
		this.bot.setCircle();
		this.mid.setCircle();
	}
	public Branch(Plan plan,Tree mother,Branch top,Branch bot,int x,int y) {
		this.mother=mother;
		this.bBot=bot;
		this.bTop=top;
		this.loc=new int[]{x,y};
		
		this.top=new ButtonText(plan, ">", top.getLoc()[0]+Tree.slotX+5, top.getLoc()[1]+Tree.slotY-Tree.btn/2, Tree.btn, Tree.btn, top.getColor(), tBtn);
		this.bot=new ButtonText(plan, ">", bot.getLoc()[0]+Tree.slotX+5, bot.getLoc()[1]+Tree.slotY-Tree.btn/2, Tree.btn, Tree.btn, bot.getColor(), bBtn);
		this.mid=new ButtonText(plan, "--", loc[0]-5-Tree.btn, loc[1]+Tree.slotY-Tree.btn/2, Tree.btn, Tree.btn, colorDef, mBtn);
		this.top.setCircle();
		this.bot.setCircle();
		this.mid.setCircle();
	}
	public Branch(Plan plan,Tree mother,Branch top,Slot bot,int x,int y) {
		this.mother=mother;
		this.sBot=bot;
		this.bTop=top;
		this.loc=new int[]{x,y};
		this.top=new ButtonText(plan, ">", top.getLoc()[0]+Tree.slotX+5, top.getLoc()[1]+Tree.slotY-Tree.btn/2, Tree.btn, Tree.btn, top.getColor(),tBtn);
		this.bot=new ButtonText(plan, ">", bot.getLoc()[0]+Tree.slotX+5, bot.getLoc()[1]+Tree.slotY-Tree.btn/2, Tree.btn, Tree.btn, bot.getColor(),bBtn);
		this.mid=new ButtonText(plan, "--", loc[0]-5-Tree.btn, loc[1]+Tree.slotY-Tree.btn/2, Tree.btn, Tree.btn, colorDef, mBtn);
		this.top.setCircle();
		this.bot.setCircle();
		this.mid.setCircle();
	}
	public void update() {
		if(sTop!=null) {
			top.setLoc(sTop.getLoc()[0]+mother.getLoc()[0]+Tree.slotX+5, sTop.getLoc()[1]+mother.getLoc()[1]+Tree.slotY/2-Tree.btn/2);
		}else {
			top.setLoc(bTop.getLoc()[0]+mother.getLoc()[0]+Tree.slotX+5, bTop.getLoc()[1]+mother.getLoc()[1]+Tree.slotY/2-Tree.btn/2);
			top.setColor(bTop.getColor());
		}
		if(sBot!=null) {
			bot.setLoc(sBot.getLoc()[0]+mother.getLoc()[0]+Tree.slotX+5, sBot.getLoc()[1]+mother.getLoc()[1]+Tree.slotY/2-Tree.btn/2);
		}else {
			bot.setLoc(bBot.getLoc()[0]+mother.getLoc()[0]+Tree.slotX+5, bBot.getLoc()[1]+mother.getLoc()[1]+Tree.slotY/2-Tree.btn/2);
			bot.setColor(bBot.getColor());
		}
		switch(winSet) {
		case 1:
			if(sTop!=null)win=sTop;
			else if(bTop.getWin()!=null)win=bTop.getWin();
			else {
				bTop.flash();
				winSet=0;
			}
			break;
		case 2:
			if(sBot!=null)win=sBot;
			else if(bBot.getWin()!=null)win=bBot.getWin();
			else{
				bBot.flash();
				winSet=0;
			}
			break;
		default:
			win=null;
		}
		if(win==null)winSet=0;
		
		mid.setLoc(loc[0]+mother.getLoc()[0]-5-Tree.btn, loc[1]+mother.getLoc()[1]+Tree.slotY/2-Tree.btn/2);
		top.update();
		bot.update();
		mid.update();
		
		if(flash>0) {
			flash-=20;
			if(flash<0)flash=0;
		}
	}
	public void render(Graphics g,int x,int y) {
		int[] side=new int[] {0,0},
			plane=new int[] {loc[0]+Tree.slotX/2,loc[1]};
		if(sTop!=null) {
			side=new int[] {sTop.getLoc()[0]+Tree.slotX,sTop.getLoc()[1]+Tree.slotY/2};
		}else if(bTop!=null) {
			side=new int[] {bTop.getLoc()[0]+Tree.slotX,bTop.getLoc()[1]+Tree.slotY/2};
		}
		
		if(winSet==1 && getWin()!=null)g.setColor(getWin().getColor());
		else g.setColor(colorDef);
		g.drawLine(side[0]+x, side[1]+y, plane[0]+x, side[1]+y);
		g.drawLine(plane[0]+x, side[1]+y, plane[0]+x, plane[1]+y);
		
		plane[1]+=Tree.slotY;
		if(sBot!=null) {
			side=new int[] {sBot.getLoc()[0]+Tree.slotX,sBot.getLoc()[1]+Tree.slotY/2};
		}else if(bBot!=null) {
			side=new int[] {bBot.getLoc()[0]+Tree.slotX,bBot.getLoc()[1]+Tree.slotY/2};
		}
		if(winSet==2 && getWin()!=null)g.setColor(getWin().getColor());
		else g.setColor(colorDef);
		g.drawLine(side[0]+x, side[1]+y, plane[0]+x, side[1]+y);
		g.drawLine(plane[0]+x, side[1]+y, plane[0]+x, plane[1]+y);
		
		top.render(g);
		bot.render(g);
		mid.render(g);
		if(win!=null) {
			win.render(g, loc[0], loc[1],x,y);
		}else {
			g.setColor(colorDef);
			g.drawRect(loc[0]+x, loc[1]+y, Tree.slotX, Tree.slotY);
		}
		
		if(flash>0) {
			g.setColor(new Color(255,0,0,flash));
			g.fillRect(loc[0]+x-5, loc[1]+y-5, Tree.slotX+10, Tree.slotY+10);
		}
	}
	
	public Slot getSlot(boolean top) {
		if(top) {
			if(sTop!=null)return sTop;
			else {
				return bTop.getWin();
			}
		}
		else {
			if(sBot!=null)return sBot;
			else {
				return bBot.getWin();
			}
		} 
	}
	public void setSlots(Slot sTop,Slot sBot) {
		this.sTop=sTop;
		this.sBot=sBot;
	}
	public Slot getWin() {
		return win;
	}
	public void setBranches(Branch bTop,Branch bBot) {
		if(bTop!=this)this.bTop=bTop;
		else this.bTop=null;
		if(bBot!=this)this.bBot=bBot;
		else {this.bTop=null;}
	}
	public void setLoc(int x, int y) {this.loc=new int[]{x,y};}
	public int[] getLoc() {return this.loc;}
	public Color getColor() {
		if(win!=null)return win.getColor();
		return this.colorDef;
	}
	public void setColor(Color to) {this.colorDef=to;}
	public void flash() {this.flash=200;}
	public void clear() {
		this.winSet=0;
		this.win=null;
	}
}
