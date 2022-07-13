package e3ps.groupware.board;

import e3ps.common.util.NumberCodeUtil;
import ext.ket.shared.log.Kogger;

/*
 * [PLM System 1차개선]
 * 파일명 : ImproveBoardData.java
 * 설명 : ImproveBoard 객체 wrapper
 * 작성일자 : 2013. 06. 13
 * 작성자 : 김무준
 */

public class ImproveBoardData extends HelpBoardData {

    // 분류
    public static final String CLASS_ETC = "ETC";

    public ImproveBoardData(ImproveBoard boardObj, int type) {
        super(boardObj, type);
    }

    @Override
    public ImproveBoard getBoardObj() {
        return (ImproveBoard) boardObj;
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

    public String getDivision() {
        return getBoardObj().getDivision();
    }
    public String getDivisionStr() {
        return divisionToString(getDivision());
    }

    public static String stateToString(String state) {
        try {
            return NumberCodeUtil.getNumberCodeValue("IMPROVESTATUS", state);
        }
        catch(Exception e) {
            Kogger.error(ImproveBoardData.class, e);
        }
        return "";
    }

    public static String classificationToString(String classification) {
        try {
            return NumberCodeUtil.getNumberCodeValue("IMPROVETYPE", classification);
        }
        catch(Exception e) {
            Kogger.error(ImproveBoardData.class, e);
        }
        return "";
    }

    public static String divisionToString(String division) {
        try {
            return NumberCodeUtil.getNumberCodeValue("IMPROVEITEM", division);
        }
        catch(Exception e) {
            Kogger.error(ImproveBoardData.class, e);
        }
        return "";
    }

    public String getCause(){
    	if(getBoardObj().getCause() != null){
    		return getBoardObj().getCause();
    	}else{
    		return "";
    	}
    }

    public String getHandleResult(){
    	if(getBoardObj().getHandleResult() != null){
    		return getBoardObj().getHandleResult();
    	}else{
    		return "";
    	}
    }
    
    public String getManager(){
	if(getBoardObj().getUser() != null){
	    return getBoardObj().getUser().getFullName();
	}else{
	    return "";
	}
    }

}
