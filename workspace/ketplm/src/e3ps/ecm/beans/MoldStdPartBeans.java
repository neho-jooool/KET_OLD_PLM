package e3ps.ecm.beans;

import java.util.ArrayList;
import java.util.Hashtable;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import e3ps.bom.service.KETPartHelper;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.StringUtil;
import e3ps.ecm.entity.KETMoldECADocLink;
import e3ps.ecm.entity.KETMoldStdPartLine;
import e3ps.ecm.entity.KETMoldStdPartLink;
import ext.ket.shared.log.Kogger;

public class MoldStdPartBeans {

	public boolean isExistStdPartLine( String docLinkOid )
	{
		boolean isExist = false;
		QueryResult qr = null;
		
		try
		{
			qr = PersistenceHelper.manager.navigate( (KETMoldECADocLink)KETObjectUtil.getObject(docLinkOid) , "part", KETMoldStdPartLink.class );
			
			if( qr.hasMoreElements() )
			{
				isExist = true;
			}
		}
		catch( Exception e )
		{
			Kogger.error(getClass(), e);
		}
		
		return isExist;
	}
	
	public ArrayList<Hashtable<String, String>> getStdPartLineList( String docLinkOid )
	{
		ArrayList<Hashtable<String, String>> partLineList = new ArrayList<Hashtable<String, String>>();
		
		QueryResult qr = null;
		KETMoldStdPartLine partLine = null;
		Hashtable<String, String> partHash = null;
		
		try
		{
			qr = PersistenceHelper.manager.navigate( (KETMoldECADocLink)KETObjectUtil.getObject(docLinkOid), "part", KETMoldStdPartLink.class );
			while( qr.hasMoreElements() )
			{
				partLine = (KETMoldStdPartLine)qr.nextElement();
				
				partHash = new Hashtable<String, String>();
				partHash.put( "partOid" , StringUtil.checkNull(partLine.getPartOid()) );
				partHash.put( "partNo" , StringUtil.checkNull(partLine.getPartNo()) );
				partHash.put( "partName" , KETPartHelper.service.getPart(StringUtil.checkNull(partLine.getPartNo())).getName() );
				partHash.put( "changeType" , StringUtil.checkNull(partLine.getChangeType()) );
				partHash.put( "description", StringUtil.checkNull(partLine.getDescription()) );
				partHash.put( "moldEcoOid" , StringUtil.checkNull(partLine.getMoldEcoOid()) );
				
				partLineList.add( partHash );				
			}
		}
		catch( Exception e )
		{
			Kogger.error(getClass(), e);
		}
		
		return partLineList;
	}
}
