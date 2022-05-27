package ebu9.util;

public class ResponseVO {
    private String title; // 待办标题
    private String linkurl; // 跳转链接
    private String creater; // 创建者
    private String currentnode; // 当前节点
    private String receiveDate; // 接收日期

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCurrentnode() {
        return currentnode;
    }

    public void setCurrentnode(String currentnode) {
        this.currentnode = currentnode;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }
}
