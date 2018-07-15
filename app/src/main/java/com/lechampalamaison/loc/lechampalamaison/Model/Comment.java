package com.lechampalamaison.loc.lechampalamaison.Model;

import java.util.Date;

public class Comment {

    private Date dateComment;
    private String loginComment;
    private Double stars;
    private String comment;

    public Comment(Date dateComment, String loginComment, Double stars, String comment) {
        this.dateComment = dateComment;
        this.loginComment = loginComment;
        this.stars = stars;
        this.comment = comment;
    }

    public Date getDateComment() {
        return dateComment;
    }

    public void setDateComment(Date dateComment) {
        this.dateComment = dateComment;
    }

    public String getLoginComment() {
        return loginComment;
    }

    public void setLoginComment(String loginComment) {
        this.loginComment = loginComment;
    }

    public Double getStars() {
        return stars;
    }

    public void setStars(Double stars) {
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
