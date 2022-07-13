package e3ps.common.content.remote;

import java.io.InputStream;
import java.util.Vector;

import wt.util.WTThread;

public class ContentDownloadThread extends WTThread
{
	private boolean ready;
    private boolean done;
    private InputStream inputStream;
    private String fileName;
    
    public ContentDownloadThread(Runnable runnable)
    {
        super(runnable);
        ready = false; 
        done = false;
    }

    public boolean isReady()
    {
        return ready || done;
    }
    
    

    public void setInputStream(InputStream inputstream)
    {   
    	inputStream = inputstream;
        ready = false;
    }

    public synchronized InputStream getInputStream()
    {
        ready = true;
        notifyAll();
        while(ready) 
            try
            {
                wait();
            }
            catch(InterruptedException interruptedexception) { }
        Vector v = new Vector();
        return inputStream;
    }

    public synchronized void done()
    {
        done = true;
        notifyAll();
    }

}

/* $Log: not supported by cvs2svn $
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
