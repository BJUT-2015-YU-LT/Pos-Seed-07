package com.company;

/**
 * Created by dell on 2016/1/4.
 */
import java.sql.*;
import java.util.ArrayList;

public class Test
{
    public static void main(String[] args)
    {
    ExeSQL sql1=new ExeSQL();
    sql1.connSQL();
    sql1.GetGoodList();
    }
}
