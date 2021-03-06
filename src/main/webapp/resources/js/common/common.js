/**
 * 格式化日期
 */
Date.prototype.Format = function(fmt) {
    var o = {
        "M+" : this.getMonth() + 1, // 月份
        "d+" : this.getDate(), // 日
        "h+" : this.getHours(), // 小时
        "m+" : this.getMinutes(), // 分
        "s+" : this.getSeconds(), // 秒
        "q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
        "S" : this.getMilliseconds()
        // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for ( var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
                : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
/**
 * 验证码操作
 */
function changeVerifyCode(img) {
    img.src = "../Kaptcha?" + Math.floor(Math.random() * 100);
}
/**
 * 匹配传递过来的参数
 */
function getQueryString(name) {
    // 正则表达式
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    // 匹配url
    var r = window.location.search.substr(1).match(reg);
    // 符合参数名，则取值
    if (r != null) {
        return decodeURIComponent(r[2]);
    }
    return '';
}

/**
 * 获取项目的contextpath，修正图片路由让其正确显示
 */
function getContextPath(){
    return "/o2o/";
}