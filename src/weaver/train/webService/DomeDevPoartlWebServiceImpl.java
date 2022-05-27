package weaver.train.webService;

import com.alibaba.fastjson.JSONObject;
import com.wbi.util.Util;
import weaver.conn.RecordSet;

import java.util.HashMap;
import java.util.Map;

public class DomeDevPoartlWebServiceImpl implements DomeDevPoartlWebService {
    @Override
    public String getDate(String id) throws Exception {
        Map<String, Object> result = new HashMap<>();
        //创建
        RecordSet recordSet = new RecordSet();
        String sql = "select filedkey,filedvalue,fileddesc from uf_test where id=?";
        recordSet.executeQuery(sql, id);
        if (recordSet.next()) {
            result.put("filedKey", Util.null2String(recordSet.getString("filedkey")));
            result.put("filedValue", Util.null2String(recordSet.getString("filedvalue")));
            result.put("filedDesc", Util.null2String(recordSet.getString("fileddesc")));
        }
        return JSONObject.toJSONString(result);
    }
}
