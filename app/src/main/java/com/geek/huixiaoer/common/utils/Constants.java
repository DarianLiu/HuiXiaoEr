package com.geek.huixiaoer.common.utils;

import android.os.Environment;

/**
 * 常用常量
 * Created by LiuLi on 2017/11/30.
 */

public class Constants {


    /**
     * 文件保存地址
     **/
    public static final String BASE_PATH = Environment.getExternalStorageDirectory() + "/vetor";

    public static final String PATH_IMAGE = BASE_PATH + "/identity/";
    public static final String PATH_IMAGE_CROP = BASE_PATH + "/identity/crop/";

    public static final String IMAGE_IDENTITY_NAME = "identity.png";
    public static final String IMAGE_IDENTITY_CROP_NAME = "crop.png";

    //获取短信验证码时间间隔
    public static final int SMS_MAX_TIME = 60;

    /**
     * ================================================
     * SharePreference Key
     * ================================================
     */
    public static final String SP_USER_INFO = "user_info";

    public static final String SP_TOKEN = "token";

//    public static final String SP_MCH_KEY = "mch_key";

    public static final String SP_LANGUAGE = "language";

    public static final String SP_COUNTRY_ID = "country_id";

    public static final String SP_FILE_NAME = "occ_config";


    /**
     * ================================================
     * Intent Key
     * ================================================
     */
    public static final String INTENT_DIALOG_TITLE = "dialog_title";
    public static final String INTENT_DIALOG_MESSAGE = "dialog_message";
    public static final String INTENT_DIALOG_INPUT_TYPE = "dialog_InputType";
    public static final String INTENT_DIALOG_POSITIVE_TEXT = "dialog_positive_text";
    public static final String INTENT_DIALOG_NEGATIVE_TEXT = "dialog_negative_text";

    public static final String INTENT_TYPE = "type";
    public static final String INTENT_MOBILE = "mobile";
    public static final String INTENT_GOODS_NAME = "goods_name";
    public static final String INTENT_GOODS_SN = "goods_sn";
    public static final String INTENT_GOODS_URL = "goods_url";
    public static final String INTENT_CART_LIST = "cart_list";


    /**
     * ================================================
     * RequestCode
     * ================================================
     */
    public static final int REQUEST_CODE_ALIPAY = 101;


    /**
     * ================================================
     * Cash Key
     * ================================================
     */
    public static final String CASH_COUNTRY = "country";

    public static final String CASH_IDENTITY_TYPE = "identity_type";
}
