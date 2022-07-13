package e3ps.bom.command.loadexcelbom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import wt.content.ApplicationData;
import wt.content.ContentHolder;
import wt.content.ContentServerHelper;
import wt.content.HolderToContent;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTIOException;
import e3ps.common.drm.E3PSDRMHelper;
import e3ps.common.util.CommonUtil;
import ext.ket.shared.log.Kogger;


public class ContentDownloadStream implements Serializable
{
	private static final long serialVersionUID = -7201426360269959935L;

	private static final int BUFSIZ = 8192;
	private transient Object applicationData;
	private transient boolean download;
    private transient FilterInputStream stream;
    private transient String clientIP;

	public ContentDownloadStream(Object applicationData)
	{
	    download = false;
	    this.applicationData = applicationData;
	}

	public ContentDownloadStream( Object applicationData, String clientIP )
	{
	    download = false;
	    this.applicationData = applicationData;
	    this.clientIP = clientIP;
	}

	public InputStream getInputStream(){
		return stream;
	}

	private void readObject(ObjectInputStream objectinputstream) throws IOException, ClassNotFoundException
	{
	    boolean flag = objectinputstream.readBoolean();

	    Kogger.debug(getClass(), "flag === " + flag);
	    if(flag)
	    {
	        ContentDownloadThread contentdownloadthread = (ContentDownloadThread)Thread.currentThread();
	        synchronized(contentdownloadthread)
	        {
	            while( !contentdownloadthread.isReady() )
	            {
	                try
	                {

	                    contentdownloadthread.wait();
	                }
	                catch(InterruptedException interruptedexception) { }
	            }

	            FilterInputStream filterinputstream = new FilterInputStream(objectinputstream)
	            {
	                public void close()
	                {
	                }
	            };

	            contentdownloadthread.setInputStream(filterinputstream);
	            contentdownloadthread.notifyAll();

	            while( !contentdownloadthread.isReady() )
	            {
	                try
	                {
	                    contentdownloadthread.wait();
	                }
	                catch(InterruptedException interruptedexception1) { }
	            }
	        }
	    }
	    else
	    {
	        applicationData = (Object)objectinputstream.readObject();
	        download = true;
	    }

	}

	private void writeObject(ObjectOutputStream objectoutputstream) throws IOException
	{
	    objectoutputstream.writeBoolean(download);

	    if( download )
	    {
	        InputStream inputstream = null;

	        try
	        {
	            inputstream = findContentStream(applicationData);

	            byte abyte0[] = new byte[BUFSIZ];
	            int i;
	            while(( i = inputstream.read(abyte0, 0, BUFSIZ)) > 0 )
	            {
	                objectoutputstream.write(abyte0, 0, i);
	            }
	        }
	        catch(WTException wtexception)
	        {
	            throw new WTIOException((String)null, wtexception);
	        }
	        finally
	        {
	            if( inputstream != null )
	                inputstream.close();
	        }
	    }
	    else
	    {
	        objectoutputstream.writeObject(applicationData);
	    }
	}

	private InputStream findContentStream(Object applicationdata) throws WTException
	{
		SessionContext sessioncontext = SessionContext.newContext();

        try
        {
            SessionHelper.manager.setAdministrator();

            Object obj = null;
            String drmFileName = null;
            boolean isDrm = false;
            if( applicationdata instanceof DRMRequestData )
            {
            	DRMRequestData dramdata = (DRMRequestData)applicationdata;
            	obj = dramdata.object;
            	drmFileName = dramdata.fileName;
            	isDrm = true;
            }
            else
            {
            	obj = applicationdata;
            }

            if( obj instanceof ApplicationData )
            {
            	ApplicationData appData = (ApplicationData)obj;
            	InputStream in = ContentServerHelper.service.findContentStream((ApplicationData)obj);

            	if( isDrm && E3PSDRMHelper.isDrmApprove(drmFileName) )
            	{
            		HolderToContent hlink = appData.getHolderLink();
            		String holderOid = "";

            		if( hlink != null )
            		{
            			holderOid = CommonUtil.getOIDString(hlink.getContentHolder());
            		}

            		File file = E3PSDRMHelper.download(in, drmFileName, holderOid, clientIP);

            		try
            		{
						in = new FileInputStream(file);
					}
            		catch( FileNotFoundException e )
            		{
						Kogger.error(getClass(), e);
						throw new WTException(e);
					}
            	}

            	return in;
            }
            else if( obj instanceof RequestData )
            {
            	RequestData data = (RequestData)obj;
            	String holderOid = data.holderOid;
        		String applicationOid = data.applicationOid;

        		ContentHolder contentHolder = (ContentHolder)CommonUtil.getObject(holderOid);
        		ApplicationData ad = (ApplicationData)CommonUtil.getObject(applicationOid);
        		String fileName = ad.getFileName();

        		try
        		{
					ad.setHolderLink( getHolderToContent(contentHolder, ad) );
        		}
        		catch( Exception e )
        		{
					Kogger.error(getClass(), e);
					throw new WTException(e);
				}

        		InputStream in = ContentServerHelper.service.findContentStream(ad);

        		if( isDrm && E3PSDRMHelper.isDrmApprove(drmFileName) )
        		{
            		File file = E3PSDRMHelper.download(in, drmFileName, holderOid, clientIP);

            		try
            		{
						in = new FileInputStream(file);
					}
            		catch( FileNotFoundException e )
            		{
						Kogger.error(getClass(), e);
						throw new WTException(e);
					}
            	}

				return in;
            }
            else
            {
            	InputStream in = null;

            	try
            	{
				   in = new FileInputStream(new File((String)obj));
				}
            	catch( FileNotFoundException e )
            	{
					Kogger.error(getClass(), e);
					throw new WTException(e);
				}

				if( isDrm )
				{
            		File file = E3PSDRMHelper.download(in, "ContentDownloadLocalFile", "", clientIP);

            		try
            		{
						in = new FileInputStream(file);
					}
            		catch( FileNotFoundException e )
            		{
						Kogger.error(getClass(), e);
						throw new WTException(e);
					}
            	}

				return in;
            }
        }
        finally
        {
        	SessionContext.setContext(sessioncontext);
        }
	}

	private HolderToContent getHolderToContent(ContentHolder contentHolder, ApplicationData ad) throws Exception
	{
		QuerySpec spec = new QuerySpec(ApplicationData.class, wt.content.HolderToContent.class);
		spec.appendWhere(new SearchCondition(ApplicationData.class, "thePersistInfo.theObjectIdentifier.id", "=",  ad.getPersistInfo().getObjectIdentifier().getId()));
		QueryResult queryresult = PersistenceHelper.manager.navigate(contentHolder, "theContentItem" , spec, false);//(pp, "theContentItem", wt.content.HolderToContent.class, false);

		Kogger.debug(getClass(), queryresult.size());

		HolderToContent holdertocontent = (HolderToContent)queryresult.nextElement();

		return holdertocontent;
	}
}

/* $Log: not supported by cvs2svn $
/* Revision 1.3  2011/03/24 03:06:53  mjoh
/* *** empty log message ***
/*
/* Revision 1.2  2011/01/04 01:53:21  mjoh
/* DRM적용 : Client IP 처리
/*
/* Revision 1.1  2010/09/10 04:40:53  syoh
/* 최초등록
/*
/* Revision 1.4  2010/01/02 13:12:48  administrator
/*
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.3  2009/12/27 12:26:27  administrator
/*
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.2  2009/10/27 06:57:42  administrator
/*
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.1  2009/08/11 04:16:22  administrator
/* Init Source
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.1  2009/02/25 01:26:02  smkim
/* 최초 작성
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.3  2008/09/29 02:35:09  sjhan
/* *** empty log message ***
/*
/* Revision 1.2  2008/06/02 08:32:00  administrator
/* 수정
/*
/* Revision 1.1  2008/03/12 04:51:01  hyun
/* content service 수정
/*
/* Revision 1.2  2008/02/19 10:04:42  hyun
/* 협력업체 도면발송
/*
/* Revision 1.1  2007/12/12 06:44:20  plmadmin
/* *** empty log message ***
/*
/* Revision 1.1  2006/01/25 05:39:41  khkim
/* *** empty log message ***
/* */
