package com.xiaoe.app_base;

import com.billy.cc.core.component.IParamJsonConverter;
import com.google.gson.Gson;

/**
 * 用Gson来进行跨app调用时的json转换
 */
public class GsonParamConverter implements IParamJsonConverter {
    Gson gson = new Gson();

    @Override
    public <T> T json2Object(String input, Class<T> clazz) {
        return gson.fromJson(input, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        return gson.toJson(instance);
    }
}
