package com.chisapp.modules.doctorworkstation.service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Tandy
 * @Date: 2019/11/7 12:45
 * @Version 1.0
 */
public interface DiagnoseLibraryService {
    List<Map<String, Object>> getByCriteria(Integer dwtDiagnoseTypeId,
                                            Integer dwtDiagnoseClassifyId,
                                            String name);

}
