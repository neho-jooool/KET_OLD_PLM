package e3ps.groupware.board;

import e3ps.common.util.NumberCodeUtil;
import ext.ket.shared.log.Kogger;

/*
 * [PLM System 1차개선]
 * 파일명 : QnaBoardData.java
 * 설명 : QnaBoard 객체 wrapper
 * 작성일자 : 2013. 06. 13
 * 작성자 : 김무준
 */

public class QnaBoardData extends HelpBoardData {

    // 분류
    public static final String CLASS_ETC = "ETC";

    public QnaBoardData(QnaBoard boardObj, int type) {
        super(boardObj, type);
    }

    @Override
    public QnaBoard getBoardObj() {
        return (QnaBoard) boardObj;
    }

    public String getClassification() {
        return getBoardObj().getClassification();
    }
    public String getClassificationStr() {
        return classificationToString(getClassification());
    }

    public String getState() {
        return getBoardObj().getState();
    }
    public String getStateStr() {
        return stateToString(getState());
    }
    public boolean isRegisteredState() {
        return STATE_REGISTERED.equalsIgnoreCase(getState());
    }
    public boolean isProcessingState() {
        return STATE_PROCESSING.equalsIgnoreCase(getState());
    }

    public static String stateToString(String state) {
        try {
            return NumberCodeUtil.getNumberCodeValue("QNASTATUS", state);
        }
        catch(Exception e) {
            Kogger.error(QnaBoardData.class, e);
        }
        return "";
    }

    public static String classificationToString(String classification) {
        try {
            return NumberCodeUtil.getNumberCodeValue("QNATYPE", classification);
        }
        catch(Exception e) {
            Kogger.error(QnaBoardData.class, e);
        }
        return "";
    }

}
