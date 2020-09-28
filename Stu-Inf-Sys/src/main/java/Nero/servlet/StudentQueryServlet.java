package Nero.servlet;

import Nero.dao.StudentDAO;
import Nero.model.Page;
import Nero.model.Student;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/student/query")
public class StudentQueryServlet extends AbstractBaseServlet{
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        Page p = Page.parse(req);
        List<Student> students = StudentDAO.query(p);
        return students;
    }
}
