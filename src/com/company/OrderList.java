package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dell on 2016/1/12.
 */
public class OrderList extends JFrame {
    private JPanel J1;
    private JPanel J2;
    private JTextArea JT1;
    private JScrollPane pane;
    public static JButton Commit;
    private JButton Cancel;
    private Container contentPane;
    private JFrame orderList;
    OrderList(){
        orderList= new JFrame("购物清单列表");
        orderList.setSize(600, 500);
        orderList.setLocation(300, 200);
        orderList.setVisible(true);
        contentPane=orderList.getContentPane();
        contentPane.setLayout(new BorderLayout());
        J1= new JPanel();
        J2= new JPanel();
        JT1= new JTextArea();
        J1.setSize(300,100);
        JT1.setSize(600,400);
        pane=new JScrollPane(JT1);
        Commit=new JButton("非会员入口");
        Cancel=new JButton("取消");
        J1.add(Commit);
        J1.add(Cancel);
        contentPane.add(J1,BorderLayout.SOUTH);
        contentPane.add(pane,BorderLayout.CENTER);
        JT1.setEnabled(false);

        Commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                orderList.dispose();
            }
        });
        Cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                orderList.dispose();
            }
        });

    }

    //打印归类后的OrderList并返回折扣价
    public void PrintOrderList(ArrayList<Goods> GoodList) {
        Double DiscountPrice=0.0;  //存打折价
        Double Price=0.0;          //存总价
        Double GivePrice=0.0;      //存赠送商品的总价
        int giveNum=0;
        Map<String, Double> map; //此时Map中只有5种不重复的Good
        map = sum(GoodList);
        ArrayList<Goods> GiveList=new ArrayList<Goods>();
        Set<String> set = map.keySet();
        JT1.append("***商店购物清单***");
        JT1.append("\n");
        for (Iterator<String> iter = set.iterator(); iter.hasNext();)
        {
            giveNum=0;
            Goods good=new Goods();
            String key = (String)iter.next();
            good=GetGoodByBarCode(key,GoodList);
            Double value = (Double)map.get(key);
            if(good.getIsPromotion()==1){
                giveNum = (int)(value/good.getPrice())/2;
                for(int i=0;i<giveNum;i++){
                    GiveList.add(good);
                }
            }
            JT1.append("商品BarCode:   "+key+"   "+
                    "名称:   "+good.getName()+"   "+
                    "数量:   "+(int)(value/good.getPrice()+giveNum)+"   "+
                    "单价:   "+good.getPrice()+"   "+
                    "小计:   " + value*good.getDiscount());
            JT1.append("\n");
            DiscountPrice+=value*good.getDiscount();
            Price+=value;
        }

        map = sum(GiveList);
        set = map.keySet();
        JT1.append("\n\n");
        JT1.append("***挥泪赠送商品***\n");
        for (Iterator<String> iter = set.iterator(); iter.hasNext();){
            Goods good=new Goods();
            String key = (String)iter.next();
            good=GetGoodByBarCode(key,GiveList);
            Double value = (Double)map.get(key);
            JT1.append("商品BarCode:   "+key+"   "+
                    "名称:   "+good.getName()+"   "+
                    "数量:   "+(int)(value/good.getPrice())+"   "+
                    "单价:   "+good.getPrice()+"   "+
                    "小计:   " + value);
            GivePrice+=value;
            JT1.append("\n");
        }
        JT1.append("\n\n");
        JT1.append("总计:"+DiscountPrice+"(元)\n");
        JT1.append("节省了"+ToBigDecimal(Price-DiscountPrice+GivePrice)+"(元)\n");
    }

    //Double 返回2位
    public Double ToBigDecimal(Double f) {
        BigDecimal b=new BigDecimal(f);
        double f1=b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    //用Map 归类同名商品为一类
    public Map<String, Double> sum(ArrayList<Goods> list) {
        Map<String, Double> map = new LinkedHashMap<String, Double>();
        for (Goods exp:list)
        {
            if(map.containsKey(exp.getBarcode())){
                double price=map.get(exp.getBarcode());
                map.put(exp.getBarcode(), exp.getPrice()+price);
            }else{
                map.put(exp.getBarcode(), exp.getPrice());
            }
        }
        return map;
    }
    //通过BarCode返回Good
    public Goods GetGoodByBarCode(String BarCode,ArrayList<Goods> list) {
        for (Goods exp:list){
            if (exp.getBarcode().equals(BarCode)){
                return exp;
            }
        }
        return null;
    }

}
