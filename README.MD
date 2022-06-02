

jersey接口路径
模块    文件路径（含下级）                   接口访问地址
流程    com.api.train.devworkflow.web    /api/train/devworkflow/web
门户    com.api.train.devportal.web    
文档    com.api.train.devdoc.web   
建模    com.api.train.devformmode.web    
移动建模 com.api.train.devmobilemode.web   
会议    com.api.train.devmeeting.web 
人力    com.api.train.devhrm.web    
财务    com.api.train.devfna.web    
项目    com.api.train.devprj.web   
公文    com.api.train.devodoc.web   
集成    com.api.train.devintegration.web    
微博    com.api.train.devblog.web    


weavrt.interfaces.cron  计划任务
weaver.interfaces.action 自定义流程接口
weaver.interfaces.formmodel 自定义建模接口

ebu9.entity 实体
ebu9.mapper 
ebu9.service
ebu9.util
BaseUtil 业务相关的工具类（创建流程工具，）
UniversalUtil 通用工具类
ResponseVO代办抓取字段属性映射

DomeApi待办抓取接口
DomeDevportalApi标准的resful接口
DomeDevPoartlWebService:webservice接口
配置好webservice接口需要在/ecology/classbean/MTA-NF/frie/services.xml中配置
<service>
<name>DevPoratalService</name> 
<namespace>http://localhsot/services/DevPortalService</namespace>
<serviceClass>weaver.train.wenservice.DerPortalService</servicesClass>
<implementationClass>weaver.train.wenservice.DerPortalServiceImpl</implementationClass>
</service>


hrmService 人员组织架构实现接口，需搭配定时任务使用，根据需求实现具体接口，