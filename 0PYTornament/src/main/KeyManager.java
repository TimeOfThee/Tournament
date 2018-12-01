package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{

	private boolean[] keys,pressed,nopress;
	public boolean kA,kB,kC,kD,
					kE,kF,kG,kH,
					kI,kJ,kK,kL,
					kM,kN,kO,kP,
					kQ,kR,kS,kT,
					kU,kV,kW,kX,
					kY,kZ,
		kUP,kDO,kLE,kRI,kSP,kEN,kBA;
	public KeyManager(){
		keys=new boolean[256];
		pressed=new boolean[keys.length];
		nopress=new boolean[keys.length];
	}
	public void update() {
		for(int i=0;i<keys.length;i++) {
			if(nopress[i] && !keys[i])
				nopress[i]=false;
			else if(pressed[i]) {
				nopress[i]=true;
				pressed[i]=false;
			}
			if(!nopress[i] && keys[i]) {
				pressed[i]=true;
				System.out.println(i);
			}
		}
		
		kA=keys[KeyEvent.VK_A];
		kB=keys[KeyEvent.VK_B];
		kC=keys[KeyEvent.VK_C];
		kD=keys[KeyEvent.VK_D];
		kE=keys[KeyEvent.VK_E];
		kF=keys[KeyEvent.VK_F];
		kG=keys[KeyEvent.VK_G];
		kH=keys[KeyEvent.VK_H];
		kI=keys[KeyEvent.VK_I];
		kJ=keys[KeyEvent.VK_J];
		kK=keys[KeyEvent.VK_K];
		kL=keys[KeyEvent.VK_L];
		kM=keys[KeyEvent.VK_M];
		kN=keys[KeyEvent.VK_N];
		kO=keys[KeyEvent.VK_O];
		kP=keys[KeyEvent.VK_P];
		kQ=keys[KeyEvent.VK_Q];
		kR=keys[KeyEvent.VK_R];
		kS=keys[KeyEvent.VK_S];
		kT=keys[KeyEvent.VK_T];
		kU=keys[KeyEvent.VK_U];
		kV=keys[KeyEvent.VK_V];
		kW=keys[KeyEvent.VK_W];
		kX=keys[KeyEvent.VK_X];
		kY=keys[KeyEvent.VK_Y];
		kZ=keys[KeyEvent.VK_Z];
		kUP=keys[KeyEvent.VK_UP];
		kDO=keys[KeyEvent.VK_DOWN];
		kLE=keys[KeyEvent.VK_LEFT];
		kRI=keys[KeyEvent.VK_RIGHT];
		kSP=keys[KeyEvent.VK_SPACE];
		kEN=keys[KeyEvent.VK_ENTER];
		kBA=keys[KeyEvent.VK_BACK_SPACE];
	}
	public boolean justPressed(int keyCode){
		if(keyCode < 0 || keyCode >= keys.length)
			return false;
		return pressed[keyCode];
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode()<0||arg0.getKeyCode()>=keys.length)
			return;
		keys[arg0.getKeyCode()]=true;
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		if(arg0.getKeyCode()<0||arg0.getKeyCode()>=keys.length)
			return;
		keys[arg0.getKeyCode()]=false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

}
