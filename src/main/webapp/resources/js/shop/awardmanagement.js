/**
 * Created by WavyPeng on 2018/7/23.
 */
$(function() {
    // 获取此店铺下的奖品列表的URL
    var listUrl = '/o2o/shopadmin/listawardsbyshop?pageIndex=1&pageSize=999';
    // 商品下架URL
    var statusUrl = '/o2o/shopadmin/modifyaward';
    getList();
    /**
     * 获取此店铺下奖品列表
     *
     * @returns
     */
    function getList() {
        // 从后台获取此店铺的奖品列表
        $.getJSON(listUrl, function(data) {
            if (data.success) {
                var awardList = data.awardList;
                var tempHtml = '';

                awardList.map(function(item, index) {
                    var textOp = "下架";
                    var contraryStatus = 0;
                    if (item.enableStatus == 0) {
                        // 若状态值为0，表明是已下架的商品，操作变为上架(即点击上架按钮上架相关商品)
                        textOp = "上架";
                        contraryStatus = 1;
                    } else {
                        contraryStatus = 0;
                    }
                    // 拼接每件商品的行信息
                    tempHtml += '' + '<div class="row row-award">'
                        + '<div class="col-33">'
                        + item.awardName
                        + '</div>'
                        + '<div class="col-20">'
                        + item.point
                        + '</div>'
                        + '<div class="col-40">'
                        + '<a href="#" class="edit" data-id="'
                        + item.awardId
                        + '" data-status="'
                        + item.enableStatus
                        + '">编辑</a>'
                        + '<a href="#" class="status" data-id="'
                        + item.awardId
                        + '" data-status="'
                        + contraryStatus
                        + '">'
                        + textOp
                        + '</a>'
                        + '<a href="#" class="preview" data-id="'
                        + item.awardId
                        + '" data-status="'
                        + item.enableStatus
                        + '">预览</a>'
                        + '</div>'
                        + '</div>';
                });
                // 将拼接好的信息赋值进html控件中
                $('.award-wrap').html(tempHtml);
            }
        });
    }
    // 将class为award-wrap里面的a标签绑定上点击的事件
    $('.award-wrap')
        .on(
            'click',
            'a',
            function(e) {
                var target = $(e.currentTarget);
                if (target.hasClass('edit')) {
                    // 如果有class edit则点击就进入店铺信息编辑页面，并带有awardId参数
                    window.location.href = '/o2o/shopadmin/awardoperation?productId='
                        + e.currentTarget.dataset.id;
                } else if (target.hasClass('delete')) {
                    // 如果有class status则调用后台功能上/下架相关商品，并带有productId参数
                    changeItemStatus(e.currentTarget.dataset.id,
                        e.currentTarget.dataset.status);
                } else if (target.hasClass('preview')) {
                    // 如果有class preview则去前台展示系统该商品详情页预览商品情况
                    window.location.href = '/o2o/frontend/awarddetail?awardId='
                        + e.currentTarget.dataset.id;
                }
            });
    // 给新增按钮绑定点击事件
    $('#new').click(function(){
       window.location.href = '/o2o/shopadmin/awardoperation';
    });
    function changeItemStatus(id, enableStatus) {
        // 定义product json对象并添加productId以及状态(上架/下架)
        var award = {};
        award.awardId = id;
        award.enableStatus = enableStatus;
        $.confirm('确定么?', function() {
            // 上下架相关商品
            $.ajax({
                url : statusUrl,
                type : 'POST',
                data : {
                    productStr : JSON.stringify(award),
                    statusChange : true
                },
                dataType : 'json',
                success : function(data) {
                    if (data.success) {
                        $.toast('操作成功！');
                        getList();
                    } else {
                        $.toast('操作失败！');
                    }
                }
            });
        });
    }
});