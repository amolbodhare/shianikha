package com.nikha.shianikha.commen;

import com.adoisstudio.helper.Json;
import com.nikha.App;

import org.json.JSONObject;

public class RequestModel extends Json {

    private int version = 1;

    public RequestModel(String api) {
        addString(P.api, api);
        addString(P.version, "1.0");
        addString(P.device, App.device_id);
        addJSON(P.data, new JSONObject());
    }

    public static RequestModel newRequestModel(String api) {
        return new RequestModel(api);
    }

}//class