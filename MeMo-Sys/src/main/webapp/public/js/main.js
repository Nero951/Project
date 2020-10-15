function logout(){
    $.ajax({
        url: "user/logout",
        contentType: "application/json",
        success: function (r) {
            showSuccessModal("注销成功", function () {
                window.location.href = "public/index.html";
            });
        },
        error: function (error) {
            showError(error);
        }
    });
    return false;
}
//事务管理
let classesTabOptions = {
    id: "classes_table",//表格id
    url:'classes/query',//表格查询url
    toolbar: [{
        type: "add",//新增按钮
        title: "新增事务",//弹出框标题
        url: "classes/add",//弹出框提交url
        invisible:["createTime"],//弹出框不展示的字段
    },{
        type: "update",//修改按钮
        title: "修改事项",
        queryUrl: "classes/queryById",//弹出框初始化数据url
        url: "classes/update",
        // invisible:["createTime"],
    },{
        type: "delete",//删除按钮
        url: "classes/delete",
    }],
    columns: [{
        checkbox: true
    },{
        title: '事务内容',
        field: "classesName",
        switchable: false,//表格是否允许不展示列
        sortable: true,//表格字段是否可排序
        required: true,//新增/修改时，字段是否必填
        tooltip: "请输入事务内容",//提交时字段验证提示内容
     },//{
    //     title: '毕业年份',
    //     field: 'classesGraduateYear',
    //     type: "select",//新增/修改时，字段为下拉菜单
    //     dictionaryKey: "000001",//数据字典键
    // },
        {
        title: '专业',
        field: 'classesMajor',
        // submitName: 'classesMajor',//新增/修改提交的字段名
        type: "select",//新增/修改时，字段为下拉菜单
        dictionaryKey: "000002",//数据字典键
    },{
        title: '描述',
        field: 'classesDesc',
    },{
        title: '创建时间',
        field: 'createTime',
        type: "datetime",
        disabled: true,
    }],
};


$(function () {
    setTableDefaultSettings();
    initNav("main_nav", [{
        id: "classes_tab",
        init: function () {
            initTable(classesTabOptions);
        }
    },{
        id: "stu_tab",
        init: function () {
            initTable(stuTabOptions);
        },
    },{
        id: "user_tab",
        init: function () {
            initTable(userTabOptions);
        },
        default: true
    },{
        id: "settings_tab",
        init: function () {
        },
        active: false
    }]);
});