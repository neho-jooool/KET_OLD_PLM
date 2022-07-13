package ext.ket.part.entity.dto;

import java.io.Serializable;
import java.util.List;

final public class PartClassAttrLinkInputDTO implements Serializable {

    private List<PartClassAttrLinkDTO> partClassAttrLinkDTOs;

    public List<PartClassAttrLinkDTO> getPartClassAttrLinkDTOs() {
	return partClassAttrLinkDTOs;
    }

    public void setPartClassAttrLinkDTOs(List<PartClassAttrLinkDTO> partClassAttrLinkDTOs) {
	this.partClassAttrLinkDTOs = partClassAttrLinkDTOs;
    }

}
