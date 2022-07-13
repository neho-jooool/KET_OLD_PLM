package e3ps.cost.migration.service;

import wt.method.RemoteInterface;

@RemoteInterface
public interface KetMigCostService {
    public void migCostRerpot(String filePath, String lastSheet, String fileName) throws Exception;
    
}
