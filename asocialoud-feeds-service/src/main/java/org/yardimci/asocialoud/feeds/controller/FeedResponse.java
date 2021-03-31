package org.yardimci.asocialoud.feeds.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FeedResponse {

    @JsonProperty("status")
    private String status;
    @JsonProperty("data")
    private Object data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
