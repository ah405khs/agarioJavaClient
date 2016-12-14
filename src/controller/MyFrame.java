package controller;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;

import model.Blob;
import model.Dot;

public class MyFrame extends Frame {
	private Blob blob;
	private ArrayList<Blob> blobs;
	private ArrayList<Dot> dots;

	public MyFrame(String s, Blob blob, ArrayList<Blob> blobs, ArrayList<Dot> dots) {
		super(s);
		this.blob = blob;
		this.blobs = blobs;
		this.dots = dots;

		setBounds(0, 0, 600, 600);
		add(blob);
		blobs.add(blob);
		setVisible(true);

		this.addWindowListener(new WindowAdapter() {// frame종료
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	

	public void paint(Graphics g) {
		//blob.paint(g);
		for (Blob b : blobs) {//Blob이 다른 클라이언트에서 들어온 놈들 모두다 paint()
			b.paint(g);
		}
//		synchronized (dots) {
//			Iterator i = ((ArrayList)dots.clone()).iterator();
//			while (i.hasNext()) {
//				Dot d = (Dot) i.next();
//				d.paint(g);
//			}
//		}	
		synchronized (dots) {
			Iterator i = ((ArrayList<Dot>)dots.clone()).iterator();
			while (i.hasNext()) {
				Dot d = (Dot) i.next();
				d.paint(g);
			}
		}

	}

	public void update(Graphics g) {// 더블 버퍼 사용(update 에서 더
		// 더블 버퍼 쓸수 있게 재정의 repaint() 호출시.-> update -> paint()
		Image buffer1 = createImage(getWidth(), getHeight());
		g = buffer1.getGraphics();
		paint(g);
		//getGraphics().drawImage(buffer1, blob.getX()/2, blob.getY()/2, this);
		
		getGraphics().drawImage(buffer1, 0, 0, this);
		//getGraphics().drawImage(buffer2, 0, 0, this);

	}

}
