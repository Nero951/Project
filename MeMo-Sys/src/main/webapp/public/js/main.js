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
/**
 * 中间功能未实现
 *
 */
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