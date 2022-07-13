package ext.ket.edm.stamping.util;

public class StampingConstants {

    /**
     * Stamping XML Setting
     */
    public final static String NONE = "None";
    public final static String FILE_SEPERATOR = "\\";

    public final static String COMPANY_NAME = "KOREA ELECTRIC TERMINAL CO.,LTD.";

    public final static String STAMP_ERROR_FILE_SUFFIX = "_e";
    public final static String STAMP_LOG_FILE_EXT = "log";
    public final static String STAMP_FILE_SUFFIX = "_s";

    public final static String TRANS_NX_FILE_SUFFIX = "_nx_t";
    public final static String TRANS_CREO_FILE_SUFFIX = "_creo_t";
    public final static String TRANS_LOG_FILE_EXT = "log";
    public final static String TRANS_ERROR_FILE_SUFFIX = "_e";

    public final static String Y = "Y";
    public final static String N = "N";

    /**
     * Stamping result upload
     */
    public final static String STAMP_COLLECT_ALL_FOLD = "_COLLECT_SA";
    public final static String STAMP_COLLECT_ALL_SUB_EPM_FOLD = "drawing";
    public final static String STAMP_COLLECT_ALL_SUB_DOC_FOLD = "document";
    public final static String STAMP_COLLECT_ZIO_NAME = "drawing_all.zip";

    // //////////////////////////////////////////////////////
    // edm.properties 세팅 값
    // //////////////////////////////////////////////////////

    /**
     * Stamping fold path
     */
    public final static String EPM_STAMPING_SERVER_ROOT_FOLD = "epm.stamping.stampinServer.rootFold"; // stamping root fold
    public final static String EPM_WC_SERVER_ROOT_FOLD = "epm.stamping.windchillServer.rootFold"; // wc mounted root fold

    /**
     * Stamping Queue
     */
    public final static String EPM_STAMPING_JMS_BROKERURL = "epm.stamping.jms.brokerUrl"; // 접속 URL
    public final static String EPM_STAMPING_JMS_QUEUE_NAME = "epm.stamping.jms.queue"; // Queue 명
    // Stamping Queue상태에 대해서 재전송시 시간 계산
    public final static String EPM_STAMPING_LIMITHOUR_RE_INPUT_QUEUE = "epm.stamping.limitHourAboutReInputQueue";
    /**
     * Stamping Team Name line feed
     */
    public final static String EPM_STAMPING_TEAMNAME_LIMIT_LINE_FEED = "epm.stamping.limitLineFeed";

}
