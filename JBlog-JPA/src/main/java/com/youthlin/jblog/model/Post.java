package com.youthlin.jblog.model;

import com.youthlin.jblog.constant.Constant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lin on 2016-09-02-002.
 * 发表内容
 */
@Entity
@Table(name = "t_post")
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type = Constant.POST_TYPE_TEXT;  //类型取值Text或Image
    private String title;                           //标题
    @Lob                                            //Text是大文本
    private String content;                         //内容，文本或照片全URL路径
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "publish_date")
    private Date publishDate;                       //发表时间
    @ManyToOne
    private User author;                            //作者
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;                 //该文章或照片下的评论

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publishDate=" + publishDate +
                ", author=" + author +
                ", comments=" + comments +
                '}';
    }

    //region getter and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    //endregion
}