/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : XlsCellFormat.java
 * 작성자 : 신대범
 * 작성일자 : 2010. 12. 10.
 */
package e3ps.bom.common.util;

import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import ext.ket.shared.log.Kogger;

public class XlsCellFormat {

	public void clearSheet(int x, int y, WritableSheet sheet){
		WritableCellFormat format = null;

		try{
			for(int i=0; i<x; i++){
				for(int j=0; j<y; j++){
					format = new WritableCellFormat();
					format.setBackground(jxl.format.Colour.WHITE );
					format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.NONE);
					sheet.addCell(new jxl.write.Blank( i, j, format));

				}
			}
		}catch(Exception e){
			Kogger.error(getClass(), e);
		}
	}
	
	public WritableCellFormat getTitleFormat() throws Exception {
		WritableCellFormat format = null;
		
		try{
			format = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK));
			format.setBackground(jxl.format.Colour.WHITE );
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setAlignment(jxl.format.Alignment.CENTRE);
			format.setWrap(true);
		}catch(Exception e){
			Kogger.error(getClass(), e);
		}
		return format;
	}
	
	/**==================================================================
	 * 항목명 필드의 속성
	 * @param 
	 * @return XLS CELL FORMAT
	 ====================================================================*/
	public WritableCellFormat getHeadFormat() throws Exception {
		return getHeadFormat(10);
	}

	public WritableCellFormat getHeadFormat(int fontSize) throws Exception {
		return getHeadFormat(fontSize, jxl.format.Alignment.LEFT);
	}
	
	public WritableCellFormat getHeadFormat(int fontSize, jxl.format.Alignment alignment) throws Exception {
		return getHeadFormat(fontSize, alignment, true);
	}
	
	public WritableCellFormat getHeadFormat(int fontSize, jxl.format.Alignment alignment, boolean isWrap) throws Exception {
		WritableCellFormat format = null;
		
		try{
			format = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, fontSize, WritableFont.BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK));
			format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN );
			format.setBackground(jxl.format.Colour.GRAY_25 );
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setWrap(isWrap);
			format.setAlignment(alignment);
		}catch(Exception e){
			Kogger.error(getClass(), e);
		}
		return format;
	}
	
	
	/**==================================================================
	 * 항목명 필드의 속성
	 * @param 
	 * @return XLS CELL FORMAT
	 ====================================================================*/
	public WritableCellFormat getSubHeadFormat() throws Exception {
		return getSubHeadFormat(10);
	}
	
	public WritableCellFormat getSubHeadFormat(int fontSize) throws Exception {
		return getSubHeadFormat(fontSize, jxl.format.Alignment.CENTRE);
	}
	
	public WritableCellFormat getSubHeadFormat(int fontSize, jxl.format.Alignment alignment) throws Exception {
		return getSubHeadFormat(fontSize, alignment, false);
	}
	
	public WritableCellFormat getSubHeadFormat(int fontSize, jxl.format.Alignment alignment, boolean isWrap) throws Exception {
		WritableCellFormat format = null;
		
		try{
			format = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, fontSize, WritableFont.NO_BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK));
			format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN );
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setWrap(isWrap);
			format.setAlignment(alignment);
		}catch(Exception e){
			Kogger.error(getClass(), e);
		}
		return format;
	}

	public WritableCellFormat getSubLeftHeadFormat(int fontSize, jxl.format.Alignment alignment) throws Exception {
		WritableCellFormat format = null;
		
		try{
			format = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, fontSize, WritableFont.NO_BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK));
			format.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THIN );
			format.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.NONE );
			format.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THIN );
			format.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.THIN );
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setAlignment(alignment);
		}catch(Exception e){
			Kogger.error(getClass(), e);
		}
		return format;
	}
	
	public WritableCellFormat getSubRightHeadFormat(int fontSize, jxl.format.Alignment alignment) throws Exception {
		WritableCellFormat format = null;
		
		try{
			format = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, fontSize, WritableFont.NO_BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK));
			format.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.NONE );
			format.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.THIN );
			format.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THIN );
			format.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.THIN );
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setAlignment(alignment);
		}catch(Exception e){
			Kogger.error(getClass(), e);
		}
		return format;
	}
	
	public WritableCellFormat getSubMiddleHeadFormat(int fontSize, jxl.format.Alignment alignment) throws Exception {
		WritableCellFormat format = null;
		
		try{
			format = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, fontSize, WritableFont.NO_BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK));
			format.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.NONE );
			format.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.NONE );
			format.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THIN );
			format.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.THIN );
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setAlignment(alignment);
		}catch(Exception e){
			Kogger.error(getClass(), e);
		}
		return format;
	}
	
	public WritableCellFormat getInputLineFormat() throws Exception {
		return getInputLineFormat(jxl.format.Colour.BRIGHT_GREEN);
	}
	
	public WritableCellFormat getInputLineFormat(jxl.format.Colour bgColor) throws Exception {
		WritableCellFormat format = null;
		
		try{
			format = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 9, WritableFont.BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.DARK_BLUE));
			format.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.NONE );
			format.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.NONE );
			format.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THIN );
			format.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.THIN );
			format.setBackground(bgColor);
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setAlignment(jxl.format.Alignment.RIGHT);
		}catch(Exception e){
			Kogger.error(getClass(), e);
		}
		return format;
	}
	
	public WritableCellFormat getCommonTextFormat() throws Exception {
		WritableCellFormat format = null;
		
		try{
			format = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK));
			format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN );
			format.setBackground(jxl.format.Colour.WHITE );
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setAlignment(jxl.format.Alignment.LEFT);
		}catch(Exception e){
			Kogger.error(getClass(), e);
		}
		return format;
	}
	
	//public WritableCellFormat getBlankFormat() throws Exception {
	//	return getBlankFormat(9);
	//}

	/*
	 * 이건 jxl 버젼 차이로 안되는건가???
	 * public WritableCellFormat getBlankFormat(int fontSize) throws Exception {
		WritableCellFormat format = null;

		try{

			format = new WritableCellFormat(NumberFormats.TEXT);
			format.setFont(new WritableFont(WritableFont.ARIAL, fontSize, WritableFont.BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.DARK_BLUE));
			format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN );
			format.setBackground(jxl.format.Colour.WHITE );
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setAlignment(jxl.format.Alignment.LEFT);

		}catch(Exception e){
			Kogger.error(getClass(), e);
		}
		return format;
	}*/
	
	public WritableCellFormat getCommentFormat() throws Exception {
		WritableCellFormat format = null;
		
		try{
			format = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 8, WritableFont.NO_BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.LIGHT_BLUE));
			format.setBackground(jxl.format.Colour.WHITE );
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format.setAlignment(jxl.format.Alignment.LEFT);
		}catch(Exception e){
			Kogger.error(getClass(), e);
		}
		return format;
	}

}
