package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by dell on 2016/1/12.
 */
public class PurchaseList extends JFrame  {

    private  ArrayList<Goods> goodsList;  //商品列表
    private  ArrayList<Goods> purchaseList;
    PurchaseList(){
        ExeSQL sql1=new ExeSQL();
        sql1.connSQL();
        goodsList = sql1.GetGoodList();
        JFrame jframe=new JFrame("超市购物管理");
        JMenuBar jmb=new JMenuBar();
        JMenu menu_shoping=new JMenu("查看购买商品");
        JMenu menu_user=new JMenu("会员管理");
        JMenu menu_store=new JMenu("仓库管理");
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
        contentPane.setLayout(new FlowLayout());
        JComboBox jcb1=new JComboBox<String>();
        jcb1.addItem("请选择");
        for(int i = 0; i < goodsList.size(); i++)
        {
            Goods emp = goodsList.get(i);
            jcb1.addItem(emp.getName());
        }
        JButton search=new JButton("结算");
        JButton add=new JButton("添加到购物车");
        contentPane.add(jcb1);
        contentPane.add(add);
        contentPane.add(search);

        jmb.add(menu_current_user);
        jmb.add(menu_shoping);
        jmb.add(menu_user);
        jmb.add(menu_store);
        jmb.add(menu_exit);
        jframe.setJMenuBar(jmb);
        jframe.add(pane,BorderLayout.NORTH);
        jframe.setSize(600, 500);
        jframe.setLocation(300, 200);

        jframe.setVisible(true);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                purchaseList =new ArrayList<Goods>();
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
               OrderList orderList = new OrderList();
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
