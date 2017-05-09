package com.chaospro.rxmoviesdemo.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by chaosboy on 2017/5/8.
 */

public class HttpResult<T> implements Serializable{

    @SerializedName("error")
    private boolean error;

    @SerializedName("results")
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }


}
