package com.flouis.npjt.dao;

import java.util.List;
import java.util.Map;

public interface FileOperationDao {

    String getKeyInsert(Object[] args);

    List<Map<String, Object>> getFileList();

    int deleteById(String id);
}
