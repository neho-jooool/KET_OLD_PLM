package ext.ket.edm.migration.stamping;

import java.io.IOException;

import ext.ket.shared.log.Kogger;

public class CommandExecutor {

    public static void executeTransCommand(String... cmd) {
	executeCommand(cmd);
    }

    public static void executeStampingCommnad(String... cmd) {
	executeCommand(cmd);
    }

    private static void executeCommand(String... cmd) {

	Kogger.debug(CommandExecutor.class, "#####################################");
	Kogger.debug(CommandExecutor.class, "#####################################");
	Kogger.debug(CommandExecutor.class, "cmd:" + cmd);
	Process p;
	
	try {

	    p = Runtime.getRuntime().exec(cmd);
	    p.waitFor();
	    Kogger.debug(CommandExecutor.class, "@@@@@@@@@@@@@@@@@@@@@@@@ End @@@@@@@@@@@");

	} catch (IOException e1) {
	    Kogger.error(CommandExecutor.class, e1);
	} catch (InterruptedException e) {
	    Kogger.error(CommandExecutor.class, e);
	}
	
	Kogger.debug(CommandExecutor.class, "#####################################");
	Kogger.debug(CommandExecutor.class, "#####################################");
    }

    public static void main(String[] args) throws IOException {
	String[] testArgs = new String[]{"E:\\stamping\\acadworker\\acadworker.exe", "-log=noshow", "-autocad=noshow", "-close_after=true"
		, "-file=\"E:\\stamping\\acadworker\\work\\real_sample.xml\"" };
	
	CommandExecutor.executeCommand( testArgs );
	
    }
}
