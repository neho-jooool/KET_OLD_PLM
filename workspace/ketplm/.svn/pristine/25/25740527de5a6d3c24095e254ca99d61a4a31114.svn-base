/* bcwti
 *
 * Copyright (c) 2010 Parametric Technology Corporation (PTC). All Rights Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */

package wt.fv.master;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer; // Preserved unmodeled dependency
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import wt.access.AccessControlEvent;
import wt.access.AccessControlHelper;
import wt.access.AccessPermission;
import wt.access.NotAuthorizedException;
import wt.admin.Selector;
import wt.cache.CacheManager;
import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentHttp;
import wt.content.ContentServiceEvent;
import wt.content.HolderToContent;
import wt.content.QuarantinedContentManager;
import wt.content.StreamData;
import wt.content.Streamed;
import wt.events.KeyedEvent;
import wt.fc.ObjectIdentifier;
import wt.fc.ObjectNoLongerExistsException;
import wt.fc.ObjectReference;
import wt.fc.PersistInfo;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceManagerEvent;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTCollection;
import wt.fc.collections.WTHashSet;
import wt.fc.collections.WTKeyedHashMap;
import wt.fc.collections.WTKeyedMap;
import wt.fc.collections.WTSet;
import wt.fv.AutoVaultCleanupCriteria;
import wt.fv.FileFolder;
import wt.fv.FileNameUSNPool;
import wt.fv.FvFileCanNotBeStored;
import wt.fv.FvFolder;
import wt.fv.FvHelper;
import wt.fv.FvHost;
import wt.fv.FvItem;
import wt.fv.FvMount;
import wt.fv.FvPolicyItem;
import wt.fv.FvPolicyRule;
import wt.fv.FvProperties;
import wt.fv.FvService;
import wt.fv.FvServiceEvent;
import wt.fv.FvSurrogate;
import wt.fv.FvVault;
import wt.fv.RootFolder;
import wt.fv.StandardFvService;
import wt.fv.StoredItem;
import wt.fv.Vault;
import wt.fv.fvResource;
import wt.fv.configurator.SiteDesc;
import wt.fv.contentmover.ContentMover;
import wt.fv.uploadtocache.BackupedFile;
import wt.httpgw.CGIConstants;
import wt.httpgw.HTTPRequest;
import wt.httpgw.HTTPResponse;
import wt.index.IndexPolicyHelper;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.intersvrcom.InterSvrComConstants;
import wt.intersvrcom.InterSvrComHelper;
import wt.intersvrcom.Site;
import wt.intersvrcom.SiteChecksumLevel;
import wt.intersvrcom.StandardInterSvrComService;
import wt.intersvrcom.URLAuthenticator;
import wt.log4j.LogR;
import wt.method.MethodContext;
import wt.org.DirectoryContextProvider;
import wt.org.OrganizationServicesHelper;
import wt.org.WTGroup;
import wt.org.WTOrganization;
import wt.org.WTPrincipal;
import wt.org.WTPrincipalReference;
import wt.pds.PartialResultException;
import wt.pds.StatementSpec;
import wt.pom.Transaction;
import wt.pom.TransactionListener;
import wt.preference.PreferenceDefinition;
import wt.preference.PreferenceHelper;
import wt.preference.PreferenceInstance;
import wt.query.ArrayExpression;
import wt.query.ClassAttribute;
import wt.query.CompoundQuerySpec;
import wt.query.ConstantExpression;
import wt.query.ExistsExpression;
import wt.query.NegatedExpression;
import wt.query.OrderBy;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.SetOperator;
import wt.query.TableExpression;
import wt.query.WhereExpression;
import wt.queue.ProcessingQueue;
import wt.queue.QueueHelper;
import wt.queue.StandardQueueService;
import wt.services.Manager;
import wt.services.ManagerException;
import wt.services.ManagerServiceFactory;
import wt.services.ServiceEventListenerAdapter;
import wt.services.StandardManager;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.session.SessionServerHelper;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import wt.util.WTMessage;
import wt.util.WTProperties;
import wt.util.WTPropertyVetoException;
import wt.util.WTRuntimeException;
import wt.util.WTThread;
import wt.wrmf.delivery.ActionShippingItem;
import wt.wrmf.delivery.DeliveryServiceEvent;
import wt.wrmf.delivery.DeliveryType;
import wt.wrmf.delivery.MethodInvocationDesc;
import wt.wrmf.delivery.ReceiverHelper;
import wt.wrmf.delivery.ReturnPreference;
import wt.wrmf.delivery.ShippingHelper;
import wt.wrmf.delivery.ShippingItem;
import wt.wrmf.delivery.ShippingLabel;
import wt.wrmf.delivery.SiteAddress;
import wt.wrmf.delivery.TransparentInbox;
import wt.wrmf.delivery.TransportType;
import wt.wrmf.delivery.WTDeliveryException;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.AuthUtil;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;

/**
 * Standard implementation of the file vault Master service. Contains implementation of the client and server APIs.These APIs can be accessed using following pattern:
 * MasterHelper.service.API_Name()
 * 
 * <p>
 * Use the <code>newStandardMasterService</code> static factory method(s), not the <code>StandardMasterService</code> constructor, to construct instances of this class. Instances
 * must be constructed using the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * <BR>
 * <BR>
 * <B>Supported API: </B>true <BR>
 * <BR>
 * <B>Extendable: </B>false
 * 
 * @version 1.0
 **/
public class StandardMasterService extends StandardManager implements MasterService, MasterServiceSvr, Serializable {
	private static final String RESOURCE = "wt.fv.master.masterResource";
	private static final String CLASSNAME = StandardMasterService.class.getName();

	private static final Logger logger = LogR.getLogger("wt.fv.master");

	private static final String outboxName = "CR_Outbox";
	private static final Class outboxType = wt.wrmf.delivery.PerDestinationOutbox.class;
	private static final String inboxName = "CR_Inbox";
	private static final Class inboxType = wt.wrmf.delivery.TransparentInbox.class;
	private static String ADHOC_CACHING_QUEUE = "Adhoc_caching_queue";
	private static String PREDICTIVE_CACHING_QUEUE = "Predictive_Caching_Queue";
	private static final boolean PREDICTIVE_CACHING_ENABLED;
	private static String REPLICATION_MANAGERS_GROUP = "Replication Managers";
	// private static final String transportName = "GZIPHttpPipe";
	private static final String transportName = null;
	private static final int threadNumMax;
	private static int threadCount;
	private static int bufferSize;
	private static int siteConfigDeliveryAttempts;
	private static int millisecsToWait;
	private static Executor pool;
	private static SiteChecksumLevel masterSiteChecksumLevel = null;
	public static final String CONTENT_TYPE_HDR = "Content-Type";
	public static final String PULL_ADHOC = "PULL_ADHOC";

	private static FvSurrogate surrogate = new FvSurrogate();
	private static String sitePreferenceDefinitionName = "/wt/content/contentCacheSite";
	private static ReplicationManagerGroupCache _replicationManagerGroupCache = null;
	private static final String localReplicaPrefix = "wt.fv.localReplica.";
	static final String FV_RESOURCE = "wt.fv.fvResource";
	private final static Vector<String> supportedBrowsers = new Vector<String>();

	// This is the definition name which will be stored into the
	// PreferenceDefinition table.

	class FvMasterServiceEventListener extends ServiceEventListenerAdapter {
		public FvMasterServiceEventListener(String manager_name) {
			super(manager_name);
		}

		public void notifyVetoableEvent(Object event) throws WTException, WTPropertyVetoException {
			if (!(event instanceof KeyedEvent)) {
				return;
			}
			KeyedEvent keyedEvent = (KeyedEvent) event;
			String eventKey = keyedEvent.getEventKey();
			Object targetObject = keyedEvent.getEventTarget();

			/*
			 * if ( targetObject instanceof FvItem){ if (keyedEvent.getEventType ().equals (PersistenceManagerEvent.POST_DELETE)){ FvItem fItem = (FvItem)targetObject; QuerySpec qs
			 * = new QuerySpec(FvItem.class); qs.appendWhere( new SearchCondition( FvItem.class, FvItem.STREAM_ID, SearchCondition.EQUAL, fItem.getStreamId()));
			 * 
			 * qs.appendAnd();
			 * 
			 * qs.appendWhere( new SearchCondition( FvItem.class, FvItem.UNIQUE_SEQUENCE_NUMBER, SearchCondition.NOT_EQUAL, fItem.getUniqueSequenceNumber()));
			 * 
			 * QueryResult qr = PersistenceHelper.manager.find ( qs ); if (!qr.hasMoreElements()){ QuerySpec qsStI = new QuerySpec(StreamData.class); qsStI.appendWhere( new
			 * SearchCondition( StreamData.class, Streamed.STREAM_ID, SearchCondition.EQUAL, fItem.getStreamId()));
			 * 
			 * QueryResult qrStI = PersistenceHelper.manager.find ( qsStI ); if (!qrStI.hasMoreElements()){ QuerySpec qsRI = new QuerySpec(ReplicatedItem.class); qsRI.appendWhere(
			 * new SearchCondition( ReplicatedItem.class, Streamed.STREAM_ID, SearchCondition.EQUAL, fItem.getStreamId())); QueryResult qrRI = PersistenceHelper.manager.find ( qsRI
			 * ); if (qrRI.hasMoreElements()){ ReplicatedItem rItem = (ReplicatedItem)qrRI.nextElement(); rItem.setReplication(ReplicationStatus.UNREFERENCED); } } } } } if (
			 * targetObject instanceof StreamData){ if (keyedEvent.getEventType ().equals (PersistenceManagerEvent.REMOVE)){ StreamData sItem = (StreamData)targetObject; QuerySpec
			 * qs = new QuerySpec(FvItem.class); qs.appendWhere( new SearchCondition( FvItem.class, Streamed.STREAM_ID, SearchCondition.EQUAL, sItem.getStreamId()));
			 * 
			 * QueryResult qr = PersistenceHelper.manager.find ( qs ); if (!qr.hasMoreElements()){ QuerySpec qsRI = new QuerySpec(ReplicatedItem.class); qsRI.appendWhere( new
			 * SearchCondition( ReplicatedItem.class, Streamed.STREAM_ID, SearchCondition.EQUAL, sItem.getStreamId())); QueryResult qrRI = PersistenceHelper.manager.find ( qsRI );
			 * if (qrRI.hasMoreElements()){ ReplicatedItem rItem = (ReplicatedItem)qrRI.nextElement(); rItem.setReplication(ReplicationStatus.UNREFERENCED); } } } }
			 */
			/*
			 * if ( targetObject instanceof ContentHolder){ ContentHolder holder = (ContentHolder)targetObject; if (keyedEvent.getEventType ().equals
			 * (PersistenceManagerEvent.POST_DELETE)){ QuerySpec qs = new QuerySpec( ContentHolder.class ); int idxCHolder = qs.addClassList(ContentHolder.class, false); int
			 * idxAppData = qs.addClassList(ApplicationData.class, false); int idxHolderToC = qs.addClassList(HolderToContent.class, false); int idxReplItem =
			 * qs.addClassList(ReplicatedItem.class, true); int idxFvItem = qs.addClassList(FvItem.class, false); int idxStreamData = qs.addClassList(StreamData.class, false);
			 * 
			 * qs.appendWhere ( new SearchCondition(ContentHolder.class, WTAttributeNameIfc.ID_NAME, HolderToContent.class, WTAttributeNameIfc.ROLEA_OBJECT_ID), idxCHolder,
			 * idxHolderToC); qs.appendAnd();
			 * 
			 * qs.appendWhere( new SearchCondition(HolderToContent.class, WTAttributeNameIfc.ROLEB_OBJECT_ID, ApplicationData.class, WTAttributeNameIfc.ID_NAME), idxHolderToC,
			 * idxAppData); qs.appendAnd(); qs.appendOpenParen();
			 * 
			 * qs.appendWhere( new SearchCondition( FvItem.class, WTAttributeNameIfc.ID_NAME, ApplicationData.class, ApplicationData.STREAM_DATA+"."+
			 * WTAttributeNameIfc.REF_OBJECT_ID), idxFvItem, idxAppData); qs.appendAnd(); qs.appendWhere( new SearchCondition( FvItem.class, Streamed.STREAM_ID,
			 * ReplicatedItem.class, Streamed.STREAM_ID), idxFvItem, idxReplItem);
			 * 
			 * qs.appendOr(); qs.appendWhere( new SearchCondition( StreamData.class, WTAttributeNameIfc.ID_NAME, ApplicationData.class, ApplicationData.STREAM_DATA+"."+
			 * WTAttributeNameIfc.REF_OBJECT_ID), idxStreamData, idxAppData);
			 * 
			 * qs.appendAnd(); qs.appendWhere( new SearchCondition( StreamData.class, Streamed.STREAM_ID, ReplicatedItem.class, Streamed.STREAM_ID), idxStreamData, idxReplItem);
			 * 
			 * qs.appendCloseParen();
			 * 
			 * QueryResult qr = PersistenceHelper.manager.find ( qs ); if (qr.hasMoreElements()){ ReplicatedItem rItem = (ReplicatedItem)qr.nextElement();
			 * rItem.setReplication(ReplicationStatus.UNREFERENCED); }
			 * 
			 * } }
			 */
			if (targetObject instanceof ActionShippingItem) {
				if (keyedEvent.getEventType().equals(DeliveryServiceEvent.DELIVERY_FAILED)) {
					logger.debug("ActionShippingItem - DELIVERY_FAILED");
				}
				if (keyedEvent.getEventType().equals(DeliveryServiceEvent.DELIVERY_COMPLETE)) {
					logger.debug("ActionShippingItem - DELIVERY_COMPLETE");
				}
			}
			// to avoid user to set the invalid site name
			else if (targetObject instanceof PreferenceInstance) {
				String updatingSiteName = StandardFvService.IS_UPDATING_SITENAME;
				MethodContext mc = null;
				Boolean isUpdatingSiteName = false;
				try {
					mc = MethodContext.getContext();
					if (mc != null) {
						isUpdatingSiteName = (Boolean) mc.get(updatingSiteName);
					}
				} finally {
					if (mc != null)
						mc.remove(updatingSiteName);
				}
				if (keyedEvent.getEventType().equals(PersistenceManagerEvent.POST_MODIFY)) {
					if (isUpdatingSiteName != null && isUpdatingSiteName) {
						// skip validation when then event is generated in
						// process of updating site name
						// as the new name is not yet stored in DB
						logger.debug("While updating site name - skipping validation of site name " + "in event listener while setting the preference instance value.");
					} else {
						PreferenceInstance entry = (PreferenceInstance) targetObject;
						PreferenceDefinition prefDef = entry.getPreferenceDefinition();

						if (prefDef.getName().equals(sitePreferenceDefinitionName)) { // it is of our
							// interests
							String currSiteName = (String) PreferenceHelper.service.getValue(prefDef.getName(), entry.getWTContainer(), entry.getWTUser());

							Vector allReplSiteNames = PreferencesModel.getAllContReplSitesNames();
							for (int ii = 0; ii < allReplSiteNames.size(); ii++) {
								String siteName = (String) allReplSiteNames.elementAt(ii);
								if (siteName.equals(currSiteName))
									return;
							}
							throw new WTException("Invalid user preferred site name");
						}
					}
				}
			} else if (targetObject instanceof WTGroup) {
				WTGroup group = (WTGroup) targetObject;
				logger.debug("Group : " + group.getName());
				logger.debug("Event : " + keyedEvent.getEventType());
				if (keyedEvent.getEventType().equals(PersistenceManagerEvent.POST_STORE) || keyedEvent.getEventType().equals(PersistenceManagerEvent.POST_MODIFY)
						|| keyedEvent.getEventType().equals(PersistenceManagerEvent.POST_DELETE)) {
					if (group.getName().equals(REPLICATION_MANAGERS_GROUP))
						updateReplicationManagerGroupCache(group);
				}
			}
		}
	}

	public static class ReplicaOverflowListener implements TransactionListener {
		private Object[] fvRepObject;

		public ReplicaOverflowListener(Object object) {
			fvRepObject = new Object[] { object };
		}

		public ReplicaOverflowListener(Object[] object) {
			fvRepObject = object;
		}

		public void notifyRollback() {
			OnOverflow();
		}

		public void notifyCommit() {
			OnOverflow();
		}

		private void OnOverflow() {
			// Start ReplicaFolder/VaultFull notifying Process.
			try {
				onReplicaSaveFailure(fvRepObject);
			}
			// do nothing on exception, cannot throw from here.
			catch (WTException e) {
			} catch (WTPropertyVetoException pve) {
			}
		}
	}

	class MasterEventListener extends ServiceEventListenerAdapter {
		public MasterEventListener(String manager_name) {
			super(manager_name);
		}

		public void notifyVetoableEvent(Object event) throws WTException {
			if (!(event instanceof KeyedEvent)) {
				return;
			}
			KeyedEvent keyedEvent = (KeyedEvent) event;
			String eventKey = keyedEvent.getEventKey();
			if (keyedEvent.getEventType().equals(ContentServiceEvent.POST_DOWNLOAD)) {
				if (event instanceof ContentServiceEvent) {
					ContentServiceEvent cse = (ContentServiceEvent) event;
					logger.debug(" calling performAdhocCaching ----");
					performAdhocCaching(cse.getApplicationData(), cse.getContentHolder(), null);
				}
			}
		}
	}

	public static class ReplicationManagerGroupCache extends CacheManager {
		public ReplicationManagerGroupCache() throws RemoteException {
			super();
		}
	}

	static {
		try {
			WTProperties properties = WTProperties.getServerProperties();
			bufferSize = properties.getProperty("wt.fv.download.buffer_size", 8192);
			threadNumMax = MasterProperties.CONCURRENT_REPLICATION_MAXIMUM;
			threadCount = 0;
			siteConfigDeliveryAttempts = properties.getProperty("wt.fv.master.siteConfigDeliveryAttempts", 3);
			millisecsToWait = properties.getProperty("wt.fv.master.millisecsToWait", 30000);
			pool = Executors.newFixedThreadPool(MasterProperties.THREAD_POOLSIZE);

			/**
			 * wt.fv.predictiveReplicationEnabled - This property decides whether to turn predictive caching on or off. Turning predictive caching on would start predictively
			 * replicating future iterations of only that content which is accessed after the property has been turned on. Default Value - true
			 **/
			PREDICTIVE_CACHING_ENABLED = properties.getProperty("wt.fv.predictiveReplicationEnabled", true);

			if (MasterProperties.VERBOSE) {
				logger.setLevel(Level.ALL);
			}
			// Name of the Replication Managers group comes from
			// wt.admin.replicationManagersGroup default value is 'Replication
			// Managers'
			REPLICATION_MANAGERS_GROUP = properties.getProperty("wt.admin.replicationManagersGroup", "Replication Managers");
			StringTokenizer token = new StringTokenizer(MasterProperties.SUPPORTED_BROWSERS, ",");
			while (token.hasMoreTokens()) {
				supportedBrowsers.add(token.nextToken());
			}
		} catch (Throwable t) {
			logger.error("", t);
			throw new ExceptionInInitializerError(t);
		}
	}

	/**
	 * Returns the conceptual (modeled) name for the class.
	 * 
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * @deprecated
	 * 
	 * @return String
	 **/
	public String getConceptualClassname() {
		return CLASSNAME;
	}

	/**
	 * Initialization tasks performed by the engine service. Includes queue activation and event subscription.
	 * 
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * @exception wt.services.ManagerException
	 **/
	@Override
	protected void performStartupProcess() throws ManagerException {
		boolean current = SessionServerHelper.manager.setAccessEnforced(false);
		try {
			performStartupProcessControlAccess();
		} finally {
			SessionServerHelper.manager.setAccessEnforced(current);
		}
		try {
			masterSiteChecksumLevel = new SiteChecksumLevel();
			masterSiteChecksumLevel.populateSiteChecksumLevel(true);
		} catch (WTException e) {
			throw new ManagerException(this, e, "Unable to get the ReplicaSite ChecksumLevel ");
		}
	}

	/**
	 * This is the method which is called by the StandardSchedulingService when it is time to replicate the content of a vault to a remote vault. This is a demo prototype. It may
	 * be advantageous to change its name and signature when we develop the production version, or even if it is too inconvenient to implement it on the demo time frame.
	 * 
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * @param theVault
	 * @param history
	 * @exception wt.util.WTException
	 **/
	public static void doContentReplication(ReplicaVault theVault, ContReplHistory history) throws WTException {
		StandardMasterService service = (StandardMasterService) ManagerServiceFactory.getDefault().getManager(StandardMasterService.class);
		service.doThreadedReplication(theVault, history);
	}

	/**
	 * 
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * @param appData
	 * @param holder
	 * @param prefSite
	 * @exception wt.util.WTException
	 **/
	public static void performAdhocCaching(ApplicationData appData, ContentHolder holder, Site prefSite) throws WTException {
		logger.debug(" *** in performAdhocCaching **");
		if (MasterProperties.ADHOC_CACHING_FLAG == 0) {
			return;
		}
		Site localSite = InterSvrComHelper.service.getLocalSite();
		if (prefSite == null)
			prefSite = PreferencesModel.getCurrentPreferredSite();
		MethodContext ctxt = MethodContext.getContext();
		Boolean adhoc = false;
		if (ctxt != null) {
			adhoc = (Boolean) ctxt.get(PULL_ADHOC);
			if (adhoc == null)
				adhoc = false;
		}
		if (prefSite.equals(localSite)) {
			if (!adhoc) {
				logger.debug("Prefered site is Local site and input stream is instance of RemoteUploadInputStream");
				return;
			}
		} else {
			if (MasterProperties.REPLICA_TO_REPLICA_CACHING)
				return;
		}
		appData = (ApplicationData) PersistenceHelper.manager.refresh(appData);
		ContentHolder actualHolder = holder;
		QueryResult qr = PersistenceHelper.manager.navigate(appData, HolderToContent.CONTENT_HOLDER_ROLE, HolderToContent.class);
		if (qr.hasMoreElements()) {
			actualHolder = (ContentHolder) qr.nextElement();
		}
		ReplicaVault rVault = StandardMasterService.getVaultForCaching(prefSite, actualHolder);
		if (rVault == null) {
			logger.debug("Cache Vault not found for site " + prefSite.getName());
			return;
		}
		ObjectReference rVaultRef = ObjectReference.newObjectReference(rVault);
		ObjectReference chRef = ObjectReference.newObjectReference(actualHolder);
		ArrayList list = new ArrayList();
		list.add(chRef);
		MasterServerHelper.service.queuePredictiveCaching(rVaultRef, list, false);
		// for local site is preffered site condition adhoc is already pulled.
		if (!prefSite.equals(localSite))
			doQueuedReplication(appData, rVault);
		// Code to update entry in new table for predictive caching will come
		// here
	}

	/**
	 * 
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * @param appData
	 * @param rVault
	 * @exception wt.util.WTException
	 **/
	public static void doQueuedReplication(ApplicationData appData, ReplicaVault rVault) throws WTException {
		ProcessingQueue queue = QueueHelper.manager.getQueue(ADHOC_CACHING_QUEUE);
		if (queue == null) {
			queue = QueueHelper.manager.createQueue(ADHOC_CACHING_QUEUE);
			QueueHelper.manager.startQueue(queue);
		}
		boolean current = SessionServerHelper.manager.setAccessEnforced(false);
		try {
			WTPrincipal principal = SessionHelper.manager.getAdministrator();
			String method = "startAdhocCaching";
			String className = CLASSNAME;
			Class[] argType = { ObjectIdentifier.class, ObjectIdentifier.class };
			appData = (ApplicationData) PersistenceHelper.manager.refresh(appData);
			Object[] args = { PersistenceHelper.getObjectIdentifier(appData), PersistenceHelper.getObjectIdentifier(rVault) };
			queue.addEntry(principal, method, className, argType, args);
		} finally {
			SessionServerHelper.manager.setAccessEnforced(current);
		}
	}

	/**
	 * 
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * @param ad
	 * @param rVault
	 **/
	public static void startAdhocCaching(ObjectIdentifier ad, ObjectIdentifier rVault) {
		logger.debug("start of AdhocCaching ****");
		try {
			ApplicationData appData = (ApplicationData) PersistenceHelper.manager.refresh(ad);
			ReplicaVault vault = (ReplicaVault) PersistenceHelper.manager.refresh(rVault);
			long contentID = appData.getStreamId();
			MethodContext mc = MethodContext.getContext();
			AdhocCachingSession session = new AdhocCachingSession(vault, appData, mc.getEffectivePrincipal(), mc.getSessionContext());
			pool.execute(session);
		} catch (WTRuntimeException wtre) {
			logger.error("", wtre);
		} catch (WTException wte) {
			logger.error("", wte);
		}
	}

	/**
	 * 
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * @param vault
	 * @param rootFolderId
	 * @exception wt.util.WTException
	 **/
	public static void addRemoteAutoFolder(Vault vault, long rootFolderId) throws WTException {
		ActionShippingItem asi = ShippingHelper.service.createActionShippingItem();
		try {
			SiteAddress to = SiteAddress.newSiteAddress();
			to.setAddress(vault.getSite().getUrl());

			MethodInvocationDesc mid = MethodInvocationDesc.newMethodInvocationDesc();

			mid.setTargetClass("wt.fv.replica.StandardReplicaService");
			mid.setTargetMethod("addRemoteAutoFolder");
			mid.setArgTypes(new String[] { Long.class.getName(), Long.class.getName(), String.class.getName() });
			mid.setArgs(new Serializable[] { new Long(PersistenceHelper.getObjectIdentifier(vault).getId()), new Long(rootFolderId), StandardInterSvrComService.MASTER_URL });

			asi.setDesc(mid);
			try {
				TransportType tt = TransportType.newTransportType();
				tt.setTransportType(null);
				ShippingHelper.service.sendImmediateItem((ShippingItem) asi, to, tt);
			} catch (WTDeliveryException wtde) {
				if (logger.isDebugEnabled()) {
					logger.debug(" *** ActionShippingItem - DELIVERY_FAILED " + vault);
					logger.debug("Failure msg [" + wtde.getLocalizedMessage() + "]");
				}
				throw wtde;
			}
		} catch (WTPropertyVetoException e) {
			logger.error("", e);
			throw new WTException(e);
		}
	}

	/**
	 * Collects the notification for the replica site that remote auto creation of folder is completed. Note- This is a shipping service callback function which only acts as
	 * wrapper StandardFvService.addRemoteAutoFolderComplete(..)
	 * 
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * @param rootFolderId
	 *            Holds the id of the root folder which has been created on the remote replica site.
	 * @param rfsToMarkRO
	 *            Holds the collection of the root folders for which physical folder on replica site could not be created.
	 * @param newFolderName
	 *            The name of newly created physical folder at the replica site.
	 * @param fileSeparator
	 *            The file separator (i.e. '\' or '/' is operating system dependant parameters) is additional parameter which is used by Master Windchill system to ease the access
	 *            of the mounted path (location) irrespective of the operating system, Windchill System will fetch it from the replica which is running on OS at that end.
	 * @exception wt.util.WTException
	 **/
	public static void addRemoteAutoFolderComplete(Long rootFolderId, Vector<Long> rfsToMarkRO, String newFolderName, String fileSeparator) throws WTException {
		StandardFvService.addRemoteAutoFolderComplete(rootFolderId, rfsToMarkRO, newFolderName, fileSeparator);
	}

	/**
	 * 
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * @param url
	 * @param rootFolderMountIdStr
	 * @param mountPathsMap
	 * @exception wt.util.WTException
	 **/
	public static void checkFolderSubFolderMountPaths(String url, String rootFolderMountIdStr, HashMap mountPathsMap) throws WTException {
		ActionShippingItem asi = ShippingHelper.service.createActionShippingItem();
		try {
			SiteAddress to = SiteAddress.newSiteAddress();
			to.setAddress(url);

			MethodInvocationDesc mid = MethodInvocationDesc.newMethodInvocationDesc();

			mid.setTargetClass("wt.fv.replica.StandardReplicaService");
			mid.setTargetMethod("checkFolderSubFolderMountPaths");
			mid.setArgTypes(new String[] { String.class.getName(), HashMap.class.getName() });
			mid.setArgs(new Serializable[] { rootFolderMountIdStr, mountPathsMap });

			asi.setDesc(mid);
			try {
				TransportType tt = TransportType.newTransportType();
				tt.setTransportType(null);
				ShippingHelper.service.sendImmediateItem((ShippingItem) asi, to, tt);
			} catch (WTDeliveryException wtde) {
				if (logger.isDebugEnabled()) {
					logger.debug(" *** ActionShippingItem - DELIVERY_FAILED " + url);
					logger.debug("Failure msg [" + wtde.getLocalizedMessage() + "]");
				}
				throw wtde;
			}
		} catch (WTPropertyVetoException e) {
			logger.error("", e);
			throw new WTException(e);
		}
	}

	/**
	 * 
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * @param statusCode
	 * @param rootFolderMountIdStr
	 * @param missingFoldersMap
	 * @param mountPathsMap
	 * @exception wt.util.WTPropertyVetoException
	 * @exception wt.util.WTException
	 **/
	public static void checkFolderSubFolderMountPathsComplete(Integer statusCode, String rootFolderMountIdStr, HashMap missingFoldersMap, HashMap mountPathsMap)
			throws WTPropertyVetoException, WTException {
		logger.debug("Standard Master Service : Recieved notification for " + "checkFolderSubFolderMountPaths completion");
		StandardFvService.checkFolderSubFolderMountPathsComplete(statusCode, rootFolderMountIdStr, missingFoldersMap, mountPathsMap);
	}

	/**
	 * Default factory for the class.
	 * 
	 * <BR>
	 * <BR>
	 * <B>Supported API: </B>false
	 * 
	 * @return StandardMasterService
	 * @exception wt.util.WTException
	 **/
	public static StandardMasterService newStandardMasterService() throws WTException {
		StandardMasterService instance = new StandardMasterService();
		instance.initialize();
		return instance;
	}

	@Override
	public FvPolicyRule addRemotePolicyRule(FvPolicyRule rule) throws WTException {
		return (FvPolicyRule) surrogate.doAddPolicyRule(rule);
	}

	@Override
	public Enumeration getRemotePolicyRules(Selector sel) throws WTException {
		return surrogate.doGetVaultingPolicyRules(sel, ReplicaVault.class.getName());
	}

	@Override
	public Enumeration getRemotePolicyRules(ReplicaVault vault) throws WTException {
		return surrogate.doGetVaultingPolicyRules(vault);
	}

	@Override
	public FvPolicyRule[] getAllRemotePolicyRules(String domainName) throws WTException {
		Vector ruleV = surrogate.doGetPolicyRulesOfType(domainName, ReplicaVault.class.getName());
		FvPolicyRule[] ruleArray = new FvPolicyRule[ruleV.size()];
		ruleV.copyInto(ruleArray);
		return ruleArray;
	}

	@Override
	public ReplicaFolder addFolder(Vault vault, ReplicaFolder folder) throws WTException {
		logger.debug("Adding ReplicaFolder [" + folder.getName() + "] to ReplicaVault [" + vault.getName() + "]");
		return (ReplicaFolder) surrogate.doAddFolder(vault, folder);
	}

	@Override
	public Vector getFolders(Site site, Vault vault) throws WTException {
		Vector resultElems = null;
		if (vault != null) {
			resultElems = surrogate.doGetFolders(site, vault);
		} else {
			resultElems = surrogate.doGetFoldersOfType(site, ReplicaFolder.class.getName());
		}
		return resultElems;
	}

	@Override
	public Vector getRemoteVaults(Site site) throws WTException {
		Enumeration vEnum = surrogate.doGetVaultsOfType(site, ReplicaVault.class.getName());
		Vector vv = new Vector();
		while (vEnum.hasMoreElements()) {
			vv.addElement(vEnum.nextElement());
		}
		return vv;
	}

	@Override
	public FvMount mountFolder(FvHost host, ReplicaFolder folder, String path) throws WTPropertyVetoException, WTException {
		return surrogate.doMountFolder(host, folder, path, false);
	}

	@Override
	public ReplicaFolder moveFolder(ReplicaFolder folder, ReplicaVault newVault) throws WTException, WTPropertyVetoException {
		return (ReplicaFolder) surrogate.doMoveFolder(folder, newVault);
	}

	@Override
	public FvPolicyItem[] getRemotePolicyItems(Selector sel) throws WTException {
		QueryResult qr = surrogate.doGetVaultingPolicyItems(sel, ReplicaVault.class.getName());
		int size = qr.size();
		FvPolicyItem[] itemArr = new FvPolicyItem[size];
		for (int ii = 0; ii < size; ii++) {
			itemArr[ii] = (FvPolicyItem) qr.nextElement();
		}
		return itemArr;
	}

	@Override
	public Vector getAlternativeURLs(ApplicationData appData, ContentHolder holder, boolean downloadFromMaster) throws QueryException, WTException, IOException {
		if (downloadFromMaster) {
			return RedirectDownload.getMirrorURLs(appData, holder);
		}
		return RedirectDownload.getAlternativeURLs(appData, holder, downloadFromMaster);
		/*
		 * Vector URLs = RedirectDownloadHTTP.getAlternativeURLs(appData, holder, downloadFromMaster); if (((URLs == null) || (URLs.size() <= 0 )) && downloadFromMaster) {
		 * URLs.addElement(ContentHelper.getDownloadURL(holder, appData, false).toString()); }
		 * 
		 * return URLs;
		 */
	}

	@Override
	public void purgeReplicatedItems(Site site, ReplicaVault vault, boolean fullPurge) throws WTException {
		/**********************************************************************
		 ** Query to find the ReplicatedItem obj. belonging to a certain site and vault. --------------------------------------------------------------------- If vault input arg. is
		 * null, find all for site. If fullPurge input arg. is true, find allobj. If fullPurge input arg. is false, find only objects wherre ReplicationStatus is not euqal
		 * REPLICATED.
		 ***********************************************************************/
		QuerySpec qs = new QuerySpec();
		QueryResult qr = null;
		int itemIdx = qs.addClassList(ReplicatedItem.class, true);
		int folderIdx = qs.addClassList(ReplicaFolder.class, false);
		int vaultIdx = qs.addClassList(ReplicaVault.class, false);

		qs.appendWhere(new SearchCondition(ReplicatedItem.class, StoredItem.FOLDER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, ReplicaFolder.class,
				WTAttributeNameIfc.ID_NAME), new int[] { itemIdx, folderIdx });
		qs.appendAnd();

		qs.appendWhere(
				new SearchCondition(ReplicaFolder.class, FileFolder.VAULT_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, ReplicaVault.class, WTAttributeNameIfc.ID_NAME),
				new int[] { folderIdx, vaultIdx });
		qs.appendAnd();

		qs.appendWhere(new SearchCondition(ReplicaVault.class, Vault.SITE_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, PersistenceHelper
				.getObjectIdentifier(site).getId()), new int[] { vaultIdx });
		if (vault != null) {
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(ReplicaVault.class, WTAttributeNameIfc.ID_NAME, SearchCondition.EQUAL, PersistenceHelper.getObjectIdentifier(vault).getId()),
					new int[] { vaultIdx });
		}
		if (!fullPurge) {
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(ReplicatedItem.class, ReplicatedItem.REPLICATION, SearchCondition.NOT_EQUAL, ReplicationStatus.REPLICATED), new int[] { itemIdx });
			// Do not purge ReplicatedItem with status CLEANED - Story B-74720
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(ReplicatedItem.class, ReplicatedItem.REPLICATION, SearchCondition.NOT_EQUAL, ReplicationStatus.CLEANED), new int[] { itemIdx });
		}
		qs.setQueryLimit(FvProperties.REVAULT_QUERY_SIZE);
		qs.setLock(true);
		qs.setAdvancedQueryEnabled(true);
		Transaction trx = null;
		try {
			// Execute query until all such obj. are found and deleted.
			while (true) {
				trx = new Transaction();
				trx.start();
				// execute query
				try {
					qr = PersistenceServerHelper.manager.query(qs);
				} catch (PartialResultException pre) {
					qr = pre.getQueryResult();
				}
				if (!qr.hasMoreElements()) {
					break;
				}
				WTSet repItemsToDelete = new WTHashSet(qr.size());
				while (qr.hasMoreElements()) {
					Persistable[] tmp = (Persistable[]) qr.nextElement();
					ReplicatedItem repItem = (ReplicatedItem) tmp[0];
					repItemsToDelete.add(repItem);
				}
				PersistenceServerHelper.manager.remove(repItemsToDelete);
				trx.commit();
				trx = null;
			}
		} finally {
			if (trx != null) {
				trx.rollback();
			}
		}
	}

	@Override
	public void purgeReplicatedItems(WTCollection contentHolders, Site site, boolean resetSharedItems, boolean purgePredictiveCachingRules) throws WTException {
		WTPrincipal user = SessionHelper.getPrincipal();
		WTGroup administrators = null;
		boolean current = SessionServerHelper.manager.setAccessEnforced(false);
		WTContainerRef ref = WTContainerHelper.service.getExchangeRef();
		if (ref != null) {
			administrators = ref.getContainer().getAdministrators();
		}
		if (administrators == null || !administrators.isMember(user)) {
			throw new WTException("This operation can only be run by a user in the Administrators group, please relogin with an administrator username and password");
		}

		try {
			logger.debug("purgeReplicatedItems started ");
			logger.debug("resetSharedItems = " + resetSharedItems);
			logger.debug("purgePredictiveCachingRules = " + purgePredictiveCachingRules);
			logger.debug("site = " + site);
			if (logger.isTraceEnabled()) {
				logger.trace("contentHolders= " + contentHolders);
			}
			long siteID = site.getPersistInfo().getObjectIdentifier().getId();

			Map streamIdToRIMap = getItemsToDelete(contentHolders, siteID);
			if (!resetSharedItems) {
				filterItemsToDelete(contentHolders, streamIdToRIMap);
			}

			WTSet itemsToDelete = new WTHashSet();
			Iterator iter = streamIdToRIMap.values().iterator();
			while (iter.hasNext()) {
				WTSet temp = (WTHashSet) iter.next();
				itemsToDelete.addAll(temp);
			}
			if (!itemsToDelete.isEmpty()) {
				if (logger.isTraceEnabled()) {
					logger.trace("contentHolders= " + itemsToDelete);
				}
				PersistenceServerHelper.manager.remove(itemsToDelete);
				logger.debug("Removed [" + itemsToDelete.size() + "] ReplicatedItems from site [" + site + "]");
			}
			if (purgePredictiveCachingRules) {
				Class replicationRuleHelperClass = Class.forName("com.ptc.windchill.enterprise.replication.server.ReplicationRuleHelper");
				Method method = replicationRuleHelperClass.getMethod("purgePredictiveCachingRules", new Class[] { WTCollection.class, Site.class });
				logger.debug("Invoking " + method);
				method.invoke(null, new Object[] { contentHolders, site });
			}
		} catch (WTException wte) {
			logger.error("Error in purgeReplicatedItems", wte);
			throw wte;
		} catch (Exception e) {
			logger.error("Error in purgeReplicatedItems", e);
			throw new WTException(e);
		} finally {
			SessionServerHelper.manager.setAccessEnforced(current);
		}
	}

	private static Map getItemsToDelete(WTCollection contentHolders, long siteID) throws WTException {
		Map StreamIDToRIMap = new HashMap();
		WTCollection contentHoldersCopy = new WTHashSet(contentHolders);// this
																		// collection
																		// will
																		// keep
																		// on
																		// decreasing
																		// with
																		// each
																		// loop
																		// run
		WTProperties properties = null;
		try {
			properties = WTProperties.getServerProperties();
		} catch (Throwable t) {
			throw new WTException(t);
		}
		int autoCleanupQuerySize = properties.getProperty("wt.fv.master.replicaCleanupQueryLimit", 10000);
		while (true) {
			QuerySpec qs = getQueryForResetReplication(contentHoldersCopy, siteID);
			if (logger.isDebugEnabled()) {
				logger.debug("qs = " + qs);
			}
			QueryResult qr = null;
			try {
				qr = PersistenceServerHelper.manager.query(qs);
			} catch (PartialResultException pre) {
				qr = pre.getQueryResult();
			}
			if (qr == null || qr.size() == 0) {
				break;
			}
			WTSet processedCH = new WTHashSet();
			ObjectReference chRef = null;
			while (qr.hasMoreElements()) {
				Object[] o = (Object[]) qr.nextElement();
				long ri = ((BigDecimal) o[0]).longValue();
				long streamId = ((BigDecimal) o[1]).longValue();
				String chClass = (String) o[2];
				long chId = ((BigDecimal) o[3]).longValue();

				WTSet itemsToDelete = null;
				itemsToDelete = (WTHashSet) StreamIDToRIMap.get(streamId);
				if (itemsToDelete == null) {
					itemsToDelete = new WTHashSet();
				}

				ObjectReference objRef = ObjectReference.newObjectReference(new ObjectIdentifier(ReplicatedItem.class.getName() + ":" + ri));
				itemsToDelete.add(objRef);
				StreamIDToRIMap.put(streamId, itemsToDelete);

				chRef = ObjectReference.newObjectReference(new ObjectIdentifier(chClass + ":" + chId));
				processedCH.add(chRef);
			}
			if (qr.size() >= autoCleanupQuerySize) {
				// Last CH may have more connected items that are not processed
				// in this batch. If query result is less than the fetch size,
				// it means all the items are processed in this batch.
				// Do process last CH again in next loop.
				processedCH.remove(chRef);
			}
			contentHoldersCopy.removeAll(processedCH);
			if (contentHoldersCopy.isEmpty()) {
				break;// all CH are processed now.
			}
		}// end while
		return StreamIDToRIMap;
	}

	/*
	 * This API removes the ReplicatedItem from Map, if connected ContentHolder is not supplied by client.
	 */
	private static void filterItemsToDelete(WTCollection contentHolders, Map StreamIDToRIMap) throws WTException {
		QuerySpec qs2 = getQueryForResetReplication(StreamIDToRIMap.keySet());
		if (logger.isDebugEnabled()) {
			logger.debug("qs2 = " + qs2);
		}
		QueryResult qr = PersistenceServerHelper.manager.query(qs2);
		while (qr != null && qr.hasMoreElements()) {
			Object[] o = (Object[]) qr.nextElement();
			long streamId = ((BigDecimal) o[0]).longValue();
			String chClass = (String) o[1];
			long chId = ((BigDecimal) o[2]).longValue();

			ObjectReference chRef = ObjectReference.newObjectReference(new ObjectIdentifier(chClass + ":" + chId));
			if (!contentHolders.contains(chRef)) {
				StreamIDToRIMap.remove(streamId);
			}
		}
	}

	/*
	 * SELECT A2.idA2A2,A1.streamId,A0.classnamekeyroleAObjectRef,A0.idA3A5 FROM wt.content.HolderToContent A0,wt.content.ApplicationData A1,wt.fv.master.ReplicatedItem
	 * A2,wt.fv.master.ReplicaFolder A3,wt.fv.master.ReplicaVault A4 WHERE (A0.idA3A5 IN (138190,138176)) AND (A0.idA3B5 = A1.idA2A2) AND (A1.streamId = A2.streamId) AND (A2.idA3A4
	 * = A3.idA2A2) AND (A3.idA3A5 = A4.idA2A2) AND (A4.idA3A5 = 124045) ORDER BY A0.idA3A5 joins=null useBind=true [wt.query.ArrayExpression{[138190, 138176]}, 124045]
	 */
	private static QuerySpec getQueryForResetReplication(WTCollection contentHolders, long siteID) throws WTException {
		WTProperties properties = null;
		try {
			properties = WTProperties.getServerProperties();
		} catch (Throwable t) {
			throw new WTException(t);
		}
		int autoCleanupQuerySize = properties.getProperty("wt.fv.master.replicaCleanupQueryLimit", 10000);
		QuerySpec qs = new QuerySpec();
		int idxHTC = qs.addClassList(HolderToContent.class, false);
		int idxAD = qs.addClassList(ApplicationData.class, false);
		int idxRI = qs.addClassList(ReplicatedItem.class, false);
		int idxRF = qs.addClassList(ReplicaFolder.class, false);
		int idxRV = qs.addClassList(ReplicaVault.class, false);
		qs.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, idxRI, false);
		qs.appendSelectAttribute(ApplicationData.STREAM_ID, idxAD, false);
		qs.appendSelectAttribute(HolderToContent.ROLE_AOBJECT_REF + "." + WTAttributeNameIfc.REF_CLASSNAME, idxHTC, false);

		qs.appendWhere(new SearchCondition(new ClassAttribute(HolderToContent.class, HolderToContent.ROLE_AOBJECT_REF + "." + WTAttributeNameIfc.REF_OBJECT_ID),
				SearchCondition.IN, new ArrayExpression(contentHolders.toIdArray())), new int[] { idxHTC, -1 });
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(HolderToContent.class, HolderToContent.ROLE_BOBJECT_REF + "." + WTAttributeNameIfc.REF_OBJECT_ID, ApplicationData.class,
				WTAttributeNameIfc.ID_NAME), new int[] { idxHTC, idxAD });
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(ApplicationData.class, ApplicationData.STREAM_ID, ReplicatedItem.class, ReplicatedItem.STREAM_ID), new int[] { idxAD, idxRI });
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(ReplicatedItem.class, ReplicatedItem.FOLDER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, ReplicaFolder.class,
				WTAttributeNameIfc.ID_NAME), new int[] { idxRI, idxRF });
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(ReplicaFolder.class, ReplicaFolder.VAULT_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, ReplicaVault.class,
				WTAttributeNameIfc.ID_NAME), new int[] { idxRF, idxRV });
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(ReplicaVault.class, ReplicaVault.SITE_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, siteID),
				new int[] { idxRV });
		qs.setQueryLimit(autoCleanupQuerySize);
		qs.setAdvancedQueryEnabled(true);
		// order by ContentHolder in ascending order
		int[] tmp1 = new int[1];
		tmp1[0] = qs.getFromClause().getPosition(HolderToContent.class);
		ClassAttribute idA3A5 = new ClassAttribute(HolderToContent.class, HolderToContent.ROLE_AOBJECT_REF + "." + WTAttributeNameIfc.REF_OBJECT_ID);
		try {
			idA3A5.setColumnAlias("idA3A5");
		} catch (WTPropertyVetoException wtpve) {
			throw new WTException(wtpve);
		}
		qs.appendSelect(idA3A5, tmp1, false);
		OrderBy orderBy = new OrderBy(idA3A5, false);
		qs.appendOrderBy(orderBy, tmp1);
		return qs;
	}

	/*
	 * SELECT streamId,classnamekeyroleAObjectRef,idA3A5 FROM wt.content.HolderToContent A0,wt.content.ApplicationData A1 WHERE (A0.idA3B5 = A1.idA2A2) AND (A1.streamId = 40102)
	 * joins=null useBind=true [40102]
	 */
	private static QuerySpec getQueryForResetReplication(Set StreamIds) throws WTException {
		QuerySpec qs = new QuerySpec();
		int idxHTC = qs.addClassList(HolderToContent.class, false);
		int idxAD = qs.addClassList(ApplicationData.class, false);
		qs.appendSelectAttribute(ApplicationData.STREAM_ID, idxAD, false);
		qs.appendSelectAttribute(HolderToContent.ROLE_AOBJECT_REF + "." + WTAttributeNameIfc.REF_CLASSNAME, idxHTC, false);
		qs.appendSelectAttribute(HolderToContent.ROLE_AOBJECT_REF + "." + WTAttributeNameIfc.REF_OBJECT_ID, idxHTC, false);
		qs.appendWhere(new SearchCondition(HolderToContent.class, HolderToContent.ROLE_BOBJECT_REF + "." + WTAttributeNameIfc.REF_OBJECT_ID, ApplicationData.class,
				WTAttributeNameIfc.ID_NAME), new int[] { idxHTC, idxAD });
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(new ClassAttribute(ApplicationData.class, ApplicationData.STREAM_ID), SearchCondition.IN, new ArrayExpression(StreamIds.toArray())),
				new int[] { idxAD, -1 });
		return qs;
	}

	@Override
	public void performUserInitiatedReplication(ArrayList objectIds, ArrayList siteIds, String dependants) throws WTException {
		logger.debug("Enter : performUserInitiatedReplication...");
		boolean current = SessionServerHelper.manager.setAccessEnforced(false);
		// Check whether the user belongs to 'Replication Managers' group or not
		WTPrincipalReference user = SessionHelper.manager.getPrincipalReference();

		if (!isReplicationManager(user.getPrincipal())) {
			if (logger.isDebugEnabled()) {
				logger.debug("Replication Aborted.");
				logger.debug(user.getName() + " is not a member of '" + REPLICATION_MANAGERS_GROUP + "' group.");
			}
			WTException ex = new wt.access.NotAuthorizedException((Exception) null, RESOURCE, masterResource.NO_ACCESS_FOR_REPL, new Object[] { user.getName(),
					REPLICATION_MANAGERS_GROUP });
			WTCollection wtColl = new wt.fc.collections.WTArrayList();
			wtColl.addAll(objectIds);
			AccessControlHelper.manager.emitAccessEvent(AccessControlEvent.NOT_AUTHORIZED, wtColl, AccessPermission.ADMINISTRATIVE, new WTMessage(RESOURCE,
					masterResource.NO_ACCESS_FOR_REPL, new Object[] { user.getName(), REPLICATION_MANAGERS_GROUP }));

			logger.error(ex.getLocalizedMessage(), ex);
			throw ex;
		} else {
			logger.debug(user.getName() + " is a member of '" + REPLICATION_MANAGERS_GROUP + "' group.");
		}
		try {
			String className = "com.ptc.windchill.enterprise.replication.server.ReplicationHelper";
			String methodName = "executeUserInitiatedReplication";
			Class classObj = Class.forName(className);
			java.lang.reflect.Method method = classObj.getMethod(methodName, new Class[] { ArrayList.class, ArrayList.class, String.class });
			if (logger.isDebugEnabled())
				logger.debug("Calling : executeUserInitiatedReplication - parameters==> oids= " + objectIds + ", siteIds=" + siteIds + ", dependants=" + dependants);
			method.invoke(null, new Object[] { siteIds, objectIds, dependants });
		} catch (NoSuchMethodException e) {
			logger.error("Exception...", e);
		} catch (Exception e) {
			throw new WTException(e);
		} finally {
			SessionServerHelper.manager.setAccessEnforced(current);
		}
	}

	@Override
	public void broadcastConfig(Site site) throws WTException {
		if (site.isReplica()) {
			updateSiteConfig(site);
		}
	}

	@Override
	public Vector getAllContentReplSites() throws WTException {
		Vector v = new Vector();
		boolean current = SessionServerHelper.manager.setAccessEnforced(false);
		try {
			QuerySpec qs = new QuerySpec(Site.class);
			qs.appendWhere(new SearchCondition(Site.class, Site.REPLICA, SearchCondition.IS_TRUE));
			QueryResult qr = PersistenceHelper.manager.find(qs);

			while (qr.hasMoreElements()) {
				v.addElement(qr.nextElement());
			}
		} finally {
			SessionServerHelper.manager.setAccessEnforced(current);
		}
		return v;
	}

	@Override
	public Site getCurrentPreferredSite() throws WTException {
		return PreferencesModel.getCurrentPreferredSite();
	}

	@Override
	public void setPreferredSite(Site site) throws WTException {
		PreferencesModel.setPreferredSite(site);
	}

	@Override
	public void removeUnusedReplicaItems(Long timeInMilli, String site, String vault, Float size) throws WTException {
		// ReplicaSizeManager.deleteOldReplicaItems(timeInMilli, site, vault,
		// size);
	}

	/**
	 * Method generates the configuration info for each site keyed by site host. This method calls deliverSiteConfig, which delivers the site configuration information to the
	 * method server which posted this request.
	 */
	public static void generateSiteConfig(HTTPRequest req, HTTPResponse resp, InputStream is) throws WTException {
		boolean current = true;
		String siteUrl = null;
		MethodContext context = null;
		SessionContext previousSession = null;
		ObjectInputStream ois = null;
		try {
			if (MethodContext.getContext(Thread.currentThread()) == null) {
				context = new MethodContext(null, null);
			}
			// try {
			// MethodContext.getContext();
			// }
			// catch (MethodServerException e) {
			// context = new MethodContext("", null);
			// }
			previousSession = SessionContext.newContext();
			SessionHelper.manager.setAdministrator();
			current = SessionServerHelper.manager.setAccessEnforced( /* enforcement */false);

			siteUrl = req.getQueryString();
			ois = new ObjectInputStream(is);
			logger.debug("Generating Config Info for Site [" + siteUrl + "]");
			wt.intersvrcom.SiteChecksumLevel replica_site_checksum_level = (SiteChecksumLevel) ois.readObject();

			if (logger.isDebugEnabled())
				logger.debug("*************** Replica Site " + siteUrl + " SiteChecksumLevel " + replica_site_checksum_level);
			// replica_site_checksum_level
			if (replica_site_checksum_level != null) {
				boolean canReplicaAcceptUpdate = replica_site_checksum_level.isUpdateRequired();
				if (canReplicaAcceptUpdate) {
					masterSiteChecksumLevel = masterSiteChecksumLevel.populateSiteChecksumLevel(true);
					if (masterSiteChecksumLevel != null && masterSiteChecksumLevel.isAutoManageCCS()) {
						if (logger.isDebugEnabled()) {
							logger.debug(" ===== masterSiteChecksumLevel " + masterSiteChecksumLevel.toString());
							logger.debug(" ===== replica_site_checksum_level " + replica_site_checksum_level.toString());
						}
						boolean areSiteLevelsMatching = masterSiteChecksumLevel.equals(replica_site_checksum_level);
						if (!areSiteLevelsMatching) {
							replica_site_checksum_level = masterSiteChecksumLevel.getMissingEntriesModified(replica_site_checksum_level);
							StandardInterSvrComService.upgradeCCS(siteUrl, replica_site_checksum_level);
						} else {
							Site replica_site = InterSvrComHelper.service.getSite(siteUrl);
							if (replica_site == null) {
								logger.warn(" No site is present corresponding to the siteUrl " + siteUrl);
								return;
							}
							int stat = replica_site.getStatus();
							logger.debug("generateSiteConfig: status of site " + replica_site.getName() + stat);
							if (stat == Site.RESTART_REQUIRED || stat == Site.UPDATE_IN_PROCESS) {
								Transaction trx = new Transaction();
								try {
									trx.start();
									PersistenceServerHelper.manager.lock(replica_site);
									try {
										logger.debug("Updating Site status to ONLINE");
										replica_site.setStatus(Site.ONLINE);
									} catch (WTPropertyVetoException vte) {
										throw new WTException(vte);
									}
									PersistenceHelper.manager.save(replica_site);
									trx.commit();
									trx = null;
								} catch (WTException wte) {
									logger.debug("Caught exception while saving the site object...", wte);
									throw wte;
								} finally {
									if (trx != null) {
										trx.rollback();
									}
								}
							}
						}
					}
				} else {
					Site replica_site = InterSvrComHelper.service.getSite(siteUrl);
					if (replica_site == null) {
						logger.warn(" No site is present corresponding to the siteUrl " + siteUrl);
						return;
					}
					int stat = replica_site.getStatus();
					logger.debug("generateSiteConfig: status of site " + replica_site.getName() + stat);
					if (stat == Site.RESTART_REQUIRED || stat == Site.UPDATE_IN_PROCESS) {
						Transaction trx = new Transaction();
						try {
							trx.start();
							PersistenceServerHelper.manager.lock(replica_site);
							try {
								logger.debug("Updating Site status to ONLINE");
								replica_site.setStatus(Site.ONLINE);
							} catch (WTPropertyVetoException vte) {
								throw new WTException(vte);
							}
							PersistenceHelper.manager.save(replica_site);
							trx.commit();
							trx = null;
						} catch (WTException wte) {
							logger.debug("Caught exception while saving the site object...", wte);
							throw wte;
						} finally {
							if (trx != null) {
								trx.rollback();
							}
						}
					}
				}
			}

			// now continue broadcasting the configuration.
			QuerySpec qs = new QuerySpec(Site.class);
			qs.appendWhere(new SearchCondition(Site.class, Site.URL, SearchCondition.EQUAL, siteUrl.trim()));
			QueryResult qr = PersistenceHelper.manager.find(qs);
			Site theSite = null;
			int resultCount = 0;
			while (qr.hasMoreElements()) {
				theSite = (Site) qr.nextElement();
				resultCount++;
			}
			if (theSite == null) {
				logger.debug("Site [" + siteUrl + "] not found");
				throw new WTException(RESOURCE, masterResource.SITE_NOT_FOUND, null);
			}
			if (resultCount != 1) {
				throw new WTException(RESOURCE, masterResource.SITE_NAME_IS_NOT_UNIQUE, null);
			}
			SiteDesc site = SiteConfigGenerator.createSiteDesc(theSite);

			try {
				Site masterSite = InterSvrComHelper.service.getLocalSite();
				site.setMasterURL(masterSite.getUrl());
				long myId = (PersistenceHelper.getObjectIdentifier(masterSite)).getId();
				site.setMasterSiteID(myId);
			} catch (WTPropertyVetoException pve) {
				logger.error("", pve);
			}

			resp.setStatus(HttpURLConnection.HTTP_OK);

			String remURL = theSite.getUrl();
			deliverSiteConfig(siteUrl, remURL, site);
		} catch (ClassNotFoundException cnfe) {
			logger.error("", cnfe);
		} catch (WTException wte) {
			logger.error("", wte);
			throw wte;
		} catch (IOException ioe) {
			logger.error("", ioe);
		} catch (Exception e) {
			logger.error("", e);
			throw new WTException(e);
		} finally { // re-enable authentication
			logger.debug("Configuration information sent to replica [" + siteUrl + "]");
			current = SessionServerHelper.manager.setAccessEnforced(current);
			if (context != null) {
				context.unregister();
			}
			if (previousSession != null)
				SessionContext.setContext(previousSession);
		}
	}

	public static void generateSiteConfig(HTTPRequest req, HTTPResponse resp) throws WTException {

	}

	/**
	 * We will be using the delivery service to send the site info back to the site.
	 */
	private static void deliverSiteConfig(String site, String destURL, SiteDesc retHash) {
		WTThread t = new WTThread(new DeliverSiteConfig(site, destURL, retHash));
		t.start();
	}

	static class DeliverSiteConfig implements Runnable {
		String site;
		String destURL;
		SiteDesc retHash;

		public DeliverSiteConfig(String site, String destURL, SiteDesc retHash) {
			this.site = site;
			this.destURL = destURL;
			this.retHash = retHash;
		}

		public void run() {
			MethodContext cntxt = null;
			boolean current = false;
			boolean success = false;
			SessionContext previous_context = null;
			int noOfAttempts = siteConfigDeliveryAttempts;
			try {
				cntxt = new MethodContext("", null);
				previous_context = SessionContext.newContext();
				if (previous_context == null) {
					previous_context = SessionContext.newContext();
					previous_context.register();
				}
				cntxt.setSessionContext(previous_context);
				cntxt.setEffectivePrincipal(SessionHelper.manager.getAdministrator());
				current = SessionServerHelper.manager.setAccessEnforced( /* enforcement */false);

				while (!success && noOfAttempts-- > 0) {
					success = deliverSiteConfig0(site, destURL, retHash);
					if (!success) {
						Thread.currentThread().sleep(millisecsToWait);
					}
				}
			} catch (Exception e) {
				logger.debug("DeliverSiteConfig thread failed in run method");
			} finally {
				current = SessionServerHelper.manager.setAccessEnforced(current);
				if (previous_context != null) {
					SessionContext.setContext(previous_context);
				}
				if (cntxt != null) {
					cntxt.unregister();
				}
			}
		}

		private boolean deliverSiteConfig0(String site, String destURL, SiteDesc retHash) {
			if (logger.isDebugEnabled()) {
				String outString = "Preparing config info for delivery to Site [" + site + "]";
				logger.debug(outString);

				String outString2 = " to the method server located at: " + destURL;
				logger.debug(outString2);
			}
			try {
				initInbox();
				MethodInvocationDesc methodDesc = MethodInvocationDesc.newMethodInvocationDesc();
				methodDesc.setTargetClass("wt.fv.replica.StandardReplicaService");
				methodDesc.setTargetMethod("receiveConfigInfo");
				String[] argTypes = new String[2];
				argTypes[0] = "java.lang.String";
				argTypes[1] = "wt.fv.configurator.SiteDesc";
				methodDesc.setArgTypes(argTypes);

				// gets correct master site info
				Site masterSite = InterSvrComHelper.service.getLocalSite();
				// sets proper method parameters
				Serializable[] theArgs = { masterSite.getName(), retHash };
				methodDesc.setArgs(theArgs);

				ActionShippingItem theItem = ShippingHelper.service.createActionShippingItem();
				theItem.setDesc(methodDesc);
				TransportType transType = TransportType.newTransportType();
				transType.setTransportType(transportName);
				SiteAddress to = SiteAddress.newSiteAddress();
				to.setAddress(destURL);

				if (logger.isDebugEnabled()) {
					logger.debug("Sending config info to replica site [" + site + "]");
					logger.debug("Located at: " + destURL);
				}
				ShippingHelper.service.sendImmediateItem(theItem, to, transType);
				return true;
			} catch (WTException wte) {
				logger.error("", wte);
				return false;
			} catch (WTPropertyVetoException pve) {
				logger.error("", pve);
				return false;
			}
		}
	}

	private static void initInbox() throws WTException {
		try {
			ReceiverHelper.service.createInbox(TransparentInbox.class, "ConfigInfo");
		} catch (WTDeliveryException e) {
			logger.debug("Inbox creation failed");
			throw new WTException(RESOURCE, masterResource.INBOX_CREATION_FAILED, null);
		}
	}

	public void replicateStreamed(Streamed obj, ReplicaVault rVault, ContReplHistory session, ReplicatedItem ri) throws WTException {
		replicateStreamed(obj, rVault, session, null, ri);
	}

	/**
	 * This method performs the actual replication for a Streamed object. This method does the following :- 1. Creates a ReplicatedItem (in IN_TRANSIT state) 2. Checks for the
	 * input stream 3. Calls item mover
	 * 
	 * @param objIdent
	 *            - Streamed ObjectIdentifier
	 * @param rVault
	 *            - Target Replica Vault object
	 * @param session
	 *            - ContReplHistory object which maintains the replication status
	 * @param streamIdDbSizeHash
	 *            - contains the actual DB size with key as the streamId
	 * @throws WTException
	 */
	public void replicateStreamed(Streamed obj, ReplicaVault rVault, ContReplHistory session, Hashtable streamIdDbSizeHash, ReplicatedItem ri) throws WTException {
		boolean success = false;
		boolean decrementTotalCount = false;
		boolean objectNoLongerExists = false;

		FileFolder activeFolder = null;
		if (!rVault.isWriteEnabled()) {
			logger.debug("vault is not writable" + rVault);
			throw new FvFileCanNotBeStored(FV_RESOURCE, fvResource.CANT_SAVE_VAULT_RO, null);
		}
		logger.debug("creating new StoredItem");
		activeFolder = StandardFvService.getActiveFolder(rVault);
		if (activeFolder == null) {
			logger.debug("no active folder found for vault" + rVault);
			throw new FvFileCanNotBeStored(FV_RESOURCE, fvResource.CANT_SAVE_NO_ACT_FOLDER, null);
		}
		try {
			// check if object is still in DB if not, decrement total + exit
			// mover
			try {
				obj = (Streamed) PersistenceServerHelper.manager.restore(obj);
			} catch (ObjectNoLongerExistsException e) {
				logger.debug("source object to replicate could not be found - object may have been deleted");
				decrementTotalCount = true;
				objectNoLongerExists = true;
			}
			if (!decrementTotalCount) {
				/**
				 * For FvItem, set the actual DB file size as fetched from the query. The retrieveStream() method will then check whether this DB filesize matches with the actual
				 * physical filesize. If there is a mismatch do not replicate the item and set the item as quarantined.
				 */
				if (logger.isDebugEnabled()) {
					logger.debug("Replication - source object = " + obj + "target vault = " + rVault);
				}

				// this function should always have the input object StreamData
				// or an FvItem it will never be anything else
				if (FvProperties.CHECK_DB_SIZE && obj instanceof FvItem) {
					long streamid = obj.getStreamId();
					logger.debug("FvItem StreamId = " + streamid);
					if (streamIdDbSizeHash != null) {
						Long fileSize = (Long) streamIdDbSizeHash.get(new Long(streamid));
						logger.debug("got...db fileSize=" + fileSize);
						obj.setDbFileSize(fileSize == null ? 0 : fileSize.longValue());
					}
				}

				ContentMover mover = null;
				if (ri == null)
					mover = ContentMover.getMover(obj, rVault);
				else
					mover = ContentMover.getMover(obj, ri);
				if (mover == null) {
					decrementTotalCount = true;
				} else if (mover.isMoveNeeded()) {
					ri = (ReplicatedItem) mover.move();
					if (ri != null) {
						success = true;
						if (mover.isIndexable()) {
							logger.debug("Indexing the Content.");
							WTCollection riCol = new WTHashSet();
							riCol.add(ri);
							IndexPolicyHelper.manager.index(riCol);
						}
					}
				} else {
					logger.debug("no replication done for current item as it already exists at specified site");
					decrementTotalCount = true;
				}
			}
		} catch (Throwable t) {
			logger.error("replication failed for object " + obj, t);
		} finally {
			// creating replicated items - when item exists at specified site
			// or for any reason replication fails with status NOT_REPLICATED
			if (!success) {
				Transaction trx = null;
				try {
					if (ri == null && !objectNoLongerExists) {
						ri = ReplicatedItem.newReplicatedItem();
						long uniqueSequencenumber = -1;
						if (obj instanceof StreamData)
							uniqueSequencenumber = ((StreamData) obj).getUniqueSequenceNumber();
						else
							uniqueSequencenumber = ((StoredItem) obj).getUniqueSequenceNumber();
						// set folder reference and uniquesequencenumber
						try {
							ri.setUniqueSequenceNumber(FileNameUSNPool.getUniqueSequenceNumberForTargetItem(uniqueSequencenumber));
							ri.setFolderReference(ObjectReference.newObjectReference(activeFolder));
						} catch (WTPropertyVetoException e) {
							throw new WTException(e);
						}
						// set stream id
						ri.setStreamId(obj.getStreamId());
					}
					if (ri != null) {
						trx = new Transaction();
						trx.start();
						// set status
						ri.setReplication(ReplicationStatus.NOT_REPLICATED);
						ri = (ReplicatedItem) PersistenceHelper.manager.save(ri);
						trx.commit();
						trx = null;
					}
				} catch (WTPropertyVetoException e) {
					throw new WTException(e);
				} finally {
					if (trx != null) {
						trx.rollback();
					}
				}
			}
			updateReplicaSessionStatus(session, success, decrementTotalCount);
		}
	}

	private void updateReplicaSessionStatus(ContReplHistory session, boolean success, boolean decrementTotalCount) {
		Transaction trx = null;
		try {
			session = (ContReplHistory) PersistenceServerHelper.manager.restore(session);
			if (session.isSessionInProgress()) {
				trx = new Transaction();
				trx.start();
				PersistenceServerHelper.manager.lock(session, FvProperties.REVAULTING_LOCK_TIME, 1);
				session = (ContReplHistory) PersistenceServerHelper.manager.restore(session);
				if (success) {
					logger.debug("updating ContReplHistory for successful replication");
					session.incrementExecutedRuns();
				} else {
					if (decrementTotalCount) {
						logger.debug("updating ContReplHistory to decrement total count as source object could not be found");
						session.decrementTotalRuns();
					} else {
						logger.debug("updating ContReplHistory for failed replication");
						session.incrementErrorRuns();
					}
				}
				session = session.checkForCompletition();
				PersistenceServerHelper.manager.update(session);
				trx.commit();
				trx = null;
			}
		} catch (Throwable e) {
			logger.error("error updating ContReplHistory " + session + ". This could lead to inappropriate session counts/status.");
			logger.error("We continue with further replication process");
		} finally {
			if (trx != null) {
				trx.rollback();
			}
		}
	}

	public static void replicateStreamedComplete(Long rItemID, Long folderID, Long sessionID, Long folderToMarkReadOnlyID) throws WTException {
		logger.debug("Received notification of completion from replica.");
		// get the Content Replication History object
		ContReplHistory crHist = null;
		Transaction trx = null;
		if (sessionID.longValue() != -1) {
			try {
				ObjectReference rsRef = ObjectReference.newObjectReference(ObjectIdentifier.newObjectIdentifier(ContReplHistory.class, sessionID.longValue()));
				crHist = (ContReplHistory) rsRef.getObject();
			} catch (WTRuntimeException e) {
				/* Content Replication History D.N.E. Process should cont. */
				logger.debug("Content replication history does not exist to update in replicate streamed complete", e);
			}
		}
		// if an IO error occured while writing to folder, we mark the folder as
		// Read Only
		ReplicaFolder folderToMarkRO = null;

		// If could not replicate content
		if (folderID.longValue() <= 0) {
			// find folder that should be marked ReadOnly and mark it.
			if (folderToMarkReadOnlyID.longValue() != -1) {
				try {
					ObjectReference folderRefToMarkRO = ObjectReference.newObjectReference(new ObjectIdentifier(ReplicaFolder.class, folderToMarkReadOnlyID.longValue()));
					folderToMarkRO = (ReplicaFolder) folderRefToMarkRO.getObject();
				} catch (WTRuntimeException e) {
					/* File to be marked readnly D.N.E. Nothing to worry about */
					logger.debug("Could not find folder [" + folderToMarkReadOnlyID.longValue() + "] to mark ReadOnly.", e);
				}

				if (folderToMarkRO != null) {
					trx = new Transaction();

					if (logger.isDebugEnabled()) {
						logger.debug("[" + sessionID + "] Could not write to replica folder [" + folderToMarkRO.getName() + "]");
						logger.debug("[" + sessionID + "]       Possible causes:");
						logger.debug("[" + sessionID + "]       1.   Disk, where the folder is mounted, is full");
						logger.debug("[" + sessionID + "]       2.   Folder is mounted to path which does not exist");
						logger.debug("[" + sessionID + "] Marking ReplicaFolder [" + folderToMarkRO.getName() + "] as ReadOnly");
					}
					try {
						trx.start();
						PersistenceServerHelper.manager.lock(folderToMarkRO, FvProperties.REVAULTING_LOCK_TIME, 1);
						folderToMarkRO = (ReplicaFolder) PersistenceServerHelper.manager.restore(folderToMarkRO);

						trx.addTransactionListener(new ReplicaOverflowListener(folderToMarkRO));
						folderToMarkRO.setReadOnly(true);
						PersistenceServerHelper.manager.update(folderToMarkRO);

						// now check if vault has active folders, and if not,
						// make it readonly also
						ReplicaVault rv = (ReplicaVault) folderToMarkRO.getVault();

						if (StandardFvService.getActiveFolder(rv) == null) {
							if (logger.isDebugEnabled())
								logger.debug("[" + sessionID + "] Marking ReplicaVault [" + rv.getName() + "] as ReadOnly. No more Active Folders");
							PersistenceServerHelper.manager.lock(rv, FvProperties.REVAULTING_LOCK_TIME, 1);
							rv = (ReplicaVault) PersistenceServerHelper.manager.restore(rv);
							trx.addTransactionListener(new ReplicaOverflowListener(rv));
							rv.setReadOnly(true);
							PersistenceServerHelper.manager.update(rv);
						}
						trx.commit();
						trx = null;
					} finally {
						if (trx != null) {
							trx.rollback();
							logger.debug("[" + sessionID + "] ERROR: Could not mark ReplicaFolder [" + folderToMarkRO.getName() + "] as ReadOnly");
						}
					}
				}
			}
			// set not_replicated status;
			QuerySpec qs = new QuerySpec(ReplicatedItem.class);
			qs.appendWhere(new SearchCondition(ReplicatedItem.class, ReplicatedItem.UNIQUE_SEQUENCE_NUMBER, SearchCondition.EQUAL, rItemID.longValue()));
			QueryResult qr = PersistenceHelper.manager.find(qs);
			if (!qr.hasMoreElements()) {
				// Replica replied but replicated item not found,
				// probably user has reset Replica or reset undelivered items
				logger.debug("Response from Replica, but did not find replicated item on Master. Probable Replica reset.");
			} else {
				ReplicatedItem ri = (ReplicatedItem) qr.nextElement();
				trx = new Transaction();
				try {
					logger.debug("[" + sessionID + "] Streamed Replication Failed StreamID[" + ri.getStreamId() + "]");
					trx.start();
					PersistenceServerHelper.manager.lock(ri, FvProperties.REVAULTING_LOCK_TIME, 1);
					ri = (ReplicatedItem) PersistenceServerHelper.manager.restore(ri);
					if (crHist != null) {
						PersistenceServerHelper.manager.lock(crHist, FvProperties.REVAULTING_LOCK_TIME, 1);
						crHist = (ContReplHistory) PersistenceServerHelper.manager.restore(crHist);
					}
					try {
						ri.setReplication(ReplicationStatus.NOT_REPLICATED);
					} catch (WTPropertyVetoException e) {
						WTException wte = new WTException(e);
						logger.debug("", wte);
					}
					if (crHist != null) {
						crHist.incrementErrorRuns();
						crHist.checkForCompletition();
						crHist = (ContReplHistory) PersistenceHelper.manager.save(crHist);
					}
					PersistenceServerHelper.manager.update(ri);
					trx.commit();
					trx = null;
				} finally {
					if (trx != null) {
						trx.rollback();

						// update to increment error runs
						if (crHist != null) {
							trx = new Transaction();
							try {
								trx.start();
								PersistenceServerHelper.manager.lock(crHist, FvProperties.REVAULTING_LOCK_TIME, 1);
								crHist = (ContReplHistory) PersistenceServerHelper.manager.restore(crHist);
								crHist.incrementErrorRuns();
								crHist.checkForCompletition();
								crHist = (ContReplHistory) PersistenceHelper.manager.save(crHist);
								trx.commit();
								trx = null;
							} finally {
								if (trx != null) {
									trx.rollback();
								}
							}
						}
					}
				}
			}
		} else { // arrive here if content is replicated successfully
			QuerySpec qs = new QuerySpec(ReplicatedItem.class);
			qs.appendWhere(new SearchCondition(ReplicatedItem.class, ReplicatedItem.UNIQUE_SEQUENCE_NUMBER, SearchCondition.EQUAL, rItemID.longValue()));
			QueryResult qr = PersistenceHelper.manager.find(qs);
			if (!qr.hasMoreElements()) {
				// Replica replied but replicated item not found,
				// probably user has reset Replica or reset undelivered items
				logger.debug("Response from Replica, but did not find replicated item on Master. Probable Replica reset.");
			} else {
				ReplicatedItem ri = (ReplicatedItem) qr.nextElement();
				// find correct Folder to attach to - i.e. the real folder the
				// replicatedItem put in
				ObjectReference folderRef = ObjectReference.newObjectReference(new ObjectIdentifier(ReplicaFolder.class, folderID.longValue()));
				// check if the folder exists.
				ReplicaFolder rf = (ReplicaFolder) folderRef.getObject();
				trx = new Transaction();
				try {
					if (logger.isDebugEnabled())
						logger.debug("[" + sessionID + "] Streamed Replication Completed StreamID[" + ri.getStreamId() + "]");
					trx.start();
					PersistenceServerHelper.manager.lock(ri, FvProperties.REVAULTING_LOCK_TIME, 1);
					ri = (ReplicatedItem) PersistenceServerHelper.manager.restore(ri);
					try {
						// On the master site, the replicated item at this point
						// is still referencing
						// the orginial folder which was assigned to the item,
						// however, the actually
						// folder the item is stored could be different from the
						// original one, this could
						// happen if the original folder is full and mover on
						// the
						// replica site has to switch to another folder of more
						// space to store it.
						// Here we check if the original folder is different
						// from the actual one, if
						// so, set the orginal folder (on this master site) to
						// read-only so that it
						// will be in-sync with what was done on replica site by
						// mover.
						// ReplicaFolder originalFolder = (ReplicaFolder)
						// ri.getFolder();
						// if (!(originalFolder.equals (rf)))
						// {
						// trx.addTransactionListener(new
						// ReplicaOverflowListener(originalFolder));
						// originalFolder.setReadOnly(true);
						// PersistenceServerHelper.manager.update(originalFolder);
						// }
						// Assign the actual folder to the replica item
						ri.setReplication(ReplicationStatus.REPLICATED);
						ri.setLastAccessedTimeInMilli(System.currentTimeMillis());
						ri.setFolder(rf);
					} catch (WTPropertyVetoException e) {
						logger.debug("", e);
						throw new WTException(e);
					}
					PersistenceServerHelper.manager.update(ri);
					trx.commit();
					trx = null;
				} finally {
					if (trx != null) {
						trx.rollback();
					}
					// update session history w/result of a run
					if (crHist != null) {
						trx = new Transaction();
						try {
							trx.start();
							PersistenceServerHelper.manager.lock(crHist, FvProperties.REVAULTING_LOCK_TIME, 1);
							crHist = (ContReplHistory) PersistenceServerHelper.manager.restore(crHist);
							crHist.incrementExecutedRuns();
							crHist.checkForCompletition();
							crHist = (ContReplHistory) PersistenceHelper.manager.save(crHist);
							trx.commit();
							trx = null;
						} finally {
							if (trx != null) {
								trx.rollback();
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Refer com.ptc.windchill.enterprise.replication.server.ReplicationHelper for the latest code This method will get only used when the property
	 * wt.fv.oldReplicationRulesAndSchedules=true, By default its false
	 * 
	 * @deprecated
	 * @param vault
	 * @param crHistory
	 * @throws WTException
	 */
	private void doThreadedReplication(ReplicaVault vault, ContReplHistory crHistory) throws WTException {
		try {
			while (threadCount >= threadNumMax) {
				WTThread.sleep(5 * 60 * 100);
			}
		} catch (InterruptedException e) {
			logger.error("Replication is interupted while waiting for thread", e);
			return;
		}
		threadCount++;
		MethodContext mc = MethodContext.getContext();

		ContentReplicationSession replSession = new ContentReplicationSession(vault, crHistory, mc.getEffectivePrincipal(), mc.getSessionContext());
		WTThread th = new WTThread(replSession);
		th.start();
	}

	public void replicateRemoteVaultItems(ReplicaVault vault, ContReplHistory crHistory, Collection itemsForRemoteReplicate) throws WTException {
		boolean sessionInProgress = crHistory.isReplicationInProgress();
		if (sessionInProgress && !itemsForRemoteReplicate.isEmpty()) {
			Iterator itemsIter = itemsForRemoteReplicate.iterator();
			while (itemsIter.hasNext() && sessionInProgress) {
				// attempt to replicate the element
				boolean ok = false;

				Object obj = itemsIter.next();
				ReplicatedItem ri = null;
				ReplicaFolder rf = null;

				if (obj instanceof ObjectReference) {
					ObjectReference objRef = (ObjectReference) obj;
					ri = (ReplicatedItem) objRef.getObject();
					rf = (ReplicaFolder) ri.getFolderReference().getObject();
				} else {
					Persistable[] tmp = (Persistable[]) obj;
					ri = (ReplicatedItem) tmp[0];
					rf = (ReplicaFolder) tmp[1];
				}
				try {
					remoteCopyItemVaultToVault(vault, ri, rf, crHistory);
					ok = true;
				} catch (Exception e) {
					logger.error("Exception during replication, continuing to replicate the other items", e);
				} // if one element fails, process should not stop
				finally {
					if (!ok) {
						// if mover failed need to record the failure
						updateReplicaSessionStatus(crHistory, false, false);
					}
					crHistory = (ContReplHistory) PersistenceServerHelper.manager.restore(crHistory);
					sessionInProgress = crHistory.isReplicationInProgress();
				}
			}
		}
	}

	public void replicateFromMaster(ReplicaVault vault, ContReplHistory crHistory, WTCollection itemsFromMasterToReplicate, HashMap streamIdToReplicatedItem) throws WTException {
		logger.trace("StandardMasterService.replicateFromMaster...");
		boolean isEmpty = itemsFromMasterToReplicate.isEmpty();
		Hashtable streamIdDbSizeHash = null;
		if (FvProperties.CHECK_DB_SIZE && !isEmpty)
			streamIdDbSizeHash = fetchDBSize(itemsFromMasterToReplicate);
		logger.debug("streamIdDbSizeHash=" + streamIdDbSizeHash);
		boolean sessionInProgress = crHistory.isReplicationInProgress();
		if (sessionInProgress && !itemsFromMasterToReplicate.isEmpty()) {
			Iterator itemsIter = itemsFromMasterToReplicate.persistableIterator();
			while (itemsIter.hasNext() && sessionInProgress) {
				// attempt to replicate the element
				boolean ok = false;
				try {
					Long id = null;
					Streamed streamedObject = null;
					Object obj = itemsIter.next();
					if (obj instanceof Streamed) {
						streamedObject = (Streamed) obj;
						id = streamedObject.getStreamId();
					} else {
						logger.warn("Object to be replicated not a valid type");
					}
					if (id != null && streamIdToReplicatedItem != null) {
						ReplicatedItem ri = (ReplicatedItem) streamIdToReplicatedItem.get(id);
						if (id == ri.getStreamId()) {
							replicateStreamed(streamedObject, vault, crHistory, streamIdDbSizeHash, ri);
							ok = true;
						}
					} else {
						logger.warn("Stream Id or streamIdToReplicatedItem is null");
						// Fix for SPR 2088629
						replicateStreamed(streamedObject, vault, crHistory, streamIdDbSizeHash, null);
						ok = true;
					}

				} catch (WTException e) {
					logger.error("", e);
				}// if one element fails, process should not stop
				finally {
					if (!ok) {
						// if mover failed need to record the failure
						updateReplicaSessionStatus(crHistory, false, false);
					}
					// check if current session is still in progress
					crHistory = (ContReplHistory) PersistenceServerHelper.manager.restore(crHistory);
					sessionInProgress = crHistory.isReplicationInProgress();
				}
			}
		}
	}

	public void queuePredictiveCaching(ObjectReference rVaultRef, Collection objects, boolean runImmediately) throws WTException {
		if (PREDICTIVE_CACHING_ENABLED) {
			if (logger.isDebugEnabled()) {
				logger.debug("Predictive Caching is enabled.");
				logger.debug("Calling ReplicationHelper.executePredictiveCaching...");
			}

			// We need to set the current user as Administrator otherwise users
			// that are not part
			// of administrator group will not be able to put their tasks in the
			// queues
			WTPrincipal administrator = SessionHelper.manager.getAdministrator();
			WTPrincipal principal = SessionContext.setEffectivePrincipal(administrator);
			ProcessingQueue queue = QueueHelper.manager.getQueue(PREDICTIVE_CACHING_QUEUE);
			if (queue == null) {
				StandardQueueService service = (StandardQueueService) ManagerServiceFactory.getDefault().getManager(wt.queue.StandardQueueService.class);
				queue = service.createQueue(PREDICTIVE_CACHING_QUEUE, true);
			}
			boolean current = SessionServerHelper.manager.setAccessEnforced(false);
			try {
				// WTPrincipal principal =
				// SessionHelper.manager.getAdministrator();
				String method = "executePredictiveCaching";
				if (runImmediately)
					method = "executeImmediatePredictiveCaching";
				String className = "com.ptc.windchill.enterprise.replication.server.ReplicationHelper";

				Class[] argType = { ObjectReference.class, Collection.class };
				Object[] args = { rVaultRef, objects };
				queue.addEntry(administrator, method, className, argType, args);
			} finally {
				SessionServerHelper.manager.setAccessEnforced(current);
				SessionContext.setEffectivePrincipal(principal);
			}
		} else {
			logger.debug("Predictive Caching is not enabled.");
		}
	}

	/**
	 * Refer com.ptc.windchill.enterprise.replication.server.ReplicationHelper for the latest code This method will get only used when the property
	 * wt.fv.oldReplicationRulesAndSchedules=true, By default its false
	 * 
	 * @deprecated
	 * @param vault
	 * @param crHistory
	 * @throws WTException
	 */
	private void doReplication(ReplicaVault vault, ContReplHistory crHistory) throws WTException {
		// purgeUndeliveredMasteredOnReplicaItems();
		//
		// ReplicatorElemFinder repFinder = new ReplicatorElemFinder();
		// long idRepl =
		// PersistenceHelper.getObjectIdentifier(crHistory).getId();
		// int totalElems = 0;
		// //use this varable for all transactions needed in this method
		// Transaction trx = null;
		// boolean sessionInProgress = true; //checks if current cont. repl.
		// session is in progress
		// //ContReplHistory gets EXECUTING status when scheduling queue calls
		// it
		// if ( logger.isDebugEnabled() )
		// logger.debug("Started Replication with id ["+idRepl+"] on ReplicaVault ["+vault.getName()+"]");
		//
		// int numberReturned = FvProperties.REVAULT_QUERY_SIZE;
		//
		// //check if vault is not read only, if it is abort replication
		// if ( vault.isReadOnly() || (!vault.isEnabled()) ){
		// if ( vault.isReadOnly() ){
		// logger.debug("["+idRepl+"] ERROR: ReplicaVault ["+vault.getName()+"] is Read Only.  Aborting Replication!");
		// }
		// if ( !vault.isEnabled() ){
		// logger.debug("["+idRepl+"] ERROR: ReplicaVault ["+vault.getName()+"] is Not Enabled.  Aborting Replication!");
		// }
		// sessionInProgress = false;
		// trx = new Transaction();
		// try{
		// trx.start();
		// PersistenceServerHelper.manager.lock(crHistory,
		// FvProperties.REVAULTING_LOCK_TIME, 1 );
		// crHistory = (ContReplHistory)
		// PersistenceServerHelper.manager.restore(crHistory);
		// crHistory.updateSessionStatus(SchedStatusInfo.ABORTED);
		// crHistory =
		// (ContReplHistory)PersistenceHelper.manager.save(crHistory);
		// trx.commit();
		// trx = null;
		// }
		// finally{
		// if ( trx != null){
		// trx.rollback();
		// }
		// }
		// }
		//
		// if ( sessionInProgress ){
		// preReplicationRepItemCleanUp(vault);
		// }
		// //add new finder
		//
		//
		// crHistory = (ContReplHistory)
		// PersistenceServerHelper.manager.restore(crHistory);
		// sessionInProgress = crHistory.isReplicationInProgress();
		//
		// while ((numberReturned >= FvProperties.REVAULT_QUERY_SIZE)&&
		// sessionInProgress ){
		// Vector numOut = new Vector();
		// Vector foundElems = repFinder.findEromOtherRemoteVaultItems(vault,
		// numOut);
		//
		// numberReturned = ((Integer)(numOut.firstElement())).intValue();
		// if ( logger.isDebugEnabled() )
		// logger.debug("["+idRepl+"] Got "+foundElems.size()+" elements to be copied between vaults on replica site.");
		// totalElems = totalElems + foundElems.size();
		//
		// Enumeration elemEnum = foundElems.elements();
		// trx = new Transaction();
		// try{
		// trx.start();
		// PersistenceServerHelper.manager.lock(crHistory,
		// FvProperties.REVAULTING_LOCK_TIME, 1 );
		// crHistory = (ContReplHistory)
		// PersistenceServerHelper.manager.restore(crHistory);
		// crHistory = crHistory.updateTotalElem(totalElems+1);
		// crHistory =
		// (ContReplHistory)PersistenceHelper.manager.save(crHistory);
		// sessionInProgress = crHistory.isReplicationInProgress();
		// trx.commit();
		// trx = null;
		// }catch(WTException e){
		// logger.error("", e);
		// } //D.N. Affect the process, only visual representation.
		// finally{
		// if ( trx != null ){
		// trx.rollback();
		// }
		// }
		// while ( elemEnum.hasMoreElements() && sessionInProgress){
		// //attempt to replicate the element
		// boolean ok = false;
		// Persistable[] tmp = (Persistable[])elemEnum.nextElement();
		// try{
		// remoteCopyItemVaultToVault(vault,(ReplicatedItem)tmp[0],
		// (ReplicaFolder)tmp[1], crHistory );
		// ok =true;
		// }
		// catch(WTException e)
		// {
		// logger.error("Exception during replication, continuing to replicate the other items",
		// e);
		// }//if one element fails, process should not stop
		// finally{
		// if ( !ok ){
		// // if mover failed need to record the failure
		// trx = new Transaction();
		// try{
		// trx.start();
		// PersistenceServerHelper.manager.lock(crHistory,
		// FvProperties.REVAULTING_LOCK_TIME, 1 );
		// crHistory = (ContReplHistory)
		// PersistenceServerHelper.manager.restore(crHistory);
		// crHistory.incrementErrorRuns();
		// crHistory =
		// (ContReplHistory)PersistenceHelper.manager.save(crHistory);
		// trx.commit();
		// trx = null;
		// }
		// finally{
		// if ( trx != null ){
		// trx.rollback();
		// }
		// }
		// }
		// crHistory = (ContReplHistory)
		// PersistenceServerHelper.manager.restore(crHistory);
		// sessionInProgress = crHistory.isReplicationInProgress();
		// }
		// }
		//
		// }
		// numberReturned = FvProperties.REVAULT_QUERY_SIZE;
		//
		// //get items from Original content and copy them
		// while ((numberReturned >= FvProperties.REVAULT_QUERY_SIZE)&&
		// sessionInProgress ){
		// Vector numOut = new Vector();
		// Vector foundElems = repFinder.findFromOrigContentItems(vault,
		// numOut);
		//
		// numberReturned = ((Integer)(numOut.firstElement())).intValue();
		// if ( logger.isDebugEnabled() )
		// logger.debug("["+idRepl+"] Got "+foundElems.size()+" elements the Master Site.");
		// totalElems = totalElems + foundElems.size();
		//
		// Enumeration elemEnum = foundElems.elements();
		// //following transactions should be nested,
		// //but since the 3rd is in the while() loop, if it fails, the whole
		// set of transctions
		// //will roll back. I try to avoid this.
		// trx = new Transaction();
		// try{
		// trx.start();
		// PersistenceServerHelper.manager.lock(crHistory,
		// FvProperties.REVAULTING_LOCK_TIME, 1 );
		// crHistory = (ContReplHistory)
		// PersistenceServerHelper.manager.restore(crHistory);
		// crHistory = crHistory.updateTotalElem(totalElems+1);
		// crHistory =
		// (ContReplHistory)PersistenceHelper.manager.save(crHistory);
		// trx.commit();
		// trx = null;
		// }
		//
		// catch(WTException e)
		// {
		// logger.error("", e);
		// } //D.N. Affect the process, only visual representation.
		// finally{
		// if ( trx != null ){
		// trx.rollback();
		// }
		// }
		// while ( elemEnum.hasMoreElements() && sessionInProgress){
		// //attempt to replicate the element
		// boolean ok =false;
		// try{
		// replicateStreamed((Streamed)elemEnum.nextElement(), vault, crHistory,
		// null);
		// ok = true;
		// }
		// catch(WTException e)
		// {
		// logger.error("", e);
		// }//if one element fails, process should not stop
		// finally{
		// if ( !ok ){
		// // if mover failed need to record the failure
		// trx = new Transaction();
		// try{
		// trx.start();
		// PersistenceServerHelper.manager.lock(crHistory,
		// FvProperties.REVAULTING_LOCK_TIME, 1 );
		// crHistory = (ContReplHistory)
		// PersistenceServerHelper.manager.restore(crHistory);
		// crHistory.incrementErrorRuns();
		// crHistory =
		// (ContReplHistory)PersistenceHelper.manager.save(crHistory);
		// trx.commit();
		// trx = null;
		// }
		// finally{
		// if ( trx != null ){
		// trx.rollback();
		// }
		// }
		// }
		// //check if current session is still in progress
		// crHistory = (ContReplHistory)
		// PersistenceServerHelper.manager.restore(crHistory);
		// sessionInProgress = crHistory.isReplicationInProgress();
		// }
		// }
		// }
		// //new finder for the MasteredOnReplicaItems
		// int[] num_q = new int[1];
		// num_q[0] = FvProperties.REVAULT_QUERY_SIZE;
		// sessionInProgress = crHistory.isReplicationInProgress();
		// while ((num_q[0] >= FvProperties.REVAULT_QUERY_SIZE)&&
		// sessionInProgress){
		// Vector remoteItems =
		// repFinder.findRemotelyUploadedMasterContentItems(vault, num_q);
		//
		// numberReturned = remoteItems.size();
		// Enumeration foundElems = remoteItems.elements();
		//
		//
		// if ( logger.isDebugEnabled() )
		// logger.debug("["+idRepl+"] Got "+numberReturned+" Mastered On Replica elements to be uploaded from the Replica Site.");
		// totalElems = totalElems + numberReturned;
		//
		// trx = new Transaction();
		// try{
		// trx.start();
		// PersistenceServerHelper.manager.lock(crHistory,
		// FvProperties.REVAULTING_LOCK_TIME, 1 );
		// crHistory = (ContReplHistory)
		// PersistenceServerHelper.manager.restore(crHistory);
		// crHistory = crHistory.updateTotalElem(totalElems+1);
		// crHistory =
		// (ContReplHistory)PersistenceHelper.manager.save(crHistory);
		// sessionInProgress = crHistory.isReplicationInProgress();
		// trx.commit();
		// trx = null;
		// }
		//
		// catch(WTException e)
		// {
		// logger.error("", e);
		// } //D.N. Affect the process, only visual representation.
		// finally{
		// if ( trx != null ){
		// trx.rollback();
		// }
		// }
		// while ( foundElems.hasMoreElements() && sessionInProgress){
		// trx = new Transaction();
		// try{
		//
		//
		// trx.start();
		// //attempt to replicate the element
		// MasteredOnReplicaItem item =
		// (MasteredOnReplicaItem)foundElems.nextElement();
		//
		// RemoteContentUploadEvent revEvent =
		// RemoteContentUploadEvent.newRemoteContentUploadEvent(
		// (new
		// Long(PersistenceHelper.getObjectIdentifier(item).getId())).toString(),
		// "wt.fv.master.CCSElemMover.uploadRemoteContent",
		// new Serializable[]{
		// ObjectReference.newObjectReference(item)},
		// crHistory);
		// try{
		// getManagerService().dispatchVetoableEvent( revEvent,
		// revEvent.getEventKey() );
		// }
		// catch (Exception exc){
		// if (exc instanceof WTException){
		// throw (WTException)exc;
		// }
		// throw new WTException(exc);
		// }
		//
		// //remoteCopyItemVaultToVault(vault,(ReplicatedItem)tmp[0],
		// (ReplicaFolder)tmp[1], crHistory );
		// trx.commit();
		// trx = null;
		// }
		// catch(WTException e)
		// {
		// logger.error("Exception during replication, continuing to replicate the other items",
		// e);
		// }//if one element fails, process should not stop
		// finally{
		// if ( trx != null ){
		// trx.rollback();
		// // if mover failed need to record the failure
		// trx = new Transaction();
		// try{
		// trx.start();
		// PersistenceServerHelper.manager.lock(crHistory,
		// FvProperties.REVAULTING_LOCK_TIME, 1 );
		// crHistory = (ContReplHistory)
		// PersistenceServerHelper.manager.restore(crHistory);
		// crHistory.incrementErrorRuns();
		// crHistory =
		// (ContReplHistory)PersistenceHelper.manager.save(crHistory);
		// trx.commit();
		// trx = null;
		// }
		// finally{
		// if ( trx != null ){
		// trx.rollback();
		// }
		// }
		// }
		// //check if current session is still in progress
		// crHistory = (ContReplHistory)
		// PersistenceServerHelper.manager.restore(crHistory);
		// sessionInProgress = crHistory.isReplicationInProgress();
		//
		// }
		// }
		//
		// }
		// numberReturned = FvProperties.REVAULT_QUERY_SIZE;
		//
		// if ( sessionInProgress ){
		// logger.debug("["+idRepl+"] Session finders are done. ");
		// }
		// //need to check for completion here in case there were no elems to
		// replicate
		// if ( (crHistory != null) && sessionInProgress ){
		// trx = new Transaction();
		// try{
		// trx.start();
		// PersistenceServerHelper.manager.lock(crHistory,
		// FvProperties.REVAULTING_LOCK_TIME, 1);
		// crHistory = (ContReplHistory)
		// PersistenceServerHelper.manager.restore( crHistory );
		// crHistory.updateTotalElem(totalElems);
		// crHistory.checkForCompletition();
		// crHistory =
		// (ContReplHistory)PersistenceHelper.manager.save(crHistory);
		// trx.commit();
		// trx = null;
		//
		// }
		// finally{
		// if ( trx != null ){
		// trx.rollback();
		// }
		// }
		// }
	}

	public void remoteCleanUpFolder(ReplicaVault vault, ReplicaFolder folder, Enumeration itemsToRemove) throws WTException {
		Vector itemIdList = new Vector(0);
		// fill itemIdList with ids of the items, which asoc. files should be
		// removed
		// from the ReplicaVault
		while (itemsToRemove.hasMoreElements()) {
			try {
				ReplicatedItem item = (ReplicatedItem) itemsToRemove.nextElement();
				item = (ReplicatedItem) PersistenceServerHelper.manager.restore(item);
				itemIdList.addElement(new Long(item.getUniqueSequenceNumber()));
			} catch (ObjectNoLongerExistsException e) {
				// no problem , if item d.n.e. we skip it
			}
		}

		// create an evelope to be sent
		ActionShippingItem psi = ShippingHelper.service.createActionShippingItem();
		// create a reply envelope
		ActionShippingItem reply = ShippingHelper.service.createActionShippingItem();
		try {
			ShippingLabel back = ShippingHelper.service.createShippingLabel(DeliveryType.REMOTE_IMMEDIATE, this.inboxName, this.outboxName, "don't bother", this.transportName);
			// create description of what to execute upon return
			MethodInvocationDesc midReply = MethodInvocationDesc.newMethodInvocationDesc();
			// set method
			midReply.setTargetClass("wt.fv.master.StandardMasterService");
			midReply.setTargetMethod("remoteCleanUpFolderComplete");
			// set argument types of the method
			midReply.setArgTypes(new String[] { Long.class.getName(), Long.class.getName(), Vector.class.getName() });
			// set values for arguments
			midReply.setArgs(new Serializable[] { new Long(PersistenceHelper.getObjectIdentifier(vault).getId()), new Long(PersistenceHelper.getObjectIdentifier(folder).getId()),
					new Vector(0) });
			// set invok. desc. in reply envelope
			reply.setDesc(midReply);

			ShippingLabel to = ShippingHelper.service.createShippingLabel(DeliveryType.REMOTE_IMMEDIATE, this.inboxName, this.outboxName, vault.getSite().getUrl(),
					this.transportName);

			// set desc. of what to exec on the remote side
			MethodInvocationDesc mid = MethodInvocationDesc.newMethodInvocationDesc();

			// set name and signature of method to be exec.
			mid.setTargetClass("wt.fv.replica.StandardReplicaService");
			mid.setTargetMethod("cleanUpFilesInFolder");
			mid.setArgTypes(new String[] { Long.class.getName(), Vector.class.getName(), String.class.getName(), ActionShippingItem.class.getName() });
			// set values for the args
			mid.setArgs(new Serializable[] { new Long(PersistenceHelper.getObjectIdentifier(folder).getId()), itemIdList, // list of
					// filenames to be
					// removed
					StandardInterSvrComService.MASTER_URL, reply }); // include
																		// return
																		// letter

			psi.setDesc(mid);
			// send the to envelope
			ShippingHelper.service.sendItem(psi, to);
		} catch (WTPropertyVetoException e) {
			logger.debug("Clean up remote folders failed.", e);
			throw new WTException(e);
		}
	}

	public static void remoteCleanUpFolderComplete(Long vaultID, Long folderID, Vector removedItems) throws WTException {
		try {
			logger.debug("Remote folder cleanup completed, updating statuses");
			ObjectReference vaultRef = ObjectReference.newObjectReference(ObjectIdentifier.newObjectIdentifier(ReplicaVault.class, vaultID.longValue()));
			ReplicaVault vault = (ReplicaVault) vaultRef.getObject();
			ObjectReference folderRef = ObjectReference.newObjectReference(ObjectIdentifier.newObjectIdentifier(ReplicaFolder.class, folderID.longValue()));
			ReplicaFolder folder = (ReplicaFolder) vaultRef.getObject();
		}
		// if any of the above fails, we are done
		catch (WTRuntimeException e) {
			logger.debug("", e);
			throw new WTException(e);
		}

		Enumeration removedIds = removedItems.elements();
		Transaction trx = null;
		while (removedIds.hasMoreElements()) {
			try {
				Long itemId = (Long) removedIds.nextElement();
				QuerySpec qs = new QuerySpec(ReplicatedItem.class);
				qs.appendWhere(new SearchCondition(ReplicatedItem.class, ReplicatedItem.UNIQUE_SEQUENCE_NUMBER, SearchCondition.EQUAL, itemId.longValue()));
				QueryResult qr = PersistenceServerHelper.manager.query(qs);

				if (qr.hasMoreElements()) {
					ReplicatedItem ri = (ReplicatedItem) qr.nextElement();
					trx = new Transaction();
					try {
						trx.start();
						PersistenceServerHelper.manager.lock(ri, FvProperties.REVAULTING_LOCK_TIME, 1);
						PersistenceServerHelper.manager.remove(ri);

						trx.commit();
						trx = null;
					} finally {
						if (trx != null) {
							trx.rollback();
						}
					}
				}
				logger.debug("Object statuses updated successfully");
			}
			// ADD catch for lock exception
			catch (WTRuntimeException e) {
				// there is no problem if the element is not in DB anymore
			}
		}
	}

	public void remoteCopyItemVaultToVault(ReplicaVault destVault, ReplicatedItem itemToCopy, ReplicaFolder fromFolder, ContReplHistory session) throws WTException {
		boolean success = false;
		boolean decrementTotalRuns = false;
		FileFolder activeFolder = null;
		if (!destVault.isWriteEnabled()) {
			logger.debug("vault is not writable" + destVault);
			throw new FvFileCanNotBeStored(FV_RESOURCE, fvResource.CANT_SAVE_VAULT_RO, null);
		}
		activeFolder = StandardFvService.getActiveFolder(destVault);
		if (activeFolder == null) {
			logger.debug("no active folder found for vault" + destVault);
			throw new FvFileCanNotBeStored(FV_RESOURCE, fvResource.CANT_SAVE_NO_ACT_FOLDER, null);
		}
		try {
			try {
				itemToCopy = (ReplicatedItem) PersistenceServerHelper.manager.restore(itemToCopy);
			} catch (ObjectNoLongerExistsException e) {
				logger.debug("source object to replicate could not be found - object may have been deleted");
				decrementTotalRuns = true;
			}

			// this mover we get below will always be RVToRVLocalMOver or
			// LVToLVMover
			ContentMover mover = ContentMover.getMover(itemToCopy, destVault);
			if (mover != null && mover.isMoveNeeded()) {
				ReplicatedItem ri = (ReplicatedItem) mover.move();
				if (ri != null) {
					success = true;
					if (mover.isIndexable()) {
						logger.debug("Indexing the Content.");
						WTCollection riCol = new WTHashSet();
						riCol.add(ri);
						IndexPolicyHelper.manager.index(riCol);
					}
				}
			} else {
				logger.debug("no replication done for this item as it already exists in destination");
				decrementTotalRuns = true;
			}
		} catch (Throwable t) {
			logger.error("replication failed for object " + itemToCopy, t);
		} finally {
			// Create replicated items with status NOT_REPLICATED if move is not
			// sucessful
			if (!success && !decrementTotalRuns) {
				ReplicatedItem ri = null;
				Transaction trx = null;
				try {
					trx = new Transaction();
					trx.start();
					ri = ReplicatedItem.newReplicatedItem();
					// set folder reference
					try {
						ri.setFolderReference(ObjectReference.newObjectReference(activeFolder));
					} catch (WTPropertyVetoException e) {
						throw new WTException(e);
					}
					// set stream id
					ri.setStreamId(itemToCopy.getStreamId());
					// set uniquesequencenumber
					ri.setUniqueSequenceNumber(FileNameUSNPool.getUniqueSequenceNumberForTargetItem(itemToCopy.getUniqueSequenceNumber()));
					// set status
					ri.setReplication(ReplicationStatus.NOT_REPLICATED);
					ri = (ReplicatedItem) PersistenceHelper.manager.save(ri);
					trx.commit();
					trx = null;
				} catch (WTPropertyVetoException e) {
					throw new WTException(e);
				} finally {
					if (trx != null) {
						trx.rollback();
					}
				}
			}
			updateReplicaSessionStatus(session, success, decrementTotalRuns);
		}
	}

	public static void updateSiteConfig(Site remSite) throws WTException {
		if (!remSite.isReplica()) { // must be a replica site
			return;
		}

		boolean current = true;
		try {
			// current = SessionServerHelper.manager.setAccessEnforced( /*
			// enforcement */ false );
			if (logger.isDebugEnabled())
				logger.debug("Updating  Config Info for Site [" + remSite.getName() + "]");
			SiteDesc siteD = SiteConfigGenerator.createSiteDesc(remSite);
			Site masterSite = InterSvrComHelper.service.getLocalSite();

			siteD.setMasterURL(masterSite.getUrl());
			long myId = (PersistenceHelper.getObjectIdentifier(masterSite)).getId();
			siteD.setMasterSiteID(myId);

			String outString = "Preparing config info for delivery to Site [" + remSite.getName() + "]";
			outString += " to the method server located at " + remSite.getUrl();
			logger.debug(outString);

			MethodInvocationDesc methodDesc = MethodInvocationDesc.newMethodInvocationDesc();
			methodDesc.setTargetClass("wt.fv.replica.StandardReplicaService");
			methodDesc.setTargetMethod("receiveConfigInfo");
			String[] argTypes = new String[2];
			argTypes[0] = "java.lang.String";
			argTypes[1] = "wt.fv.configurator.SiteDesc";
			methodDesc.setArgTypes(argTypes);
			Serializable[] theArgs = { masterSite.getName(), siteD };
			methodDesc.setArgs(theArgs);

			ActionShippingItem theItem = ShippingHelper.service.createActionShippingItem();
			theItem.setDesc(methodDesc);
			ShippingLabel to = ShippingHelper.service.createShippingLabel(DeliveryType.IMMEDIATE, StandardMasterService.inboxName, StandardMasterService.outboxName,
					remSite.getUrl(), StandardMasterService.transportName);

			TransportType tType = new TransportType();
			tType.setTransportType(StandardMasterService.transportName);

			SiteAddress sAdd = SiteAddress.newSiteAddress();
			sAdd.setAddress(remSite.getUrl());

			ShippingHelper.service.sendImmediateItem(theItem, sAdd, tType);
			logger.debug("Sent Update Config to Site [ " + remSite.getName() + "]");
		} catch (WTPropertyVetoException pve) {
			logger.error("", pve);
			throw new WTException(pve);
		} finally {
			// re-enable authentication
			// current = SessionServerHelper.manager.setAccessEnforced(
			// current);
		}
	}

	public static void checkUpdateRelSiteConfig(Persistable object) throws WTException {
		Site masterSite = InterSvrComHelper.service.getLocalSite();

		if (!(object instanceof Site)) {
			Persistable target = object;
			if (target instanceof FvMount) {
				target = (Persistable) ((FvMount) target).getFolder();
			}
			if (target instanceof ReplicaFolder) {
				target = (Persistable) ((ReplicaFolder) target).getVault();
			}
			if (target instanceof ReplicaVault) {
				target = (Persistable) ((ReplicaVault) target).getSite();
			}
			if (target instanceof FvHost) {
				target = (Persistable) ((FvHost) target).getSite();
			}
			// if we passes through the if chain above, and incoming object was
			// what we were waiting, the target will store Site related to the
			// object
			if (target instanceof Site) {
				// if site object is not our locla site, update its config
				Site updSite = (Site) target;
				if (updSite.isReplica()) {
					updateSiteConfig(updSite);
				}
			}
		}
	}

	/**
	 * This method performs the pre Replication Clean Up. This method will delete all the ReplicatedItem objects which 15 days old and are not REPLICATED
	 * 
	 * @param rv
	 * @throws WTException
	 */
	public void preReplicationRepItemCleanUp(ReplicaVault rv) throws WTException {
		logger.trace("[StandardMasterService.preReplicationRepItemCleanUp] ");
		int purgeInterval = FvProperties.PURGE_NOT_REPLICATED_ITEM_OLDER_THAN;
		purgeInterval = purgeInterval > 2 ? purgeInterval : 2; // min 48 hours
		long timeLimit = purgeInterval * 24 * 60 * 60 * 1000L;
		int count = 0;
		QuerySpec qs = new QuerySpec();
		int idxI = qs.addClassList(ReplicatedItem.class, true);
		int idxF = qs.addClassList(ReplicaFolder.class, false);

		int idxJoinF[] = { idxF, -1 };
		qs.appendWhere(new SearchCondition(ReplicaFolder.class, FileFolder.VAULT_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, PersistenceHelper
				.getObjectIdentifier(rv).getId()), idxJoinF);
		qs.appendAnd();

		int idxJoinF_I[] = { idxF, idxI };
		qs.appendWhere(new SearchCondition(ReplicaFolder.class, WTAttributeNameIfc.ID_NAME, ReplicatedItem.class, StoredItem.FOLDER_REFERENCE + "."
				+ WTAttributeNameIfc.REF_OBJECT_ID), idxJoinF_I);
		qs.appendAnd();

		int idxJoinI[] = { idxI, -1 };
		qs.appendWhere(new SearchCondition(ReplicatedItem.class, ReplicatedItem.REPLICATION, SearchCondition.NOT_EQUAL, ReplicationStatus.REPLICATED), idxJoinI);
		qs.appendAnd();

		qs.appendWhere(
				new SearchCondition(ReplicatedItem.class, Persistable.PERSIST_INFO + "." + PersistInfo.CREATE_STAMP, SearchCondition.LESS_THAN, new Timestamp(System
						.currentTimeMillis() - timeLimit)), idxJoinI);
		qs.appendAnd();

		int idxJoinII[] = { idxI, -1 };
		qs.appendWhere(new SearchCondition(ReplicatedItem.class, ReplicatedItem.REPLICATION, SearchCondition.NOT_EQUAL, ReplicationStatus.CLEANED), idxJoinII);

		QueryResult qr = null;
		while (true) {
			try {
				qr = PersistenceHelper.manager.find((StatementSpec) qs);
				count += deleteNonReplicatedQueriedRepItems(qr);
				break;
			} catch (PartialResultException pre) {
				qr = pre.getQueryResult();
				count += deleteNonReplicatedQueriedRepItems(qr);
			}

		}
		logger.debug("Performed Pre-Replication CleanUp. Removed [" + count + "] items.");
	}

	private int deleteNonReplicatedQueriedRepItems(QueryResult qr) {
		int count = 0;
		while (qr.hasMoreElements()) {
			Persistable[] tmp = (Persistable[]) qr.nextElement();
			ReplicatedItem repI = (ReplicatedItem) tmp[0];
			Transaction trx = new Transaction();
			try {
				trx.start();
				PersistenceServerHelper.manager.lock(repI);
				repI = (ReplicatedItem) PersistenceHelper.manager.refresh(repI);
				PersistenceServerHelper.manager.remove(repI);
				count++;
				trx.commit();
				trx = null;
			} catch (WTException e) {
				logger.warn(e.getMessage(), e);
			} finally {
				if (trx != null) {
					trx.rollback();
				}
			}
		}
		return count;
	}

	private void initiateBroadcastConfig() {
		logger.debug("Initiating broadcast threads to configure all replica sites.");
		brodcastSiteConfigOnStartUp();
	}

	private void brodcastSiteConfigOnStartUp() {
		Site aSite = null;
		boolean current = false;
		// MethodContext cntxt = new MethodContext("",null);
		try {
			current = SessionServerHelper.manager.setAccessEnforced( /* enforcement */false);

			Site masterSite = InterSvrComHelper.service.getLocalSite();
			if (masterSite != null) {
				StandardInterSvrComService service = (StandardInterSvrComService) ManagerServiceFactory.getDefault().getManager(StandardInterSvrComService.class);
				Enumeration sites = service.getAllSitesServer();

				while (sites.hasMoreElements()) {
					aSite = (Site) sites.nextElement();
					// boadcast to all replica sites except the master and
					// portal (if not replica)
					if (!masterSite.getName().equals(aSite.getName()) && aSite.isReplica()) {
						WTThread configBroadcaster = new WTThread(new ConfigBroadcastThread(aSite));
						configBroadcaster.start();
					}
				}
			} else
				logger.debug("ERROR: local site not found; check site URLs");
		} catch (WTException e) {
			logger.debug("ERROR: while Broadcasting Configuration for sites", e);
		} finally {
			current = SessionServerHelper.manager.setAccessEnforced(current);
		}
	}

	private class ConfigBroadcastThread implements Runnable {
		Site _replicaSite = null;

		ConfigBroadcastThread(Site replicaSite) {
			super();
			_replicaSite = replicaSite;
		}

		public void run() {
			boolean current = false;
			MethodContext cntxt = null;
			try {
				try {
					Thread.sleep(15000);
				} catch (InterruptedException ignored) {
				}
				cntxt = new MethodContext("", null);
				current = SessionServerHelper.manager.setAccessEnforced( /* enforcement */false);

				// write thread info to the log
				if (logger.isDebugEnabled()) {
					if (_replicaSite == null) {
						logger.debug("Config Broadcast Thread running. Broadcasting for all replica sites.");
					} else {
						logger.debug("Config Broadcast Thread running. Broadcasting for site [" + _replicaSite.getName() + "]");
					}
				}
				if (_replicaSite == null) {
					brodcastSiteConfigOnStartUp();
				} else {
					logger.debug("Broadcasting Configuration for site [" + _replicaSite.getName() + "]");
					try {
						updateSiteConfig(_replicaSite);
					} catch (WTException wte) {
						logger.debug("ERROR: while Brodcasting Configuration for site [" + _replicaSite.getName() + "]", wte);
					}
				}
			} finally {
				if (cntxt != null) {
					current = SessionServerHelper.manager.setAccessEnforced(current);
					cntxt.unregister();
				}
			}
		}
	}

	protected static void onReplicaSaveFailure(Object[] objectArr) throws WTException, WTPropertyVetoException {
		Manager fvMan = ManagerServiceFactory.getDefault().getManager(wt.fv.FvService.class);
		if (fvMan == null) {
			Object[] param = { "wt.fv.FvService" };
			throw new WTException("wt.fc.fcResource", wt.fc.fcResource.UNREGISTERED_SERVICE, param);
		}

		logger.debug("In multi object onSaveFailure");
		Transaction trx = new Transaction();
		try {
			trx.start();
			for (int i = 0; i < objectArr.length; i++) {
				Object object = objectArr[i];
				if (logger.isDebugEnabled()) {
					logger.debug("throwing overflow event for " + object);
				}
				onReplicaSaveFailure(object);
			}
			trx.commit();
			trx = null;
		} finally {
			if (trx != null) {
				trx.rollback();
			}
		}
	}

	protected static void onReplicaSaveFailure(Object object) throws WTException, WTPropertyVetoException {
		Manager fvMan = ManagerServiceFactory.getDefault().getManager(wt.fv.FvService.class);

		if (fvMan == null) {
			Object[] param = { "wt.fv.FvService" };
			throw new WTException("wt.fc.fcResource", wt.fc.fcResource.UNREGISTERED_SERVICE, param);
		}
		Transaction trx = new Transaction();
		try {
			trx.start();

			FvServiceEvent event = new FvServiceEvent((FvService) fvMan, FvServiceEvent.OVERFLOW, object);
			try {
				fvMan.getManagerService().dispatchVetoableEvent(event, event.getEventKey());
			} catch (Exception exc) {
				if (exc instanceof WTException) {
					throw (WTException) exc;
				}
				throw new WTException(exc);
			}

			trx.commit();
			trx = null;
		} finally {
			if (trx != null) {
				trx.rollback();
			}
		}
	}

	/**
	 * This method purges the Undelivered MasteredOnReplicaItems This method will purge the expired MasteredOnReplicaItem that were not successfully uploaded to the replica site
	 * 
	 * @throws WTException
	 */
	public void purgeUndeliveredMasteredOnReplicaItems() throws WTException {
		long timeLimit = 30l * 24 * 60 * 60 * 1000; // milliseconds in 30 days -
													// default wait period
		if (logger.isDebugEnabled())
			logger.debug("Purging expired MasteredOnReplicaItem objects created before [" + (new Timestamp(System.currentTimeMillis() - timeLimit)).toString() + "]");

		// create a query that finds all MasteredOnReplicaItems that were not
		// successfully uploaded to replica site(or we do not know that result
		// was a success)
		QuerySpec qs = new QuerySpec();
		QueryResult qr = null;
		qs.setAdvancedQueryEnabled(true);
		int idxMOR = qs.appendClassList(MasteredOnReplicaItem.class, true);

		int[] idxJoin = { idxMOR, -1 };
		qs.appendWhere(
				new SearchCondition(MasteredOnReplicaItem.class, Persistable.PERSIST_INFO + "." + PersistInfo.CREATE_STAMP, SearchCondition.LESS_THAN, new Timestamp(System
						.currentTimeMillis() - timeLimit)), idxJoin);
		qs.appendAnd();

		// create sub expression
		QuerySpec subQS = new QuerySpec();
		subQS.setAdvancedQueryEnabled(true);
		try {
			subQS.getFromClause().setAliasPrefix("B");
		} catch (WTPropertyVetoException e) {
		}

		int idxAD = subQS.appendClassList(ApplicationData.class, false);
		int idxMOR_B = subQS.appendClassList(MasteredOnReplicaItem.class, false);

		subQS.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, idxMOR_B, true);

		TableExpression[] tables = new TableExpression[2];
		String[] aliases = new String[2];

		tables[0] = subQS.getFromClause().getTableExpressionAt(idxMOR_B);
		tables[1] = qs.getFromClause().getTableExpressionAt(idxMOR);
		aliases[0] = subQS.getFromClause().getAliasAt(idxMOR_B);
		aliases[1] = qs.getFromClause().getAliasAt(idxMOR);
		// /fill sub select
		subQS.appendWhere(new SearchCondition(new ClassAttribute(MasteredOnReplicaItem.class, WTAttributeNameIfc.ID_NAME), SearchCondition.EQUAL, new ClassAttribute(
				MasteredOnReplicaItem.class, WTAttributeNameIfc.ID_NAME)), tables, aliases);
		subQS.appendAnd();
		int[] idxJoinMOR_AD = { idxMOR_B, idxAD };
		subQS.appendWhere(new SearchCondition(MasteredOnReplicaItem.class, WTAttributeNameIfc.ID_NAME, ApplicationData.class, ApplicationData.STREAM_DATA + "."
				+ WTAttributeNameIfc.REF_OBJECT_ID), idxJoinMOR_AD);

		// add sub expression to the main expression
		qs.appendWhere(new NegatedExpression(new ExistsExpression(subQS)));

		qs.setQueryLimit(1000);

		int returnedNum = 1000;
		Transaction trx = new Transaction();
		try {
			trx.start();
			while (returnedNum >= 1000) {
				try {
					qr = PersistenceServerHelper.manager.query(qs);
				} catch (PartialResultException pre) {
					qr = pre.getQueryResult();
				}
				returnedNum = qr.size();
				if (logger.isDebugEnabled())
					logger.debug("Retrieved [" + returnedNum + "] incomplete MasteredOnReplicaItems to purge");
				while (qr.hasMoreElements()) {
					Persistable[] tmp = (Persistable[]) qr.nextElement();
					MasteredOnReplicaItem mor = (MasteredOnReplicaItem) tmp[0];
					try {
						PersistenceServerHelper.manager.lock(mor);
						mor = (MasteredOnReplicaItem) PersistenceHelper.manager.refresh(mor);
						PersistenceHelper.manager.delete(mor);
					} catch (WTException wte) {
						if (logger.isDebugEnabled())
							logger.debug("Error trying to remove MateredOnReplicaItem StreamId=[" + mor.getStreamId() + "]");
						throw wte;
					}
				}
			}
			trx.commit();
			trx = null;
		} catch (WTException wte) {
			logger.error("", wte);
		} finally {
			if (trx != null) {
				trx.rollback();
				logger.debug("Purge of incomplete MasteredOnReplicaItems failed.  Some objects are in use.");
			}
		}
	}

	/**
	 * Refer com.ptc.windchill.enterprise.replication.server.ReplicationHelper for the latest code This code will get only used when the property
	 * wt.fv.oldReplicationRulesAndSchedules=true, By default its false
	 * 
	 * @deprecated
	 * 
	 */
	private class ContentReplicationSession implements Runnable {
		private ReplicaVault vault = null;
		private ContReplHistory crHistory = null;
		private Object currentUser = null;
		private Object sessionContext = null;

		public ContentReplicationSession(ReplicaVault vault, ContReplHistory crHistory, Object currentUser, Object sessionContext) {
			this.vault = vault;
			this.crHistory = crHistory;
			this.currentUser = currentUser;
			this.sessionContext = sessionContext;
		}

		/**
		 * Refer com.ptc.windchill.enterprise.replication.server.ReplicationHelper for the latest code This method will get only used when the property
		 * wt.fv.oldReplicationRulesAndSchedules=true, By default its false
		 * 
		 * @deprecated
		 */
		public void run() {
			MethodContext cntx = null;
			boolean current = true;
			try {
				cntx = new MethodContext("", null);
				cntx.setEffectivePrincipal(currentUser);
				cntx.setSessionContext(sessionContext);

				current = SessionServerHelper.manager.setAccessEnforced(false);
				StandardMasterService service = (StandardMasterService) ManagerServiceFactory.getDefault().getManager(StandardMasterService.class);
				service.doReplication(vault, crHistory);
			} catch (WTException wte) {
				logger.debug("", wte);
			} finally {
				threadCount--;
				SessionServerHelper.manager.setAccessEnforced(current);
				cntx.unregister();
			}
		}
	}

	private void performStartupProcessControlAccess() throws ManagerException {
		logger.debug("Starting StandardMasterService");

		logger.trace("register MasterServiceEvent.PRE_DOWNLOAD, MasterServiceEvent.POST_DOWNLOAD");
		getManagerService().addEventBranch(MasterServiceEvent.generateEventKey(MasterServiceEvent.PRE_DOWNLOAD), MasterServiceEvent.class.getName(),
				MasterServiceEvent.PRE_DOWNLOAD);
		getManagerService().addEventBranch(MasterServiceEvent.generateEventKey(MasterServiceEvent.POST_DOWNLOAD), MasterServiceEvent.class.getName(),
				MasterServiceEvent.POST_DOWNLOAD);
		logger.trace("MasterServiceEvent.PRE_DOWNLOAD, MasterServiceEvent.POST_DOWNLOAD are registered");

		FvMasterServiceEventListener listener = new FvMasterServiceEventListener(this.getConceptualClassname());

		/*
		 * getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey( PersistenceManagerEvent.PRE_DELETE ));
		 * getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey( PersistenceManagerEvent.POST_DELETE ));
		 * getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey( PersistenceManagerEvent.PRE_MODIFY ));
		 * getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey( PersistenceManagerEvent.POST_MODIFY ));
		 * getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey( PersistenceManagerEvent.PRE_STORE ));
		 * getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey( PersistenceManagerEvent.POST_STORE ));
		 * getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey( PersistenceManagerEvent.REMOVE ));
		 */
		getManagerService().addEventListener(listener, DeliveryServiceEvent.generateEventKey(DeliveryServiceEvent.DELIVERY_FAILED));
		getManagerService().addEventListener(listener, DeliveryServiceEvent.generateEventKey(DeliveryServiceEvent.DELIVERY_COMPLETE));

		getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey(PersistenceManagerEvent.POST_MODIFY));
		getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey(PersistenceManagerEvent.POST_STORE));
		getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey(PersistenceManagerEvent.POST_DELETE));
		MasterEventListener list = new MasterEventListener(this.getConceptualClassname());
		getManagerService().addEventListener(list, ContentServiceEvent.generateEventKey(ContentServiceEvent.PRE_DOWNLOAD));
		getManagerService().addEventListener(list, ContentServiceEvent.generateEventKey(ContentServiceEvent.POST_DOWNLOAD));
		// if ( MasterProperties.VERBOSE ) {
		MasterServiceEventListener mlistener = new MasterServiceEventListener(this.CLASSNAME);
		getManagerService().addEventListener(mlistener, MasterServiceEvent.generateEventKey(MasterServiceEvent.PRE_DOWNLOAD));
		getManagerService().addEventListener(mlistener, MasterServiceEvent.generateEventKey(MasterServiceEvent.POST_DOWNLOAD));
		// }

		// create Delivery Outbox and Inbox
		logger.debug("Setting up Inbox and Outbox at startup");
		try {
			ShippingHelper.service.createOutbox(this.outboxType, this.outboxName);
			ReceiverHelper.service.createInbox(this.inboxType, this.inboxName);
		} catch (WTDeliveryException e) {
			throw new ManagerException(this, e, "Error in initializing Inbox and Outbox.");
		}

		// brodcast fresh slave configurations
		initiateBroadcastConfig();
	}

	/**
	 * Special method to handle post requests . The inputstream will be ignored for non chaptered data requests. Creo is the only known client that will send a plain text input
	 * stream .
	 * 
	 * @param req
	 * @param resp
	 * @throws WTException
	 */
	public static void doDirectDownload(HTTPRequest req, HTTPResponse resp, InputStream is) throws WTException {
		req.bizData = ContentHttp.parsePOSTInputStream(req, is);
	}

	public static void doDirectDownload(HTTPRequest req, HTTPResponse resp) throws WTException {

		Kogger.debug(StandardMasterService.class, "ketCustomFlag Check:" + req.getProperty(CGIConstants.CGI_REFERER));

		BufferedInputStream br = null;
		if (logger.isDebugEnabled())
			logger.debug("StdMasterSvc.doDirectDownload: Processing URL: " + req.getFullURL());
		try {
			if (!URLAuthenticator.checkAuthentication(req)) {
				if (logger.isDebugEnabled())
					logger.debug("Authorization failed while attempting to get: " + req.getFullURL());

				resp.setStatus(401);
				throw new WTException(RESOURCE, masterResource.AUTHENTICATION_FAILED, null);
			}

			boolean msie = req.getProperty(CGIConstants.CGI_USER_AGENT, "").indexOf("MSIE") >= 0 || req.getProperty(CGIConstants.CGI_USER_AGENT, "").indexOf("chromeframe") >= 0;
			String pathInfo = req.getProperty(CGIConstants.CGI_PATH_INFO, "");
			String originalFileName = pathInfo.substring(pathInfo.lastIndexOf('/') + 1);

			Properties reqProps = req.splitQueryString();
			String tmpFN = (String) reqProps.get(ContentHelper.ORIGINAL_FILE_NAME_TAG);
			if (tmpFN != null) {
				originalFileName = tmpFN;
			}
			String masterURL = (String) reqProps.get(InterSvrComConstants.PARAM_SITE);

			String folderIDStr = (String) reqProps.get(RedirectDownloadConstants.FOLDER_TAG);
			String folderTypeStr = (String) reqProps.get(RedirectDownloadConstants.FOLDER_TYPE_TAG);
			String fname = (String) reqProps.get(RedirectDownloadConstants.FILE_NAME_TAG);

			String mimeType = (String) reqProps.get(RedirectDownloadConstants.MIME_TAG);
			String principalIdString = (String) reqProps.get(RedirectDownloadConstants.USER_TAG);
			String appDataIdString = (String) reqProps.get(RedirectDownloadConstants.AD_TAG);

			String ref_length = reqProps.getProperty(RedirectDownloadConstants.REF_SIZE, "-1");
			String strRIID = reqProps.getProperty(RedirectDownloadConstants.REPLICATED_ITEM_TAG, "-1");

			String reqUrl = req.getProperty(CGIConstants.CGI_REFERER);

			// ############# 파일명 강제 변환 처리 ##########################
			if (reqUrl != null && reqUrl.indexOf("fileName=") >= 0) {
				reqUrl = URLDecoder.decode(reqUrl, "UTF-8");

				String fileName = reqUrl.substring(reqUrl.indexOf("fileName=") + 9);

				if (fileName.indexOf("&") >= 0) {
					fileName.substring(0, fileName.indexOf("&") - 1);
				}
				originalFileName = fileName;
			}
			// ############# 파일명 강제 변환 처리 ##########################

			originalFileName = ContentHttp.addForceContentInfoToResponse(req, resp, originalFileName, null, true, msie);

			resp.setHeader(CONTENT_TYPE_HDR, mimeType);

			// add expires header depending on the property.
			ContentHttp.addExpiresHeader(req, resp);
			// get all needed objects through caches
			long folderID = (new Long(folderIDStr)).longValue();
			boolean current = SessionServerHelper.manager.setAccessEnforced( /* enforcement */false);
			FvMount fvm = null;
			FileFolder fvf = null;
			Class folderType = null;
			if (folderTypeStr.equals(RedirectDownloadConstants.FOLDER_TYPE_CLASS_TO_URL_PARAM_MAP.get(FvFolder.class.getName()))) {
				folderType = FvFolder.class;
			} else {
				folderType = ReplicaFolder.class;
			}
			try {
				fvf = StandardFvService.getFileFolderFromCache(new ObjectIdentifier(folderType, folderID));
				fvm = StandardFvService.getLocalMount(fvf);
			} catch (ObjectNoLongerExistsException onle) {
				if (fvf == null)
					throw new WTException("StdMasterSvc.doDirectDownload:  Folder with id [" + folderID + "] does not exist!");
			} finally {
				SessionServerHelper.manager.setAccessEnforced(current);
			}

			StandardMasterService service = (StandardMasterService) ManagerServiceFactory.getDefault().getManager(StandardMasterService.class);
			logger.trace("firing master pre download event");
			MasterServiceEvent event = new MasterServiceEvent(MasterServiceEvent.PRE_DOWNLOAD, new Long(principalIdString), new Long(appDataIdString), new Long(Long.parseLong(
					fname, 16)), new Long(folderID), folderType, new Long(strRIID));
			service.getManagerService().dispatchVetoableEvent(event, event.getEventKey());
			logger.trace("master pre download event done");

			if (fvm == null) {
				throw new WTException(FV_RESOURCE, fvResource.CANT_READ_NO_LOC_MOUNT, null);
			}
			// get actual file path+name
			String path = fvm.getPath();
			File contentf = new BackupedFile(path, fname).getFirstFile();
			boolean contentfexists = true;
			contentfexists = contentf.exists();

			/* insert quarantine stuff */
			long expectedFileLength = Long.parseLong(ref_length);
			if (QuarantinedContentManager.getQuarantinedContentManager().isQuarantineEnabled() && expectedFileLength > -1) {
				Streamed s = null;
				current = SessionServerHelper.manager.setAccessEnforced(false);
				try {
					if (!contentfexists && contentf.getParentFile().exists()) {
						s = getStreamed(fvf, fname);
						QuarantinedContentManager.getQuarantinedContentManager().quarantineContent_FileDNE(s);
					} else if (contentfexists && (contentf.length() != expectedFileLength)) {
						s = getStreamed(fvf, fname);
						QuarantinedContentManager.getQuarantinedContentManager().quarantineContent_SizeInconsistency(s);
					}
				} catch (WTException wte) {
					logger.error("Could not add content with UnSeqNum [" + fname + "] in folder [" + fvf.getName() + "] to quarantine", wte);
				} finally {
					SessionServerHelper.manager.setAccessEnforced(current);
				}
				if (s != null) {
					if (!QuarantinedContentManager.getQuarantinedContentManager().isAllowDownloadQuarantinedContent()) {
						QuarantinedContentManager.getQuarantinedContentManager().onQuarantinedContentDownloadAttempt(s);
					}
				}
			}

			/* end quarantine stuff */
			if (!contentfexists) {
				throw new WTException(FV_RESOURCE, fvResource.CANT_READ_NO_FILE, new Object[] { contentf.getAbsolutePath() });
			}

			boolean isSecu = true;
			if (ContentHttp.isMultiChapterDownloadRequest(req)) {
				logger.debug("post doDirectDownload request");
				ContentHttp.generateMultipartResponse(req, resp, contentf);
			} else {

				SessionContext sessioncontext = SessionContext.newContext();
				try {
					SessionHelper.manager.setAdministrator();
					isSecu = AuthUtil.isContentSecu(appDataIdString, principalIdString);
				} catch (Exception e) {
					Kogger.error(StandardMasterService.class, e);
				} finally {
					SessionContext.setContext(sessioncontext);
				}

				if (!isSecu) {
					//String msg = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "00990");
					String msg = "권한이 없습니다.";
					String str = "\n <script language=\"javascript\">alert(\"" + msg + "\");</script>";
					// String str = "\n <script language=\"javascript\">\n alert(\"" + msg +
					// "\"); try{window.close();}catch(e){} \n </script>";
					// str += "\n <script language=\"javascript\">\n history.go(-1)" + "\n </script>";

					resp.setHeader("Content-Type", "text/html;charset=UTF-8");
					resp.setHeader("Content-Disposition", "");

					InputStream in = null;
					in = new ByteArrayInputStream(str.getBytes("UTF8"));
					br = new BufferedInputStream(in, bufferSize);
					OutputStream out = resp.getOutputStream();

					int len = 0;
					byte[] buffer = new byte[bufferSize];
					int cnt = 0;

					while ((len = br.read(buffer, 0, buffer.length)) > 0) {
						cnt += len;
						out.write(buffer, 0, len);
					}
					out.flush();
					return;

				} else {
					/* KET DRM start */
					Kogger.debug(StandardMasterService.class, "----------------------------------------------------------");
					Kogger.debug(StandardMasterService.class,
							"excute DRMHelper.encryptFile : " + appDataIdString + " / " + contentf + " / " + principalIdString + " / " + req.getProperty("cgi.remote_addr"));

					Kogger.debug(StandardMasterService.class, "ketCustomFlag Check:" + req.getProperty(CGIConstants.CGI_REFERER));

					if (DRMHelper.useDrm)
						contentf = DRMHelper.encryptFile(appDataIdString, contentf, principalIdString, req.getProperty(CGIConstants.CGI_REMOTE_ADDR),
								req.getProperty(CGIConstants.CGI_REFERER));
					Kogger.debug(StandardMasterService.class, "----------------------------------------------------------");
					/* KET DRM end */
					long fileSize = contentf.length();
					resp.setHeader("X-PTC-Content-Length", Long.toString(fileSize));
					resp.setHeader("Content-Length", Long.toString(fileSize));

					// long fid = folderID.longValue();
					FileInputStream fin = null;

					String folderPath = null;
					try {
						fin = new FileInputStream(contentf);
						br = new BufferedInputStream(fin, bufferSize);

						OutputStream out = resp.getOutputStream();

						int len = 0;
						byte[] buffer = new byte[bufferSize];
						int cnt = 0;

						while ((len = br.read(buffer, 0, buffer.length)) > 0) {
							cnt += len;
							out.write(buffer, 0, len);
						}
						out.flush();
						if (logger.isDebugEnabled())
							logger.debug("StdMasterSvc.doDirectDownload: " + cnt + " bytes successfully downloaded.");
					}
					// If anything goes wrong respond with SC_NOT_FOUND
					catch (java.io.FileNotFoundException fnf) { // redundant
						logger.error("StdMasterSvc.doDirectDownload: File [" + contentf.getName() + "] was not found.", fnf);
						resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
						throw new WTException(FV_RESOURCE, fvResource.CANT_READ_NO_FILE, new Object[] { contentf.getAbsolutePath() });
					} catch (IOException ioe) {
						logger.error("StdMasterSvc.doDirectDownload: IOException encountered during download", ioe);
						resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND);
						throw new WTException(FV_RESOURCE, fvResource.CANT_READ_NO_FILE, new Object[] { contentf.getAbsolutePath() });
					}
				}
			}
			logger.trace("firing master post download event");
			event = new MasterServiceEvent(MasterServiceEvent.POST_DOWNLOAD, new Long(principalIdString), new Long(appDataIdString), new Long(Long.parseLong(fname, 16)), new Long(
					folderID), folderType, new Long(strRIID));
			service.getManagerService().dispatchVetoableEvent(event, event.getEventKey());
			logger.trace("master post download event fired");

		} catch (Exception ex) {
			boolean isBrowser = false;
			for (int i = 0; i < supportedBrowsers.size(); i++) {
				if (req.getProperty(CGIConstants.CGI_USER_AGENT, "").indexOf(supportedBrowsers.get(i)) >= 0) {
					isBrowser = true;
					break;
				}
			}
			if (isBrowser) {
				logger.error("Error in doDirectDownload", ex);
				resp.setStatus(500, ex.getLocalizedMessage());
			} else {
				if (ex instanceof WTException)
					throw (WTException) ex;
				else
					throw new WTException(ex);
			}
			/*
			 * if(req.getProperty( CGIConstants.CGI_USER_AGENT, "").indexOf( "Java" ) == -1 && isWriteStarted == false){ boolean current = true; try{
			 * 
			 * //set access current = SessionServerHelper.manager.setAccessEnforced( false ); OutputStream os = resp.getOutputStream(); String acceptLanguage = req.getProperty(
			 * req.CGI_ACCEPT_LANGUAGE ); Vector preferences = LanguagePreference.getAcceptLanguagePreferences( acceptLanguage );
			 * 
			 * Enumeration downloadEnum = ( Enumeration )req.bizData;
			 * 
			 * // holder = ( ContentHolder )downloadEnum.nextElement( ); WTProperties props = WTProperties.getLocalProperties( );
			 * 
			 * ContentHtml generator = new ContentHtml();
			 * 
			 * generator.addToResponseExceptions(ex); //handleExceptionTP("viewContent", ex, true, props, Locale.getDefault(), os); String templateName = props.getProperty(
			 * "wt.content.downloadFailureTemplate", "content/DownloadFailure" ); logger.debug( "DEBUG: generateDownloadHtml 2 - " + templateName );
			 * 
			 * HTMLTemplate template = new HTMLTemplate( TemplateName.getWindchill( templateName ) ); template.init( preferences ); template.process( os, generator );
			 * 
			 * os.flush( ); }catch (IOException e) { Kogger.error(StandardMasterService.class, e); } finally{ SessionServerHelper.manager.setAccessEnforced( current ); } } else{
			 * if(ex instanceof WTException) throw (WTException)ex; else throw new WTException(ex); }
			 */
		} finally {
			// Make sure the FileInputstream is closed.
			if (br != null) {
				try {
					br.close();
				} catch (IOException ignored) {
				}// well at least we tried to do the "right thing"
			}
		}
	}

	private static Streamed getStreamed(FileFolder f, String fileName) throws WTException {
		Streamed s = null;
		long unseqnum = Long.parseLong(fileName, 16);
		Class c = (f instanceof FvFolder) ? FvItem.class : MasteredOnReplicaItem.class;
		QuerySpec qs = new QuerySpec(c);
		qs.appendWhere(new SearchCondition(c, StoredItem.UNIQUE_SEQUENCE_NUMBER, SearchCondition.EQUAL, unseqnum), new int[] { 0, -1 });
		QueryResult qr = PersistenceServerHelper.manager.query(qs);
		if (qr.hasMoreElements()) {
			s = (Streamed) qr.nextElement();
		}
		return s;
	}

	/**
	 * This method returns the Rule based vault if replication rule is present. else it returns the default target vault for the site.
	 * 
	 * @param site
	 * @param holder
	 * @return vault
	 * @throws WTException
	 */
	public static ReplicaVault getVaultForCaching(Site site, ContentHolder holder) throws WTException {
		Vault vault = null;
		boolean curr = SessionServerHelper.manager.setAccessEnforced(false);
		try {
			Class rulesCacheMgrCls = Class.forName("wt.dataops.replication.RulesCacheManager");
			Method getInstMethod = rulesCacheMgrCls.getMethod("getInstance", new Class[] {});

			Object rulesCacheMgr = getInstMethod.invoke(null, new Object[] {});
			Method method = rulesCacheMgrCls.getMethod("getVaultForObject", new Class[] { Persistable.class, Site.class });
			ArrayList list = (ArrayList) method.invoke(rulesCacheMgr, new Object[] { holder, site });
			if (list != null && list.size() > 0) {
				return (ReplicaVault) list.get(0);
			}
		} catch (Exception e) {
			throw new WTException(e);
		} finally {
			SessionServerHelper.manager.setAccessEnforced(curr);
		}
		if (MasterProperties.ADHOC_CACHING_FLAG == 1)
			return null;
		boolean current = SessionServerHelper.manager.setAccessEnforced(false);
		try {
			vault = (ReplicaVault) FvHelper.service.getDefaultTargetForSite(site);
		} finally {
			SessionServerHelper.manager.setAccessEnforced(current);
		}
		if (vault == null)
			logger.debug("StandardMasterService: getCacheVault: Vault for Site [" + site.getName() + "] not found");
		return (ReplicaVault) vault;
	}

	private static boolean isItemReplicated(ReplicaVault vault, long contentID) throws WTException {
		QuerySpec qs = new QuerySpec();
		int idxRItem = qs.appendClassList(ReplicatedItem.class, true);
		int idxF = qs.appendClassList(ReplicaFolder.class, false);
		int idxV = qs.appendClassList(ReplicaVault.class, false);

		qs.appendWhere(new SearchCondition(ReplicatedItem.class, ReplicatedItem.STREAM_ID, SearchCondition.EQUAL, contentID), idxRItem, -1);

		qs.appendAnd();
		qs.appendWhere(new SearchCondition(ReplicatedItem.class, ReplicatedItem.FOLDER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, ReplicaFolder.class,
				WTAttributeNameIfc.ID_NAME), idxRItem, idxF);
		qs.appendAnd();

		qs.appendWhere(new SearchCondition(ReplicaFolder.class, ReplicaFolder.VAULT_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, PersistenceHelper
				.getObjectIdentifier(vault).getId()), idxF, -1);

		QueryResult qr = PersistenceHelper.manager.find(qs);
		if (qr.hasMoreElements()) {
			Object[] replicatedContentInfo = (Object[]) qr.nextElement();
			ReplicatedItem rItem = (ReplicatedItem) replicatedContentInfo[idxRItem];
			if (rItem != null) {
				if (rItem.getReplication() == ReplicationStatus.IN_TRANSIT || rItem.getReplication() == ReplicationStatus.REPLICATED) {
					return true;
				} else
					return false;
			} else
				return false;
		} else
			return false;
	}

	private static class AdhocCachingSession implements Runnable {
		private ReplicaVault vault = null;
		private ApplicationData appData = null;
		private Object currentUser = null;
		private Object sessionContext = null;

		public AdhocCachingSession(ReplicaVault rVault, ApplicationData appData, Object currentUser, Object sessionContext) {
			this.vault = rVault;
			this.appData = appData;
			this.currentUser = currentUser;
			this.sessionContext = sessionContext;
		}

		public void run() {
			logger.debug(" *** Start of AdhocCaching session for Streamed :" + appData.getStreamId());

			MethodContext cntx = null;
			boolean current = true;
			Transaction trx = null;
			try {
				cntx = new MethodContext("", null);
				cntx.setEffectivePrincipal(currentUser);
				cntx.setSessionContext(sessionContext);
				current = SessionServerHelper.manager.setAccessEnforced(false);
				appData = (ApplicationData) PersistenceHelper.manager.refresh(appData);
				Streamed streamed = (Streamed) appData.getStreamData().getObject();
				ObjectReference objRef = appData.getStreamData();
				ObjectIdentifier objId = (ObjectIdentifier) objRef.getKey();
				if (isItemReplicated(vault, streamed.getStreamId())) {
					logger.debug("AdhocCachingSession : Replication for streamed : " + streamed.getStreamId() + " already done");
					return;
				}
				trx = new Transaction();
				trx.start();
				streamed = (Streamed) PersistenceHelper.manager.lockAndRefresh(streamed);
				StandardMasterService service = (StandardMasterService) ManagerServiceFactory.getDefault().getManager(StandardMasterService.class);
				service.replicateStreamed(streamed, vault, null, null);
				trx.commit();
				trx = null;
			} catch (WTException wte) {
				logger.error("", wte);
			} finally {
				if (trx != null)
					trx.rollback();
				SessionServerHelper.manager.setAccessEnforced(current);
				cntx.unregister();
				logger.debug(" *** End of AdhocCaching for Streamed :" + appData.getStreamId());
			}
		}
	}

	public static void replicationComplete(HTTPRequest req, HTTPResponse resp) throws WTException {
		logger.debug("Standard Master Service : Recieved notification for replication completion");
		Properties reqProps = req.splitQueryString();
		String strFolderId = reqProps.getProperty("folderId");
		String strROfolderId = reqProps.getProperty("ROfolderId");
		String strUSN = reqProps.getProperty("fname");
		if (logger.isDebugEnabled()) {
			logger.debug("strFolderId = " + strFolderId);
			logger.debug("strROfolderId = " + strROfolderId);
			logger.debug("strUSN = " + strUSN);
		}
		Long usn = new Long(strUSN);
		Long folderId = new Long(strFolderId);
		Long roFolderID = new Long(strROfolderId);
		boolean access = SessionServerHelper.manager.setAccessEnforced(false);
		try {
			replicateStreamedComplete(usn, folderId, new Long(-1), roFolderID);
		} finally {
			SessionServerHelper.manager.setAccessEnforced(access);
		}
	}

	public static SiteChecksumLevel getMasterSiteChecksumLevel() throws WTException {
		return masterSiteChecksumLevel.populateSiteChecksumLevel(true);
	}

	public static void replicaAdHocPullComplete(HTTPRequest req, HTTPResponse resp) throws WTException {
		logger.debug("Standard Master Service : Recieved notification for replication completion");
		ReplicatedItem ri = null;
		Properties reqProps = req.splitQueryString();
		String strUSN = reqProps.getProperty("fname");
		if (strUSN == null) {
			return;
		}
		String strFolderId = reqProps.getProperty("folderId");
		String siteUrl = reqProps.getProperty("remotefsurl");
		String strRIID = reqProps.getProperty("riid");
		logger.debug("For replica Site [" + siteUrl + "]");
		if (logger.isDebugEnabled()) {
			logger.debug("strFolderId = " + strFolderId);
			logger.debug("strUSN = " + strUSN);
			logger.debug("strRIID = " + strRIID);
		}
		// request validation part
		Vector<Long> fileFolderIdVec = null;
		Vector<Long> rootFolderIdVec = null;
		try {
			String key = reqProps.getProperty("cachekey");
			Object[] retVals = null;
			if (strFolderId != null) {
				retVals = validateReplicaAdHocPullCompleteRequest(siteUrl, key, strUSN, strFolderId);
				logger.debug("Returned from validateReplicaAdHocPullCompleteRequest, retVals = " + retVals);
				strUSN = (String) retVals[2];
				strFolderId = (String) retVals[3];
			} else {
				logger.debug("no need to verify adhoc completion as folder id not available. Only check root folders readonly");
				if (key != null) {
					retVals = validateMarkFolderReadOnlyRequest(siteUrl, key);
				}
			}
			if (retVals != null) {
				fileFolderIdVec = (Vector<Long>) retVals[0];
				rootFolderIdVec = (Vector<Long>) retVals[1];
			}
		} catch (WTException wte) {
			logger.error("", wte);
			throw wte;
		}

		boolean access = SessionServerHelper.manager.isAccessEnforced();
		try {
			if (strRIID == null || Long.parseLong(strRIID) == -1) {
				return;
			}
			SessionServerHelper.manager.setAccessEnforced(false);
			QuerySpec qs = new QuerySpec(ReplicatedItem.class);
			qs.appendWhere(new SearchCondition(ReplicatedItem.class, WTAttributeNameIfc.ID_NAME, SearchCondition.EQUAL, Long.parseLong(strRIID)), new int[] {});
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(ReplicatedItem.class, ReplicatedItem.REPLICATION, SearchCondition.EQUAL, ReplicationStatus.IN_TRANSIT), new int[] {});
			QueryResult qr = PersistenceServerHelper.manager.query(qs);
			if (qr.hasMoreElements()) {
				ri = (ReplicatedItem) qr.nextElement();
				Transaction trx = null;
				try {
					trx = new Transaction();
					trx.start();
					ri = (ReplicatedItem) PersistenceHelper.manager.lockAndRefresh(ri);
					if (strFolderId != null) {
						ObjectIdentifier actualFolderOI = ObjectIdentifier.newObjectIdentifier(strFolderId);
						FileFolder actualFolder = (FileFolder) ObjectReference.newObjectReference(actualFolderOI).getObject();
						FileFolder origFolder = ri.getFolder();
						if (!actualFolder.equals(origFolder)) {
							logger.debug("actual folder " + actualFolder + " different from originally set folder " + origFolder);
							ri.setFolder(actualFolder);
						}
						ri.markMoved(true);
					} else {
						ri.markMoved(false);
					}
					PersistenceHelper.manager.save(ri);
					trx.commit();
					trx = null;
				} finally {
					if (trx != null) {
						trx.rollback();
					}
				}
			} else {
				logger.error("no item found for adhoc stream stored on replica - replica stream will remain unreferenced");
			}
		} catch (Exception e) {
			logger.error("meta data could not be created for adhoc stream stored on replica - replica stream will remain unreferenced", e);
			if (ri != null) {
				logger.debug("removing the placeholder item created for adhoc pull");
				PersistenceServerHelper.manager.remove(ri);
			}
			throw new WTException(e);
		} finally {
			SessionServerHelper.manager.setAccessEnforced(access);
			if (fileFolderIdVec != null || rootFolderIdVec != null) {
				markFolderReadOnlyInternal(fileFolderIdVec, rootFolderIdVec);
			}
		}
	}

	private static Object[] validateReplicaAdHocPullCompleteRequest(String siteUrl, String key, String strUSN, String strFolderId) throws WTException {
		Site replica_site = InterSvrComHelper.service.getSite(siteUrl);
		if (replica_site == null) {
			logger.warn(" No site is present corresponding to the siteUrl " + siteUrl);
			throw new WTException("No site found for [" + siteUrl + "]");
		}
		try {
			initInbox();
			MethodInvocationDesc methodDesc = MethodInvocationDesc.newMethodInvocationDesc();
			methodDesc.setTargetClass("wt.fv.replica.StandardReplicaService");
			methodDesc.setTargetMethod("verifyReplicaAdHocPullCompletion");
			String[] argTypes = new String[4];
			argTypes[0] = "java.lang.String";
			argTypes[1] = "java.lang.String";
			argTypes[2] = "java.lang.String";
			argTypes[3] = "java.lang.String";
			methodDesc.setArgTypes(argTypes);

			// sets proper method parameters
			String masterUrl = StandardInterSvrComService.MASTER_URL;
			Serializable[] theArgs = { key, masterUrl, strUSN, strFolderId };
			methodDesc.setArgs(theArgs);

			ActionShippingItem theItem = ShippingHelper.service.createActionShippingItem();
			theItem.setDesc(methodDesc);
			TransportType transType = TransportType.newTransportType();
			transType.setTransportType(null);
			SiteAddress to = SiteAddress.newSiteAddress();
			to.setAddress(replica_site.getUrl());
			if (logger.isDebugEnabled()) {
				logger.debug("Sending config info to replica site [" + replica_site + "]");
				logger.debug("Located at: " + siteUrl);
			}
			InputStream retIPStream = ShippingHelper.service.sendImmediateItem((ShippingItem) theItem, to, transType, ReturnPreference.DIRECT);
			// the 1st object will always be the PayloadShippingItem. we read
			// and
			// ignore its value
			ObjectInputStream ois = new ObjectInputStream(retIPStream);
			ois.readObject();
			ObjectInputStream ois1 = new ObjectInputStream(retIPStream);
			return (Object[]) ois1.readObject();
		} catch (ClassNotFoundException cnfe) {
			throw new WTException(cnfe);
		} catch (IOException ioe) {
			throw new WTException(ioe);
		} catch (WTPropertyVetoException wtpe) {
			throw new WTException(wtpe);
		}
	}

	private static Object[] validateMarkFolderReadOnlyRequest(String siteUrl, String key) throws WTException {
		Site replica_site = InterSvrComHelper.service.getSite(siteUrl);
		if (replica_site == null) {
			logger.warn(" No site is present corresponding to the siteUrl " + siteUrl);
			throw new WTException("No site found for [" + siteUrl + "]");
		}
		try {
			initInbox();
			MethodInvocationDesc methodDesc = MethodInvocationDesc.newMethodInvocationDesc();
			methodDesc.setTargetClass("wt.fv.replica.StandardReplicaService");
			methodDesc.setTargetMethod("verifyReadOnlyFolders");
			String[] argTypes = new String[2];
			argTypes[0] = "java.lang.String";
			argTypes[1] = "java.lang.String";
			methodDesc.setArgTypes(argTypes);

			// sets proper method parameters
			String masterUrl = StandardInterSvrComService.MASTER_URL;
			Serializable[] theArgs = { key, masterUrl };
			methodDesc.setArgs(theArgs);

			ActionShippingItem theItem = ShippingHelper.service.createActionShippingItem();
			theItem.setDesc(methodDesc);
			TransportType transType = TransportType.newTransportType();
			transType.setTransportType(null);
			SiteAddress to = SiteAddress.newSiteAddress();
			to.setAddress(replica_site.getUrl());
			if (logger.isDebugEnabled()) {
				logger.debug("Sending config info to replica site [" + replica_site + "]");
				logger.debug("Located at: " + siteUrl);
			}
			InputStream retIPStream = ShippingHelper.service.sendImmediateItem((ShippingItem) theItem, to, transType, ReturnPreference.DIRECT);
			// the 1st object will always be the PayloadShippingItem. we read
			// and
			// ignore its value
			ObjectInputStream ois = new ObjectInputStream(retIPStream);
			ois.readObject();
			ObjectInputStream ois1 = new ObjectInputStream(retIPStream);
			return (Object[]) ois1.readObject();
		} catch (ClassNotFoundException cnfe) {
			throw new WTException(cnfe);
		} catch (IOException ioe) {
			throw new WTException(ioe);
		} catch (WTPropertyVetoException wtpe) {
			throw new WTException(wtpe);
		}
	}

	public static void markFolderReadOnly(HTTPRequest req, HTTPResponse resp) throws WTException {
		Properties reqProps = req.splitQueryString();
		String siteUrl = reqProps.getProperty("remotefsurl");
		logger.debug("For replica Site [" + siteUrl + "]");
		try {
			String key = reqProps.getProperty("cachekey");
			Vector<Long> fileFolderIdVec = new Vector<Long>();
			Vector<Long> rootFolderIdVec = new Vector<Long>();
			// request validation part
			Object[] retVals = validateMarkFolderReadOnlyRequest(siteUrl, key);
			logger.debug("Returned from validateMarkFolderReadOnlyRequest, retVals = " + retVals);
			fileFolderIdVec = (Vector<Long>) retVals[0];
			rootFolderIdVec = (Vector<Long>) retVals[1];

			if (fileFolderIdVec.size() > 0 || rootFolderIdVec.size() > 0) {
				markFolderReadOnlyInternal(fileFolderIdVec, rootFolderIdVec);
			}
		} catch (Exception e) {
			logger.error("", e);
			throw new WTException(e);
		} catch (Error error) {
			logger.error("", error);
			throw error;
		}
	}

	public static void markFolderReadOnly(Vector<Long> fileFolderIdVec, Vector<Long> rootFolderIdVec) {
		final class CallerForFolderMarker implements Runnable {
			private Vector<Long> fileFolderIdVec = null;
			private Vector<Long> rootFolderIdVec = null;
			private Object currentUser = null;
			private Object sessionContext = null;

			public CallerForFolderMarker(Vector<Long> fileFolderIdVec, Vector<Long> rootFolderIdVec, Object cu, Object sc) {
				super();
				this.fileFolderIdVec = fileFolderIdVec;
				this.rootFolderIdVec = rootFolderIdVec;
				currentUser = cu;
				sessionContext = sc;
			}

			public void run() {
				MethodContext cntx = null;
				try {
					cntx = new MethodContext("", null);
					cntx.setEffectivePrincipal(currentUser);
					cntx.setSessionContext(sessionContext);
					markFolderReadOnlyInternal(fileFolderIdVec, rootFolderIdVec);
				} catch (WTException wte) {
					logger.error(wte);
				} finally {
					if (cntx != null)
						cntx.unregister();
				}
			}
		}
		MethodContext mc = MethodContext.getContext();
		CallerForFolderMarker caller = new CallerForFolderMarker(fileFolderIdVec, rootFolderIdVec, mc.getEffectivePrincipal(), mc.getSessionContext());
		WTThread t = new WTThread(caller);
		t.start();
	}

	synchronized private static void markFolderReadOnlyInternal(Vector<Long> fileFolderIdVec, Vector<Long> rootFolderIdVec) throws WTException {
		SessionContext previous_session = SessionContext.newContext();
		boolean current = SessionServerHelper.manager.setAccessEnforced(false);
		WTCollection wtColl = new wt.fc.collections.WTArrayList();
		ArrayList objectsForOverflowEvent = new ArrayList();
		try {
			SessionHelper.manager.setAdministrator();
			Transaction trx = new Transaction();
			try {
				trx.start();
				if (fileFolderIdVec != null && fileFolderIdVec.size() > 0) {
					if (logger.isDebugEnabled()) {
						logger.debug("Start to mark file folders readOnly with folderId " + fileFolderIdVec.toString());
					}
					QuerySpec sqeQS = new QuerySpec(FileFolder.class);
					int idxSQE = sqeQS.addClassList(FileFolder.class, true);
					sqeQS.appendWhere(
							new SearchCondition(new ClassAttribute(FileFolder.class, WTAttributeNameIfc.ID_NAME), SearchCondition.IN,
									new ArrayExpression(fileFolderIdVec.toArray())), new int[] { idxSQE, -1 });
					sqeQS.setLock(true);
					QueryResult folders = PersistenceServerHelper.manager.query(sqeQS);

					while (folders.hasMoreElements()) {
						FileFolder folder = (FileFolder) folders.nextElement();
						folder.setReadOnly(true);
						wtColl.add(folder);
						objectsForOverflowEvent.add(folder);
					}
				}

				if (rootFolderIdVec != null && rootFolderIdVec.size() > 0) {
					if (logger.isDebugEnabled()) {
						logger.debug("Start to mark root folders readOnly with folderId " + rootFolderIdVec.toString());
					}
					QuerySpec sqeQSForRF = new QuerySpec(RootFolder.class);
					int idxSQEForRF = sqeQSForRF.addClassList(RootFolder.class, true);
					sqeQSForRF.appendWhere(new SearchCondition(new ClassAttribute(RootFolder.class, WTAttributeNameIfc.ID_NAME), SearchCondition.IN, new ArrayExpression(
							rootFolderIdVec.toArray())), new int[] { idxSQEForRF, -1 });
					sqeQSForRF.setLock(true);
					QueryResult rootFolders = PersistenceServerHelper.manager.query(sqeQSForRF);
					while (rootFolders.hasMoreElements()) {
						RootFolder rootFolder = (RootFolder) rootFolders.nextElement();
						rootFolder.setReadOnly(true);
						wtColl.add(rootFolder);
						objectsForOverflowEvent.add(rootFolder);
					}
				}

				PersistenceHelper.manager.modify(wtColl);

				WTCollection vaultsToMarkRO = new wt.fc.collections.WTArrayList();
				Iterator<Persistable> pIter = wtColl.persistableIterator();
				while (pIter.hasNext()) {
					Persistable p = pIter.next();
					Vault v = null;
					if (p instanceof FileFolder) {
						v = ((FileFolder) p).getVault();
					} else {
						v = ((RootFolder) p).getVault();
					}

					if (!vaultsToMarkRO.contains(v)) {
						boolean markVaultReadOnly = StandardFvService.shouldMarkVaultReadOnly(v);
						if (markVaultReadOnly) {
							v.setReadOnly(true);
							vaultsToMarkRO.add(v);
							objectsForOverflowEvent.add(v);
						}
					}
					PersistenceHelper.manager.modify(vaultsToMarkRO);
				}
				trx.commit();
				trx = null;
			} finally {
				if (trx != null) {
					trx.rollback();
				}
				// the call below sends notifications
				onReplicaSaveFailure(objectsForOverflowEvent.toArray());
			}
		} catch (Exception e) {
			logger.error("", e);
			throw new WTException(e);
		} catch (Error error) {
			logger.error("", error);
			throw error;
		} finally {
			try {
				SessionServerHelper.manager.setAccessEnforced(current);
				SessionContext.setContext(previous_session);
			} catch (Throwable th) {
				logger.warn("", th);
			}
		}
	}

	private Hashtable fetchDBSize(Collection itemsFromMasterToReplicate) throws WTException {
		// TODO Auto-generated method stub
		logger.trace("StandardMasterService.fetchDBSize");
		boolean foundFvItem = false;
		long[] streamedIds = new long[itemsFromMasterToReplicate.size()];
		int i = 0;
		for (Iterator iter = itemsFromMasterToReplicate.iterator(); iter.hasNext();) {
			Object obj = iter.next();
			if (obj instanceof ObjectReference) {
				ObjectReference objRef = (ObjectReference) obj;
				String className = objRef.getKey().getClassname();
				// logger.debug("className="+className);
				if (FvItem.class.getName().equals(className)) {
					foundFvItem = true;
					long fvItemId = ((ObjectIdentifier) objRef.getKey()).getId();
					streamedIds[i++] = fvItemId;
				}
			} else if (obj instanceof FvItem) {
				foundFvItem = true;
				long fvItemId = ((FvItem) obj).getPersistInfo().getObjectIdentifier().getId();
				streamedIds[i++] = fvItemId;
			}
		}
		if (!foundFvItem) {
			logger.debug("No FvItem found.");
			return null;
		}

		QuerySpec qs = new QuerySpec();

		int idxFvItem = qs.appendClassList(FvItem.class, false);
		int idxAppData = qs.appendClassList(ApplicationData.class, false);
		qs.appendSelectAttribute(FvItem.STREAM_ID, idxFvItem, false);
		qs.appendSelectAttribute(ApplicationData.FILE_SIZE, idxAppData, false);

		int[] idxJoinAD_FVI = { idxAppData, idxFvItem };
		qs.appendWhere(new SearchCondition(ApplicationData.class, ApplicationData.STREAM_DATA + "." + WTAttributeNameIfc.REF_OBJECT_ID, FvItem.class, WTAttributeNameIfc.ID_NAME),
				idxJoinAD_FVI);
		qs.appendAnd();

		qs.appendWhere(new SearchCondition(new ClassAttribute(FvItem.class, WTAttributeNameIfc.ID_NAME), SearchCondition.IN, new ArrayExpression(streamedIds)),
				new int[] { idxFvItem });
		// if(logger.isDebugEnabled())
		// logger.debug("Query------->"+qs.toString());

		QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);
		Hashtable streamIdDbSizeHash = null;
		while (qr.hasMoreElements()) {
			if (streamIdDbSizeHash == null)
				streamIdDbSizeHash = new Hashtable();
			Object[] result = (Object[]) qr.nextElement();
			BigDecimal fvItemID = (BigDecimal) result[0];
			long fvItemIDValue = fvItemID.longValue();
			BigDecimal fileSize = (BigDecimal) result[1];
			long fileSizeValue = fileSize.longValue();
			// if(logger.isDebugEnabled())
			// logger.debug("fvItemIDValue="+fvItemIDValue+" ,fileSizeValue="+fileSizeValue);
			streamIdDbSizeHash.put(new Long(fvItemIDValue), new Long(fileSizeValue));
		}
		return streamIdDbSizeHash;
	}

	public static boolean isReplicationManager(WTPrincipal arg0) throws WTException {
		if (logger.isDebugEnabled()) {
			logger.debug("In StandardMasterService.isReplicationManager()");
			logger.debug("Checking 'Replication Action Access' for user " + arg0.getName() + "...");
		}
		boolean isMember = false;
		WTPrincipalReference replicationManagersGroupRef = null;
		if (logger.isDebugEnabled()) {
			logger.debug("Looking in Default Replication Managers Group.");
		}
		replicationManagersGroupRef = getDefaultReplicationManagersGroupRef();
		if (replicationManagersGroupRef != null)
			isMember = OrganizationServicesHelper.manager.isMember(replicationManagersGroupRef, arg0);
		if (logger.isDebugEnabled()) {
			logger.debug("Is Member : " + isMember);
		}
		if (isMember == false) {
			logger.debug("Looking in User Organization's Replication Managers Group.");
			replicationManagersGroupRef = getUserOrganizationReplicationManagersGroupRef(arg0);
			if (replicationManagersGroupRef != null)
				isMember = OrganizationServicesHelper.manager.isMember(replicationManagersGroupRef, arg0);
			if (logger.isDebugEnabled()) {
				logger.debug("Is Member : " + isMember);
			}
		}
		return isMember;
	}

	private static WTPrincipalReference getDefaultReplicationManagersGroupRef() throws WTException {
		WTContainerRef exchangeContainerRef = WTContainerHelper.service.getExchangeRef();
		if (logger.isDebugEnabled()) {
			logger.debug("exchangeContainerRef = " + exchangeContainerRef);
		}
		if (exchangeContainerRef == null)
			return null;
		return getReplicationManagersGroupRef(exchangeContainerRef);
	}

	private static WTPrincipalReference getUserOrganizationReplicationManagersGroupRef(WTPrincipal arg0) throws WTException {
		WTOrganization wtOrg = OrganizationServicesHelper.manager.getOrganization(arg0);
		WTContainerRef organizationContainerRef = null;
		if (wtOrg != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Found " + arg0.getName() + "'s organization - " + wtOrg.getName() + ".");
			}
			organizationContainerRef = WTContainerHelper.service.getOrgContainerRef(wtOrg);
			if (logger.isDebugEnabled()) {
				logger.debug("organizationContainerRef = " + organizationContainerRef);
			}
			if (organizationContainerRef == null)
				return null;
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("Found " + arg0.getName() + "'s organization - null.");
			}
			return null;
		}
		return getReplicationManagersGroupRef(organizationContainerRef);
	}

	private static WTPrincipalReference getReplicationManagersGroupRef(WTContainerRef containerRef) throws WTException {
		ObjectReference replicationManagersGroupRef = null;
		// Get from Cache
		if (_replicationManagerGroupCache != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Retrieving Replication Managers Group from Cache.");
			}
			replicationManagersGroupRef = (ObjectReference) _replicationManagerGroupCache.get(containerRef);
			if (logger.isDebugEnabled()) {
				logger.debug("replicationManagersGroupRef (From Cache) = " + replicationManagersGroupRef);
			}
		} else {
			try {
				if (logger.isDebugEnabled()) {
					logger.debug("Building ReplicationManagerGroupCache");
				}
				_replicationManagerGroupCache = new ReplicationManagerGroupCache();
			} catch (RemoteException re) {
				if (logger.isDebugEnabled()) {
					logger.debug("Warning: can not create new cache");
				}
				_replicationManagerGroupCache = null;
			}
		}
		// Get from database and LDAP
		if (replicationManagersGroupRef == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Retrieving Replication Managers Group from Database.");
			}

			DirectoryContextProvider directoryContextProvider = WTContainerHelper.service.getContextProvider(containerRef);
			WTGroup replicationManagersGroup = OrganizationServicesHelper.manager.getGroup(REPLICATION_MANAGERS_GROUP, directoryContextProvider);
			if (replicationManagersGroup != null)
				replicationManagersGroupRef = WTPrincipalReference.newWTPrincipalReference(replicationManagersGroup);
			if (logger.isDebugEnabled()) {
				logger.debug("replicationManagersGroupRef (From Database) = " + replicationManagersGroupRef);
			}
			if (_replicationManagerGroupCache != null) {
				if (replicationManagersGroupRef == null) {
					if (logger.isDebugEnabled()) {
						logger.debug("No Replication Managers Group found.");
					}
					// put empty object to avoid database and LDAP queries again
					replicationManagersGroupRef = ObjectReference.newObjectReference();
				}
				_replicationManagerGroupCache.put(containerRef, replicationManagersGroupRef);
			}
		}

		if (replicationManagersGroupRef instanceof WTPrincipalReference)
			return (WTPrincipalReference) replicationManagersGroupRef;
		else
			return null;
	}

	private static void updateReplicationManagerGroupCache(WTGroup arg0) {
		if (_replicationManagerGroupCache != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Updating ReplicationManagerGroupCache");
			}
			WTContainerRef containerRef = arg0.getContainerReference();
			// clean up the cache
			if (containerRef != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("Removing entry from Cache for key, containerRef = " + containerRef);
				}
				_replicationManagerGroupCache.remove(containerRef);
			}
		}
	}

	public static QueryResult getItemAtPreferredSite(Site site, long streamID) throws WTException, WTPropertyVetoException {
		QuerySpec qsM = new QuerySpec();
		int idxMItem = qsM.appendClassList(MasteredOnReplicaItem.class, false);
		int idxFM = qsM.appendClassList(ReplicaFolder.class, false);
		int idxVM = qsM.appendClassList(ReplicaVault.class, false);
		qsM.appendSelectAttribute(WTAttributeNameIfc.OID_CLASSNAME, idxMItem, false);
		qsM.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, idxMItem, false);
		qsM.appendSelectAttribute(MasteredOnReplicaItem.UNIQUE_SEQUENCE_NUMBER, idxMItem, false);
		qsM.appendSelectAttribute(WTAttributeNameIfc.OID_CLASSNAME, idxFM, false);
		qsM.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, idxFM, false);
		qsM.appendSelectAttribute(WTAttributeNameIfc.OID_CLASSNAME, idxVM, false);
		qsM.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, idxVM, false);

		qsM.appendWhere(new SearchCondition(MasteredOnReplicaItem.class, MasteredOnReplicaItem.STREAM_ID, SearchCondition.EQUAL, streamID), new int[] { idxMItem, -1 });
		qsM.appendAnd();
		qsM.appendWhere(new SearchCondition(MasteredOnReplicaItem.class, MasteredOnReplicaItem.FOLDER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, ReplicaFolder.class,
				WTAttributeNameIfc.ID_NAME), new int[] { idxMItem, idxFM });
		qsM.appendAnd();
		qsM.appendWhere(new SearchCondition(ReplicaFolder.class, ReplicaFolder.VAULT_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, ReplicaVault.class,
				WTAttributeNameIfc.ID_NAME), new int[] { idxFM, idxVM });
		qsM.appendAnd();
		qsM.appendWhere(new SearchCondition(ReplicaVault.class, ReplicaVault.SITE_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, PersistenceHelper
				.getObjectIdentifier(site).getId()), new int[] { idxVM, -1 });
		qsM.appendAnd();
		qsM.appendWhere(new SearchCondition(ReplicaVault.class, ReplicaVault.FOR_MASTERED_ITEMS, SearchCondition.IS_TRUE), new int[] { idxVM, -1 });

		QuerySpec qsR = new QuerySpec();
		int idxRItem = qsR.appendClassList(ReplicatedItem.class, false);
		int idxFR = qsR.appendClassList(ReplicaFolder.class, false);
		int idxVR = qsR.appendClassList(ReplicaVault.class, false);
		qsR.appendSelectAttribute(WTAttributeNameIfc.OID_CLASSNAME, idxRItem, false);
		qsR.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, idxRItem, false);
		qsR.appendSelectAttribute(ReplicatedItem.UNIQUE_SEQUENCE_NUMBER, idxRItem, false);
		qsR.appendSelectAttribute(WTAttributeNameIfc.OID_CLASSNAME, idxFR, false);
		qsR.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, idxFR, false);
		qsR.appendSelectAttribute(WTAttributeNameIfc.OID_CLASSNAME, idxVR, false);
		qsR.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, idxVR, false);

		qsR.appendWhere(new SearchCondition(ReplicatedItem.class, ReplicatedItem.STREAM_ID, SearchCondition.EQUAL, streamID), new int[] { idxRItem, -1 });
		qsR.appendAnd();
		qsR.appendWhere(new SearchCondition(ReplicatedItem.class, ReplicatedItem.REPLICATION, SearchCondition.EQUAL, ReplicationStatus.REPLICATED), new int[] { idxRItem, -1 });
		qsR.appendAnd();
		qsR.appendWhere(new SearchCondition(ReplicatedItem.class, ReplicatedItem.FOLDER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, ReplicaFolder.class,
				WTAttributeNameIfc.ID_NAME), new int[] { idxRItem, idxFR });
		qsR.appendAnd();
		qsR.appendWhere(new SearchCondition(ReplicaFolder.class, ReplicaFolder.VAULT_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, ReplicaVault.class,
				WTAttributeNameIfc.ID_NAME), new int[] { idxFR, idxVR });
		qsR.appendAnd();
		qsR.appendWhere(new SearchCondition(ReplicaVault.class, ReplicaVault.SITE_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, PersistenceHelper
				.getObjectIdentifier(site).getId()), new int[] { idxVR, -1 });

		QuerySpec qsF = new QuerySpec();
		int idxFItem = qsF.appendClassList(FvItem.class, false);
		int idxFF = qsF.appendClassList(FvFolder.class, false);
		int idxVF = qsF.appendClassList(FvVault.class, false);
		qsF.appendSelectAttribute(WTAttributeNameIfc.OID_CLASSNAME, idxFItem, false);
		qsF.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, idxFItem, false);
		qsF.appendSelectAttribute(FvItem.UNIQUE_SEQUENCE_NUMBER, idxFItem, false);
		qsF.appendSelectAttribute(WTAttributeNameIfc.OID_CLASSNAME, idxFF, false);
		qsF.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, idxFF, false);
		qsF.appendSelectAttribute(WTAttributeNameIfc.OID_CLASSNAME, idxVF, false);
		qsF.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, idxVF, false);

		qsF.appendWhere(new SearchCondition(FvItem.class, FvItem.STREAM_ID, SearchCondition.EQUAL, streamID), new int[] { idxFItem, -1 });
		qsF.appendAnd();
		qsF.appendWhere(new SearchCondition(FvItem.class, FvItem.FOLDER_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, FvFolder.class, WTAttributeNameIfc.ID_NAME), new int[] {
				idxFItem, idxFF });
		qsF.appendAnd();
		qsF.appendWhere(new SearchCondition(FvFolder.class, FvFolder.VAULT_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, FvVault.class, WTAttributeNameIfc.ID_NAME),
				new int[] { idxFF, idxVF });
		qsF.appendAnd();
		qsF.appendWhere(new SearchCondition(FvVault.class, FvVault.SITE_REFERENCE + "." + WTAttributeNameIfc.REF_OBJECT_ID, SearchCondition.EQUAL, PersistenceHelper
				.getObjectIdentifier(site).getId()), new int[] { idxVF, -1 });

		CompoundQuerySpec siteData = new CompoundQuerySpec();
		siteData.setSetOperator(SetOperator.UNION_ALL);
		siteData.setAdvancedQueryEnabled(true);
		siteData.addComponent(qsM);
		siteData.addComponent(qsR);
		siteData.addComponent(qsF);

		if (site.isLocalMaster()) {
			QuerySpec qsSD = new QuerySpec();
			int idxSD = qsSD.addClassList(StreamData.class, false);
			qsSD.appendSelectAttribute(WTAttributeNameIfc.OID_CLASSNAME, idxSD, false);
			qsSD.appendSelectAttribute(WTAttributeNameIfc.ID_NAME, idxSD, false);
			qsSD.appendSelect(new ConstantExpression(new Long(-1)), false); // no
			// unique
			// sequence
			// number
			// for
			// stream
			// data
			Object fcname = new String("");
			qsSD.appendSelect(new ConstantExpression(fcname), false); // no
			// folder
			// class
			// name
			// for
			// stream
			// data
			qsSD.appendSelect(new ConstantExpression(new Long(-1)), false); // no
			// folder
			// ida2a2
			// for
			// stream
			// data

			qsSD.appendWhere(new SearchCondition(StreamData.class, StreamData.STREAM_ID, SearchCondition.EQUAL, streamID), new int[] { idxSD, -1 });
		}

		QueryResult qr = PersistenceServerHelper.manager.query(siteData);

		return qr;
	}

	/**
	 * Find the replicateditem based on USN and folder . IF folderId is -1 search will be based on usn only When called from doDirectDownload folder id is passed when called from
	 * doDownload folder id is passed when called from doindirectdownload folder id is - 1 in either case if duplicates are returned the first item returned by db is returned
	 * 
	 * @param folderId
	 * @param filenameUSN
	 * @return
	 * @throws WTException
	 */
	private static ReplicatedItem getReplicatedItemFromFolderId(long folderId, long filenameUSN) throws WTException {
		QuerySpec qs = new QuerySpec(ReplicatedItem.class);
		WhereExpression we = null;
		if (folderId != -1) {
			logger.debug("Querying for RI with USN " + filenameUSN + " in folder " + folderId);
			we = new SearchCondition(ReplicatedItem.class, "folderReference.key.id", SearchCondition.EQUAL, folderId);
			qs.appendWhere(we);
			qs.appendAnd();
		} else {
			logger.debug("Querying for RI with USN" + filenameUSN);
		}
		we = new SearchCondition(ReplicatedItem.class, "uniqueSequenceNumber", SearchCondition.EQUAL, filenameUSN);
		qs.appendWhere(we);
		QueryResult qr = PersistenceHelper.manager.find((StatementSpec) qs);
		if (qr != null && qr.size() > 0) {
			ReplicatedItem ri = (ReplicatedItem) qr.nextElement();
			return ri;
		}
		return null;
	}

	public static void _updateLastAccessedTimeInRI(HTTPRequest req, HTTPResponse resp) throws WTException {
		try {
			wt.session.SessionServerHelper.manager.setAccessEnforced(false);
			long riid = -1;
			Map<String, String[]> requestParamMap = req.getParameterMap();
			String[] reqParam = new String[1];
			reqParam = requestParamMap.get("riid");
			riid = Long.parseLong(reqParam[0]);
			_updateLastAccessedTimeInRI(riid);
		} catch (Exception ex) {
			logger.error("Failed to update LastAccessedTime", ex);
			throw new WTException(ex);
		} finally {
			wt.session.SessionServerHelper.manager.setAccessEnforced(true);
		}
	}

	public static void _updateLastAccessedTimeInRI(long riid) throws WTException {
		Transaction trx = null;
		try {
			if (riid == -1) {
				return;
			}
			SessionServerHelper.manager.setAccessEnforced(false);
			trx = new Transaction();
			trx.start();
			ObjectIdentifier oid = ObjectIdentifier.newObjectIdentifier(ReplicatedItem.class, riid);
			ReplicatedItem ri = (ReplicatedItem) PersistenceHelper.manager.refresh(oid);
			if (ri != null) {
				ri = (ReplicatedItem) PersistenceHelper.manager.lockAndRefresh(ri);
				ri.setLastAccessedTimeInMilli(System.currentTimeMillis());
				PersistenceHelper.manager.save(ri);
			}
			trx.commit();
			trx = null;
		} catch (Exception ex) {
			logger.error("Failed to update LastAccessedTime", ex);
			throw new WTException(ex);
		} finally {
			if (trx != null) {
				trx.rollback();
			}
			wt.session.SessionServerHelper.manager.setAccessEnforced(true);
		}
	}

	public static void doAutoCleanupOfVaultsPerSite(Site theSite, Boolean isImmediate) throws WTException {
		try {
			AutoVaultCleanupCriteria criteria = null;
			criteria = StandardFvService.getAutoCleanupCriteriaFromSiteName(theSite.getName(), isImmediate);
			if (criteria != null) {
				AutoVaultCleanupUtility cleanupDoer = new AutoVaultCleanupUtility(theSite, criteria, criteria.isImmediate());
				cleanupDoer.doVaultCleanup();
			}
		} catch (Exception ex) {
			Kogger.debug(StandardMasterService.class, "caught exception in doAutoCleanupOfVaultsPerSite");
			throw new WTException(ex);
		}
	}

	class MasterServiceEventListener extends ServiceEventListenerAdapter {
		public MasterServiceEventListener(String manager_name) {
			super(manager_name);
		}

		public void notifyVetoableEvent(Object event) throws WTException {
			logger.trace(" master event listener ");
			if (!(event instanceof KeyedEvent)) {
				return;
			}
			KeyedEvent keyedEvent = (KeyedEvent) event;

			String eventKey = keyedEvent.getEventKey();
			Object targetObject = keyedEvent.getEventTarget();
			if (targetObject instanceof Long) {
				if (keyedEvent.getEventType().equals(MasterServiceEvent.PRE_DOWNLOAD)) {
					MasterServiceEvent msEvent = (MasterServiceEvent) event;
					logger.debug("Master - Pre-Download Listener: eventType = " + eventKey + " targetObject(AppDataId) = " + targetObject + " principal id = "
							+ msEvent.getUserId());
				}
				if (keyedEvent.getEventType().equals(MasterServiceEvent.POST_DOWNLOAD)) {
					MasterServiceEvent msEvent = (MasterServiceEvent) event;
					logger.debug("Master - Post-Download Listener: eventType = " + eventKey + " targetObject(AppDataId) = " + targetObject + " principal id = "
							+ msEvent.getUserId());
					Long folderId = msEvent.getFolderID();
					Long filnameUSN = msEvent.getFilenameUSN();
					long riid = msEvent.getReplicatedItemId();
					logger.debug("riid = " + riid);
					_updateLastAccessedTimeInRI(riid);
				}
			}
		}
	}

	/**
	 * Checks if Site principal has access to the content holder. If the throwException paramter is true there is no need to check the return value .
	 * 
	 * @param prefSite
	 *            Preferred site
	 * 
	 * @param holder
	 *            Content holder
	 * @param throwException
	 *            , will throw exception if true , else will return a boolean value .
	 * @return true if Site principal has access to the content holder
	 * @throws WTException
	 */
	public static boolean checkSitePrincipalAccess(Site prefSite, ContentHolder holder, boolean throwException) throws WTException {
		WTArrayList al = new WTArrayList(1);
		al.add(holder);
		WTKeyedMap holderToAccessFlagMap = checkSitePrincipalAccess(prefSite, al, throwException);
		Boolean hasAccess = (Boolean) holderToAccessFlagMap.get(holder);
		if (hasAccess == null) {
			return false;
		}
		return hasAccess;
	}

	/**
	 * Multi -object version of {@link #checkSitePrincipalAccess(Site, ContentHolder, boolean)} If the throwException paramter is true there is no need to check the return value .
	 * 
	 * @param prefSite
	 * @param holders
	 * @param throwException
	 *            will throw exception if true , else will return a boolean value .
	 * @return WTKeyedMap of holder to hasAccess flag
	 * @throws WTException
	 */
	public static WTKeyedMap checkSitePrincipalAccess(Site prefSite, WTCollection holders, boolean throwException) throws WTException {
		WTKeyedMap holderToAccessFlagMap = new WTKeyedHashMap();
		WTPrincipal sitePrincipal = InterSvrComHelper.service.getSitePrincipal(prefSite);
		if (logger.isDebugEnabled()) {
			logger.debug("Site principal for site " + prefSite.getName() + " is " + ((sitePrincipal != null) ? sitePrincipal.getName() : "null"));
		}
		if (sitePrincipal == null) {
			throw new WTException("Site does not have principal,please contact your Administrator");
		}
		WTPrincipal previous = SessionContext.setEffectivePrincipal(sitePrincipal);
		boolean previousAccess = SessionServerHelper.manager.setAccessEnforced(true);
		logger.debug("Set accessEnforced to true. Previous value - " + previousAccess);
		try {
			if (throwException) {
				AccessControlHelper.manager.checkAccess(holders, AccessPermission.READ);
			} else {
				for (Object holderObj : holders) {
					boolean hasAccess = AccessControlHelper.manager.hasAccess(holderObj, AccessPermission.READ);
					holderToAccessFlagMap.put(holderObj, hasAccess);
				}
			}
		} catch (NotAuthorizedException ne) {
			// log the failure with useful information and rethrow
			logger.error("Site principal " + sitePrincipal.getName() + " for site " + prefSite.getName()
					+ " does not have read acces for objects.Please contact system administrator.");
			if (logger.isTraceEnabled()) {
				logger.trace(holders.toString());
			}
			throw ne;
		} finally {
			SessionServerHelper.manager.setAccessEnforced(previousAccess);
			logger.debug("Reset accessEnforced to value - " + previousAccess);
			SessionContext.setEffectivePrincipal(previous);
		}
		return holderToAccessFlagMap;
	}

	/**
	 * This method checks that the content can actually be stored in the specified vault. If the site prinicipal for the site corresponding to the vault has READ access to the
	 * passed in content holders the method succeeds , else throws an exception .
	 * 
	 * @param vaultId
	 * @param holders
	 * @throws WTException
	 */
	public static void checkAccessForUpload(long vaultId, WTCollection holders) throws WTException {
		// make sure that the siteprincipal has access to the objects .
		if (holders != null && holders.size() > 0) {
			// we will go through the vault to the site so that we don't have
			// any ambiguity about whether
			// the site and the vault match .
			Vault v = StandardFvService.getVaultbyId(vaultId);
			Site s = v.getSite();// will be hopefully inflated
			StandardMasterService.checkSitePrincipalAccess(s, holders, true);
		}
	}

}
