package jksj.concurrent.lyra;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by wuquanfang on 2017/9/5.
 */
public class Menu {
    private String id;
    private String icon;
    private List<Menu> sub;
    private int level;
    private boolean isDefault;
    private String name;
    private String type;
    private Boolean isHide = false;
    private String reportName;
    private int reportId;
    private String url;
    private JSONObject sct;
    private Boolean isNew = false;
    private Boolean isStatistic = true;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Menu> getSub() {
        return sub;
    }

    public void setSub(List<Menu> sub) {
        this.sub = sub;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean aDefault) {
        isDefault = aDefault;
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

    public boolean getIsHide() {
        return isHide;
    }

    public void setIsHide(boolean isHide) {
        this.isHide = isHide;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
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

    public boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public Boolean getIsStatistic() {
        return isStatistic;
    }

    public void setIsStatistic(Boolean statistic) {
        isStatistic = statistic;
    }
}
