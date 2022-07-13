package ext.ket.edm.stamping.jms;

public interface StampReqSender {

    public boolean sendMessage(String reqNumber, String reqOid);

}
