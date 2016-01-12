package com.company;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by dell on 2016/1/12.
 */
public class ExeSQL {

    private Connection conn = null;
    private PreparedStatement statement = null;
    private ResultSet rs = null;

    void connSQL() {
        String url = "jdbc:mysql://localhost:3306/pos";
        String username = "root";
        String password = ""; // 加载驱动程序以连接数据库
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
        }
        //捕获加载驱动程序异常
        catch (ClassNotFoundException cnfex) {
            System.err.println(
                    "装载 JDBC/ODBC 驱动程序失败。");
            cnfex.printStackTrace();
        }
        //捕获连接数据库异常
        catch (SQLException sqlex) {
            System.err.println("无法连接数据库");
            sqlex.printStackTrace();
        }
    }

    // disconnect to MySQL
    void deconnSQL() {
        try {
            if (conn != null)
                conn.close();
        } catch (Exception e) {
            System.out.println("关闭数据库问题 ：");
            e.printStackTrace();
        }
    }

    public ArrayList<Goods> GetGoodList() {
        String sql;
        sql = "select * from good";
        rs = selectSQL(sql);
        ArrayList<Goods> goodsList = new ArrayList<Goods>();
        try {
            while (rs.next()) {
                Goods goods = new Goods();
                goods.setBarcode(rs.getString("barcode"));
                goods.setName(rs.getString("name"));
                goods.setUnit(rs.getString("unit"));
                goods.setPrice(rs.getDouble("price"));
                goods.setDiscount(rs.getDouble("discount"));
                goodsList.add(goods);
            }
        } catch (SQLException e) {
            System.out.println("显示时数据库出错。");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("显示出错。");
            e.printStackTrace();
        }
        return goodsList;
    }


    ResultSet selectSQL(String sql) {
        ResultSet r1 = null;
        try {
            statement = conn.prepareStatement(sql);
            r1 = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r1;
    }

    //execute delete language
    boolean deleteSQL(String sql) {
        try {
            statement = conn.prepareStatement(sql);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("插入数据库时出错");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("插入时出错");
            e.printStackTrace();
        }
        return false;
    }

    //execute update language
    boolean updateSQL(String sql) {
        try {
            statement = conn.prepareStatement(sql);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("插入数据库时出错");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("插入时出错：");
            e.printStackTrace();
        }
        return false;
    }

    // execute insertion language
    boolean insertSQL(String sql) {
        try {
            statement = conn.prepareStatement(sql);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("插入数据库时出错：");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("插入时出错：");
            e.printStackTrace();
        }
        return false;
    }

    public Goods GetGoodByBarCode(String Name) {
        String sql = "select * from good where barcode='"+Name+"'";
        rs = selectSQL(sql);
        Goods goods = new Goods();
        try {
            while (rs.next()) {
                goods.setBarcode(rs.getString("barcode"));
                goods.setName(rs.getString("name"));
                goods.setUnit(rs.getString("unit"));
                goods.setPrice(rs.getDouble("price"));
                goods.setDiscount(rs.getDouble("discount"));
            }
        } catch (SQLException e) {
            System.out.println("显示时数据库出错。");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("显示出错。");
            e.printStackTrace();
        }
        return goods;
    }
}
