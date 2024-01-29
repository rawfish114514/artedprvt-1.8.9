package com.artedprvt.core.app;

import java.util.List;

/**
 * 应用程序目标
 * 此对象在一定程度上已经处理了应用程序体
 * 并将对应用程序的操作抽象出来
 * 除了直接打开应用程序还可以请求使用其他功能
 *
 * @param <T>
 */
public interface AppTarget<T extends AppProcess<T>> {
    /**
     * 打开应用程序
     *
     * @param args 参数
     * @return
     * @throws Exception
     */
    T open(List<String> args) throws Exception;

    /**
     * 请求功能
     *
     * @param request 请求类型
     * @return 如果请求成功返回请求结果对象否则null
     */
    Object request(String request);

    AppType<T> getAppType();
}
