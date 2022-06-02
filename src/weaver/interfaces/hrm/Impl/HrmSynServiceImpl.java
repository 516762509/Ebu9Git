package weaver.interfaces.hrm.Impl;

import weaver.general.BaseBean;
import weaver.interfaces.hrm.*;
import weaver.interfaces.hrm.HrmSynService;

public class HrmSynServiceImpl extends BaseBean implements HrmSynService {

    @Override
    public String SynTimingToOASubCompany() {
        String strxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><orglist>" +
                "<org action=\"add\">" +
                "<code>001</code><shortname>测试分部一</shortname><fullname>测试分部一</fullname><parent_code>0</parent_code><order>0</order>" +
                "</org>" +
                "<org action=\"edit\">" +
                "<code>002</code><shortname>测试分部二</shortname><fullname>测试分部二</fullname><parent_code>0</parent_code><order>1</order>" +
                "</org>" +
                "<org action=\"delete\">" +
                "<code>003</code>" +
                "<canceled>1</canceled>" +
                "</org>";
        return strxml;

    }

    @Override
    public String SynTimingToOADepartment() {
        String strxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><orglist>" +
                "<org action=\"add\">" +
                "<code>001</code><shortname>测试部门一</shortname><fullname>测试部门一</fullname><org_code>001</org_code><parent_code>0</parent_code><order>0</order>" +
                "</org>" +
                "<org action=\"add\">" +
                "<code>002</code><shortname>测试部门二</shortname><fullname>测试部门二</fullname><org_code>002</org_code><parent_code>0</parent_code><order>1</order>" +
                "</org>" +
                "</orglist></root>";
        return strxml;

    }

    @Override
    public String SynTimingToOAJobtitle() {
        String strxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><jobtitlelist>" +
                "<jobtitle action=\"add\">" +
                "<jobtitlecode>001</jobtitlecode><jobtitlename>测试岗位一</jobtitlename><jobtitleremark>测试岗位一</jobtitleremark><jobtitledept>001</jobtitledept>" +
                "</jobtitle>" +
                "</jobtitlelist></root>";
        return strxml;

    }

    @Override
    public String SynTimingToOAHrmResource() {
        String strxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><hrmlist>" +
                "<hrm action=\"add\">" +
                "<workcode>001</workcode><lastname>测试人员</lastname><jobtitlecode>001</jobtitlecode><departmentcode>001</departmentcode>" +
                "</hrm>" +
                "</hrmlist></root>";
        return strxml;

    }

    @Override
    public void SynTimingFromOASubCompany(SubCompanyBean[] subcompanybeanlist) {
        for (int i = 0; i < subcompanybeanlist.length; i++) {
            String code = subcompanybeanlist[i].get_code();                  //分部编码
            String shortname = subcompanybeanlist[i].get_shortname();        //分部简称
            String fullname = subcompanybeanlist[i].get_fullname();          //分部全称
            String parentcode = subcompanybeanlist[i].get_parentcode();      //上级分部编码
            String showorder = subcompanybeanlist[i].get_showorder();       //显示顺序
            String canceled = subcompanybeanlist[i].get_canceled();          //0：正常，1：封存
            String id = subcompanybeanlist[i].get_subcompanyid();            //分部ID
            //得到上面值之后可以根据需求把值传到第三方系统里面，怎样同步过去，下面的代码就需要自己去编写
        }

    }

    @Override
    public void SynTimingFromOADepartment(DepartmentBean[] departmentbeanlist) {
        for (int i = 0; i < departmentbeanlist.length; i++) {
            String code = departmentbeanlist[i].get_code();                  //部门编码
            String shortname = departmentbeanlist[i].get_shortname();        //部门简称
            String fullname = departmentbeanlist[i].get_fullname();          //部门全称
            String parentcode = departmentbeanlist[i].getParent_code();  //所属分部编码
            String subcompanycode = departmentbeanlist[i].getSubcompany_code();  //所属分部编码
            String showorder = departmentbeanlist[i].get_showorder();       //显示顺序
            String canceled = departmentbeanlist[i].get_canceled();          //0：正常，1：封存
            String id = departmentbeanlist[i].get_departmentid();            //部门ID
            System.out.println("shortname:" + shortname);

            //得到上面值之后可以根据需求把值传到第三方系统里面，怎样同步过去，下面的代码就需要自己去编写
        }

    }

    @Override
    public void SynTimingFromOAJobtitle(JobTitleBean[] jobtitlebeanlist) {
        for (int i = 0; i < jobtitlebeanlist.length; i++) {
            String code = jobtitlebeanlist[i].get_code();                  //岗位编码
            String deptcode = jobtitlebeanlist[i].get_departmentcode();    //部门简称
            String shortname = jobtitlebeanlist[i].get_shortname();        //岗位简称
            String fullname = jobtitlebeanlist[i].get_fullname();          //岗位全称
            String id = jobtitlebeanlist[i].get_jobtitleid();              //岗位id

            //得到上面值之后可以根据需求把值传到第三方系统里面，怎样同步过去，下面的代码就需要自己去编写
        }

    }

    @Override
    public void SynTimingFromOAHrmResource(UserBean[] userbeanlist) {
        for(int i=0; i<userbeanlist.length; i++) {
            String lastname = userbeanlist[i].getLastname();
            String loginid = userbeanlist[i].getLoginid();
            String sex = userbeanlist[i].getSex();
            String telephone = userbeanlist[i].getTelephone();
            String mobile = userbeanlist[i].getMobile();
            String email = userbeanlist[i].getEmail();
            String startdate = userbeanlist[i].getStartdate();
            String enddate = userbeanlist[i].getEnddate();
            String jobtitle = userbeanlist[i].getJobtitle();
            String seclevel = userbeanlist[i].getSeclevel();
            String subcompanyid1 = userbeanlist[i].getSubcompanyid1();
            String departmentid = userbeanlist[i].getDepartmentid();
            String managerid = userbeanlist[i].getManagerid();
            String certificatenum = userbeanlist[i].getCertificatenum();
            String workcode = userbeanlist[i].getWorkcode();
            String status = userbeanlist[i].getStatus();
            String birthday = userbeanlist[i].getBirthday();
            String createdate = userbeanlist[i].getCreatedate();
            String lastmoddate = userbeanlist[i].getLastChangdate();

            //得到上面值之后可以根据需求把值传到第三方系统里面，怎样同步过去，下面的代码就需要自己去编写
        }

    }

    @Override
    public void SynInstantSubCompany(SubCompanyBean subcompanybean) {
        String code = subcompanybean.get_code();                  //分部编码
        String shortname = subcompanybean.get_shortname();        //分部简称
        String fullname = subcompanybean.get_fullname();          //分部全称
        String parentcode = subcompanybean.get_parentcode();      //上级分部编码
        String showorder  = subcompanybean.get_showorder();       //显示顺序
        String canceled = subcompanybean.get_canceled();          //0：正常，1：封存
        String id = subcompanybean.get_subcompanyid();            //分部ID

    }

    @Override
    public void SynInstantDepartment(DepartmentBean departmentbean) {
        String code = departmentbean.get_code();                  //部门编码
        String shortname = departmentbean.get_shortname();        //部门简称
        String fullname = departmentbean.get_fullname();          //部门全称
        String parentcode = departmentbean.getParent_code();  //所属分部编码
        String subcompanycode = departmentbean.getSubcompany_code();  //所属分部编码
        String showorder  = departmentbean.get_showorder();       //显示顺序
        String canceled = departmentbean.get_canceled();          //0：正常，1：封存
        String id = departmentbean.get_departmentid();
    }

    @Override
    public void SynInstantJobtitle(JobTitleBean jobtitlebean) {
        String code = jobtitlebean.get_code();                  //岗位编码
        String deptcode = jobtitlebean.get_departmentcode();    //部门简称
        String shortname = jobtitlebean.get_shortname();        //岗位简称
        String fullname = jobtitlebean.get_fullname();          //岗位全称
        String id = jobtitlebean.get_jobtitleid();              //岗位id

    }

    @Override
    public void SynInstantHrmResource(UserBean userbean) {
        String lastname = userbean.getLastname();
        String loginid = userbean.getLoginid();
        String sex = userbean.getSex();
        String telephone = userbean.getTelephone();
        String mobile = userbean.getMobile();
        String email = userbean.getEmail();
        String startdate = userbean.getStartdate();
        String enddate = userbean.getEnddate();
        String jobtitle = userbean.getJobtitle();
        String seclevel = userbean.getSeclevel();
        String subcompanyid1 = userbean.getSubcompanyid1();
        String departmentid = userbean.getDepartmentid();
        String managerid = userbean.getManagerid();
        String certificatenum = userbean.getCertificatenum();
        String workcode = userbean.getWorkcode();
        String status = userbean.getStatus();
        String birthday = userbean.getBirthday();
        String createdate = userbean.getCreatedate();
        String lastmoddate = userbean.getLastChangdate();

    }

    @Override
    public boolean SynSendMessage(String sender, String receiver, String title, String content, String url) {
        return false;
    }
}
