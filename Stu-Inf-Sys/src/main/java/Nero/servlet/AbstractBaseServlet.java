package Nero.servlet;

import Nero.model.Response;
import Nero.util.JSONUtil;

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
        }
        pw.println(JSONUtil.write(r));
        pw.flush();
    }

    protected abstract Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception;

}
