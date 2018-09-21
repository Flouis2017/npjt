package com.flouis.npjt.service;


import com.flouis.npjt.common.ServerResult;

import java.util.Map;

public interface ITestExportService {

    ServerResult exportExcel(Map filterConditions);
}
