package Nero.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.servlet.http.HttpServletRequest;

@Setter
@Getter
@ToString
public class Page {
    private String searchText;//条件查询的字段
    private String sortOrder;//排序的条件
    private Integer pageSize;//每页的数量
    private Integer pageNumber;//每页的页码

    public static Page parse(HttpServletRequest req){
        Page p = new Page();
        p.searchText = req.getParameter("searchText");
        p.sortOrder = req.getParameter("sortOrder");
        p.pageSize = Integer.parseInt(req.getParameter("pageSize"));
        p.pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
        return p;
    }
}
