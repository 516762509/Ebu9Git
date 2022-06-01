package ebu9.entity;

import java.util.Date;

public class UpdateDeptMsg {
    private int id;
    private String departmentmark;
    private String departmentname;
    private int subcompanyid1;
    private int supdepid;
    private String allsupdepid;
    private char canceled;
    private String departmentcode;
    private int coadjutant;
    private String zzjgbmfzr;
    private String zzjgbmfgld;
    private String jzglbmfzr;
    private String jzglbmfgld;
    private String bmfzr;
    private String bmfgld;
    private String outkey;
    private int budgetAtuoMoveOrder;
    private String ecology_pinyin_search;
    private int tlevel;
    private Date created;
    private int creater;
    private Date modified;
    private int modifier;
    private String uuid;
    private float showorder;
    private int showOrderOfTree;

    public UpdateDeptMsg() {
    }


    @Override
    public String toString() {
        return "UpdateDeptMsg{" +
                "id=" + id +
                ", departmentmark='" + departmentmark + '\'' +
                ", departmentname='" + departmentname + '\'' +
                ", subcompanyid1=" + subcompanyid1 +
                ", supdepid=" + supdepid +
                ", allsupdepid='" + allsupdepid + '\'' +
                ", canceled=" + canceled +
                ", departmentcode='" + departmentcode + '\'' +
                ", coadjutant=" + coadjutant +
                ", zzjgbmfzr='" + zzjgbmfzr + '\'' +
                ", zzjgbmfgld='" + zzjgbmfgld + '\'' +
                ", jzglbmfzr='" + jzglbmfzr + '\'' +
                ", jzglbmfgld='" + jzglbmfgld + '\'' +
                ", bmfzr='" + bmfzr + '\'' +
                ", bmfgld='" + bmfgld + '\'' +
                ", outkey='" + outkey + '\'' +
                ", budgetAtuoMoveOrder=" + budgetAtuoMoveOrder +
                ", ecology_pinyin_search='" + ecology_pinyin_search + '\'' +
                ", tlevel=" + tlevel +
                ", created=" + created +
                ", creater=" + creater +
                ", modified=" + modified +
                ", modifier=" + modifier +
                ", uuid='" + uuid + '\'' +
                ", showorder=" + showorder +
                ", showOrderOfTree=" + showOrderOfTree +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartmentmark() {
        return departmentmark;
    }

    public void setDepartmentmark(String departmentmark) {
        this.departmentmark = departmentmark;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }

    public int getSubcompanyid1() {
        return subcompanyid1;
    }

    public void setSubcompanyid1(int subcompanyid1) {
        this.subcompanyid1 = subcompanyid1;
    }

    public int getSupdepid() {
        return supdepid;
    }

    public void setSupdepid(int supdepid) {
        this.supdepid = supdepid;
    }

    public String getAllsupdepid() {
        return allsupdepid;
    }

    public void setAllsupdepid(String allsupdepid) {
        this.allsupdepid = allsupdepid;
    }

    public char getCanceled() {
        return canceled;
    }

    public void setCanceled(char canceled) {
        this.canceled = canceled;
    }

    public String getDepartmentcode() {
        return departmentcode;
    }

    public void setDepartmentcode(String departmentcode) {
        this.departmentcode = departmentcode;
    }

    public int getCoadjutant() {
        return coadjutant;
    }

    public void setCoadjutant(int coadjutant) {
        this.coadjutant = coadjutant;
    }

    public String getZzjgbmfzr() {
        return zzjgbmfzr;
    }

    public void setZzjgbmfzr(String zzjgbmfzr) {
        this.zzjgbmfzr = zzjgbmfzr;
    }

    public String getZzjgbmfgld() {
        return zzjgbmfgld;
    }

    public void setZzjgbmfgld(String zzjgbmfgld) {
        this.zzjgbmfgld = zzjgbmfgld;
    }

    public String getJzglbmfzr() {
        return jzglbmfzr;
    }

    public void setJzglbmfzr(String jzglbmfzr) {
        this.jzglbmfzr = jzglbmfzr;
    }

    public String getJzglbmfgld() {
        return jzglbmfgld;
    }

    public void setJzglbmfgld(String jzglbmfgld) {
        this.jzglbmfgld = jzglbmfgld;
    }

    public String getBmfzr() {
        return bmfzr;
    }

    public void setBmfzr(String bmfzr) {
        this.bmfzr = bmfzr;
    }

    public String getBmfgld() {
        return bmfgld;
    }

    public void setBmfgld(String bmfgld) {
        this.bmfgld = bmfgld;
    }

    public String getOutkey() {
        return outkey;
    }

    public void setOutkey(String outkey) {
        this.outkey = outkey;
    }

    public int getBudgetAtuoMoveOrder() {
        return budgetAtuoMoveOrder;
    }

    public void setBudgetAtuoMoveOrder(int budgetAtuoMoveOrder) {
        this.budgetAtuoMoveOrder = budgetAtuoMoveOrder;
    }

    public String getEcology_pinyin_search() {
        return ecology_pinyin_search;
    }

    public void setEcology_pinyin_search(String ecology_pinyin_search) {
        this.ecology_pinyin_search = ecology_pinyin_search;
    }

    public int getTlevel() {
        return tlevel;
    }

    public void setTlevel(int tlevel) {
        this.tlevel = tlevel;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getCreater() {
        return creater;
    }

    public void setCreater(int creater) {
        this.creater = creater;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public float getShoworder() {
        return showorder;
    }

    public void setShoworder(float showorder) {
        this.showorder = showorder;
    }

    public int getShowOrderOfTree() {
        return showOrderOfTree;
    }

    public void setShowOrderOfTree(int showOrderOfTree) {
        this.showOrderOfTree = showOrderOfTree;
    }

    public UpdateDeptMsg(int id, String departmentmark, String departmentname, int subcompanyid1, int supdepid, String allsupdepid, char canceled, String departmentcode, int coadjutant, String zzjgbmfzr, String zzjgbmfgld, String jzglbmfzr, String jzglbmfgld, String bmfzr, String bmfgld, String outkey, int budgetAtuoMoveOrder, String ecology_pinyin_search, int tlevel, Date created, int creater, Date modified, int modifier, String uuid, float showorder, int showOrderOfTree) {
        this.id = id;
        this.departmentmark = departmentmark;
        this.departmentname = departmentname;
        this.subcompanyid1 = subcompanyid1;
        this.supdepid = supdepid;
        this.allsupdepid = allsupdepid;
        this.canceled = canceled;
        this.departmentcode = departmentcode;
        this.coadjutant = coadjutant;
        this.zzjgbmfzr = zzjgbmfzr;
        this.zzjgbmfgld = zzjgbmfgld;
        this.jzglbmfzr = jzglbmfzr;
        this.jzglbmfgld = jzglbmfgld;
        this.bmfzr = bmfzr;
        this.bmfgld = bmfgld;
        this.outkey = outkey;
        this.budgetAtuoMoveOrder = budgetAtuoMoveOrder;
        this.ecology_pinyin_search = ecology_pinyin_search;
        this.tlevel = tlevel;
        this.created = created;
        this.creater = creater;
        this.modified = modified;
        this.modifier = modifier;
        this.uuid = uuid;
        this.showorder = showorder;
        this.showOrderOfTree = showOrderOfTree;
    }
}
