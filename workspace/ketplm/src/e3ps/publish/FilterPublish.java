package e3ps.publish;

import java.util.Locale;

import wt.epm.EPMDocument;
import wt.fc.Persistable;

import com.ptc.wvs.server.util.PublishUtils;

import ext.ket.shared.log.Kogger;

public class FilterPublish {
    public static boolean FilterEPMDocumentPublish(EPMDocument epmdoc) {
        //Kogger.debug(getClass(), "  IN FilterEPMDocumentPublish for : " +	epmdoc.getCADName() +"================="+ epmdoc.getNumber());
        if(epmdoc.getCADName().endsWith(".gph")){
            Kogger.debug(FilterPublish.class, epmdoc.getCADName());
        }

        return !(epmdoc.getCADName().endsWith(".gph") || epmdoc.getCADName().endsWith(".GPH") || epmdoc.getAuthoringApplication().getDisplay(Locale.KOREAN).equals("EXCESS") || epmdoc.getAuthoringApplication().getDisplay(Locale.KOREAN).equals("Mentor Graphics PADS 레이아웃")  || epmdoc.getAuthoringApplication().getDisplay(Locale.KOREAN).equals("Mentor Graphics PADS Logic") );
    }

    public static boolean FilterPublish(Persistable p, Boolean publishFromDB) {
        EPMDocument epmdoc = PublishUtils.findEPMDocument(p);
        //Kogger.debug(getClass(), "  IN FilterEPMDocumentPublish for : " +	epmdoc.getCADName() +"================="+ epmdoc.getNumber() );
        if(epmdoc.getCADName().endsWith(".gph")){
            Kogger.debug(FilterPublish.class, epmdoc.getCADName());
        }

      return !(epmdoc.getNumber().endsWith(".gph") || epmdoc.getNumber().endsWith(".GPH") || epmdoc.getAuthoringApplication().getDisplay(Locale.KOREAN).equals("EXCESS") || epmdoc.getAuthoringApplication().getDisplay(Locale.KOREAN).equals("Mentor Graphics PADS 레이아웃")  || epmdoc.getAuthoringApplication().getDisplay(Locale.KOREAN).equals("Mentor Graphics PADS Logic") );
    }
}
