/**
 * @copy  : Park Pil Keun (pkpark@lgcns.com)
 * @since : 2006.09.05
 * 
 * Cell 병합용
 */
package e3ps.bom.common.jtable;

public interface CellSpan {

	public final int ROW = 0;

	public final int COLUMN = 1;

	public int[] getSpan(int row, int column);

	public void setSpan(int[] span, int row, int column);

	public boolean isVisible(int row, int column);

	public void combine(int[] rows, int[] columns);

	public void split(int row, int column);
}
