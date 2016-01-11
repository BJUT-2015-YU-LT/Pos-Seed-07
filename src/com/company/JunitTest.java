package com.company;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dell on 2016/1/5.
 */
public class JunitTest {

    @Test
    public void TestGetGoodByBarCode()
    {
        ArrayList<Goods> GoodList = new ArrayList<Goods>();
        Goods g1=new Goods();
        g1.setBarcode("1");
        g1.setName("我是一");
        GoodList.add(g1);
        Goods g2=new Goods();
        g2.setBarcode("2");
        g2.setName("我是二");
        GoodList.add(g2);
        PrintGoodList ct=new PrintGoodList();
        Goods g3=ct.GetGoodByBarCode("1",GoodList);
        System.out.println(g3.getName());
    }

    @Test
    public void TestsumMap()
    {
        ArrayList<Goods> GoodList=new ArrayList<Goods>();
        Map<String,Double> map = new LinkedHashMap<String, Double>();
        for(int i=0;i<3;i++)
        {
            Integer I=i;
            Goods goods=new Goods();
            goods.setBarcode(I.toString());
            goods.setPrice((double) (i+100));
            GoodList.add(goods);
        }
        Goods goods=new Goods();
        goods.setBarcode("2");
        goods.setPrice((double)200);
        GoodList.add(goods);
        for (Goods exp:GoodList) {
            if(map.containsKey(exp.getBarcode())){
                Double price=map.get(exp.getBarcode());
                map.put(exp.getBarcode(), exp.getPrice()+price);
            }else {
                map.put(exp.getBarcode(),exp.getPrice());
            }
        }
        Set<String> set = map.keySet();
        for (Iterator<String> iter = set.iterator(); iter.hasNext();)
        {
            Goods good=new Goods();
            String key = (String)iter.next();
            Double value = (Double)map.get(key);
            System.out.println("商品BarCode:   "+key+"   "+
                    "小计:   " + value);
        }
    }

    @Test
    public void TestToBigDecimal()
    {
        BigDecimal b=new BigDecimal(5.5555);
        double f1=b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(f1);
    }

    @Test
    public void TestTransJsonToEntity()
    {
        String JsonContext = new ReadJson().ReadFile("D:\\test\\1.json");
        JSONArray jsonArray = JSONArray.fromObject(JsonContext);
        int size = jsonArray.size();
        ArrayList<Goods> GoodList = new ArrayList<Goods>();
        for(int  i = 0; i < size; i++) {
            Goods goods = new Goods();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            goods.setBarcode(jsonObject.getString("barcode"));
            goods.setName(jsonObject.getString("name"));
            goods.setUnit(jsonObject.getString("unit"));
            goods.setPrice(jsonObject.getDouble("price"));
            if (jsonObject.get("discount") != null) {
                goods.setDiscount(jsonObject.getDouble("discount"));
            }
            GoodList.add(goods);
        }
        for (Goods exp:GoodList) {
            System.out.println(exp.getBarcode());
        }

    }

    @Test
    public void TestGetIndexList()
    {
        String JsonContext = new ReadJson().ReadFile("D:\\test\\index.json");
        JSONObject getJsonObj = JSONObject.fromObject(JsonContext);
        Iterator keys=getJsonObj.keys();
        ArrayList<Goods> GoodList=new ArrayList<Goods>();
        while(keys.hasNext()){
            Goods good = new Goods();
            String barcode =(String)keys.next();
            good.setBarcode(barcode);
            JSONObject ChildObject=getJsonObj.getJSONObject(barcode); //根据Barcode查
            Iterator Childkeys=ChildObject.keys();
            while (Childkeys.hasNext()) {
                String ChildKey=(String)Childkeys.next();
                if (ChildKey.equals("name")){
                    good.setName(ChildObject.getString(ChildKey));
                }
                else if (ChildKey.equals("unit")){
                    good.setUnit(ChildObject.get(ChildKey).toString());
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
        for (Goods exp:GoodList) {
            System.out.println(exp.getBarcode());
        }
    }

}
