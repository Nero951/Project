package Nero.dao;

import Nero.model.Classes;
import Nero.model.Student;
import Nero.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
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
            String sql = "select s.id, " +
                    "                           s.student_name, " +
                    "                           s.student_no, " +
                    "                           s.id_card, " +
                    "                           s.student_email, " +
                    "                           s.classes_id, " +
                    "                           s.create_time, " +
                    "                           c.id cid, " +
                    "                           c.classes_name, " +
                    "                           c.classes_graduate_year, " +
                    "                           c.classes_major, " +
                    "                           c.classes_desc, " +
                    "                           c.create_time c_create_time " +
                    "                    from student s " +
                    "                             join classes c on s.classes_id = c.id";
            //2.创建操作命令对象
            ps = c.prepareStatement(sql);
            //3.执行sql语句
            rs = ps.executeQuery();
            //4.处理查询结果集
            while(rs.next()){
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setStudentName(rs.getString("student_name"));
                student.setStudentNo(rs.getString("student_no"));
                student.setIdCard(rs.getString("id_card"));
                student.setStudentEmail(rs.getString("student_email"));
                student.setClassesId(rs.getInt("classes_id"));
                student.setCreateTime(new Date(rs.getTimestamp("create_time").getTime()));
                Classes classes = new Classes();
                student.setClasses(classes);

                classes.setId(rs.getInt("cid"));
                classes.setClassesName(rs.getString("classes_name"));
                classes.setClassesGraduateYear(rs.getString("classes_graduate_year"));
                classes.setClassesMajor(rs.getString("classes_major"));
                classes.setClassesDesc(rs.getString("classes_desc"));
                classes.setCreateTime(new Date(rs.getTimestamp("c_create_time").getTime()));

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
