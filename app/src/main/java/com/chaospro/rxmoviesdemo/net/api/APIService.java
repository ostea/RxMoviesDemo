package com.chaospro.rxmoviesdemo.net.api;

import com.chaospro.rxmoviesdemo.model.AndroidModel;
import com.chaospro.rxmoviesdemo.model.GankModel;
import com.chaospro.rxmoviesdemo.model.HttpResult;
import com.chaospro.rxmoviesdemo.model.IOSModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by chaosboy on 2017/5/6.
 */

public interface APIService {


    @GET("Android/{pageSize}/{pageNum}")
    Observable<HttpResult<List<AndroidModel>>> getAndroidDataResult(@Path("pageSize") int pageSize, @Path("pageNum") int pageNum);

    @GET("iOS/{pageSize}/{pageNum}")
    Observable<HttpResult<List<IOSModel>>> getIOSDataResult(@Path("pageSize") int pageSize, @Path("pageNum") int pageNum);

    @GET("%E7%A6%8F%E5%88%A9/{pageSize}/{pageNum}")
    Observable<HttpResult<List<GankModel>>> getGankDataResult(@Path("pageSize") int pageSize, @Path("pageNum") int pageNum);
}
