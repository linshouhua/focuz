package com.focus.focus.entity;

import com.focus.base.entity.BaseEntity;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "FocusInfo")
public class FocusInfo extends BaseEntity {
    @Column(column = "title")
    private String title;
    @Column(column = "updateTs")
    private Long updateTs;
    @Column(column = "url")
    private String url;
    @Column(column = "lastMD5")
    private String lastMD5;
    @Column(column = "readFlag")
    private int readFlag = 0;
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(Long updateTs) {
        this.updateTs = updateTs;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLastMD5() {
        return lastMD5;
    }

    public void setLastMD5(String lastMD5) {
        this.lastMD5 = lastMD5;
    }

    public int getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(int updateFlag) {
        this.readFlag = updateFlag;
    }

    @Override
    public String toString() {
        return "FocusInfo [title=" + title + ", updateTs=" + updateTs + ", url=" + url + ", lastMD5=" + lastMD5 + ", readFlag=" + readFlag + "]";
    }
}
