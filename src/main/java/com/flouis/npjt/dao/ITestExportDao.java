package com.flouis.npjt.dao;

import java.util.List;
import java.util.Map;

public interface ITestExportDao {

    List<Map<String, Object>> getUserMapList(Map filterConditions);
}
