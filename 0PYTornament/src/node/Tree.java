package node;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import main.Action;
import main.Plan;
import ui.Button;
import ui.ButtonText;
import ui.Textbox;

public class Tree {
	
	private Plan plan;

	private ArrayList<Slot> slots;
	private ArrayList<Branch[]> branchList;
	private int[] loc=new int[] {0,0};
	public static int slotX=140,slotY=30,btn=20;
	
	private Textbox textBox;
	private Slot textSet=null;
	private Button[] btns;
	
	public Tree(Plan plan,String[] players,int lx,int ly) {
		this.plan=plan;
		this.textBox=new Textbox(plan.getKM());
		
		this.loc=new int[] {lx,ly};
		
		this.slots=new ArrayList<Slot>();	
		int y=0;
		for(String s:players) {
			this.slots.add(new Slot(s,new Color( (int)(Math.random()*136)+120,(int)(Math.random()*136)+120,(int)(Math.random()*136)+120 ),0,y));
			y+=slotY+10;
		}
		set();
		mix();
	}
	private void set() {
		this.branchList=new ArrayList<Branch[]>();	
		int x=slotX+5*3+btn*2;
		int amt=slots.size(),iter=0;
		if(amt%2==0) {//even
			
			amt=amt/2;
			branchList.add(new Branch[amt]);
			for(int a=0;a<amt;a++) {
				branchList.get(iter)[a]=new Branch(plan,this,slots.get(a*2),slots.get(a*2+1),x,  (slots.get(a*2).getLoc()[1]+slots.get(a*2+1).getLoc()[1])/2  );
			}
			
		}else {//odd
			amt=(amt-1)/2;
			branchList.add(new Branch[amt+1]);
			for(int a=0;a<amt;a++) {
				branchList.get(iter)[a]=new Branch(plan,this,slots.get(a*2),slots.get(a*2+1),x,  (slots.get(a*2).getLoc()[1]+slots.get(a*2+1).getLoc()[1])/2  );
			}
			x+=5*3+btn*2+slotX;
			branchList.get(iter)[amt]=new Branch(plan,this, branchList.get(iter)[amt-1],slots.get(slots.size()-1),x,  (branchList.get(iter)[amt-1].getLoc()[1]+slots.get(slots.size()-1).getLoc()[1])/2  );
		
			branchList.add(new Branch[amt]);
			for(int a=0;a<amt;a++) {
				if(a<amt-1) {
					branchList.get(branchList.size()-2)[a].setLoc(x, branchList.get(branchList.size()-2)[a].getLoc()[1]);
				}
				branchList.get(branchList.size()-1)[a]=branchList.get(branchList.size()-2)[a];
			}
			branchList.get(branchList.size()-1)[amt-1]=branchList.get(branchList.size()-2)[amt];
			
			branchList.set(branchList.size()-2, new Branch[] {branchList.get(branchList.size()-2)[branchList.get(branchList.size()-2).length-2]});
			iter++;
		}
		x+=5*3+btn*2+slotX;
		while(amt>1) {
			Branch[] from=branchList.get(branchList.size()-1);
			Branch[] add;
			amt=from.length;
			
			if(amt%2==0) {//even
				
				amt=amt/2;
				add=new Branch[amt];
				for(int a=0;a<amt;a++) {
					add[a]=new Branch(plan, this, from[a*2], from[a*2+1], x, (from[a*2].getLoc()[1]+from[a*2+1].getLoc()[1])/2);
				}
				branchList.add(add);
				
			}else {//odd
				
				amt=(amt-1)/2;
				add=new Branch[amt];
				for(int a=0;a<amt;a++) {
					add[a]=new Branch(plan, this, from[a*2], from[a*2+1], x, (from[a*2].getLoc()[1]+from[a*2+1].getLoc()[1])/2);
				}
				Branch[] sub=new Branch[] {add[add.length-1]};
				add[add.length-1]=new Branch(plan,this,sub[0],from[from.length-1],x,(sub[0].getLoc()[1]+from[from.length-1].getLoc()[1])/2);
				x+=5*3+btn*2+slotX;
				for(Branch b:add) {
					b.setLoc(x, b.getLoc()[1]);
				}
				
				branchList.add(sub);
				branchList.add(add);
			}
			x+=5*3+btn*2+slotX;
		}
		btns=new Button[slots.size()];
		for(int a=0;a<slots.size();a++) {
			Slot s=slots.get(a);
			btns[a]=new ButtonText(plan,"", s.getLoc()[0]+loc[0], s.getLoc()[1]+loc[1], Tree.slotX, Tree.slotY, s.getColor(), new Action() {

				@Override
				public void action() {
					openText(s);
				}
				
			});
		}
	}
	public void update() {
		for(Branch[] bs:branchList) {
			for(Branch b:bs) {
				b.update();
			}
		}
		for(Button b:btns) {
			b.update();
		}
		textBox.update();
		
		if(textBox.isOpen()) {
			if(plan.getKM().kEN) {
				textSet.setPlayer(textBox.close());
			}
		}
	}
	public void render(Graphics g) {
		for(Slot s:slots) {
			s.render(g,loc[0],loc[1]);
		}
		for(Branch[] bs:branchList) {
			for(Branch b:bs)
				b.render(g,loc[0],loc[1]);
		}
		for(Button b:btns) {
			b.render(g);
		}
		if(textSet!=null) textBox.render(g, 700-slotX/2, 840/2-slotY/2, slotX, slotY, textSet.getColor());
	}
	public int[] getLoc() {return this.loc;}
	public void clear() {
		for(Branch[] bl:branchList) {
			for(Branch b:bl) {
				b.clear();
			}
		}
	}
	public void mix() {
		clear();
		Slot[] set=new Slot[slots.size()];
		for(int a=0;a<set.length;a++) {
			int from=(int)(Math.random()*slots.size());
			set[a]=slots.get(from);
			slots.remove(from);
		}
		int y=0;
		for(Slot s:set) {
			s.setColor(new Color( (int)(Math.random()*136)+120,(int)(Math.random()*136)+120,(int)(Math.random()*136)+120 ));
			s.setLoc(s.getLoc()[0], y);
			slots.add(s);
			y+=slotY+10;
		}
		set();
	}
	private void openText(Slot s) {
		textSet=s;
		textBox.open(s.getPlayer());
	}
}
