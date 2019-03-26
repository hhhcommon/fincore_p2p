package com.zb.p2p.trade.common.template;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>分页处理模板</p>
 * @author Vinson
 * @version $Id: PageProcessTemplate.java, v 0.1 2018-05-30 下午5:04:45 zhengwenquan Exp $
 */
@Component
public class PageProcessTemplate {
    private static final int DEFAULT_PAGE_SIZE = 100;
    /** 每页大小 */
    private int              pageSize          = DEFAULT_PAGE_SIZE;

    /**
     * 执行
     * @param callBack
     */
    public <T> void execute(PageProcessCallback<T> callBack) {
        Paginator paginator = new Paginator(pageSize);
        List<T> items = callBack.load(paginator);
        while (!CollectionUtils.isEmpty(items)) {
            for (T item : items) {
                callBack.process(item);
            }

            paginator.setPage(paginator.getNextPage());

            // 如为分页完成回调则处理回调方法
            callBack.pageFinish();

            // 加载数据小于分页数则说明为最后一次直接退出
            if (items.size() < pageSize) {
                break;
            }

            items = callBack.load(paginator);
        }
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}