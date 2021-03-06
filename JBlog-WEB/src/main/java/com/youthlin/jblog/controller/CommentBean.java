package com.youthlin.jblog.controller;

import com.youthlin.jblog.constant.Constant;
import com.youthlin.jblog.dao.CommentDao;
import com.youthlin.jblog.dao.PostDao;
import com.youthlin.jblog.model.Comment;
import com.youthlin.jblog.model.Post;
import com.youthlin.jblog.model.User;
import com.youthlin.jblog.util.EJBUtil;
import com.youthlin.jblog.util.HTTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by lin on 2016-09-09-009.
 * 评论
 */
@ManagedBean
@RequestScoped
public class CommentBean {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private PostDao postDao = EJBUtil.getBean(PostDao.class);
    private CommentDao commentDao = EJBUtil.getBean(CommentDao.class);
    private String content;
    private long postId;

    public CommentBean() {
        log.debug("构造CommentBean");
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String postIdStr = request.getParameter("id");
        log.debug("post id = {}", postIdStr);
        if (postIdStr == null) {
            return;
        }
        try {
            postId = Long.parseLong(postIdStr);
        } catch (Exception e) {
            Post post = postDao.getNewestText();
            if (post != null) {
                postId = postDao.getNewestText().getId();
            }
        }
    }

    public String comment() {
        Comment comment = new Comment();
        comment.setAuthor((User) HTTPUtil.getSession().getAttribute(Constant.CURRENT_USER));
        comment.setContent(content);
        comment.setPublishDate(new Date());
        Post post = postDao.find(Post.class, postId);
        log.debug("comment post id = {}", post.getId());
        comment.setPost(post);
        comment = commentDao.save(comment);
        log.debug("发表评论成功,comment={},post comment count = {}", comment.getContent(), comment.getPost().getCommentCount());
        post = postDao.update(comment.getPost());
        log.trace("update comment post {}", post.getCommentCount());
        //评论数量有变
        HTTPUtil.getSession().setAttribute(Constant.allTextPostListShouldBeUpdated, true);
        return "article?id=" + post.getId() + "&faces-redirect=true";
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
