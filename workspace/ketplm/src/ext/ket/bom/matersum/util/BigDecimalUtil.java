package ext.ket.bom.matersum.util;

import java.math.BigDecimal;
import java.math.BigInteger;

import ext.ket.shared.log.Kogger;

final public class BigDecimalUtil {

    final static boolean isDebug = true;
    
    public static BigDecimal plus(BigDecimal a, BigDecimal b) {
	BigDecimal c = a.add(b);
	if(isDebug)
	    Kogger.debug( BigDecimalUtil.class, c);
	return c;
    }
    
    public static BigDecimal minus(BigDecimal pre, BigDecimal post) {
	BigDecimal c = pre.subtract(post);
	if(isDebug)
	    Kogger.debug( BigDecimalUtil.class, c);	
	return c;
    }
    
    public static BigDecimal mutipleWithRoundDown(BigDecimal a, BigDecimal b, int scale) {
	BigDecimal c = a.multiply(b);
	
//	BigInteger pre = c.toBigInteger();
//      BigDecimal post = c.subtract(new BigDecimal(pre.toString()));
//
//      Kogger.debug( BigDecimalUtil.class, "정수부분 : "+ pre);
//      Kogger.debug( BigDecimalUtil.class, "소수부분 : "+ post);
        
	c = c.setScale(scale, BigDecimal.ROUND_DOWN);
	if(isDebug)
	    Kogger.debug( BigDecimalUtil.class, c);
	return c;
    }
    
    public static BigDecimal divideWithRoundDown(BigDecimal up, BigDecimal down, int scale) {
	
	BigDecimal c = null;
	try {

	    c = up.divide(down);
	    if(isDebug)
		Kogger.debug( BigDecimalUtil.class, c);

	} catch (Exception e) {
	    
	    c = up.divide(down, scale, BigDecimal.ROUND_DOWN);
	    if(isDebug)
		Kogger.debug( BigDecimalUtil.class, c);
	}
	
	return c;
    }
    
    public static BigDecimal divideWithRoundUp(BigDecimal up, BigDecimal down, int scale) {
	
	BigDecimal c = null;
	try {

	    c = up.divide(down);
	    //if(isDebug)
		//Kogger.debug( BigDecimalUtil.class, c);

	} catch (Exception e) {
	    
	    c = up.divide(down, scale, BigDecimal.ROUND_UP );
	    //if(isDebug)
		//Kogger.debug( BigDecimalUtil.class, c);
	}
	
	return c;
    }
    
    public static boolean isPreBiggerThanPost(BigDecimal pre, BigDecimal post){
	
	int ret = pre.compareTo(post);
	
	if (isDebug)
	    Kogger.debug(BigDecimalUtil.class, String.valueOf(ret) + " " + (ret > 0) );
	
	return ret > 0;
    }
    
    public static void main(String[] args) {


	BigDecimal preNum = new BigDecimal("3.14");
	BigDecimal postNum = new BigDecimal("2.00");
	
	BigDecimal part = new BigDecimal(100);
	BigDecimal total = new BigDecimal(10);
	
	BigDecimalUtil.isPreBiggerThanPost(postNum, preNum);
	BigDecimalUtil.isPreBiggerThanPost(part, total);

//	BigDecimalUtil.mutipleWithRoundDown(preNum, postNum, 3);
//	BigDecimalUtil.divideWithRoundDown(preNum, postNum, 3);
//	BigDecimalUtil.plus(preNum, postNum);
//	BigDecimalUtil.minus(preNum, postNum);
//	BigDecimalUtil.divideWithRoundDown(postNum, preNum, 10);
//	BigDecimalUtil.divideWithRoundDown(postNum, preNum, 3);

    }

    
}
