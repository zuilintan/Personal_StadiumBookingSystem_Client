package com.lt.personal_stadiumbookingsystem.constant;

public class UrlConstant {
    //private static final String URL_SBS_HEAD = "http://10.10.20.245:8080/sbs";//宿舍
    //private static final String URL_SBS_HEAD = "http://10.80.6.133:8080/sbs";//公司
    //private static final String URL_SBS_HEAD = "http://192.168.43.109:8080/sbs";//热点
    //private static final String URL_SBS_HEAD = "http://192.168.0.135:8080/sbs";//学校寝室1
    //private static final String URL_SBS_HEAD = "http://192.168.3.89:8080/sbs";//学校寝室2
    private static final String URL_SBS_HEAD = "http://45.77.176.25:8080/sbs";//服务器
    public static final String URL_ACCOUNT_REGISTER = URL_SBS_HEAD + "/account?action=register";
    public static final String URL_ACCOUNT_LOGIN = URL_SBS_HEAD + "/account?action=login";
    public static final String URL_ACCOUNT_EDIT = URL_SBS_HEAD + "/account?action=edit";
    public static final String URL_ACCOUNT_SHOW = URL_SBS_HEAD + "/account?action=show";
    public static final String URL_GYM_SHOWALL = URL_SBS_HEAD + "/gym?action=showall";
    public static final String URL_SITE_SHOWLIST = URL_SBS_HEAD + "/site?action=showlist";
    public static final String URL_TIME_SHOWLIST = URL_SBS_HEAD + "/time?action=showlist";
    public static final String URL_ORDER_BOOK = URL_SBS_HEAD + "/order?action=book";
    public static final String URL_ORDER_PAY = URL_SBS_HEAD + "/order?action=pay";
    public static final String URL_ORDER_CANCEL = URL_SBS_HEAD + "/order?action=cancel";
    public static final String URL_ORDER_SHOWLIST = URL_SBS_HEAD + "/order?action=showlist";

    public static final String URL_BING_HEAD = "https://api.ooopn.com/image/bing/api.php";//必应每日一图
    public static final String URL_BG_EVERYDAY = URL_BING_HEAD + "?type=jump";//每次获取的图片相同
    public static final String URL_BG_RANDOM = URL_BING_HEAD + "?v=2&type=jump";//每次获取的图片不同
}
