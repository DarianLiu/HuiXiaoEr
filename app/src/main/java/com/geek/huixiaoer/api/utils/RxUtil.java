package com.geek.huixiaoer.api.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.geek.huixiaoer.api.exception.ApiException;
import com.geek.huixiaoer.common.utils.Constants;
import com.geek.huixiaoer.mvp.common.ui.activity.LoginActivity;
import com.geek.huixiaoer.storage.BaseResponse;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.rong.imkit.RongIM;

/**
 * ================================================
 * 放置便于使用 RxJava 的一些工具类
 * <p>
 * Created by JessYan on 11/10/2016 16:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class RxUtil {

    private RxUtil() {
    }

    /**
     * 配置线程调度
     *
     * @param <T> 泛型
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> applySchedulers(final IView view) {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> observable) {
                //隐藏进度条
                return observable.subscribeOn(Schedulers.io())
                        .doOnSubscribe(disposable -> {
                            view.showLoading();//显示进度条
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(view::hideLoading)
                        .compose(RxLifecycleUtils.bindToLifecycle(view));
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T> 泛型
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<BaseResponse<T>, T> handleBaseResult(Context context) {
        return observable -> observable.flatMap(new Function<BaseResponse<T>, ObservableSource<T>>() {
            @Override
            public ObservableSource<T> apply(@NonNull BaseResponse<T> tBaseResponse) throws Exception {
                if (TextUtils.equals(tBaseResponse.getResult(), "1")) {
                    return createData(tBaseResponse.getData());
                } else {
                    if (TextUtils.equals(tBaseResponse.getResult(), "2")) {
                        RongIM.getInstance().logout();
                        DataHelper.removeSF(context, Constants.SP_TOKEN);
                        DataHelper.removeSF(context, Constants.SP_USER_INFO);
                        context.startActivity(new Intent(context, LoginActivity.class));
                    }
                    return Observable.error(new ApiException(tBaseResponse.getMsg(), tBaseResponse.getResult()));
                }
            }
        });
    }

    /**
     * 统一返回结果处理
     *
     * @param <T> 泛型
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<BaseResponse<T>, T> handleBaseResultShowMessage(Context context) {
        return observable -> observable.flatMap(new Function<BaseResponse<T>, ObservableSource<T>>() {
            @Override
            public ObservableSource<T> apply(@NonNull BaseResponse<T> tBaseResponse) throws Exception {
                if (TextUtils.equals(tBaseResponse.getResult(), "1")) {
                    ArmsUtils.snackbarTextWithLong(tBaseResponse.getMsg());
                    return createData(tBaseResponse.getData());
                } else {
                    if (TextUtils.equals(tBaseResponse.getResult(), "2")) {
                        DataHelper.removeSF(context, Constants.SP_TOKEN);
                        DataHelper.removeSF(context, Constants.SP_USER_INFO);
//                        ArmsUtils.killAll();
                        context.startActivity(new Intent(context, LoginActivity.class));
                    }
                    return Observable.error(new ApiException(tBaseResponse.getMsg(), tBaseResponse.getResult()));
                }
            }
        });
    }

    private static <T> Observable<T> createData(final T t) {
        return Observable.create(emitter -> {
            try {
                emitter.onNext(t);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }
}
