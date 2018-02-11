package com.geek.huixiaoer.api;

import com.geek.huixiaoer.storage.BaseArrayData;
import com.geek.huixiaoer.storage.BaseResponse;
import com.geek.huixiaoer.storage.entity.BannerBean;
import com.geek.huixiaoer.storage.entity.CaptchaBean;
import com.geek.huixiaoer.storage.entity.CategoryBean;
import com.geek.huixiaoer.storage.entity.GoodsBean;
import com.geek.huixiaoer.storage.entity.UserBean;

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
    Observable<BaseResponse<CaptchaBean>> verificationCode(@Query("mobile") String mobile,
                                                           @Query("type") int type);

    /**
     * 检查短信验证码
     *
     * @param code 短信验证码
     */
    @GET(APIs.API.check_code)
    Observable<BaseResponse<CaptchaBean>> checkCode(@Query("code") String code);

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
     */
    @GET(APIs.API.goodsList + "{category_id}" + ".jhtml")
    Observable<BaseResponse<BaseArrayData<GoodsBean>>> goodsList(@Path("category_id") int category_id,
                                                                 @Query("pageNumber") int pageNum);

}