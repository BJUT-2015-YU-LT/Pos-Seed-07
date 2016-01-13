package com.company;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class Vip extends JFrame {
	private JPanel contentPane;
	private JTextField textField;
	private final JButton btnNewButton;
	private final JLabel lblNewLabel;
	private ArrayList<Goods> GoodList;

	public Vip( ArrayList<Goods> GoodList2) {
		GoodList=GoodList2;
		ExeSQL sql1=new ExeSQL();
		sql1.connSQL();
		setTitle("VIP支付\n");
		setBounds(450, 250, 490, 330);
		this.setResizable(false);
		contentPane = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(new ImageIcon(
						"./4fe16e8bdaadc5732eebb853a5b16d45.jpg").getImage(), 0,
						0, getWidth(), getHeight(), null);
			}
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel zh = new JLabel("会员账号");
		zh.setFont(new Font("黑体",0,13));
		zh.setForeground(Color.WHITE);
		zh.setSize(60, 60);
		zh.setLocation(110, 100);
		this.add(zh);

		textField = new JTextField();
		textField.setBounds(170, 120, 150, 25);

		contentPane.add(textField);
		textField.setColumns(10);

		btnNewButton = new JButton("确认结账");
		btnNewButton.setBounds(246, 227, 100, 25);
		getRootPane().setDefaultButton(btnNewButton);
		contentPane.add(btnNewButton);

		 lblNewLabel = new JLabel();
		lblNewLabel.setBounds(60, 220, 160, 21);
		lblNewLabel.setForeground(Color.red);
		getContentPane().add(lblNewLabel);
		lblNewLabel.setText("输入会员号享受尊贵特权");
		this.setVisible(true);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
			Users users = sql1.GetUserById(textField.getText());
				if(users.getVipId()==null){
				lblNewLabel.setText("对不起无此会员");
				}
				else{
					lblNewLabel.setText("欢迎您,"+users.getVipName());
					OrderList orderList2=new OrderList(users.getVipName());
					orderList2.PrintVipOrderList(GoodList);
				}
			}
		});


	}

	public ArrayList<Goods> getGoodList() {
		return GoodList;
	}

	public void setGoodList(ArrayList<Goods> goodList) {
		GoodList = goodList;
	}

}
