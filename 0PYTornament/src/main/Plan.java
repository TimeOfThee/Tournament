package main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import node.Tree;
import ui.Button;
import ui.ButtonText;
import ui.Textbox;

public class Plan {
	Font font = new Font("Tw Cen MT",Font.BOLD,20);
	private Plan plan=this;
	
	private KeyManager kM;
	private MouseManager mM;
	Textbox textbox;
	
	//randomize,reset,amountOplayers
	Tree tree;
	
	boolean option=false,tog=true;
	int slide=1400,amount=2,view=0;
	ArrayList<String> players=new ArrayList<String>();
	
	ButtonText open=new ButtonText(this, "+", slide-50, 840-50, 50, 50, new Color(200,200,200),new Action(){
		@Override
		public void action() {
			if(tog) {
				option=!option;
				if(option) {
					for(Button b:options) {
						b.setVisible(true);
					}
				}else {
					for(Button b:options) {
						b.setVisible(false);
					}
				}
				tog=false;
			}
		}
	});
	
	Button[] options=new Button[5];

	int plU=0,plD=1,start=2,mix=3,clear=4;
	//Tree test;
	
	public Plan(KeyManager km,MouseManager mm) {
		this.kM=km;
		this.mM=mm;
		
		textbox=new Textbox(kM);
		
		options[plU]=new ButtonText(this, "+", 1400-120, 20, 50, 50, new Color(200,200,200),new Action(){
			@Override
			public void action() {
				if(tog) {
					if(amount<20)amount++;
					tog=false;
				}
			}
		});
		options[plD]=new ButtonText(this, "-", 1400-120, 70, 50, 50, new Color(200,200,200),new Action(){
			@Override
			public void action() {
				if(tog) {
					if(amount>2)amount--;
					tog=false;
				}
			}
		});
		options[start]=new ButtonText(this, "Start", 1400-120, 840-120, 100, 100, new Color(200,200,200),new Action(){
			@Override
			public void action() {
				if(tog) {
					option=false;
					for(Button b:options) {
						b.setVisible(false);
					}
					String[] add=new String[players.size()];
					for(int a=0;a<add.length;a++) {
						add[a]=players.get(a);
					}
					tree=new Tree(plan,add,10,20);
					tog=false;
				}
			}
		});
		options[mix]=new ButtonText(this, "Mix", 1400-120, 580, 100, 60, new Color(200,200,200),new Action(){
			@Override
			public void action() {
				if(tog) {
					tree.mix();
					tree.update();
					tog=false;
				}
			}
		});
		options[clear]=new ButtonText(this, "Clear", 1400-120, 640, 100, 60, new Color(200,200,200),new Action(){
			@Override
			public void action() {
				if(tog) {
					tree.clear();
					tog=false;
				}
			}
		});
		for(Button b:options) {
			b.setVisible(false);
		}
		
		players.add("PLAYER 1");
		players.add("PLAYER 2");
		String[] start=new String[players.size()];
		for(int a=0;a<players.size();a++) {
			start[a]=players.get(a);
		}
		
		this.tree=new Tree(this,start,10,20);
		//this.test=new Tree(this, new String[] {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"},10,20);
	}
	
	public void update() {
		while(players.size()>amount) {
			players.remove(players.size()-1);
		}while(players.size()<amount) {
			players.add("PLAYER "+(players.size()+1));
		}
		textbox.update();
		
		open.setLoc(slide-50, 840-50);
		if(!textbox.isOpen()) {
			open.update();
		}else {
			if(kM.kEN) {
				players.set(view, textbox.close());
			}
		}
		for(Button b:options) {
			b.setLoc(slide+20, b.getBounds().y);
			if(!textbox.isOpen())b.update();
		}
		
		if(!mM.isM1())tog=true;
		
		int rate=20;
		if(option) {
			if(slide>1400-140) {
				slide-=rate;
				if(slide<1400-140)slide=1400-140;
			}
		}else {
			if(slide<1400) {
				slide+=rate;
				if(slide>1400)slide=1400;
			}
			tree.update();
		}
	}
	public void render(Graphics g) {
		tree.render(g);
		g.setColor(new Color(200,200,200,160));
		g.fillRect(slide, 0, 1400-slide, 840);
		open.render(g);
		for(Button b:options) {
			b.render(g);
		}
		if(option) {
			g.setColor(new Color(200,200,200));
			g.drawRect(slide+70, 20, 50, 100);
			g.setFont(font);
			g.drawString(Integer.toString(players.size()), slide+70+15, 70+10);
			for(int a=0;a<players.size();a++) {
				
				if(textbox.isOpen()) {
					g.setColor(new Color(200,200,200));
					g.fillRect(slide+20, 140+(view*20), 100, 20);
					textbox.render(g, slide+20, 140+(view*20), 100, 20, new Color(160,160,160),font);
				}
				if(mM.getMX()>slide+20 && mM.getMX()<slide+20+100 && mM.getMY()>140+(a*20) && mM.getMY()<140+(a*20)+20) {
					g.setColor(new Color(200,200,200,100));
					g.fillRect(slide+20, 140+(a*20), 100, 20);
					if(mM.isM1() && tog) {
						view=a;
						textbox.open(players.get(view));
						tog=false;
					}
				}
				
				g.setColor(new Color(200,200,200));
				g.drawRect(slide+20, 140+(a*20), 100, 20);
				if(textbox.isOpen() && a==view)continue;
				g.drawString(players.get(a), slide+20+5, 140+(a*20)+18);
			}
		}
	}
	
	public KeyManager getKM() {return this.kM;}
	public MouseManager getMM() {return this.mM;}
}
