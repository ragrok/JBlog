package com.youthlin.jblog.controller;

import com.youthlin.jblog.constant.Constant;
import com.youthlin.jblog.dao.PostDao;
import com.youthlin.jblog.dao.SettingsDao;
import com.youthlin.jblog.model.Page;
import com.youthlin.jblog.model.Post;
import com.youthlin.jblog.util.EJBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by lin on 2016-09-08-008.
 * 前台 文章列表
 */
@ManagedBean
@RequestScoped
public class ArticleListBean {
    private static final Logger log = LoggerFactory.getLogger(ArticleListBean.class);
    private static SettingsDao settingsDao = EJBUtil.getBean(SettingsDao.class);
    private static PostDao postDao = EJBUtil.getBean(PostDao.class);

    private Page<Post> textPage;

    public ArticleListBean() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String pageStr = request.getParameter("page");
        int page = 1;
        int size = 5;
        try {
            page = Integer.parseInt(pageStr);
            size = Integer.parseInt(Constant.SETTINGS_TEXT_COUNT_PER_PAGE_DEFAULT);
        } catch (Exception e) {
            //默认值
        }
        String sizeStr = settingsDao.get(Constant.SETTINGS_TEXT_COUNT_PER_PAGE);
        if (sizeStr == null) {
            settingsDao.add(Constant.SETTINGS_TEXT_COUNT_PER_PAGE, Constant.SETTINGS_TEXT_COUNT_PER_PAGE_DEFAULT);//5
        } else {
            try {
                size = Integer.parseInt(sizeStr);
            } catch (Exception e) {
                //默认值5
            }
        }
        textPage = postDao.getTextByPage(page, size);
        log.trace("page={}", textPage);
    }

    public Page<Post> getTextPage() {
        return textPage;
    }

    public void setTextPage(Page<Post> textPage) {
        this.textPage = textPage;
    }
}
