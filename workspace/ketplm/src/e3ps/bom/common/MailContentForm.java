package e3ps.bom.common;

import e3ps.bom.common.clipboard.BOMBasicInfoPool;

public class MailContentForm
{
    public StringBuffer sb;
    public String st;

    public MailContentForm(String title, String content, String partNo, String modelNameStr, String oid)
    {
        sb = new StringBuffer();
        String setPartNo = partNo;
        String modelName = modelNameStr;
        String hrefModel = "";
        String serverUrlString = BOMBasicInfoPool.getServerCodebase();
        hrefModel = serverUrlString + "execute/bc-main-bomeditor-form?oid=" + oid + "&workType=COWORK";
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title></title>");
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        sb.append("<LINK REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"").append("http://xcpc.lge.com:8500/").append("extcore/lge/cpc/common/css/lgestyle.css\">");
        sb.append("<style>");
        sb.append("td           {color: #666666; font-family:Arial, Helvetica, sans-serif; font-size:11px; text-decoration: none;}");
        sb.append("a:link       {color: #0831E2; font-family:Arial, Helvetica, sans-serif; font-size:11px; text-decoration: underline;}");
        sb.append("a:visited    {color: #0831E2; font-family:Arial, Helvetica, sans-serif; font-size:11px; text-decoration: underline;}");
        sb.append("a:active     {color: #0831E2; font-family:Arial, Helvetica, sans-serif; font-size:11px; text-decoration: underline;}");
        sb.append("a:hover      {color: #BD9D64; font-family:Arial, Helvetica, sans-serif; font-size:11px; text-decoration: underline;}");
        sb.append("a.link_10px:link    {color: #446998; font-family:Arial, Helvetica, sans-serif; font-size:10px; text-decoration: none;}");
        sb.append("a.link_10px:visited {color: #446998; font-family:Arial, Helvetica, sans-serif; font-size:10px; text-decoration: none;}");
        sb.append("a.link_10px:active  {color: #446998; font-family:Arial, Helvetica, sans-serif; font-size:10px; text-decoration: none;}");
        sb.append("a.link_10px:hover   {color: #BD9D64; font-family:Arial, Helvetica, sans-serif; font-size:10px; text-decoration: underline;}");
        sb.append("a.footer:link       {color: #111111; font-family:Arial, Helvetica, sans-serif; font-size:11px; text-decoration: none;}");
        sb.append("a.footer:visited    {color: #111111; font-family:Arial, Helvetica, sans-serif; font-size:11px; text-decoration: none;}");
        sb.append("a.footer:active     {color: #111111; font-family:Arial, Helvetica, sans-serif; font-size:11px; text-decoration: none;}");
        sb.append("a.footer:hover      {color: #BD9D64; font-family:Arial, Helvetica, sans-serif; font-size:11px; text-decoration: none;}");
        sb.append("</style>");
        sb.append("</head>");
        sb.append("<body background=\"").append("http://xcpc.lge.com:8500/extcore/").append("common/images/img_ko/image/mail_bg.gif\" > ");
        sb.append("<table width=\"551\" border=\"0\" align=\"center\" cellpadding=\"1\" cellspacing=\"0\"> ");
        sb.append("  <tr>");
        sb.append("    <td bgcolor=\"#CCCCCC\"><table width=\"551\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> ");
        sb.append("        <tr> ");
        sb.append("          <td colspan=\"3\"><img src=\"").append("http://xcpc.lge.com:8500/extcore/").append("common/images/img_ko/image/mail_title.gif\" border=\"0\"><img ");
        sb.append(" src=\"").append("http://xcpc.lge.com:8500/extcore/").append("common/images/img_ko/image/mail_title2.gif\" border=\"0\"></td> ");
        sb.append("        </tr>");
        sb.append("        <tr> ");
        sb.append("          <td height=\"30\" colspan=\"3\" bgcolor=\"#FFFFFF\">&nbsp;</td>");
        sb.append("        </tr>");
        sb.append("        <tr> ");
        sb.append("          <td colspan=\"3\" align=\"center\" valign=\"top\" bgcolor=\"#FFFFFF\"><table width=\"460\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> ");
        sb.append("              <tr> ");
        sb.append("                <td valign=\"top\"><p><strong><font color=\"#FF6600\">").append(title).append("</font></strong></p></p>");
        sb.append("                  <p>message : <br><br>");
        sb.append("                    - Model/Assy :  <a href=\"").append(hrefModel).append("\" target=\"_blank\" >").append(modelName).append("</a><br>");
        sb.append("                    - Set P/N : ").append(setPartNo != null ? setPartNo : "").append("<p>");
        sb.append("                    *** ").append(content).append(" *** <p>");
        sb.append("                    To display BOM List click the hyperlink of the Model.</p>");
        sb.append("                  </td>");
        sb.append("              </tr>");
        sb.append("            </table></td>");
        sb.append("        </tr>");
        sb.append("        <tr> ");
        sb.append("          <td height=\"50\" colspan=\"3\" bgcolor=\"#FFFFFF\">&nbsp;</td>");
        sb.append("        </tr>");
        sb.append("        <tr> ");
        sb.append("          <td height=\"1\" colspan=\"3\"></td>");
        sb.append("        </tr>");
        sb.append("        <tr> ");
        sb.append("          <td width=\"151\" height=\"55\" align=\"center\" bgcolor=\"F2F2F2\"><img ");
        sb.append("src=\"").append("http://xcpc.lge.com:8500/extcore/").append("common/images/img_ko/image/footer_bi.gif\" width=\"120\" height=\"21\"></td> ");
        sb.append("          <td width=\"20\" bgcolor=\"F2F2F2\"><img src=\"").append("http://xcpc.lge.com:8500/extcore/").append("common/images/img_ko/image/footer_img_div.gif\" width=\"1\"   height=\"46\"></td> ");
        sb.append("          <td width=\"380\" bgcolor=\"F2F2F2\"><FONT CLASS=\"footer\">Copyright 2001-2003  ");
        sb.append("            LG Electronics. All rights reserved. <A HREF=\"").append(serverUrlString).append("execute/pc-view-contact-us-form/kor/side/contactus.html\" CLASS=\"link_10px\">Contact  ");
        sb.append("            us</A></FONT></td> ");
        sb.append("        </tr> ");
        sb.append("      </table></td> ");
        sb.append("  </tr> ");
        sb.append("</table> ");
        sb.append("</body> ");
        sb.append("</html> ");
        st = sb.toString();
    }

}
