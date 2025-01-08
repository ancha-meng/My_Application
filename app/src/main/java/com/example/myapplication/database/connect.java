package com.example.myapplication.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class connect {
    private static int conn_on = 0;
    public static Connection getConnection(String dbName) throws SQLException {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver"); //加载驱动
            String ip = "rm-bp1we3etm4q7eumd5vo.mysql.rds.aliyuncs.com";
            conn =DriverManager.getConnection(
                    "jdbc:mysql://" + ip + ":3306/" + dbName,
                    "qaz398277", "Mlc7251-1111");
            conn_on=1;//用于向主函数传参，判断连接是否成功
        }catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            conn_on=2;//用于向主函数传参，判断连接是否成功
        }
        return conn;//返回Connection型变量conn用于后续连接
    }

    public static int getConn_on() {
        return conn_on;
    }

    public static int insertIntoData(final String username, final String password) throws SQLException {//增加数据
        Connection  conn = null;
        conn = getConnection("db_map");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "insert INTO user (username,password)VALUES('"+username+"','"+password+"')";//把用户名和密码插入到数据库中
        return stmt.executeUpdate(sql);
        //执行DML语句，返回受影响的记录条数
    }
    public static int insertProject(final String user_name,final String project_name) throws SQLException {
        Connection  conn = null;
        conn = getConnection("db_map");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "insert INTO project (project_name,user_name)VALUES('"+project_name+"','"+user_name+"')";
        return stmt.executeUpdate(sql);
        //执行DML语句，返回受影响的记录条数
    }
    public static int insertFeel_point(final String user_name,final String project_name,final Point point) throws SQLException {
        Connection  conn = null;
        conn = getConnection("db_map");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "insert INTO point_feel (user_name,project_name,point_name,type,x,y," +
                "hot,body,beauty,safety,clean,alive,depress,boring,rich," +
                "beauty_factor,safety_factor,clean_factor,alive_factor,depress_factor,boring_factor,rich_factor,image_name,audio_name)VALUES('"
                +user_name+"','"
                +project_name+"','"
                +point.getPoint_name()+"','"
                +point.getType()+"','"
                +point.get_X()+"','"
                +point.get_Y()+"','"
                +point.getHot()+"','"
                +point.getBody()+"','"
                +point.getScore()[0]+"','"
                +point.getScore()[1]+"','"
                +point.getScore()[2]+"','"
                +point.getScore()[3]+"','"
                +point.getScore()[4]+"','"
                +point.getScore()[5]+"','"
                +point.getScore()[6]+"','"
                +point.getFactor()[0]+"','"
                +point.getFactor()[1]+"','"
                +point.getFactor()[2]+"','"
                +point.getFactor()[3]+"','"
                +point.getFactor()[4]+"','"
                +point.getFactor()[5]+"','"
                +point.getFactor()[6]+"','"
                +point.getImage_name()+"','"
                +point.getAudio_name()+"')";
        return stmt.executeUpdate(sql);
        //执行DML语句，返回受影响的记录条数
    }
    public static int insertValue_point(final String user_name,final String project_name,final Point point) throws SQLException {
        Connection  conn = null;
        conn = getConnection("db_map");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "insert INTO point_value (user_name,project_name,point_name,type,x,y," +
                "CHILL,HEAT_INDEX,shiqiu_temperature,T_limit,diqiu_temperature,temperature,xiangdui_wetness,loudian_temperature," +
                "pressure,altitude,density,air_TVOC,air_HCHO,air_C6H6,air_CO2,air_PM25,air_PM10,daylight,wind" +
                ")VALUES('"
                +user_name+"','"
                +project_name+"','"
                +point.getPoint_name()+"','"
                +point.getType()+"','"
                +point.get_X()+"','"
                +point.get_Y()+"','"
                +point.getCHILL()+"','"
                +point.getHEAT_INDEX()+"','"
                +point.getShiqiu_temperature()+"','"
                +point.getT_limit()+"','"
                +point.getDiqiu_temperature()+"','"
                +point.getTemperature()+"','"
                +point.getXiangdui_wetness()+"','"
                +point.getLoudian_temperature()+"','"
                +point.getPressure()+"','"
                +point.getAltitude()+"','"
                +point.getDensity()+"','"
                +point.getAir_TVOC()+"','"
                +point.getAir_HCHO()+"','"
                +point.getAir_C6H6()+"','"
                +point.getAir_CO2()+"','"
                +point.getAir_PM25()+"','"
                +point.getAir_PM10()+"','"
                +point.getLight()+"','"
                +point.getWind()+"')";
        return stmt.executeUpdate(sql);
        //执行DML语句，返回受影响的记录条数
    }
    public static int insertImage(final String user_name,final String project_name,final String point_name,
                                  final String image_name,final int image_size,final byte[] image_data) throws SQLException {
        Connection  conn = null;
        conn = getConnection("db_map");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "insert INTO image (user_name,project_name,point_name,image_name,image_size,image_data)VALUES('"
                +user_name+"','"
                +project_name+"','"
                +point_name+"','"
                +image_name+"','"
                +image_size+"','"
                +image_data+"')";
        return stmt.executeUpdate(sql);
        //执行DML语句，返回受影响的记录条数
    }
    public static int insertAudio(final String user_name,final String project_name,final String point_name,
                                  final String audio_name,final byte[] audio_data) throws SQLException {
        Connection  conn = null;
        conn = getConnection("db_map");
        //使用DriverManager获取数据库连接
        Statement stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "insert INTO audio (user_name,project_name,point_name,audio_name,audio_data)VALUES('"
                +user_name+"','"
                +project_name+"','"
                +point_name+"','"
                +audio_name+"','"
                +audio_data+"')";
        return stmt.executeUpdate(sql);
        //执行DML语句，返回受影响的记录条数
    }
    public static int updateData(final String col, final int key,final String name) throws SQLException {//修改数据
        Connection  conn = null;
        conn = getConnection("db_map");
        //使用DriverManager获取数据库连接
        Statement  stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "UPDATE person SET "+col+"='"+key+"' WHERE number=name'";//修改的sql语句
        return stmt.executeUpdate(sql);//返回的同时执行sql语句，返回受影响的条目数量，一般不作处理
    }
    public static void delete(final String number1)throws SQLException{   //删除数据
        Connection  conn = null;
        conn = getConnection("person");
        //使用DriverManager获取数据库连接
        Statement  stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        String sql = "DELETE FROM person WHERE number='"+number1+"'";    // 写删除的SQL语句
        stmt.executeUpdate(sql);//返回的同时执行sql语句，返回受影响的条目数量，一般不作处理
    }

    public static String querycol(final String id) throws SQLException {//读取某一行
        //加载数据库驱动
        String a;
        Connection  conn = null;
        conn = getConnection("db_map");
        //使用DriverManager获取数据库连接
        Statement  stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        ResultSet rs =stmt.executeQuery(
                "select password from user where username='"+id+"'");//从数据库中查询用户名对应的密码并返回
        rs.first();
        a=rs.getString(1);
        rs.close();
        return a;
        //把查询结果输出来
    }
    public static String exist_col(final String table, final String col, final String value) throws SQLException {//读取某一行
        //加载数据库驱动
        String a;
        Connection  conn = null;
        conn = getConnection("db_map");
        //使用DriverManager获取数据库连接
        Statement  stmt = conn.createStatement();
        //使用Connection来创建一个Statment对象
        ResultSet rs =stmt.executeQuery(
                "select exists (SELECT * from "+table+" where "+col+"='"+value+"')");
        rs.first();
        a=rs.getString(1);
        rs.close();
        return a;
        //把查询结果输出来
    }
    public static boolean exist_col2(final String table, final String col, final String value,
                                    final String col2, final String value2) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // 假设getConnection方法能够正确获取数据库连接
            conn = getConnection("db_map");

            // 使用PreparedStatement来避免SQL注入
            String sql = "SELECT EXISTS (SELECT 1 FROM " + table + " WHERE " + col + " = ? AND " + col2 + " = ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, value);
            pstmt.setString(2, value2);

            // 执行查询
            rs = pstmt.executeQuery();

            // 由于EXISTS查询只返回一行一列，我们可以直接检查这一行这一列的值
            if (rs.next()) {
                return rs.getBoolean(1); // 或者使用 rs.getInt(1) > 0，但getBoolean更直接表达意图
            }

            // 如果没有结果，则返回false
            return false;
        } finally {
            // 关闭资源
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* log or ignore */ }
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { /* log or ignore */ }
            try { if (conn != null) conn.close(); } catch (SQLException e) { /* log or ignore */ }
        }
    }
}


