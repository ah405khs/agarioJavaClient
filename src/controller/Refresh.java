package controller;

import java.awt.Frame;
import java.awt.Label;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import model.Blob;
import model.Dot;

public class Refresh extends Thread {
	private final int INTERVAL = 2; // speed 증가
	private final int SPEEDLIMIT = 210; // 제한.
	private ArrayList<Dot> reDots;
	private Blob reBlob;
	private int reSpeed;
	private Frame reMf;
	private ArrayList<Blob> blobs;
	private int zero=0;

	public Refresh(ArrayList<Dot> dots, Blob bolb, int speed, Frame frame, ArrayList<Blob> blobs) {
		this.reDots = dots;
		this.reBlob = bolb;
		this.reSpeed = speed;
		this.reMf = frame;
		this.blobs = blobs;
	}

	public void run() {
		while (true) {

			try {
				Thread.sleep(20);
			} catch (Exception e) {
				System.out.println("error");
			}
			Rectangle myRect = new Rectangle(reBlob.getX(), reBlob.getY(), reBlob.getOvalSize(), reBlob.getOvalSize());
			synchronized (blobs) {
				Iterator itr = blobs.iterator();
				while (itr.hasNext()) {
					Blob b = (Blob) itr.next();
					Rectangle r = new Rectangle(b.getX(), b.getY(), b.getOvalSize(), b.getOvalSize());
					synchronized (blobs) {
						if (b.getID().equals(reBlob.getID())) {

						} else {
							if (b.getOvalSize() < reBlob.getOvalSize()) {
								if (r.intersects(myRect)) {
									reBlob.setOvalSize(reBlob.getOvalSize() + b.getOvalSize());
									itr.remove();
									b.repaint();
								}

							}
							if (b.getOvalSize() > reBlob.getOvalSize()) {
								if (myRect.intersects(r)) {
										try {
										
										ClientSocket.os.close();
										ClientSocket.is.close();
										ClientSocket.dos.close();
										ClientSocket.dis.close();
										ClientSocket.socket.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									Restart res = new Restart(reBlob.getID());
									reMf.setVisible(false);
									zero++;
									break;
								}
							}
						}
					}
					synchronized (reDots) {
						Iterator i = reDots.iterator();
						while (i.hasNext()) {
							Dot d = (Dot) i.next();
							Rectangle r1 = new Rectangle(d.getX(), d.getY(), d.getOvalSize(), d.getOvalSize());
							if (r1.intersects(r)) {
								i.remove();
								b.setOvalSize(b.getOvalSize() + 1);

								reSpeed += INTERVAL;
								if (reSpeed >= 200) {// 점수증가시
																					// speed
																					// 증가하여(sleep
																					// 증가)
									reSpeed = SPEEDLIMIT;
								}
								d.repaint();
							}
						}
					}
					reMf.repaint();
				}

			}
			if(zero!=0)
				break;
		}
	}

	public int getSpeed() {
		return reSpeed;
	}

}
