package com.geek.huixiaoer.api;

import com.geek.huixiaoer.mvp.supermarket.ui.activity.CartEditResultBean;
import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.geek.huixiaoer.storage.entity.MessageBean;
import com.geek.huixiaoer.storage.entity.SingleResultBean;
import com.geek.huixiaoer.storage.entity.UserBean;
import com.geek.huixiaoer.storage.entity.housewifery.CreateServiceOrderBean;
import com.geek.huixiaoer.storage.entity.housewifery.HomeServiceBean;
import com.geek.huixiaoer.storage.entity.recycle.ArticleBean;
import com.geek.huixiaoer.storage.entity.shop.CartBean;
import com.geek.huixiaoer.storage.entity.shop.CategoryBean;
import com.geek.huixiaoer.storage.entity.shop.GoodsBean;
import com.geek.huixiaoer.storage.entity.shop.OrderBean;
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
     * banner轮播图
     *
     * @param positonId 轮播图分类（7:帮你忙轮播 8:折扣店轮播 9.招牌菜店铺轮播 10.环保轮播 11.分级轮播）
     */
    @GET(APIs.API.banner)
    Observable<BaseResponse<BaseArrayData<BannerBean>>> banner(@Query("positonId") int positonId);

    /**
     * 消息列表
     *
     * @param pageNumber 当前页数
     * @param pageSize   每页显示数量
     */
    @GET(APIs.API.messageList)
    Observable<BaseResponse<BaseArrayData<MessageBean>>> messageList(@Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize);

    /**
     * 环保热帖
     *
     * @param pageNumber 当前页数
     * @param pageSize   每页显示数量
     */
    @GET(APIs.API.hotspotList)
    Observable<BaseResponse<BaseArrayData<ArticleBean>>> hotspotList(@Query("pageNumber") int pageNumber,
                                                                     @Query("pageSize") int pageSize);

    /**
     * 折扣店和招牌菜爆款
     *
     * @param pageNumber 当前页数
     * @param pageSize   每页显示数量
     * @param tagId      分类（2:折扣店爆款 3:招牌菜爆款)
     */
    @GET(APIs.API.goods_explosion)
    Observable<BaseResponse<BaseArrayData<GoodsBean>>> goodsExplosion(@Query("pageNumber") int pageNumber,
                                                                      @Query("pageSize") int pageSize,
                                                                      @Query("tagId") int tagId);

    /**
     * 折扣店列表
     *
     * @param pageNumber 当前页数
     * @param pageSize   每页显示数量
     * @param startPrice 最小价格（可以不加）
     * @param endPrice   最高价格（可以不加）
     * @param orderType  排序方式（topDesc:置顶降序 priceAsc:价格升序 priceDesc:价格降序 salesDesc:销量降序 dateDesc:日期降序）
     */
    @GET(APIs.API.goods_list)
    Observable<BaseResponse<GoodsBean>> goodsList(@Query("pageNumber") int pageNumber,
                                                  @Query("pageSize") int pageSize,
                                                  @Query("startPrice") String startPrice,
                                                  @Query("endPrice") String endPrice,
                                                  @Query("orderType") String orderType);

    /**
     * 招牌列表
     *
     * @param pageNumber 当前页数
     * @param pageSize   每页显示数量
     * @param startPrice 最小价格（可以不加）
     * @param endPrice   最高价格（可以不加）
     * @param orderType  排序方式（topDesc:置顶降序 priceAsc:价格升序 priceDesc:价格降序 salesDesc:销量降序 dateDesc:日期降序）
     */
    @GET(APIs.API.dish_list)
    Observable<BaseResponse<BaseArrayData<GoodsBean>>> dishList(@Query("pageNumber") int pageNumber,
                                                                @Query("pageSize") int pageSize,
                                                                @Query("startPrice") String startPrice,
                                                                @Query("endPrice") String endPrice,
                                                                @Query("orderType") String orderType);


    /**
     * 家政服务
     * @param pageNumber）当前页数
     * @param pageSize        每页显示数量
     * @param startPrice      最小价格（可以不加
     * @param endPrice        最高价格（可以不加）
     * @param orderType       排序方式（topDesc:置顶降序 priceAsc:价格升序 priceDesc:价格降序 salesDesc:销量降序 dateDesc:日期降序）
     */
    @GET(APIs.API.service_list)
    Observable<BaseResponse<BaseArrayData<GoodsBean>>> serviceList(@Query("pageNumber") int pageNumber,
                                                                   @Query("pageSize") int pageSize,
                                                                   @Query("startPrice") String startPrice,
                                                                   @Query("endPrice") String endPrice,
                                                                   @Query("orderType") String orderType);

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
     * @param card        身份证号码
     * @param cityCode    镇（街道）Code
     * @param areaCode    村（社区）code
     * @param address     详细地址（小区、楼号、门牌号）
     * @param mobile      手机号码
     * @param enPassword  MD5加密密码
     * @param dynamicCode 短信验证码
     * @param volunteer   是否志愿者
     */
    @POST(APIs.API.register)
    Observable<BaseResponse<UserBean>> register(@Query("nickname") String nickname,
                                                @Query("card") String card,
                                                @Query("areaCode") String cityCode,
                                                @Query("streetCode") String areaCode,
                                                @Query("communityCode") String communityCode,
                                                @Query("address") String address,
                                                @Query("mobile") String mobile,
                                                @Query("enPassword") String enPassword,
                                                @Query("dynamicCode") String dynamicCode,
                                                @Query("isVolunteer") int volunteer);

    /**
     * 登录
     *
     * @param mobile      手机号码
     * @param md5Password MD5加密密码
     * @param memberType  用户类型(会员类型传空)
     */
    @POST(APIs.API.login)
    Observable<BaseResponse<UserBean>> login(@Query("memberType") String memberType,
                                             @Query("nickname") String mobile,
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
    @GET(APIs.API.shopOrderCheckout)
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
    @GET(APIs.API.shopOrderCalculate)
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
    Observable<BaseResponse<OrderCreateResultBean>> paymentSubmitSn(@Query("token") String token,
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
    @POST(APIs.API.shopOrderCreate)
    Observable<BaseResponse<OrderCreateResultBean>> orderCreate(@Query("token") String token,
                                                                @Query("receiverId") String receiverId,
                                                                @Query("code") String code,
                                                                @Query("invoiceTitle") String invoiceTitle,
                                                                @Query("useBalance") String useBalance,
                                                                @Query("memo") String memo);

    /**
     * ********************** 享 环 保 模 块 *************************
     * <p>
     * 论坛帖子列表//@param token 用户Token
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
     *
     * @param token    token
     * @param category 文章类型
     * @param content  内容
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
     *
     * @param pageNumber 当前页数
     * @param token      token
     */
    @GET(APIs.API.receiverList)
    Observable<BaseResponse<BaseArrayData<ReceiverBean>>> receiverList(@Query("pageNumber") int pageNumber,
                                                                       @Query("token") String token);

    /**
     * 保存新的收货地址
     *
     * @param token     token
     * @param consignee 收货人
     * @param areaName  地区名字
     * @param address   详细地址
     * @param zipCode   邮政编码
     * @param phone     手机号码
     * @param isDefault 是否默认
     * @param areaId    地区ID
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
     *
     * @param token     token
     * @param consignee 收货人
     * @param areaName  地区名字
     * @param address   详细地址
     * @param zipCode   邮政编码
     * @param phone     手机号码
     * @param isDefault 是否默认
     * @param areaId    地区ID
     * @param id        收货地址ID
     * @param oId       收货地址ID
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

    /**
     * 购物订单列表
     *
     * @param token      token
     * @param pageNumber 当前页数
     * @param pageSize   每页数量
     * @param status     订单状态
     * @param type       （默认：general）
     */
    @GET(APIs.API.shopOrderList)
    Observable<BaseResponse<BaseArrayData<OrderBean>>> shopOrderList(@Query("token") String token,
                                                                     @Query("pageNumber") int pageNumber,
                                                                     @Query("pageSize") int pageSize,
                                                                     @Query("status") String status,
                                                                     @Query("type") String type);

    /**
     * 购物订单（取消）
     *
     * @param token token
     * @param sn    订单SN号
     */
    @POST(APIs.API.shopOrderCancel)
    Observable<BaseResponse<OrderBean>> shopOrderCancel(@Query("token") String token,
                                                        @Query("sn") String sn);

    /**
     * 购物订单（用户确认收货）
     *
     * @param token token
     * @param sn    订单SN号
     */
    @POST(APIs.API.shopOrderReceive)
    Observable<BaseResponse<OrderBean>> shopOrderReceive(@Query("token") String token,
                                                         @Query("sn") String sn);
    /**
     * 游客创建家政订单
     * @param consignee
     * @param address
     * @param zipCode
     * @param mobile
     * @param goodsId
     * @param amount
     * @param memo
     * @return
     */
    @POST(APIs.API.createOrderByVisitor)
    Observable<BaseResponse<CreateServiceOrderBean>> createServiceOrder (@Query("consignee")String consignee, @Query("address")String address,
                                                                         @Query("zipCode")String zipCode, @Query("mobile")String mobile, @Query("goodsId")String goodsId, @Query("amount")String amount, @Query("memo")String memo);
    /**
     * 游客创建商家订单
     * @param consignee
     * @param address
     * @param zipCode
     * @param mobile
     * @param goodsId
     * @param amount
     * @param memo
     * @return
     */
    @POST(APIs.API.createOrderByVisitor)
    Observable<BaseResponse<CreateServiceOrderBean>> createShopOrder (@Query("consignee")String consignee, @Query("address")String address,
                                                                         @Query("zipCode")String zipCode, @Query("mobile")String mobile, @Query("goodsId")String goodsId, @Query("amount")String amount, @Query("memo")String memo);
}