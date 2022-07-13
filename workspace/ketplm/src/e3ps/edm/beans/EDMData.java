package e3ps.edm.beans;

import wt.content.ContentRoleType;
import wt.epm.EPMDocument;
import wt.representation.Representable;
import wt.representation.Representation;

import com.ptc.wvs.server.util.FileHelper;
import com.ptc.wvs.server.util.PublishUtils;

public class EDMData {


	public String copyTag;
	public String thum_mini;
	public String thum;

	public EDMData(EPMDocument epm) throws Exception{
		Representable representable = PublishUtils.findRepresentable(epm);
		Representation representation = PublishUtils.getRepresentation(
				representable, true, null, false);
		if (representation != null) {
			copyTag = PublishUtils.getRefFromObject(representation);
		}
		thum = FileHelper
				.getViewContentURLForType(PublishUtils.findRepresentable(epm),
						ContentRoleType.THUMBNAIL);
		thum_mini = FileHelper.getViewContentURLForType(
				PublishUtils.findRepresentable(epm),
				ContentRoleType.THUMBNAIL_SMALL);
	}
}
