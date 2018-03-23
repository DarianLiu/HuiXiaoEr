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
    String IP = RELEASE_VERSION ? "www.yanfumall.com" : "182.254.223.66"; //192.168.1.42

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

        /*--------商城模块---------*/
        /**
         * 获取所有根节点的货品类别列表
         */
        String goodsCategoryRoot = "goods/productCategoryRoot.jhtml";

        /**
         * 获取指定类别的货品列表
         */
        String goodsList = "goods/list/";

        /**
         * 商品规格检索
         */
        String goodsSpecification = "goods/specification/";


        /**
         * 查看商品是否收藏
         */
        String goodsHasFavorite = "member/favorite/app/hasFavorite.jhtml";

        /**
         * 商品添加收藏
         */
        String goodsFavoriteAdd = "member/favorite/app/add.jhtml";

        /**
         * 商品删除收藏
         */
        String goodsFavoriteDelete = "member/favorite/app/delete.jhtml";

        /**
         * 添加购物车
         */
        String cartAdd = "cart/add.jhtml";

        /**
         * 购物车列表
         */
        String cartList = "cart/app/list.jhtml";

        /**
         * 编辑购物车项
         */
        String cartEdit = "cart/edit.jhtml";

        /**
         * 删除购物车项
         */
        String cartDelete = "cart/delete.jhtml";

        /**
         * 清空购物车项
         */
        String cartClear = "cart/clear.jhtml";

        /**
         * 获取当前会员的默认收货地址
         */
        String receiverDefault = "member/receiver/defaultReceiver.jhtml";

        /**
         * 提交普通订单需要获取的数据
         */
        String orderCheckout = "shop/order/app/checkout.jhtml";

        /**
         * 订单金额计算接口
         */
        String orderCalculate = "shop/order/calculate.jhtml";

        /**
         * 支付宝支付方式的接口(购物车入口)
         */
        String paymentSubmitNo = "payment/plugin_submit_no.jhtml";

        /**
         * 支付宝支付方式的接口(订单列表入口)
         */
        String paymentSubmitSn = "payment/plugin_submit_sn.jhtml";

        /**
         * 生成订单
         */
        String orderCreate = "shop/order/create.jhtml";



        /*--------垃圾回收模块---------*/
        /**
         * 垃圾回收列表
         */
        String articleList = "member/article/list.jhtml";

        /**
         * 垃圾回收列表
         */
        String articleAdd = "member/article/appArticle.jhtml";

        /**
         * 评论添加
         */
        String addReviewLike = "member/article/addReview.jhtml";

        /*-----------家政模块--------*/
        /**
         * 家政服务列表
         */
        String homeServiceList = "homeservice/listAll.jhtml";

    }
}
