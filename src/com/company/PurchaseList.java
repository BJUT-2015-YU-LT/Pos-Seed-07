package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by dell on 2016/1/12.
 */
public class PurchaseList extends JFrame  {

    private  ArrayList<Goods> goodsList;  //商品列表
    private  ArrayList<Goods> purchaseList;
    private  JFrame jframe;
    private  OrderList orderList;
    PurchaseList(){
        ExeSQL sql1=new ExeSQL();
        sql1.connSQL();
        goodsList = sql1.GetGoodList();
        purchaseList =new ArrayList<Goods>();
        jframe=new JFrame("模拟POS机");
        JMenuBar jmb=new JMenuBar();
        JMenu menu_shoping=new JMenu("查看购买商品");
        JMenu menu_exit=new JMenu("退出");
        JMenu menu_current_user=new JMenu("欢迎您,王维");
        JTable table=new JTable();
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.addColumn("商品条形码");
        tableModel.addColumn("商品名称");
        tableModel.addColumn("单位");
        tableModel.addColumn("商品单价");
        tableModel.addColumn("折扣");

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane pane=new JScrollPane(table);
        Container contentPane=jframe.getContentPane();
        contentPane.setLayout(new BorderLayout());
        JComboBox jcb1=new JComboBox<String>();
        jcb1.addItem("请选择");
        for(int i = 0; i < goodsList.size(); i++)
        {
            Goods emp = goodsList.get(i);
            jcb1.addItem(emp.getName());
        }
        JButton search=new JButton("结算");
        JButton add=new JButton("添加到购物车");
        JPanel J1 =new JPanel();
        J1.add(jcb1);
        J1.add(add);
        J1.add(search);

        jmb.add(menu_current_user);
        jmb.add(menu_shoping);
        jmb.add(menu_exit);
        jframe.setJMenuBar(jmb);
        jframe.add(J1,BorderLayout.NORTH);
        jframe.add(pane,BorderLayout.CENTER);
        jframe.setSize(600, 500);
        jframe.setLocation(300, 200);

        jframe.setVisible(true);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(jcb1.getSelectedItem().toString()!="请选择") {
                    Goods newgood2= GetGoodByName(jcb1.getSelectedItem().toString(),goodsList);
                    Goods newgood = sql1.GetGoodByBarCode(newgood2.getBarcode());
                    String[] rowValues = {
                            newgood.getBarcode(),newgood.getName(),newgood.getUnit(),newgood.getPrice().toString(),
                            newgood.getDiscount().toString()
                    };
                    tableModel.addRow(rowValues);
                    purchaseList.add(newgood);
                }
            }
        });

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                orderList = new OrderList();
                orderList.PrintOrderList(purchaseList);
                orderList.Commit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        tableModel.setRowCount(0);
                        purchaseList.clear();
                    }
                });
            }
        });

    }

    public static Goods GetGoodByName(String Name,ArrayList<Goods> list) {
        for (Goods exp:list){
            if (exp.getName().equals(Name)){
                return exp;
            }
        }
        return null;
    }




}
