package ebu9.mapper;


import weaver.conn.RecordSetDataSource;

public class SelectBaseMapper {

    /**
     * ecology 查询第三方视图通用方法
     *
     * @param sourceName 数据源名称
     * @param clzz 查询参数（多个参数使用逗号隔开，最后一个无需逗号）
     * @param vname      视图名称
     * @param whereParm
     * @return
     */
    public <T> T SearchViewvalueMethod(String sourceName, Class<T> clzz, String vname, String whereParm, String whereValue) throws InstantiationException, IllegalAccessException {
        //定义最终返回的对象

        RecordSetDataSource recordSetDataSource = new RecordSetDataSource(sourceName);

      //  recordSetDataSource.execute("select " + clzz + " from " + vname + " where " + whereParm + " = '" + whereValue + "' ");
        int colCounts = recordSetDataSource.getColCounts();
        while (recordSetDataSource.next()){
            T t = clzz.newInstance();
            for (int i = 0;i<colCounts;i++){

            }
            return t;
        }
        return null;
    }
}
