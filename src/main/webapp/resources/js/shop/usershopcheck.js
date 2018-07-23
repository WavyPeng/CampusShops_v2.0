/**
 * Created by WavyPeng on 2018/7/23.
 */
$(function(){
    var userName = '';
    getList();
    function getList(){
       // 获取该店铺用户积分的URL
       var listUrl = '/o2o/shopadmin/listusershopmapsbyshop?pageIndex=1&pageSize=9999&userName='+userName;
       // 访问后台，获取该店铺的购买信息列表
       $.getJSON(listUrl,function(data){
           if(data.success){
               var userShopMapList = data.userShopMapList;
               var tempHtml = '';
               // 遍历积分信息列表，拼接出列信息
               userShopMapList.map(function(item,index){
                   tempHtml+=''+'<div class="row row-usershopcheck">'
                       +'<div class="col-50">'+item.user.name+'</div>'
                       +'<div class="col-50">'+item.point+'</div>'
                       +'</div>';
               });
               $('.usershopcheck-wrap').html(tempHtml);
           }
       });
    }
    // 搜索绑定，获取并按照用户名模糊查询
    $('#search').on('change',function(e){
        // 当在搜索框内输入信息时，依据输入的商品名称模糊查询该商品的购买记录
        userName = e.target.value;
        // 清空商品购买记录列表
        $('.usershopcheck-wrap').empty();
        // 再次加载
        getList();
    });
});