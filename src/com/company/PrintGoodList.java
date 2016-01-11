package com.company;

/**
 * Created by dell on 2016/1/4.
 */
import java.math.BigDecimal;
import java.util.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PrintGoodList {
    //需求二
    public void PrintList() {
// TODO Auto-generated method stub
        Double TotalPrice=0.0;//总价
        Double DiscountPrice=0.0;//打折后总价
        ArrayList<Goods> GoodList = new ArrayList<Goods>();

        System.out.println("\n将Json数据转换为Goods对象：");
        GoodList = TransJsontoEntity("D:\\test\\1.json");   //将Json中的数据转换为Goods对象

        System.out.println("\n统计打印购物车中的信息：");
        TotalPrice=CalculatePrice(GoodList);//计算总价打印全部信息

        DiscountPrice=PrintOrderList(GoodList);//计算折扣价打印Goodslist

        System.out.println("总计:"+DiscountPrice+"(元)");

        Double f1=ToBigDecimal(TotalPrice-DiscountPrice);
        System.out.println("节省:"+f1+"(元)");
        System.out.println("**********************");
    }

    //用Map 归类同名商品为一类
    public Map<String, Double>sum(ArrayList<Goods> list) {
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

    //将Json转为对象数组
    public  ArrayList<Goods> TransJsontoEntity(String Path) {
        String JsonContext = new ReadJson().ReadFile(Path);
        JSONArray jsonArray = JSONArray.fromObject(JsonContext);
        int size = jsonArray.size();
        ArrayList<Goods> GoodList = new ArrayList<Goods>();
        for(int  i = 0; i < size; i++) {
            Goods goods = new Goods();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println("[" + i + "]BarCode=" + jsonObject.get("barcode"));
            goods.setBarcode(jsonObject.getString("barcode"));
            System.out.println("[" + i + "]Name=" + jsonObject.get("name"));
            goods.setName(jsonObject.getString("name"));
            System.out.println("[" + i + "]Unit=" + jsonObject.get("unit"));
            goods.setUnit(jsonObject.getString("unit"));
            System.out.println("[" + i + "]Price=" + jsonObject.get("price"));
            goods.setPrice(jsonObject.getDouble("price"));
            if (jsonObject.get("discount") != null) {
                System.out.println("[" + i + "]Discount=" + jsonObject.get("discount"));
                goods.setDiscount(jsonObject.getDouble("discount"));
            }
            GoodList.add(goods);
        }
        return GoodList;
    }

    //打印GoodList的信息并计算总价返回
    public Double CalculatePrice(ArrayList<Goods> GoodList) {
        Double TotalPrice=0.0;
        for(int i = 0; i < GoodList.size(); i++)
        {
            Goods emp = GoodList.get(i);
            System.out.println("barcode: "
                    +emp.getBarcode()+"     name: "
                    + emp.getName() + "     unit: "
                    + emp.getUnit() + "     Price: " + emp.getPrice()
                    +"      Discount: "+emp.getDiscount());
            TotalPrice+=emp.getPrice();
        }
        return TotalPrice;
    }

    //打印归类后的OrderList并返回折扣价
    public Double PrintOrderList(ArrayList<Goods> GoodList) {
        Double DiscountPrice=0.0;
        Map<String, Double> map;
        map = sum(GoodList);
        Set<String> set = map.keySet();
        System.out.println("***商店购物清单***");
        for (Iterator<String> iter = set.iterator(); iter.hasNext();)
        {
            Goods good=new Goods();
            String key = (String)iter.next();
            good=GetGoodByBarCode(key,GoodList);
            Double value = (Double)map.get(key);
            System.out.println("商品BarCode:   "+key+"   "+
                    "名称:   "+good.getName()+"   "+
                    "数量:   "+(int)(value/good.getPrice())+"   "+
                    "单价:   "+good.getPrice()+"   "+
                    "小计:   " + value*good.getDiscount());
            DiscountPrice+=value*good.getDiscount();
        }
        return DiscountPrice;
    }

    //Double 返回2位
    public Double ToBigDecimal(Double f) {
        BigDecimal b=new BigDecimal(f);
        double f1=b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    //需求三根据IndexList获得GoodList
    public ArrayList<Goods> GetGoodsList (String Path) {
        String JsonContext = new ReadJson().ReadFile(Path);
        JSONArray jsonArray = JSONArray.fromObject(JsonContext);
        ArrayList<Goods> GoodList = new ArrayList<Goods>();
        ArrayList<Goods> IndexList = GetIndexList("D:\\test\\index.json");
        for(int  i = 0; i < jsonArray.size(); i++){
            Goods goods=GetGoodByBarCode(jsonArray.getString(i),IndexList);
            GoodList.add(goods);
        }
        return GoodList;
    }

    //需求三
    public void PrintIndex() {
        Double TotalPrice=0.0;//总价
        Double DiscountPrice=0.0;//打折后总价
        ArrayList<Goods> GoodList = new ArrayList<Goods>();
        System.out.println("\n将Json数据转换为Goods对象：");
        GoodList = GetGoodsList("D:\\test\\item.json");   //将Json中的数据转换为Goods对象
        System.out.println("\n统计打印购物车中的信息：");
        TotalPrice=CalculatePrice(GoodList);//计算总价打印全部信息

        DiscountPrice=PrintOrderList(GoodList);//计算折扣价打印Goodslist

        System.out.println("总计:"+DiscountPrice+"(元)");

        Double f1=ToBigDecimal(TotalPrice-DiscountPrice);
        System.out.println("节省:"+f1+"(元)");
        System.out.println("**********************");

    }

    //得到物品种类IndexList 一样一个
    public ArrayList<Goods> GetIndexList(String Path){
        String JsonContext = new ReadJson().ReadFile(Path);
       // System.out.println(JsonContext);
        JSONObject getJsonObj = JSONObject.fromObject(JsonContext);
       // System.out.println(getJsonObj);
        Iterator keys=getJsonObj.keys();
        ArrayList<Goods> GoodList=new ArrayList<Goods>();
        while(keys.hasNext()){
            Goods good = new Goods();
            String barcode =( String ) keys.next();
            good.setBarcode(barcode);
            JSONObject ChildObject=getJsonObj.getJSONObject(barcode); //根据Barcode查
            Iterator Childkeys=ChildObject.keys();
            while (Childkeys.hasNext()) {
                String ChildKey=(String)Childkeys.next();
                if (ChildKey.equals("name")){
                    good.setName(ChildObject.getString(ChildKey));
                }
                else if (ChildKey.equals("unit")){
                    good.setUnit(ChildObject.getString(ChildKey));
                }
                else if (ChildKey.equals("price")){
                    good.setPrice(ChildObject.getDouble(ChildKey));
                }
                else if (ChildKey.equals("discount")){
                    good.setDiscount(ChildObject.getDouble("discount"));
                }
            }
            GoodList.add(good);
        }
        return GoodList;
    }

}

