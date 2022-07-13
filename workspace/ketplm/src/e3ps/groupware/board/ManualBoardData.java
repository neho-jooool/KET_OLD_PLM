package e3ps.groupware.board;

import e3ps.common.util.KETStringUtil;
import e3ps.common.util.NumberCodeUtil;
import ext.ket.shared.log.Kogger;

/*
 * [PLM System 1차개선]
 * 파일명 : ManualBoardData.java
 * 설명 : ManualBoard 객체 wrapper
 * 작성일자 : 2013. 06. 13
 * 작성자 : 김무준
 */

public class ManualBoardData extends HelpBoardData {

    // 구분
    public static final String CLASS_MANUAL = "MNL"; // Manual
    public static final String CLASS_PROGRAM = "PRG"; // 프로그램 설치

    public ManualBoardData(ManualBoard boardObj, int type) {
        super(boardObj, type);
    }

    @Override
    public ManualBoard getBoardObj() {
        return (ManualBoard) boardObj;
    }

    public String getClassification() {
        return getBoardObj().getClassification();
    }
    public String getClassification2() {
        return getBoardObj().getClassification2();
    }
    public String getClassificationStr() {
        return classificationToString(getClassification());
    }
    public String getClassificationStr2() {
        return classificationToString2(getClassification2());
    }

    public String getUrl() {
        return KETStringUtil.nullFilter(getBoardObj().getUrl());
    }
    
    public static String classificationToString(String classification) {
        try {
            return NumberCodeUtil.getNumberCodeValue("MANUALTYPE", classification);
        }
        catch(Exception e) {
            Kogger.error(ImproveBoardData.class, e);
        }
        return "";
    }
    public static String classificationToString2(String classification) {
        try {
            return NumberCodeUtil.getNumberCodeValue("IMPROVETYPE", classification);
        }
        catch(Exception e) {
            Kogger.error(ImproveBoardData.class, e);
        }
        return "";
    }

}
