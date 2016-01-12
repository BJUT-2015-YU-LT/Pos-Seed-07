package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dell on 2016/1/12.
 */
public class OrderList extends JFrame {
    OrderList(){
        JFrame orderList= new JFrame("购物清单列表");
        orderList.setSize(600, 500);
        orderList.setLocation(300, 200);
        orderList.setVisible(true);
        Container contentPane=orderList.getContentPane();
        contentPane.setLayout(new BorderLayout());
        JPanel J1= new JPanel();
        JPanel J2= new JPanel();
        JTextArea JT1= new JTextArea();
        J1.setSize(300,100);
        JT1.setSize(600,400);
        JScrollPane pane=new JScrollPane(JT1);
        JButton Commit=new JButton("确认");
        JButton Cancel=new JButton("取消");
        J1.add(Commit);
        J1.add(Cancel);
        contentPane.add(J1,BorderLayout.SOUTH);
        contentPane.add(pane,BorderLayout.CENTER);
        JT1.setEnabled(false);

    }
}
