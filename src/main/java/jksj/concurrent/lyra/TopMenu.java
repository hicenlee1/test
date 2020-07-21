package jksj.concurrent.lyra;


import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wuquanfang on 2017/9/5.
 */
public class TopMenu {
    private HashMap<String, Object> site;
    private List conditions;
    private String position;
    private String id;
    private List<Menu> sub;
    private List _conditions;
    private int level;
    private String name;
    private String type;
    private boolean showSidebar;
    private String url;
    private Boolean isHide ;
    private JSONObject sct;
    private Boolean isNew ;
    private Boolean isStatistic = true;

    public HashMap<String, Object> getSite() {
        return site;
    }

    public void setSite(HashMap<String, Object> site) {
        this.site = site;
    }

    public List getConditions() {
        return conditions;
    }

    public void setConditions(List conditions) {
        this.conditions = conditions;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Menu> getSub() {
        return sub;
    }

    public void setSub(List<Menu> sub) {
        this.sub = sub;
    }

    public List get_conditions() {
        return _conditions;
    }

    public void set_conditions(List _conditions) {
        this._conditions = _conditions;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isShowSidebar() {
        return showSidebar;
    }

    public void setShowSidebar(boolean showSidebar) {
        this.showSidebar = showSidebar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JSONObject getSct() {
        return sct;
    }
    public void setSct(JSONObject sct) {
        this.sct = sct;
    }

    public Boolean getIsHide() {
        return isHide;
    }

    public void setIsHide(Boolean isHide) {
        this.isHide = isHide;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Boolean getIsStatistic() {
        return isStatistic;
    }

    public void setIsStatistic(Boolean isStatistic) {
        this.isStatistic = isStatistic;
    }
}
