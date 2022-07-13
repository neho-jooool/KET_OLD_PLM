/**
 * @(#) E3PSClassTableExpression.java Copyright (c) e3ps. All rights reserverd
 * @version 1.00
 * @since jdk 1.6
 * @createdate 2010. 12. 15..
 * @author kim ki hong, khkim@e3ps.com
 * @desc 
 */
package e3ps.common.query;
import java.util.Vector;

import e3ps.project.E3PSProject;

import wt.query.ClassTableExpression;
import wt.query.QuerySpec;
import wt.util.WTException;


public class E3PSClassTableExpression extends ClassTableExpression{

	public E3PSClassTableExpression(){
		super();
	}
	
	public E3PSClassTableExpression(Class class1){
		super(class1);
	}
	
	public boolean isAccessControlled()throws WTException
    {
		return false;
    }
	
	 protected boolean getAccessControlRequired(Vector vector)
    {
        
        return false;
    }
	
	public static void main(String args[])throws Exception{
		QuerySpec spec = new QuerySpec();
		spec.appendFrom(new E3PSClassTableExpression(E3PSProject.class));
	}
}
