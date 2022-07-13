package e3ps.bom.command.loadexcelbom;

import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.util.WTException;
import ext.ket.shared.log.Kogger;



public class ContentDownload implements RemoteAccess, Serializable, Runnable
{


	/**
	 *
	 */
	private static final long serialVersionUID = 3478805821606945578L;
	/**
	 *
	 */

	private transient Vector workers;
	private transient ContentDownloadThread downloadThread;
	private transient Throwable downloadException;
	private transient RemoteMethodServer remoteMethodServer;
	private transient URL url;
	private transient Vector reVector;
	private transient String clientIP = "";

	public ContentDownload()
	{
		//remoteMethodServer = RemoteMethodServer.getDefault();
	}

	public ContentDownload( HttpServletRequest req ) {
		this.clientIP = req.getRemoteAddr();
	}

	public ContentDownload(URL url) {
		this.url = url;
	}

	public void addContentStream(Object applicationData)
	{
	    addWorker(new ContentDownloadStream(applicationData, clientIP));
	}

	protected void addWorker(Object obj)
	{
	    if(workers == null){
	    	workers = new Vector();
	    }
	    workers.add(obj);
	}

	public void execute()
	{
	    downloadThread = new ContentDownloadThread(this);
	    downloadThread.start();
	}

	public void run()
	{
	    try
	    {
	        Class aclass[] = {
	            Vector.class
	        };
	        Object aobj[] = {
	            workers
	        };
	        if(url != null) {
	        	RemoteMethodServer rs = RemoteMethodServer.getInstance(url);
	            Object obj = rs.invoke("execute", null, this, aclass, aobj);
	        }else{
	        	RemoteMethodServer.getDefault().invoke("execute", null, this, aclass, aobj);
	        }
	    }
	    catch(InvocationTargetException invocationtargetexception)
	    {
	    	Kogger.error(getClass(), invocationtargetexception);
	        downloadException = invocationtargetexception.getTargetException();
	    }
	    catch(Throwable throwable)
	    {
	    	Kogger.error(getClass(), throwable);
	        downloadException = throwable;
	    }finally{

	    }
	}

	public Vector execute(Vector vector)
	    throws WTException
	{

	    return vector;
	}

	public InputStream getInputStream()
	{
		return downloadThread.getInputStream();

	}

	public void done()
	{
	    if(downloadThread != null){
	        downloadThread.done();
	    }
	}

	public void checkStatus()
	    throws WTException
	{
	    if(downloadException != null)
	    {
	        if(downloadException instanceof WTException)
	            throw (WTException)downloadException;
	        else
	            throw new WTException(downloadException);
	    } else
	    {
	        return;
	    }
	}


}

/* $Log: not supported by cvs2svn $
/* Revision 1.2  2011/01/04 01:53:09  mjoh
/* DRM적용 : Client IP 처리
/*
/* Revision 1.1  2010/09/10 04:40:53  syoh
/* 최초등록
/*
/* Revision 1.3  2010/01/27 02:50:37  administrator
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
/* Revision 1.3  2006/01/27 00:45:09  khkim
/* *** empty log message ***
/*
/* Revision 1.2  2006/01/25 10:20:00  khkim
/* *** empty log message ***
/*
/* Revision 1.1  2006/01/25 05:39:41  khkim
/* *** empty log message ***
/* */
