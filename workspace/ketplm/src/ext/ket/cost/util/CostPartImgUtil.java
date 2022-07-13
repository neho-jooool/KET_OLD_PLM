package ext.ket.cost.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.MethodContext;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTPrincipal;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.queue.QueuePseudoMethodArgs;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.session.SessionServerHelper;
import wt.util.WTException;

import com.google.common.io.Files;

import ext.ket.common.files.util.DownloadView;
import ext.ket.cost.entity.CostPart;

@Component
public class CostPartImgUtil implements RemoteAccess {

	private static final Logger LOGGER = Logger.getLogger(CostPartImgUtil.class);
	public final static String STOREPATH;
	private static final String UPLOADPATH = "V:\\";

	static {
		STOREPATH = DownloadView.CODEBASEPATH + File.separator + "COST" + File.separator + "COSTPARTIMG" + File.separator;
	}

	@SuppressWarnings("rawtypes")
	@Scheduled(cron = "*/20 * * * * *")
	public static void uploadSchedule() {

		if (!RemoteMethodServer.ServerFlag) {
			Class argTypes[] = new Class[] {};

			Object args[] = new Object[] {};

			try {
				RemoteMethodServer.getDefault().invoke("uploadSchedule", CostPartImgUtil.class.getName(), null, argTypes, args);
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			return;
		}

		boolean bool = false;
		WTPrincipal localWTPrincipal = null;
		MethodContext localMethodContext = null;
		SessionContext localSessionContext = null;

		try {

			WTPrincipal localObject = getAdministrator();

			localMethodContext = new MethodContext(new QueuePseudoMethodArgs(CostPartImgUtil.class.getName() + "-" + "PartImgUploadScheduler", "uploadSchedule"));
			localMethodContext.put("QUEUE_NAME", "PartImgUploadScheduler");

			bool = SessionServerHelper.manager.setAccessEnforced(false);

			localWTPrincipal = SessionContext.setEffectivePrincipal((WTPrincipal) localObject);

			localSessionContext = SessionContext.newContext();

			SessionHelper.manager.setPrincipal(SessionContext.getEffectivePrincipal().getName());

			_uploadSchedule();

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("#############ERROR SCHEDULE#########" + e.getLocalizedMessage());
		} finally {

			SessionServerHelper.manager.setAccessEnforced(bool);
			SessionContext.setEffectivePrincipal(localWTPrincipal);
			SessionContext.setContext(localSessionContext);
			localMethodContext.unregister();
			localMethodContext = null;
		}
	}

	public static WTPrincipal getAdministrator() {
		WTPrincipal localWTPrincipal = null;
		MethodContext localMethodContext = MethodContext.getContext(Thread.currentThread());
		int i = localMethodContext == null ? 1 : 0;

		try {

			if (i != 0) {
				localMethodContext = new MethodContext(new QueuePseudoMethodArgs(CostPartImgUtil.class.getName() + "-" + "PartImgUploadScheduler", "getAdministrator"));
			}

			localWTPrincipal = SessionHelper.manager.getAdministrator();

		} catch (Exception localException) {
			localException.printStackTrace();
		} finally {
			if (i != 0)
				localMethodContext.unregister();
		}
		return localWTPrincipal;
	}

	public static void _uploadSchedule() throws IOException {

		File imgDir = new File(UPLOADPATH);

		if (imgDir.isDirectory()) {

			File[] imgFiles = imgDir.listFiles();

			for (File imgFile : imgFiles) {
				if (imgFile.isFile()) {

					String fileName = imgFile.getName();

					String filePath = UPLOADPATH + fileName;
					Path source = Paths.get(filePath);
					String mimeType = java.nio.file.Files.probeContentType(source);

					if (mimeType != null && mimeType.indexOf("image") >= 0) {

						String partNo = fileName.substring(0, fileName.lastIndexOf("."));

						boolean isExist = findPartNoToCostPart(partNo);

						if (isExist) {
							File fileToMove = new File(STOREPATH + partNo + ".jpg");

							Files.move(imgFile, fileToMove);
							LOGGER.info("#############STORE COMPLETE PART##########" + partNo);

						} else {
							LOGGER.info("#############NOT FOUND COST PART##########" + partNo);
						}
					} else {
						LOGGER.info("#############NOT IMAGE FILE##########" + imgFile.getName());
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static boolean findPartNoToCostPart(String partNo) {

		try {

			QuerySpec qs = new QuerySpec();
			int idx = qs.appendClassList(CostPart.class, true);
			qs.appendWhere(new SearchCondition(CostPart.class, CostPart.PART_NO, SearchCondition.EQUAL, partNo), new int[] { idx });

			QueryResult qr = PersistenceHelper.manager.find(qs);

			return qr.hasMoreElements();

		} catch (QueryException e) {
			e.printStackTrace();
		} catch (WTException e) {
			e.printStackTrace();
		}

		return false;
	}

	// cron
	// Cron Expression을 이용하여 Task 실행 주기 정의.
	//
	// Cron Expression은 6개의 Field로 구성되며 각 Field는 순서대로 second, minute, hour, day,
	// month, weekday를 의미한다. 각 Field의 구분은 Space로 한다. 또한 month와 weekday는 영어로 된 단어의 처음
	// 3개의 문자로 정의할 수 있다.
	//
	// 0 0 * * * * : 매일 매시 시작 시점
	//
	// */10 * * * * * : 10초 간격
	//
	// 0 0 8-10 * * * : 매일 8,9,10시
	//
	// 0 0/30 8-10 * * * : 매일 8:00, 8:30, 9:00, 9:30, 10:00
	//
	// 0 0 9-17 * * MON-FRI : 주중 9시부터 17시까지
	//
	// 0 0 0 25 12 ? : 매년 크리스마스 자정
	//
	// org.springframework.scheduling.support.CronSequenceGenerator API 참조
	//
	// fixed-delay 이전에 실행된 Task의 종료 시간으로부터의 fixed-delay로 정의한 시간만큼 소비한 이후 Task 실행.
	// (Milliseconds 단위로 정의)
	// fixed-rate 이전에 실행된 Task의 시작 시간으로부터 fixed-rate로 정의한 시간만큼 소비한 이후 Task 실행.
	// (Milliseconds 단위로 정의)

	// @Scheduled(fixedDelay=5000)
	// public void printWithFixedDelay() {
	// System.out.println("execute printWithFixedDelay() of Annotated PrintTask at "
	// + new Date());
	// }
	//
	// @Scheduled(fixedRate=10000)
	// public void printWithFixedRate() {
	// System.out.println("execute printWithFixedRate() of Annotated PrintTask at "
	// + new Date());
	// }
	//
	// @Scheduled(cron="*/5 * * * * *")
	// public void printWithCron() {
	// System.out.println("execute printWithCron() of Annotated PrintTask at "
	// + new Date());
	// }
}
