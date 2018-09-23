package com.flouis.npjt.service;


import com.flouis.npjt.common.ServerResult;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface ITestExportService {

    ServerResult getData(Map filterConditions);

    ResponseEntity<byte[]> exportExcel(HttpServletRequest request, Map filterConditions) throws IOException;
}
