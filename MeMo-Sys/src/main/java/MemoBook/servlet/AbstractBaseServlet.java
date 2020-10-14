package MemoBook.servlet;

import MemoBook.model.Response;
import MemoBook.util.JSONUtil;
import MemoBook.util.ThreadLocalHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class AbstractBaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req , resp);
    }
    //模板方法，参照HTTPServlet的service方法和doxxx()方法的关系，service就是一个模板方法
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");

        PrintWriter pw = resp.getWriter();
        Response r = new Response();

        try {
            Object o = process(req, resp);
            r.setSuccess(true);
            r.setCode("OK");
            r.setMessage("操作成功");
            r.setTotal(ThreadLocalHolder.getTOTAL().get());//不管是否分页接口，都获取当前线程中的total变量
            r.setData(o);
        } catch (Exception e) {
            r.setCode("ERROR500");
            r.setMessage(e.getMessage());
            StringWriter sw = new StringWriter();
            PrintWriter writer = new PrintWriter(sw);
            e.printStackTrace(writer);
            String stackTrance = sw.toString();
            System.err.println(stackTrance);
            r.setStackTrace(stackTrance);
        }finally {
            ThreadLocalHolder.getTOTAL().remove();//在线程结束前，删除变量。如果不删除，可能存在内存泄漏
        }
        pw.println(JSONUtil.write(r));
        pw.flush();
    }

    protected abstract Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception;

}
