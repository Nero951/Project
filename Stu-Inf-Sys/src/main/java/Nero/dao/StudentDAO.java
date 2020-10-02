package Nero.dao;

import Nero.model.Classes;
import Nero.model.Page;
import Nero.model.Student;
import Nero.util.DBUtil;
import Nero.util.ThreadLocalHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentDAO {

    public static List<Student> query(Page p) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Student> list = new ArrayList<>();
        try {
            //1.获取数据库连接
            c = DBUtil.getConnection();
            StringBuilder sql = new StringBuilder("select s.id, " +
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
                    "                             join classes c on s.classes_id = c.id");
            if(p.getSearchText() != null && p.getSearchText().trim().length() > 0){
                sql.append("    where s.student_name like ?"); //模糊查询，只要包含就行
            }
            if(p.getSortOrder() != null && p.getSortOrder().trim().length() > 0){
                //不能用占位符替换，占位符替换会带上‘ ’ ，变成order by xxx 'asc'
                //这里拼接字符串的方式，存在sql注入的风险，一般会校验一下，这里省略了
                sql.append("    order by s.classes_id "+p.getSortOrder());
            }
            //1.获取查询总数量：sql可以复用，子查询的方式实现
            StringBuilder countSQL = new StringBuilder("select count(0) count from(");
            countSQL.append(sql);
            countSQL.append(")tmp");
            ps = c.prepareStatement(countSQL.toString());
            if(p.getSearchText() != null && p.getSearchText().trim().length()>0){
                ps.setString(1,"%"+p.getSearchText()+"%");
            }
            rs = ps.executeQuery();
            while(rs.next()){
                int count = rs.getInt("count");
                ThreadLocalHolder.getTOTAL().set(count);//设置total变量到当前线程中的ThreadLocalMap数据结构中保存
            }
            //2.获取分页的数据
            sql.append("    limit ?,?");
            //2.创建操作命令对象
            ps = c.prepareStatement(sql.toString());
            int index = 1;
            if(p.getSearchText() != null && p.getSearchText().trim().length()>0){
                ps.setString(index++,"%"+p.getSearchText()+"%");
            }
            ps.setInt(index++, (p.getPageNumber()-1)*p.getPageSize());//设置索引等于上一页的页码*每一页的数量
            ps.setInt(index++, p.getPageSize());
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

    public static Student queryById(int id) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Student student = new Student();
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
                    "                             join classes c on s.classes_id = c.id" +
                    "   where s.id=?";

            //3.创建操作命令对象
            ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            //4.执行sql语句
            rs = ps.executeQuery();
            //5.处理查询结果集
            while(rs.next()){
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

            }
            return student;
        } catch (Exception e) {
            throw new RuntimeException("查询学生详情出错", e);
        }finally {
            DBUtil.close(c, ps, rs);
        }
    }

    public static void insert(Student s) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            //1.获取数据库连接
            c = DBUtil.getConnection();
            String sql = "insert into student(student_name,student_no,id_card,student_email,classes_id) values(?,?,?,?,?)";
            //2.创建操作命令对象
            ps = c.prepareStatement(sql);
            ps.setString(1,s.getStudentName());
            ps.setString(2,s.getStudentNo());
            ps.setString(3,s.getIdCard());
            ps.setString(4,s.getStudentEmail());
            ps.setInt(5,s.getClassesId());

            int num = ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("新增学生信息出错", e);
        }finally {
            DBUtil.close(c, ps);
        }
    }

    public static void update(Student s) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            //1.获取数据库连接
            c = DBUtil.getConnection();
            String sql = "update student set student_name=?, student_no=?, id_card=?,student_email=?, classes_id=? where id=?";
            //2.创建操作命令对象
            ps = c.prepareStatement(sql);
            ps.setString(1,s.getStudentName());
            ps.setString(2,s.getStudentNo());
            ps.setString(3,s.getIdCard());
            ps.setString(4,s.getStudentEmail());
            ps.setInt(5,s.getClassesId());
            ps.setInt(6,s.getId());

            int num = ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("修改学生信息出错", e);
        }finally {
            DBUtil.close(c, ps);
        }
    }

    public static void delete(String[] ids) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            //1.获取数据库连接
            c = DBUtil.getConnection();
            StringBuilder sql = new StringBuilder("delete from student where id in(");
            for(int i=0;i <ids.length;i++) {
                if (i != 0)
                    sql.append(",");
                sql.append("?");
            }
            sql.append(")");

            //2.创建操作命令对象
            ps = c.prepareStatement(sql.toString());
            for(int i=0;i <ids.length;i++) {
                ps.setInt(i+1, Integer.parseInt(ids[i]));
            }
            int num = ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("删除学生出错", e);
        }finally {
            DBUtil.close(c, ps);
        }
    }
}
