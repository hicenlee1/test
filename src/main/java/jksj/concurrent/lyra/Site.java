package jksj.concurrent.lyra;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;



/**
 * Created by zhaotianshuo on 2017/2/15.
 */

public class Site implements Serializable {

    Long siteId;

    String name;

    String title;
    Date gmtCreate;

    Date gmtUpdate;
    String topMenu;
    String menu;
    String notice;
    String owner;
    Long namespace;
    String description;
    String status;
    String config;
    String topMenuBak;


    @Override
    public String toString(){
        return "Site[site_id="+siteId+", name="+name+", title="+title
                +", topMenu="+topMenu +", menu="+menu +", notice="+notice
                +", owner="+owner +", namespace="+namespace +", description="+description +", status="+status+", config="+config
                +", topMenuBak="+topMenuBak +", gmtCreate="+gmtCreate +", gmtUpdate="+gmtUpdate
                +"]";
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopMenu() {
        return topMenu;
    }

    public void setTopMenu(String topMenu) {
        this.topMenu = topMenu;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getNamespace() {
        return namespace;
    }

    public void setNamespace(Long namespace) {
        this.namespace = namespace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getTopMenuBak() {
        return topMenuBak;
    }

    public void setTopMenuBak(String topMenuBak) {
        this.topMenuBak = topMenuBak;
    }

}
