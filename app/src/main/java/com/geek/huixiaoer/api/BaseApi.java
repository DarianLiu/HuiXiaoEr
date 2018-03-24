package com.geek.huixiaoer.api;

import com.geek.huixiaoer.mvp.supermarket.ui.activity.CartEditResultBean;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.geek.huixiaoer.storage.entity.SingleResultBean;
import com.geek.huixiaoer.storage.entity.UserBean;
import com.geek.huixiaoer.storage.entity.housewifery.HomeServiceBean;
import com.geek.huixiaoer.storage.entity.recycle.ArticleBean;
import com.geek.huixiaoer.storage.entity.shop.CartBean;
import com.geek.huixiaoer.storage.entity.shop.CategoryBean;
import com.geek.huixiaoer.storage.entity.shop.GoodsBean;
import com.geek.huixiaoer.storage.entity.shop.OrderCalculateResultBean;
import com.geek.huixiaoer.storage.entity.shop.OrderCheckResultBean;
import com.geek.huixiaoer.storage.entity.shop.OrderCreateResultBean;
import com.geek.huixiaoer.storage.entity.shop.ReceiverBean;
import com.geek.huixiaoer.storage.entity.shop.SpecificationBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApi {

    /**
     * ********************** 公 共（ 登 录 注 册 ）模 块 *************************
     * <p>
     * 获取短信验证码
     *
     * @param mobile 手机号码
     * @param type   0：注册/1：重置密码
     */
    @GET(APIs.API.verification_code)
    Observable<BaseResponse<SingleResultBean>> verificationCode(@Query("mobile") String mobile,
                                                                @Query("type") int type);

    /**
     * 检查短信验证码
     *
     * @param code 短信验证码
     */
    @GET(APIs.API.check_code)
    Observable<BaseResponse<SingleResultBean>> checkCode(@Query("code") String code);

    /**
     * 注册
     *
     * @param nickname    昵称
     * @param mobile      手机号码
     * @param md5Password MD5加密密码
     * @param refererCode 邀请码（非必填项）
     */
    @POST(APIs.API.register)
    Observable<BaseResponse<UserBean>> register(@Query("nickname") String nickname,
                                                @Query("mobile") String mobile,
                                                @Query("enPassword") String md5Password,
                                                @Query("refererCode") String refererCode);

    /**
     * 登录
     *
     * @param mobile      手机号码
     * @param md5Password MD5加密密码
     */
    @POST(APIs.API.login)
    Observable<BaseResponse<UserBean>> login(@Query("mobile") String mobile,
                                             @Query("enPassword") String md5Password);

    /**
     * 文章轮播图列表
     */
    @GET(APIs.API.carouselList)
    Observable<BaseResponse<BaseArrayData<BannerBean>>> articleBanner();


    /**
     * ********************** 商 超 模 块 *************************
     * <p>
     * 获取所有根节点的货品类别列表
     */
    @GET(APIs.API.goodsCategoryRoot)
    Observable<BaseResponse<BaseArrayData<CategoryBean>>> goodsCategoryRoot();

    /**
     * 获取所有根节点的货品类别列表
     *
     * @param category_id 分类ID
     * @param pageNum     页数
     */
    @GET(APIs.API.goodsList + "{category_id}" + ".jhtml")
    Observable<BaseResponse<BaseArrayData<GoodsBean>>> goodsList(@Path("category_id") int category_id,
                                                                 @Query("pageNumber") int pageNum);

    /**
     * 商品规格检索
     *
     * @param goods_sn 商品SN号
     */
    @GET(APIs.API.goodsSpecification + "{goods_sn}" + ".jhtml")
    Observable<BaseResponse<BaseArrayData<SpecificationBean>>> goodsSpecification(@Path("goods_sn") String goods_sn);

    /**
     * 查看商品是否收藏
     *
     * @param token    用户Token
     * @param goods_sn 商品SN号
     */
    @GET(APIs.API.goodsHasFavorite)
    Observable<BaseResponse<SingleResultBean>> goodsHasFavorite(@Query("token") String token,
                                                                @Query("sn") String goods_sn);

    /**
     * 商品添加收藏
     *
     * @param token    用户Token
     * @param goods_sn 商品SN号
     */
    @POST(APIs.API.goodsFavoriteAdd)
    Observable<BaseResponse<SingleResultBean>> goodsFavoriteAdd(@Query("token") String token,
                                                                @Query("sn") String goods_sn);

    /**
     * 商品删除收藏
     *
     * @param token    用户Token
     * @param goods_sn 商品SN号
     */
    @POST(APIs.API.goodsFavoriteDelete)
    Observable<BaseResponse<SingleResultBean>> goodsFavoriteDelete(@Query("token") String token,
                                                                   @Query("sn") String goods_sn);

    /**
     * 添加购物车
     *
     * @param token     用户Token
     * @param productId 产品ID
     * @param quantity  数量
     */
    @POST(APIs.API.cartAdd)
    Observable<BaseResponse<GoodsBean>> cartAdd(@Query("token") String token,
                                                @Query("productId") String productId,
                                                @Query("quantity") int quantity);

    /**
     * 购物车列表
     *
     * @param token 用户Token
     */
    @GET(APIs.API.cartList)
    Observable<BaseResponse<CartBean>> cartList(@Query("token") String token);

    /**
     * 编辑购物车项
     *
     * @param token    用户Token
     * @param id       购物车项ID
     * @param quantity 数量
     */
    @POST(APIs.API.cartEdit)
    Observable<BaseResponse<CartEditResultBean>> cartEdit(@Query("token") String token,
                                                          @Query("id") String id,
                                                          @Query("quantity") int quantity);

    /**
     * 删除购物车项
     *
     * @param token 用户Token
     * @param id    购物车项ID
     */
    @POST(APIs.API.cartDelete)
    Observable<BaseResponse<CartEditResultBean>> cartDelete(@Query("token") String token,
                                                            @Query("id") String id);

    /**
     * 清空购物车
     *
     * @param token 用户Token
     */
    @POST(APIs.API.cartClear)
    Observable<BaseResponse<CartEditResultBean>> cartClear(@Query("token") String token);

    /**
     * 获取默认收获地址
     *
     * @param token 用户Token
     */
    @GET(APIs.API.receiverDefault)
    Observable<BaseResponse<CartEditResultBean>> receiverDefault(@Query("token") String token);

    /**
     * 获取普通订单的详细数据
     *
     * @param token 用户Token
     */
    @GET(APIs.API.orderCheckout)
    Observable<BaseResponse<OrderCheckResultBean>> orderCheckout(@Query("token") String token);

    /**
     * 订单金额计算接口（使用优惠券/发票）
     *
     * @param token            用户Token
     * @param receiverId       收货地址ID
     * @param paymentMethodId  支付方式ID（默认为“1”）
     * @param shippingMethodId 快递方式ID（默认为“1”）
     * @param code             优惠码
     * @param invoiceTitle     发票title
     * @param useBalance       使用余额(0：不使用/1：使用)
     * @param memo             附言
     */
    @GET(APIs.API.orderCalculate)
    Observable<BaseResponse<OrderCalculateResultBean>> orderCalculate(@Query("token") String token,
                                                                      @Query("receiverId") String receiverId,
                                                                      @Query("paymentMethodId") String paymentMethodId,
                                                                      @Query("shippingMethodId") String shippingMethodId,
                                                                      @Query("code") String code,
                                                                      @Query("invoiceTitle") String invoiceTitle,
                                                                      @Query("useBalance") String useBalance,
                                                                      @Query("memo") String memo);

    /**
     * 支付宝支付方式的接口(购物车入口)
     *
     * @param token           用户Token
     * @param paymentPluginId 支付方式(移动端默认为：alipayMobilePaymentPlugin)
     * @param outTradeNo      交易流水号
     * @param amount          交易金额
     */
    @POST(APIs.API.paymentSubmitNo)
    Observable<BaseResponse<OrderCreateResultBean>> paymentSubmitNo(@Query("token") String token,
                                                                    @Query("paymentPluginId") String paymentPluginId,
                                                                    @Query("outTradeNo") String outTradeNo,
                                                                    @Query("amount") String amount);

    /**
     * 支付宝支付方式的接口(订单列表入口)
     *
     * @param token           用户Token
     * @param paymentPluginId 支付方式(移动端默认为：alipayMobilePaymentPlugin)
     * @param order_sn        订单SN号
     * @param amount          订单金额
     */
    @POST(APIs.API.paymentSubmitSn)
    Observable<BaseResponse<CartEditResultBean>> paymentSubmitSn(@Query("token") String token,
                                                                 @Query("paymentPluginId") String paymentPluginId,
                                                                 @Query("sn") String order_sn,
                                                                 @Query("amount") String amount);

    /**
     * 生成订单
     *
     * @param token        用户Token
     * @param receiverId   收货地址ID
     * @param code         优惠码
     * @param invoiceTitle 发票title
     * @param useBalance   使用余额(0：不使用/1：使用)
     * @param memo         附言
     */
    @POST(APIs.API.orderCreate)
    Observable<BaseResponse<OrderCreateResultBean>> orderCreate(@Query("token") String token,
                                                                @Query("receiverId") String receiverId,
                                                                @Query("code") String code,
                                                                @Query("invoiceTitle") String invoiceTitle,
                                                                @Query("useBalance") String useBalance,
                                                                @Query("memo") String memo);

    /**
     * ********************** 垃 圾 回 收 模 块 *************************
     * <p>
     * 垃圾回收列表//@param token 用户Token
     *
     * @param pageNumber 页数
     * @param pageSize   每页数量
     * @param type       排序类型（createDate）
     * @param category   文章类型（mood）
     */
    @GET(APIs.API.articleList)
    Observable<BaseResponse<BaseArrayData<ArticleBean>>> articleList(@Query("pageNumber") int pageNumber,
                                                                     @Query("pageSize") int pageSize,
                                                                     @Query("type") String type,
                                                                     @Query("category") String category);

    /**
     * 垃圾回收添加
     */
    @FormUrlEncoded
    @POST(APIs.API.articleAdd)
    Observable<BaseResponse<ArticleBean>> articleAdd(@Field("token") String token,
                                                     @Field("category") String category,
                                                     @Field("content") String content);

    /**
     * 文章添加评论
     *
     * @param token     用户Token
     * @param articleId 文章ID
     * @param content   评论内容
     */
    @POST(APIs.API.addReviewLike)
    Observable<BaseResponse<GoodsBean>> addReviewLike(@Query("token") String token,
                                                      @Query("articleId") String articleId,
                                                      @Query("content") String content);

    /**
     * ********************** 家 政 模 块 *************************
     * 家政服务项目列表
     */
    @GET(APIs.API.homeServiceList)
    Observable<BaseResponse<BaseArrayData<HomeServiceBean>>> homeServiceList();

    /**
     * ********************** 用 户 模 块 *************************
     * 收货地址列表
     */
    @GET(APIs.API.receiverList)
    Observable<BaseResponse<BaseArrayData<ReceiverBean>>> receiverList(@Query("pageNumber") int pageNumber,
                                                                       @Query("token") String token);

    /**
     * 保存新的收货地址
     */
    @POST(APIs.API.receiverSave)
    Observable<BaseResponse<ReceiverBean>> receiverSave(@Query("token") String token,
                                                        @Query("consignee") String consignee,
                                                        @Query("areaName") String areaName,
                                                        @Query("address") String address,
                                                        @Query("zipCode") String zipCode,
                                                        @Query("phone") String phone,
                                                        @Query("isDefault") boolean isDefault,
                                                        @Query("areaId") String areaId);

    /**
     * 更新收货地址
     */
    @POST(APIs.API.receiverUpdate)
    Observable<BaseResponse<ReceiverBean>> receiverUpdate(@Query("token") String token,
                                                          @Query("consignee") String consignee,
                                                          @Query("areaName") String areaName,
                                                          @Query("address") String address,
                                                          @Query("zipCode") String zipCode,
                                                          @Query("phone") String phone,
                                                          @Query("isDefault") boolean isDefault,
                                                          @Query("areaId") String areaId,
                                                          @Query("id") String id,
                                                          @Query("oId") String oId);

    /**
     * 删除收货地址
     */
    @POST(APIs.API.receiverDelete)
    Observable<BaseResponse<ReceiverBean>> receiverDelete(@Query("token") String token,
                                                          @Query("id") String id);


}