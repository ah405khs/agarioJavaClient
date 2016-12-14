package controller;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import model.Blob;
import model.Dot;

public class ClientSocket {
	static Socket socket;
	static InputStream is;
	static DataInputStream dis;
	static OutputStream os;
	static DataOutputStream dos;
	private String id;
	private String ip;
	private int port;
	int vSize;
	ArrayList<String> userID = new ArrayList<String>();

	public ClientSocket(String id, String ip, int port) {
		this.id = id;
		this.ip = ip;
		this.port = port;
		// blobs = new ArrayList<Blob>();
		network();
	}

	public void network() {
		try {
			socket = new Socket(ip, port);
			// socket = new Socket("127.0.0.1", 30000);
			if (socket != null) {
				Connection();
			}
		} catch (UnknownHostException e) {
			System.out.println("unknown error");
			// TODO: handle exception
		} catch (IOException e) {
			System.out.println("���� ���� ����");
		}
	}

	public void Connection() {
		Blob blob;
		MyFrame mf;
		try {
			is = socket.getInputStream();
			dis = new DataInputStream(is);
			os = socket.getOutputStream();
			dos = new DataOutputStream(os);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("�ý��� ���� ����");
		}

		System.out.println(id);
		Random random = new Random();
		int rX = random.nextInt(400);
		int rY = random.nextInt(400);
		Controller user = new Controller(id, rX, rY); // Ŭ���̾�Ʈ�� �������� ���ο� ��ǥ��
		blob = user.getBlob();
		mf = user.getMf();
		String colorS = Integer.toString(blob.getColor().getRGB());
		send("(" + id + "){" + rX + "," + rY + ":40}<" + colorS + ">");// �޴´�.
																		// //
																		// �޴´�.

		ArrayList<Dot> dots = new ArrayList<Dot>();
		// blobs.add(blob);
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					try {

						byte[] recv = new byte[128];
						dis.read(recv);
						String recvMsg = new String(recv);
						// recvMsg.trim();
						// String recvMsg = dis.readUTF();

						if (recvMsg.startsWith("[VECTOR]")) {
							int start;
							int end;
							// int vSize;
							String tmp;
							start = recvMsg.indexOf("(");
							end = recvMsg.indexOf(")");
							tmp = recvMsg.substring(start + 1, end);
							vSize = Integer.parseInt(tmp);
							System.out.println("vv:" + vSize);

						}
						if (recvMsg.startsWith("[DOTS]")) {
							int start;
							int end;
							int dX;
							int dY;
							Dot d;
							String tmp;
							// dot x ��ǥ//
							start = recvMsg.indexOf("(");
							end = recvMsg.indexOf(",");
							tmp = recvMsg.substring(start + 1, end);
							dX = Integer.parseInt(tmp);
							// dot Y//
							start = recvMsg.indexOf(",");
							end = recvMsg.indexOf(")");
							tmp = recvMsg.substring(start + 1, end);
							dY = Integer.parseInt(tmp);
							// System.out.println(dX+","+dY);
							d = new Dot(dX, dY);
							dots.add(d);
							user.addDot(d);
							//
							// String str = "[Dots CONNETED] ";
							// Iterator itr = dots.iterator();
							// while (itr.hasNext()) {
							// Dot d2 = (Dot) itr.next();
							// str += d2.toString() + " ";
							// }
							// // send(str);
							// System.out.println(str);

						}
						if (recvMsg.startsWith("[CONNECTED]")) {
							System.out.println(recvMsg);
							int start;
							int end;
							int sX;
							int sY = 0;
							String tmp;
							String sID;
							int size;
							int rgb;
							/// id���////
							start = recvMsg.indexOf('(');
							end = recvMsg.indexOf(')');
							sID = recvMsg.substring(start + 1, end);
							// x��ǥ ���///
							start = recvMsg.indexOf('{');
							end = recvMsg.indexOf(',');
							tmp = recvMsg.substring(start + 1, end);
							sX = Integer.parseInt(tmp);
							// y��ǥ ��� ///
							start = recvMsg.indexOf(',');
							end = recvMsg.indexOf(':');
							tmp = recvMsg.substring(start + 1, end);
							sY = Integer.parseInt(tmp);
							// size//
							start = recvMsg.indexOf(':');
							end = recvMsg.indexOf('}');
							tmp = recvMsg.substring(start + 1, end);
							size = Integer.parseInt(tmp);
							// color
							start = recvMsg.indexOf('<');
							end = recvMsg.indexOf('>');
							tmp = recvMsg.substring(start + 1, end);
							rgb = Integer.parseInt(tmp);

							if (sID.equals(id)) {
								System.out.println("���� id");
							} else {

								user.addBlob(new Blob(sID, sX, sY, size, new Color(rgb)));
							}

						}
						if (recvMsg.startsWith("[CONNECT]")) {
							int start;
							int end;
							int sX;
							int sY = 0;
							String tmp;
							String sID;
							int size;
							int rgb;
							/// id���////
							start = recvMsg.indexOf('(');
							end = recvMsg.indexOf(')');
							sID = recvMsg.substring(start + 1, end);
							// x��ǥ ���///
							start = recvMsg.indexOf('{');
							end = recvMsg.indexOf(',');
							tmp = recvMsg.substring(start + 1, end);
							sX = Integer.parseInt(tmp);
							// y��ǥ ��� ///
							start = recvMsg.indexOf(',');
							end = recvMsg.indexOf(':');
							tmp = recvMsg.substring(start + 1, end);
							sY = Integer.parseInt(tmp);
							// size//
							start = recvMsg.indexOf(':');
							end = recvMsg.indexOf('}');
							tmp = recvMsg.substring(start + 1, end);
							size = Integer.parseInt(tmp);
							// color
							start = recvMsg.indexOf('<');
							end = recvMsg.indexOf('>');
							tmp = recvMsg.substring(start + 1, end);
							rgb = Integer.parseInt(tmp);

							if (id.equals(sID)) {

							} else {
								userID.add(sID);
								Thread.sleep(210);
								send("[CONNECTED](" + id + "){" + blob.getX() + "," + blob.getY() + ":"
										+ blob.getOvalSize() + "}<" + colorS + ">");
								System.out.println(recvMsg);
								user.addBlob(new Blob(sID, sX, sY, size, new Color(rgb)));

							}

							System.out.println(recvMsg);
						} else if (recvMsg.startsWith("[MOVE]")) {
							int start;
							int end;
							int sX;
							int sY;

							String tmp;
							String sID;
							int size;
							/// id���////
							start = recvMsg.indexOf('(');
							end = recvMsg.indexOf(')');
							sID = recvMsg.substring(start + 1, end);
							// x��ǥ ���///
							start = recvMsg.indexOf('{');
							end = recvMsg.indexOf(',');
							tmp = recvMsg.substring(start + 1, end);
							sX = Integer.parseInt(tmp);
							// y��ǥ ��� ///
							start = recvMsg.indexOf(',');
							end = recvMsg.indexOf(':');
							tmp = recvMsg.substring(start + 1, end);
							sY = Integer.parseInt(tmp);
							// size
							start = recvMsg.indexOf(':');
							end = recvMsg.indexOf('}');
							tmp = recvMsg.substring(start + 1, end);
							size = Integer.parseInt(tmp);
							// System.out.println("vector:" + vSize);

							if (vSize < user.getBlobs().size()) {
								Iterator itr = user.getBlobs().iterator();
								while (itr.hasNext()) {
									Blob b = (Blob) itr.next();
									if (sID.equals(b.getID())) {

									} else {
										if (b.getID().equals(id)) {

										} else {

											itr.remove();

										}
									}
								}
							}
							if (id.equals(sID)) {
								// send�� ����ؼ� frame�� ����... frame�� ���簡 ����
							} else {

								try {
									for (Blob b : user.getBlobs()) {
										if (id.equals(b.getID())) {

										} else {
											if (b.getID().equals(sID)) {
												b.setX(sX);
												b.setY(sY);
												b.setOvalSize(size);
											}
										}

									}
								} catch (Exception e) {
									// TODO: handle exception
								}
								// send("[CONNECT](" + id + "){" + sX + "," + sY
								// + "}");
							}
						}

						// System.out.println(recvMsg);
						send("[MOVE]" + "(" + id + ")" + "{" + blob.getX() + "," + blob.getY() + ":"
								+ blob.getOvalSize() + "}");

					} catch (Exception e) {
						System.out.println("recv error");

						try {
							os.close();
							is.close();
							dos.close();
							dis.close();
							socket.close();
							break;
						} catch (Exception e2) {
							// TODO: handle exception
						}
						// TODO: handle exception
					}
				}
			}
		});
		th.start();// �����κ��� ��ǥ�� �޾Ƽ� setting

	}

	public void send(String str) {

		byte[] send;
		send = str.getBytes();
		try {
			dos.write(send, 0, str.length());
			// dos.writeUTF(str);
			// dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("send error");
		}
	}
}
