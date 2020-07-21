package jksj.concurrent.lyra;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhaotianshuo on 2016/10/21.
 */
public class Namespace implements Serializable {

    Long nsId;

    String name;

    String desc;

    String owner;

    Date gmtCreate;

    Date gmtUpdate;

    Integer index;

    Integer authEnable;

    String notice;

    public Integer getAuthEnable() {
        return authEnable;
    }

    public void setAuthEnable(Integer authEnable) {
        this.authEnable = authEnable;
    }
    public Boolean isAuthenticateEnable(){
        if(authEnable==null||authEnable==1){
            return true;
        }else{
            return false;
        }
    }
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Long getNsId() {
        return nsId;
    }

    public void setNsId(Long nsId) {
        this.nsId = nsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
