package JDBCUtils;

import BaiCiZhan.BaiCiZhan;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

//操作数据库的工具类
public class JDBCUtils {
    //获取数据库的连接
    public static Connection getConnection() throws Exception {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("baicizhan.properties");
        Properties properties = new Properties();
        properties.load(is);

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");

        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url,user,password);
        return connection;
    }
    //关闭连接和Statement的操作
    public static void closeResource(Connection cn, PreparedStatement ps){
        try {
            if (cn != null)
                cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (ps != null){
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //关闭连接和Statement和ResultSet
    public static void closeResource(Connection cn, PreparedStatement ps, ResultSet rs){
        try {
            if (cn != null)
                cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (ps != null){
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs != null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
