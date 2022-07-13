package e3ps.edm.clients.batch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import wt.services.ServiceProviderHelper;
import wt.util.WTContext;

public class EPMLoaderApplet extends JApplet {

	private ExplorerDaemon explorerDaemon;
	private Thread explorerThread;

	private EPMLoadContainer container;

	public void init() {
		WTContext wtcontext = WTContext.getContext();
		WTContext.init(this);

		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					WTContext.init(EPMLoaderApplet.this);
				} } );
		}
		catch (InvocationTargetException localInvocationTargetException) {
			localInvocationTargetException.printStackTrace();
		} catch (InterruptedException localInterruptedException) {
			localInterruptedException.printStackTrace();
		}

		commonInit(wtcontext);
	}

	public void commonInit(WTContext wtcontext) {
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(213, 213, 199));
	}

	public void commonStart(WTContext wtcontext, String paramString) {

	    final String userId = wtcontext.getParameter("userId");
	    final String type = wtcontext.getParameter("manageType");
	    final String drm = (wtcontext.getParameter("drm")==null)? "false":wtcontext.getParameter("drm");
	    final String language = wtcontext.getParameter("language");    // [PLM System 1차개선] 다국어 적용, 2013-08-29, BoLee
	    final boolean isdrm = "true".equals(drm.toLowerCase());

	    System.out.println("# Batch Loader start !!!");

	    //ServiceProviderHelper.setRemoteClientFlag(true);

		try {
			wt.method.RemoteMethodServer methodServer = wt.method.RemoteMethodServer.getDefault();
			methodServer.setAuthenticator(AuthHandler.getMethodAuthenticator(userId));

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			/*
			//SwingUtilities.invokeAndWait(new Runnable() {
			//	public void run() {
			//		try {
						SwingUtilities.invokeLater( new Runnable() {
							public void run() { EPMLoadContainer.invokeLater(userId, type, isdrm); }
						});
			//		}
			//		catch (Exception ex) {
			//			ex.printStackTrace();
			//		}
			//	}
			//});
				*/

			new Thread(new Runnable() {
				public void run() {
					EPMLoadContainer.invokeLater(userId, type, isdrm, language);   // [PLM System 1차개선] 다국어 적용, 2013-08-29, BoLee
				}
			}).start();

			//EPMLoadContainer.invokeLater(userId, type);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		catch (Throwable e) {
			e.printStackTrace();
		}

	    startExplorerDaemon();
	}

	public void start(){
		new Thread(new Runnable() {
		      public void run() {
		    	  EPMLoaderApplet.this.commonStart(WTContext.getContext(), null);
		      }
		    }).start();
	}

	public void stop() {
		WTContext wtontext = WTContext.getContext(this);
		wtontext.stop(this);
	}

	public void destroy() {
		if ((this.explorerDaemon != null) && (this.explorerThread != null)) {
			this.explorerDaemon.markDone();
			this.explorerThread.interrupt();
		}

		WTContext wtcontext = WTContext.getContext();
		if(wtcontext != null) {
			wtcontext.destroy(this);
		}
	}

	private void startExplorerDaemon() {
		if (this.explorerDaemon == null) {
			this.explorerDaemon = new ExplorerDaemon();
			this.explorerThread = new Thread(this.explorerDaemon, "explorerDaemon");
			this.explorerThread.setDaemon(true);
			this.explorerThread.setPriority(1);
			this.explorerThread.start();
		}
	}

	public static void main(String args[]) throws Exception {
		final String userId = "wcadmin";
    	final String type = "MOLD_DRAWING";
    	final String language = "ko";
    	final boolean isdrm = false;

		wt.method.RemoteMethodServer methodServer = wt.method.RemoteMethodServer.getDefault();
		if (methodServer.getUserName() == null) {
			methodServer.setUserName("wcadmin");
			methodServer.setPassword("wcadmin");
		}

		final EPMLoaderApplet applet = new EPMLoaderApplet();
		//applet.init();

		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
				try {
					applet.container.invokeLater(userId, type, isdrm, language);   // [PLM System 1차개선] 다국어 적용, 2013-08-29, BoLee
				}
				catch(Exception e) {
					e.printStackTrace();
				}
            }
        });


	}

	public class ExplorerDaemon implements Runnable {
		private static final int SLEEP_TIME = 60000;
		private boolean keepAlive = true;

		public void run() {
			try {
				WTContext localWTContext = WTContext.getContext(EPMLoaderApplet.this);
				while (this.keepAlive) {
					Thread.sleep(60000L);
					InputStream localInputStream = localWTContext.getResourceAsStream("/netmarkets/jsp/explorer/keepalive.jsp");
					if (localInputStream != null) {
						localInputStream.close();
					}
				}
			} catch (InterruptedException e) {
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void markDone() {
			this.keepAlive = false;
		}
	}
}
