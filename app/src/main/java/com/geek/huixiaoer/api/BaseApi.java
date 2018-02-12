package com.geek.huixiaoer.api;

import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.geek.huixiaoer.storage.entity.SingleResultBean;
import com.geek.huixiaoer.storage.entity.CategoryBean;
import com.geek.huixiaoer.storage.entity.GoodsBean;
import com.geek.huixiaoer.storage.entity.UserBean;
import com.geek.huixiaoer.storage.entity.article.ArticleBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApi {

    /**
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
     * @param token    用户Token
     * @param goods_sn 商品SN号
     */
    @POST(APIs.API.cartAdd)
    Observable<BaseResponse<GoodsBean>> cartAdd(@Query("token") String token,
                                                @Query("sn") String goods_sn,
                                                @Query("quantity") int quantity);

    /**
     * 购物车列表
     *
     * @param token 用户Token
     */
    @GET(APIs.API.cartList)
    Observable<BaseResponse<BaseArrayData<GoodsBean>>> cartList(@Query("token") String token);

    /**
     * 编辑购物车项
     *
     * @param token    用户Token
     * @param id       购物车项ID
     * @param quantity 数量
     */
    @POST(APIs.API.cartEdit)
    Observable<BaseResponse<GoodsBean>> cartEdit(@Query("token") String token, @Query("id") String id,
                                                 @Query("quantity") int quantity);

    /**
     * 删除购物车项
     *
     * @param token 用户Token
     * @param id    购物车项ID
     */
    @POST(APIs.API.cartDelete)
    Observable<BaseResponse<GoodsBean>> cartDelete(@Query("token") String token, @Query("id") String id);

    /**
     * 清空购物车项
     *
     * @param token 用户Token
     */
    @POST(APIs.API.cartClear)
    Observable<BaseResponse<GoodsBean>> cartClear(@Query("token") String token);

    /**
     * 文章列表//@param token 用户Token
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
}