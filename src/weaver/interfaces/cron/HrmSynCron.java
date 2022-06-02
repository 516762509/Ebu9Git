package weaver.interfaces.cron;

import org.springframework.transaction.annotation.Transactional;
import weaver.general.BaseBean;
import weaver.interfaces.hrm.HrmSynService;
import weaver.interfaces.hrm.Impl.HrmSynServiceImpl;
import weaver.interfaces.schedule.BaseCronJob;


@Transactional
public class HrmSynCron extends BaseCronJob {


    @Override
    public void execute() {
        HrmSynService hrmSynService = new HrmSynServiceImpl();
        new BaseBean().writeLog("进入组织架构.........");
        String s = hrmSynService.SynTimingToOASubCompany();
        new BaseBean().writeLog("输出的信息......" + s);
    }
}
