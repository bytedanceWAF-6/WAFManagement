layui.use(['table', 'layer', 'form', 'laypage'], function () {
    var table = layui.table;
    var form = layui.form;

    table.render({
        elem: '#goods_table'
        , id: 'goodsReload'
        , even: true //开启隔行背景
        , url: '/rule' //'/goods/goodsList'
        , request: {
            pageName: 'pageNum',
            limitName: 'pageSize'
        }
        , parseData: function (res) { // 为了满足LayUI的接口格式，必须要额外封装
            return {
                "code": 0,
                "msg": "查询成功",
                "count": res.length,
                "data": res
            }
        }
        // , toolbar: '#goods_headerBar'
        , title:
            '商品详情表'
        , height: 450
        , page:
            false // true: 开启分页
        , limit: 10
        , limits: [1, 5, 10, 20, 50, 100]
        , cols:
            [
                [
                    {field: 'id', title: '编号', width: 60, align: 'center'}
                    , {field: 'url', title: '生效URL', width: '15%', align: 'center'}
                    , {field: 'reg_expr_name', title: '规则名', width: '10%', align: 'center'}
                    , {
                    field: 'reg_expr', title: '规则内容', width: '15%', align: 'center', templet: function (d) {
                        //Html编码获取Html转义实体
                        function htmlEncode(value) {
                            return $('<div/>').text(value).html();
                        }

                        //Html解码获取Html实体
                        function htmlDecode(value) {
                            return $('<div/>').html(value).text();
                        }

                        return htmlEncode(d.reg_expr);
                    }
                }
                    , {
                    field: 'jump', title: '匹配操作', width: '10%', align: 'center', templet: function (d) {
                        console.log(d);
                        if (d.jump == 'accept') {
                            return '<span style="color: #1E9FFF;">accept</span>'
                        } else {
                            return '<span style="color: #FF5722;">drop</span>'
                        }
                    }
                }
                    , {
                    field: 'is_default', title: '规则类型', width: '10%', align: 'center', templet: function (d) {
                        console.log(d);
                        if (d.is_default == 0) {
                            return '<span>自定义规则</span>'
                        } else {
                            return '<span>默认规则</span>'
                        }
                    }
                }

                    , {field: 'global_level', title: '全局等级', width: '7.5%', align: 'center'}
                    , {field: 'local_level', title: '洞内等级', width: '7.5%', align: 'center'}
                    , {field: 'note', title: '备&emsp;注', align: 'center'}
                    , {
                    fixed: 'right',
                    title: '操作',
                    toolbar: '#goods_lineBar',
                    width: 160,
                    align: 'center'
                }
                ]]
    });

    $("#refresh").click(function () {
        // 执行一个表格重载即实现刷新功能
        table.reload('goodsReload', {
            where: '',
            contentType: 'application/x-www-form-urlencoded',
            // page: {
            //     curr: 1 //重新从第 1 页开始
            // },
            url: '/rule',
            method: 'get'
        });
    });

    $("#add").click(function () {
        var data = {};
        data.action = '/rule/update/';
        data.request_type = 'post';

        // 调用打开弹层的工具方法
        open_form("#open_div", data, '添加自定义规则', '680px', '370px');
    });

    $("#reload").click(function () {
        var token = $("meta[name='_csrf']").attr("content");
        layer.confirm('您确认要重启WAF吗？', function (index) {
            $.ajax({
                type: "get",  //数据提交方式(post/get)
                url: "/reload",  //提交到的url
                contentType: "application/json; charset=utf-8",
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
        });
    });

    table.on('tool(goods_bar)', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        var id = data.id;

        switch (layEvent) {
            case 'edit':
                // 根据编辑行为为form隐藏项赋值
                data.action = '/rule/update/';
                data.request_type = 'post';
                open_form("#open_div", data, '编辑自定义规则', '680px', '350px');
                break;
            case 'del':
                layer.confirm('您确认要删除该规则吗？', function (index) {
                    var token = $("meta[name='_csrf']").attr("content");
                    //向服务端发送删除指令
                    $.ajax({
                        type: "get",  //数据提交方式(post/get)
                        url: "/rule/delete/" + id,  //提交到的url
                        contentType: "application/json; charset=utf-8",
                        dataType: "text",//返回的数据类型格式
                        headers: {
                            "X-CSRF-TOKEN": token
                        },
                        success: function (result) {
                            obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                            layer.msg("操作成功", {icon: 1, time: 1000});
                        }, error: function (e) {
                            console.log(e, 'error');
                            layer.msg("操作失败<br>" + e.responseText, {icon: 1, time: 3000});
                        }
                    });
                    layer.close(index);
                });
                break;
        }
    });

    form.on('submit(update_submit)', function (data) {
        var uri = data.field.action;
        var type = data.field.request_type;
        console.log(data);
        // delete data.field.action;
        // delete data.field.request_type;
        var token = $("meta[name='_csrf']").attr("content");
        // var header = $("meta[name='_csrf_header']").attr("content");
        if (data.field.id != "" && (data.field.action == "/rule/update/" || data.field.action == "/rule/delete/")) {
            $.ajax({
                type: type,
                url: uri + data.field.id,
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(data.field),
                dataType: "json",
                headers: {
                    "X-CSRF-TOKEN": token
                },
                success: function (result) {
                    if (result.code == "0") {
                        table.reload('goodsReload', {
                            contentType: 'application/x-www-form-urlencoded',
                            // page: {
                            //     curr: 1 //重新从第 1 页开始
                            // },
                            url: '/rule',
                            method: 'get'
                        });
                        layer.msg('操作成功', {icon: 1, time: 1000});
                    } else {  //失败
                        layer.alert('操作失败', {icon: 2}, function () {
                            layer.close(index);
                        });
                    }
                }
            });
        } else {
            $.ajax({
                type: type,
                url: uri,
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(data.field),
                dataType: "json",
                headers: {
                    "X-CSRF-TOKEN": token
                },
                success: function (result) {
                    if (result.code == "0") {
                        table.reload('goodsReload', {
                            contentType: 'application/x-www-form-urlencoded',
                            // page: {
                            //     curr: 1 //重新从第 1 页开始
                            // },
                            url: '/rule',
                            method: 'get'
                        });
                        layer.msg('修改成功', {icon: 1, time: 1000});
                    } else {  //失败
                        layer.alert('操作失败', {icon: 2}, function () {
                            layer.close(index);
                        });
                    }
                }
            });
        }
        layer.close(index);//关闭弹出层
        return false;
    });

    // 监听搜索按钮提交事件
    form.on('submit(search)', function (data) {
        var formData = data.field;
        var count = checkForm("search_form");
        if (count !== 0) {
            //数据表格重载
            tableReload('goodsReload', formData, "application/json; charset=utf-8", '/goods/searchGoods', 'post');
        } else {
            parent.layer.msg('请先选择查询条件！', {icon: 2, time: 1500});
        }
        return false;
    });
});

var token = $("meta[name='_csrf']").attr("content");
$.ajax({
    type: "get",  //数据提交方式(post/get)
    url: "/rule/userdefine",  //提交到的url
    contentType: "application/json; charset=utf-8",
    dataType: "json",//返回的数据类型格式
    headers: {
        "X-CSRF-TOKEN": token
    },
    success: function (result) {
        if (result.UserDefine == "\"on\"") {
            $("#useUser").attr("checked", true);
        } else {
            $("#useUser").attr("checked", false);
        }
    }, error: function (e) {
        console.log(e, 'error');
        layer.msg("操作失败", {icon: 1, time: 1000});
    }
});

$("#useUser").click(function () {
    $.ajax({
        type: "get",  //数据提交方式(post/get)
        url: "/rule/switchuserdefine",  //提交到的url
        contentType: "application/json; charset=utf-8",
        dataType: "text",//返回的数据类型格式
        headers: {
            "X-CSRF-TOKEN": token
        },
        success: function (result) {
            layer.msg("操作成功", {icon: 1, time: 3000});
            window.location.reload();
        }, error: function (e) {
            console.log(e, 'error');
            layer.msg("操作失败", {icon: 1, time: 1000});
            if ($("#useUser").attr("checked")) {
                $("#useUser").attr("checked", false);
            } else {
                $("#useUser").attr("checked", true);
            }
        }
    });
});