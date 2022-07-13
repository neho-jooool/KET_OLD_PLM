package ext.ket.shared.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class EjsDTO {
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Width")
    private String width;
    @JsonProperty("RelWidth")
    private String relWidth;
    @JsonProperty("Align")
    private String align = "Center";
    @JsonProperty("CanSort")
    private String canSort = "0";
    @JsonProperty("CanEdit")
    private String carEdit = "0";

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getWidth() {
	return width;
    }

    public void setWidth(String width) {
	this.width = width;
    }

    public String getRelWidth() {
	return relWidth;
    }

    public void setRelWidth(String relWidth) {
	this.relWidth = relWidth;
    }

    public String getAlign() {
	return align;
    }

    public void setAlign(String align) {
	this.align = align;
    }

    public String getCanSort() {
	return canSort;
    }

    public void setCanSort(String canSort) {
	this.canSort = canSort;
    }

    public String getCarEdit() {
	return carEdit;
    }

    public void setCarEdit(String carEdit) {
	this.carEdit = carEdit;
    }
}
