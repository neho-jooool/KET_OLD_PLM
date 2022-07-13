/*
 * @(#) E3PSIconHelper.java  Create on 2006. 3. 3.
 * Copyright (c) e3ps. All rights reserverd
 */
package e3ps.common.content;

import wt.fc.WTObject;
import wt.util.WTException;
import wt.vc.wip.WorkInProgressHelper;
import wt.vc.wip.Workable;
import ext.ket.shared.log.Kogger;

/**
 * E3PS 모듈별 Icon
 * @author Choi Seunghwan, skyprda@e3ps.com
 * @version 1.00, 2006. 3. 3.
 * @since 1.4
 */
public class E3PSIconHelper
{ 
    public static E3PSIconHelper manager = new E3PSIconHelper();
    
    private E3PSIconHelper()
    {
    } 
    
    public String getIcon(WTObject _obj)
    {
        if(_obj == null) return "<img src='/plm/wtcore/images/generic.gif' border=0>";
        
        String icon = null;
        String checkout = "";
        if(_obj instanceof Workable)
        {
            Workable workable = (Workable)_obj;
            try
            {
                if(WorkInProgressHelper.isCheckedOut(workable))
                {
                    checkout = "<IMG BORDER=0 style='z-index:3;margin-left:-16px' src='/plm/wt/clients/images/checkout_glyph.gif'>";
                    if(WorkInProgressHelper.isWorkingCopy(workable))
                        checkout = "<IMG BORDER=0 style='z-index:3;margin-left:-16px' src='/plm/wt/clients/images/workingcopy_glyph.gif'>";
                }
            }
            catch (WTException e)
            {
                Kogger.error(getClass(), e);
            }
        }
        String className = _obj.getPersistInfo().getObjectIdentifier().getClassname();
        
        if(className.indexOf("WorkProcessForm") > 0)    // 그룹웨어
            icon = "<img src='/plm/wtcore/images/report_template_admin.gif' border=0 title='그룹웨어'>";
        else if(className.indexOf("e3ps.doc") == 0)  // 문서
            icon = "<img src='/plm/wtcore/images/com/ptc/core/ca/web/misc/paste.gif' border=0 title='문서'>";
        else if(className.indexOf("EChangeRequest") > 0)    // ECR
            icon = "<img src='/plm/wtcore/images/chgreqst.gif' border=0 title='설계변경요청'>";
        else if(className.indexOf("ECCBMeeting") > 0)    // ECCB
            icon = "<img src='/plm/wtcore/images/chgreqst.gif' border=0 title='설계변경요청'>";
        else if(className.indexOf("EChangeOrder") > 0)      // ECO
            icon = "<img src='/plm/portal/icon/fileicon/eco.gif' border=0 title='설계변경'>";
        else if(className.indexOf("EChangeActivity") > 0)      // ECA
            icon = "<img src='/plm/portal/icon/fileicon/eco.gif' border=0 title='ECA활동'>";
        else if(className.indexOf("EChangeNotify") > 0)     // ECN
            icon = "<img src='/plm/wtcore/images/chgnotice.gif' border=0 title='설계변경통보'>";
        else if(className.indexOf("EChangeReview") > 0)     // ECR검토
            icon = "<img src='/plm/portal/icon/fileicon/ecr_review.gif' border=0 title='ECR검토'>";
        else if(className.indexOf("ECNECCB") > 0)     // ECN ECCB
            icon = "<img src='/plm/portal/icon/fileicon/ecr_review.gif' border=0 title='ECN ECCB'>";
        else if(className.indexOf("E3PSPBO") > 0)           // E3PSPBO
            icon = "<img src='/plm/wtcore/images/type.gif' border=0>";
        else if(className.indexOf("ERPSendMaster") == 0)   // ERP 전송
            icon = "<img src='/plm/wtcore/images/cad_tem.gif' border=0 title='ERP 전송'>";
        else if(className.indexOf("e3ps.drawing") == 0)   // 일반도면
            icon = "<img src='/plm/wtcore/images/cad_tem.gif' border=0 title='도면'>";
        else if(className.indexOf("Distribute") > 0)     // 배포관련
            icon = "<img src='/plm/portal/icon/Distribute_icon.gif' border=0 title='배포'>";
        else if(className.indexOf("UnitErrorProcess") > 0)     // 이상발생
        	icon = "<img src='/plm/wtcore/images/com/ptc/core/ca/web/misc/paste.gif' border=0 title='이상발생'>";
        else if(className.indexOf("TemplateProject") > 0)     //Template
        	icon = "<img src='/plm/portal/images/img_common/calendar.gif' border=0 title='Template'>";
        else if(className.indexOf("JELProject") > 0)     //JELProject
        	icon = "<img src='/plm/portal/images/img_common/calendar.gif' border=0 title='JELProject'>";
        else
        {
            try
            {
                icon = e3ps.common.content.E3PSContentHelper.service.getIconImgTag(_obj);
            }
            catch (WTException e)
            {
                Kogger.error(getClass(), e);
            }
        }
        
        return icon+checkout;
    }
}
