package weaver.interfaces.hrm;

public interface HrmSynService {
    /**
     * 定时同步分部
     * 从其他系统同步到OA
     */
    public String SynTimingToOASubCompany();

    /**
     * 定时同步部门
     * 从其他系统同步到OA
     */
    public String SynTimingToOADepartment();

    /**
     * 定时同步岗位
     * 从其他系统同步到OA
     */
    public String SynTimingToOAJobtitle();

    /**
     * 定时同步人员
     * 从其他系统同步到OA
     */
    public String SynTimingToOAHrmResource();

    /**
     * 定时同步分部
     * 从OA同步到其他系统
     */
    public void SynTimingFromOASubCompany(SubCompanyBean[] subcompanybeanlist);

    /**
     * 定时同步部门
     * 从OA同步到其他系统
     */
    public void SynTimingFromOADepartment(DepartmentBean[] departmentbeanlist);

    /**
     * 定时同步岗位
     * 从OA同步到其他系统
     */
    public void SynTimingFromOAJobtitle(JobTitleBean[] jobtitlebeanlist);

    /**
     * 定时同步人员
     * 从OA同步到其他系统
     */
    public void SynTimingFromOAHrmResource(UserBean[] userbeanlist);

    /**
     * 即时同步分部（从OA同步单条数据到第三方系统中）
     * @param subcompanybean
     */
    public void SynInstantSubCompany(SubCompanyBean subcompanybean);

    /**
     * 即时同步部门（从OA同步单条数据到第三方系统中）
     * @param departmentbean
     */
    public void SynInstantDepartment(DepartmentBean departmentbean);

    /**
     * 即时同步岗位（从OA同步单条数据到第三方系统中）
     * @param jobtitlebean
     */
    public void SynInstantJobtitle(JobTitleBean jobtitlebean);

    /**
     * 即时同步人员（从OA同步单条数据到第三方系统中）
     * @param userbean
     */
    public void SynInstantHrmResource(UserBean userbean);

    /**
     * 流程消息提醒
     * @param sender       发送者
     * @param receiver     接受者
     * @param title        标题
     * @param content      内容
     * @param url
     * @return
     */
    public boolean SynSendMessage(String sender,String receiver,String title,String content,String url);

}
