package controller;

import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import model.Blob;
import model.Dot;

public class Controller extends JFrame {

	private ArrayList<Blob> blobs = new ArrayList<Blob>();
	private ArrayList<Dot> dots = new ArrayList<Dot>();
	private Blob blob;
	private MyFrame mf;
	private int mouseX = 0;
	private int mouseY = 0;
	private int xDis = 0;
	private int yDis = 0;
	private int speed = 40;
	private String id;

	public Controller(String id, int x, int y){
		this.id = id;
		blob = new Blob(id,x,y);
		mf = new MyFrame("Agario", blob, blobs, dots);
		startGame();
	}
	public void startGame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);


		mf.addMouseMotionListener(new MyMouseMoveListener());
		Refresh rf = new Refresh(dots, blob, speed, mf, blobs);
		Thread mThread = new Thread(rf);
		mThread.start();
		Thread mThread2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					try {

						Thread.sleep(rf.getSpeed());

						double easingAmount = 20;
						int x, y;
					

						x = (int) (blob.getX() + xDis / easingAmount);
						y = (int) (blob.getY() + yDis / easingAmount);
						blob.setX(x);
						blob.setY(y);
						

						

					} catch (Exception e) {

					}
				}
			}
		});
		mThread2.start();
	}

	public ArrayList<Dot> getDots() {
		return dots;
	}

	public void setDots(ArrayList<Dot> dots) {
		this.dots = dots;
		for(Dot d:dots){
			mf.add(d);
		}
	}
	
	public void addDot(Dot d){
		dots.add(d);
		mf.add(d);
	}

	public Blob getBlob() {
		return blob;
	}

	public void setBlob(Blob blob) {
		this.blob = blob;
	}


	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public MyFrame getMf(){
		return mf;
	}
	
	public void addBlob(Blob b){
		blobs.add(b);
	}
	
	public ArrayList<Blob> getBlobs(){
		return blobs;
	}

	public String getID(){
		return id;
	}

	class MyMouseMoveListener extends MouseMotionAdapter {
		public void mouseMoved(MouseEvent m) {
			mouseX = m.getX();
			mouseY = m.getY();
			xDis = mouseX - blob.getX();
			yDis = mouseY - blob.getY();
		}
	}
}