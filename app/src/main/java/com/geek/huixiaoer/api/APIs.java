package com.geek.huixiaoer.api;

/**
 * 服务器接口配置
 * Created by Administrator on 2018/2/1.
 */
public interface APIs {

    /**
     * 测试环境与生产环境切换
     **/
    boolean RELEASE_VERSION = false;

    /**
     * 服务器IP地址
     */
    String IP = RELEASE_VERSION ? "www.yanfumall.com" : "182.254.223.66";

    /**
     * 端口号
     */
    String PORT = RELEASE_VERSION ? "" : "";

//    /**
//     * 版本号
//     */
    //String VERSION_CODE = RELEASE_VERSION ? "" : "";

    /**
     * 项目名
     */
    String PROJECT = RELEASE_VERSION ? "" : "shopxx";

    /**
     * 客户端类型（区分APP跟网页端）
     */
    String CLIENT_TYPE = "mobile";

    /**
     * 接口基础URL
     */
    String BASE_URL = "http://" + IP + PORT + "/" + PROJECT + "/" + CLIENT_TYPE + "/";

    /**
     * 商品详情地址
     */
    String GOODS_URL = "http://" + IP + PORT + "/" + PROJECT + "/" + "goods/mobile/";

    /**
     * 文章分享地址
     */
    String ARTICLE_SHARE_URL = "http://" + IP + PORT + "/" + PROJECT + "/html/article/articleShare.html?articleId=";

    /**
     * 文章详情地址
     */
    String ARTICLE_DETAIL_URL = "http://" + IP + PORT + "/" + PROJECT + "/html/article/articleDetail.html?articleId=";

    /**
     * 服务器请求接口
     */
    interface API {
        /**
         * 登录
         */
        String login = "login/submit.jhtml";

        /**
         * 注册
         */
        String register = "register/submit.jhtml";

        /**
         * 获取短信验证码
         */
        String verification_code = "common/verification_code.jhtml";

        /**
         * 检查短信验证码
         */
        String check_code = "common/check_code.jhtml";

        /**
         * 文章轮播图列表
         */
        String carouselList = "member/article/carouselList.jhtml";

        /**
         * 获取所有根节点的货品类别列表
         */
        String goodsCategoryRoot = "goods/productCategoryRoot.jhtml";

        /**
         * 获取指定类别的货品列表
         */
        String goodsList = "goods/list/";

    }
}
