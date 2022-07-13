package ext.ket.edm.migration.service;

import wt.method.RemoteInterface;

@RemoteInterface
public interface KetMigPartService {

    public boolean updateMigPart() throws Exception;

    public void sendHpMig() throws Exception;

    public boolean updateMigPart2() throws Exception;

    public void updatePartImg2() throws Exception;
}
