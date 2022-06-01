package ebu9.entity;

public class NccDeptMsg {
    private String bpcode;
    private String odcode;
    private String pk_dept;
    private String odname;
    private String ismanjob;
    private String pk_org;
    private String odpk_dept;
    private String odpk_vid;
    private String pk_psndoc;
    private String id;

    public NccDeptMsg(String bpcode, String odcode, String pk_dept, String odname, String ismanjob, String pk_org, String odpk_dept, String odpk_vid, String pk_psndoc, String id) {
        this.bpcode = bpcode;
        this.odcode = odcode;
        this.pk_dept = pk_dept;
        this.odname = odname;
        this.ismanjob = ismanjob;
        this.pk_org = pk_org;
        this.odpk_dept = odpk_dept;
        this.odpk_vid = odpk_vid;
        this.pk_psndoc = pk_psndoc;
        this.id = id;
    }

    public NccDeptMsg() {
    }

    public String getBpcode() {
        return bpcode;
    }

    public void setBpcode(String bpcode) {
        this.bpcode = bpcode;
    }

    public String getOdcode() {
        return odcode;
    }

    public void setOdcode(String odcode) {
        this.odcode = odcode;
    }

    public String getPk_dept() {
        return pk_dept;
    }

    public void setPk_dept(String pk_dept) {
        this.pk_dept = pk_dept;
    }

    public String getOdname() {
        return odname;
    }

    public void setOdname(String odname) {
        this.odname = odname;
    }

    public String getIsmanjob() {
        return ismanjob;
    }

    public void setIsmanjob(String ismanjob) {
        this.ismanjob = ismanjob;
    }

    public String getPk_org() {
        return pk_org;
    }

    public void setPk_org(String pk_org) {
        this.pk_org = pk_org;
    }

    public String getOdpk_dept() {
        return odpk_dept;
    }

    public void setOdpk_dept(String odpk_dept) {
        this.odpk_dept = odpk_dept;
    }

    public String getOdpk_vid() {
        return odpk_vid;
    }

    public void setOdpk_vid(String odpk_vid) {
        this.odpk_vid = odpk_vid;
    }

    public String getPk_psndoc() {
        return pk_psndoc;
    }

    public void setPk_psndoc(String pk_psndoc) {
        this.pk_psndoc = pk_psndoc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "NccDeptMsg{" +
                "bpcode='" + bpcode + '\'' +
                ", odcode='" + odcode + '\'' +
                ", pk_dept='" + pk_dept + '\'' +
                ", odname='" + odname + '\'' +
                ", ismanjob='" + ismanjob + '\'' +
                ", pk_org='" + pk_org + '\'' +
                ", odpk_dept='" + odpk_dept + '\'' +
                ", odpk_vid='" + odpk_vid + '\'' +
                ", pk_psndoc='" + pk_psndoc + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
