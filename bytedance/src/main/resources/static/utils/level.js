$(function () {
    var token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        type: "get",
        url: "/level",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        headers: {
            "X-CSRF-TOKEN": token
        },
        success: function (result) {
            $.each(result, function (name, level) {
                $("#level_content").prepend(getSelectElement(name, level));
            });
        }, error: function (e) {
            console.log(e, 'error');
            layer.msg("操作失败", {icon: 1, time: 1000});
        }
    });
});

function getSelectElement(name, current_level) {
    level = current_level;
    if (current_level == null) {
        current_level = "";
        level = "默认";
    }
    return "<div class=\"layui-form-item\" style=\"width: 50%\">\n" +
        "                <label class=\"layui-form-label\">" + name + "</label>\n" +
        "                <div class=\"layui-input-block\">\n" +
        "                    <input type=\"text\" name=\"" + name + "\" placeholder=\"当前：" + level + "\"\n" +
        "                           autocomplete=\"off\" class=\"layui-input\" lay-verify=\"required\" value=" + current_level + ">\n" +
        "                </div>\n" +
        "            </div>"
    // return "    <div id=\"level_"+ name +"\" style=\"margin: 10px 0 10px 1%;width: 99%\">\n" +
    //     "        <p><label>"+ name +"("+ current_level +")</label>\n" +
    //     "            <select name=\"select_"+ name +"\">\n" +
    //     "                <option value=\"1\">1</option>\n" +
    //     "                <option value=\"2\">2</option>\n" +
    //     "                <option value=\"3\">3</option>\n" +
    //     "            </select></p>\n" +
    //     "    </div>"
}

$("#update_level").click(function () {
    var data = {};
    $('input').each(function () {
        tem = $(this).val();
        if (tem == "") {
            tem = null;
        }
        data[$(this).attr("name")] = tem;
    });
    var token = $("meta[name='_csrf']").attr("content");
    layer.confirm('您确认要提交并重启WAF吗？', function () {
        $.ajax({
            type: "post",  //数据提交方式(post/get)
            url: "/level",  //提交到的url
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: "text",//返回的数据类型格式
            headers: {
                "X-CSRF-TOKEN": token
            },
            success: function (result) {
                layer.msg("操作成功", {icon: 1, time: 1000});
            }, error: function (e) {
                console.log(e, 'error');
                layer.msg("操作失败", {icon: 1, time: 1000});
            }
        });
    })
});