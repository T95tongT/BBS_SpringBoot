package com.toto.po;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Administrator on 2017/5/17 0017.
 */
@Entity
@Table(name="article")
public class Article {
    private int id;
    private Integer rootid;
    private String title;
    private String content;
    private Date datetime;
    private Bbsuser bbsuser;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "rootid", nullable = true)
    public Integer getRootid() {
        return rootid;
    }

    public void setRootid(Integer rootid) {
        this.rootid = rootid;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 50)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "content", nullable = true, length = -1)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "datetime", nullable = true)
    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;


        if (rootid != null ? !rootid.equals(article.rootid) : article.rootid != null) return false;
        if (title != null ? !title.equals(article.title) : article.title != null) return false;
        if (content != null ? !content.equals(article.content) : article.content != null) return false;
        if (datetime != null ? !datetime.equals(article.datetime) : article.datetime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != 0? id: 0;
        result = 31 * result + (rootid != null ? rootid.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (datetime != null ? datetime.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    public Bbsuser getBbsuser() {
        return bbsuser;
    }

    public void setBbsuser(Bbsuser bbsuser) {
        this.bbsuser = bbsuser;
    }
}
