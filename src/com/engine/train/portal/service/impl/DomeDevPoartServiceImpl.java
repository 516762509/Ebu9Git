package com.engine.train.portal.service.impl;

import com.engine.train.portal.service.DomeDevPoartService;
import com.wbi.util.Util;
import weaver.conn.RecordSet;

import java.util.HashMap;
import java.util.Map;

public class DomeDevPoartServiceImpl implements DomeDevPoartService {
    @Override
    public Map<String, Object> getTestData(Map<String, Object> param) {
        Map<String, Object> result = new HashMap<>();
        Integer id = Util.getIntValue((Integer) param.get("id"));
        //创建
        RecordSet recordSet = new RecordSet();
        String sql = "select filedkey,filedvalue,fileddesc from uf_test where id=?";
        recordSet.executeQuery(sql, id);
        if (recordSet.next()) {
            result.put("filedKey", Util.null2String(recordSet.getString("filedkey")));
            result.put("filedValue", Util.null2String(recordSet.getString("filedvalue")));
            result.put("filedDesc", Util.null2String(recordSet.getString("fileddesc")));
        }
        return result;
    }
}
