package Nero.dao;

import Nero.model.Student;
import Nero.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public static List<Student> query() {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Student> list = new ArrayList<>();
        try {
            //1.获取数据库连接
            c = DBUtil.getConnection();
            String sql = "";
            //2.创建操作命令对象
            ps = c.prepareStatement(sql);
            //3.执行sql语句
            rs = ps.executeQuery();
            //4.处理查询结果集
            while(rs.next()){
                Student student = new Student();
                list.add(student);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("查询学生列表出错", e);
        }finally {
            DBUtil.close(c, ps, rs);
        }
    }
}
