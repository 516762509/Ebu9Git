package weaver.interfaces.cron;

import weaver.general.BaseBean;
import weaver.interfaces.schedule.BaseCronJob;

public class DomeCron extends BaseCronJob {
    @Override
    public void execute() {
        new BaseBean().writeLog("计划任务开始了=========");
    }
}
