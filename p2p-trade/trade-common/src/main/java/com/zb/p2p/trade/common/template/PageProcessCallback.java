package com.zb.p2p.trade.common.template;

import java.util.List;

/**
 * <p>分页处理回调方法</p>
 * @author Eric.fu
 * @version $Id: PageProcessCallbackWithFinish.java, v 0.1 2014-11-30 下午5:31:01 fuyangbiao Exp $
 */
public interface PageProcessCallback<T> {

    /**
     * 分页加载
     * @param paginator
     * @return
     */
    List<T> load(Paginator paginator);

    /**
     * 处理单个信息
     * @param item
     */
    void process(T item);

    /**
     * 每页完成处理
     */
    void pageFinish();

}