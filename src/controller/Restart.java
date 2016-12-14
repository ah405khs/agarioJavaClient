package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import connector.Client;
import controller.ClientSocket;

public class Restart  extends JFrame {
	
	private JPanel contentPane;
	private String id;
	
	public Restart(String id) // 생성자
	{
		this.id = id;
		init();
	}

	public void init() // 화면 구성
	{	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 200, 200);
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("@@"+id+"님에게 먹혔습니다@@");
		lblNewLabel.setBounds(0, 0, 200, 100);
		contentPane.add(lblNewLabel);
		
		JButton btnreButton = new JButton("다시하기");
		btnreButton.setBounds(0, 100, 90, 80);
		contentPane.add(btnreButton);
		

		JButton btnexitButton = new JButton("나가기");
		btnexitButton.setBounds(90, 100, 90, 80);
		contentPane.add(btnexitButton);
		
		restar restar = new restar();
		btnreButton.addActionListener(restar);
		
		exit exit1 = new exit();
		btnexitButton.addActionListener(exit1);
		
		setVisible(true);
	}
	
	class restar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {		
			Client client = new Client();
		}
	}
	
	class exit implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {		
			System.exit(1);	
		}
	}
	
}
