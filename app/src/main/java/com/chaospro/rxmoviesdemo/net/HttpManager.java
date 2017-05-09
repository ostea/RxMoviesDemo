package com.chaospro.rxmoviesdemo.net;

import android.nfc.Tag;
import android.util.Log;

import com.chaospro.rxmoviesdemo.model.AndroidModel;
import com.chaospro.rxmoviesdemo.model.GankModel;
import com.chaospro.rxmoviesdemo.model.HttpResult;
import com.chaospro.rxmoviesdemo.model.IOSModel;
import com.chaospro.rxmoviesdemo.net.api.APIService;
import com.chaospro.rxmoviesdemo.net.base.ResponseConvertFactory;
import com.chaospro.rxmoviesdemo.utils.Constant;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by chaosboy on 2017/5/6.
 */

public class HttpManager {

    public static final String TAG = "HttpManager";

    private Retrofit mRetrofit;
    private APIService mAPIService;

    private static volatile HttpManager mInstance;

    public static HttpManager getInstance() {
        if (mInstance == null) {
            synchronized (HttpManager.class) {
                if (mInstance == null) {
                    mInstance = new HttpManager();
                }
            }
        }
        return mInstance;
    }

    private HttpManager() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        mRetrofit = new Retrofit.Builder()
                .client(clientBuilder.build())
                .addConverterFactory(ResponseConvertFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.GANK_HOST)
                .build();

        mAPIService = mRetrofit.create(APIService.class);
    }

    private <T> void toSubscribe(Observable<T> o, Consumer<T> c) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(c, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "onError", throwable);
                    }
                });
    }

/********************************************************************************************************************************/
/**                                                         gank Io api                                                                         */
/********************************************************************************************************************************/


    /**
     * get android data
     *
     * @param consumer
     * @param pageNum
     */
    public void getAndroidDataResult(Consumer<List<AndroidModel>> consumer, int pageNum) {
        toSubscribe(mAPIService.getAndroidDataResult(Constant.DEFAULT_PAGE_SIZE, pageNum).map(new Function<HttpResult<List<AndroidModel>>, List<AndroidModel>>() {

            @Override
            public List<AndroidModel> apply(HttpResult<List<AndroidModel>> listAndroidResult) throws Exception {
                if (listAndroidResult != null & listAndroidResult.getResults().size() > 0) {
                    return listAndroidResult.getResults();
                }
                return null;
            }
        }), consumer);
    }

    /**
     * get ios data
     *
     * @param consumer
     * @param pageNum
     */
    public void getIOSDataResult(Consumer<List<IOSModel>> consumer, int pageNum) {
        toSubscribe(mAPIService.getIOSDataResult(Constant.DEFAULT_PAGE_SIZE, pageNum).map(new Function<HttpResult<List<IOSModel>>, List<IOSModel>>() {
            @Override
            public List<IOSModel> apply(HttpResult<List<IOSModel>> listHttpResult) throws Exception {
                if (listHttpResult != null && listHttpResult.getResults().size() > 0) {
                    return listHttpResult.getResults();
                }
                return null;
            }
        }), consumer);

    }

    /**
     * get pic data
     *
     * @param pageNum
     * @param consumer
     */
    public void getGankDataResult(Consumer<List<GankModel>> consumer, int pageNum) {

        toSubscribe(mAPIService.getGankDataResult(Constant.DEFAULT_PAGE_SIZE, pageNum).map(new Function<HttpResult<List<GankModel>>, List<GankModel>>() {
            @Override
            public List<GankModel> apply(HttpResult<List<GankModel>> listHttpResult) throws Exception {
                if (listHttpResult != null && listHttpResult.getResults().size() > 0) {
                    return listHttpResult.getResults();
                }
                return null;
            }
        }), consumer);
    }


}
