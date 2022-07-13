// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   OpenCommand.java

package e3ps.bom.framework.aif.commands.open;

import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.util.MessageBox;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class OpenCommand extends AbstractAIFCommand
{

    public OpenCommand(String saveFilePath, String fileName, String fileType)
    {
        m_fileSeparator = System.getProperty("file.separator");
        m_saveFilePath = saveFilePath;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    protected final void executeCommand()
        throws Exception
    {
        //bat 파일로 엑셀 파일 자동 오픈하는 부분....KET에서는 빼달라는 요청에 의해서 주석처리..저장되었다는 메세지만 띄움.
        MessageBox.post(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00309")/*저장되었습니다.*/, "save", MessageBox.WARNING);

       /* String batFile = "exe" + fileType + ".bat";
        StringBuffer buf = new StringBuffer();

        //shin...
        String driveSet = "c:";
        try{
        driveSet = m_saveFilePath.substring(0,2);
        }catch(Exception e){}

        buf.append(driveSet);
        buf.append("\n");
        buf.append("cd ");
        buf.append(m_saveFilePath);
        buf.append(m_fileSeparator);
        buf.append("\n");
        buf.append(fileName);
        buf.append("." + fileType);
        String aa = fileName + "." + fileType;
        File file = new File(m_saveFilePath, batFile);
        FileWriter fw = new FileWriter(file);
        fw.write(buf.toString());
        fw.flush();
        fw.close();
        Process p = null;
        InputStream is = null;
        OutputStream os = null;
        String args = m_saveFilePath + m_fileSeparator + batFile;
        try
        {
            Runtime rt = Runtime.getRuntime();
            p = rt.exec(args);
            p.waitFor();
        }
        catch(Exception exception)
        {
            Kogger.debug(getClass(), "Error File Read : " + exception.toString());
        }
        finally
        {
            if(p != null)
                try
                {
                    p.destroy();
                }
                catch(Exception exception2) { }
            if(is != null)
                try
                {
                    is.close();
                }
                catch(Exception exception3) { }
            if(os != null)
                try
                {
                    os.close();
                }
                catch(Exception exception4) { }
        }*/
        return;
    }

    private void runThreadDocOpenCheck(Process prc)
    {
        do
            try
            {
                Thread.sleep(1000L);
            }
            catch(InterruptedException interruptedexception)
            {
                Kogger.error(getClass(), interruptedexception);
            }
        while(true);
    }

    protected String m_saveFilePath;
    protected String fileName;
    protected String fileType;
    private String m_fileSeparator;
}
