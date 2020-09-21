package Nero.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    /**
     * 数据库jdbc的操作步骤？
     * 1.创建数据库连接Connection
     * 2.创建操作命令对象Statement
     * 3.执行SQL
     * 4.如果是查询操作，处理结果集
     * 5.释放资源
     */
    private static final String URL = "jdbc:mysql://localhost:3306/stu_info";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "0533";

    private static volatile DataSource DS;
    private DBUtil(){}
    private static DataSource getDataSource(){
        if(DS == null){
            synchronized (DBUtil.class){
                if(DS == null){
                    DS = new MysqlDataSource();
                    ((MysqlDataSource) DS).setURL(URL);
                    ((MysqlDataSource) DS).setUser(USERNAME);
                    ((MysqlDataSource) DS).setPassword(PASSWORD);
                }
            }
        }
        return DS;
    }
    public static Connection getConnection(){
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("获取数据库连接失败", e);
        }
    }

    public static void close(Connection c, Statement s){
        close(c, s, null);
    }
    public static void close(Connection c, Statement s, ResultSet r){
        try {
            if(r != null)
                r.close();
            if(s != null)
                s.close();
            if(c != null)
                c.close();
        } catch (SQLException e) {
            throw new RuntimeException("释放数据库资源出错", e);
        }
    }
}


