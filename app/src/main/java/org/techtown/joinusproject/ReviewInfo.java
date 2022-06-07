package org.techtown.joinusproject;

public class ReviewInfo {
    private String review_p;
    private String review_item;
    private String review_mem;
    private String review_title;
    private String review_con;
    private String review_date;

    public ReviewInfo(String review_p, String review_item, String review_mem,
                      String review_title, String review_con, String review_date) {
        this.review_p = review_p;
        this.review_item = review_item;
        this.review_mem = review_mem;
        this.review_title = review_title;
        this.review_con = review_con;
        this.review_date = review_date;
    }

    public String getReview_p() {
        return review_p;
    }

    public void setReview_p(String review_p) {
        this.review_p = review_p;
    }

    public String getReview_item() {
        return review_item;
    }

    public void setReview_item(String review_item) {
        this.review_item = review_item;
    }

    public String getReview_mem() {
        return review_mem;
    }

    public void setReview_mem(String review_mem) {
        this.review_mem = review_mem;
    }

    public String getReview_title() {
        return review_title;
    }

    public void setReview_title(String review_title) {
        this.review_title = review_title;
    }

    public String getReview_con() {
        return review_con;
    }

    public void setReview_con(String review_con) {
        this.review_con = review_con;
    }

    public String getReview_date() {
        return review_date;
    }

    public void setReview_date(String review_date) {
        this.review_date = review_date;
    }
}
