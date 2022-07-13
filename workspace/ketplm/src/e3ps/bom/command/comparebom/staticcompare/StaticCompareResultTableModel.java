package e3ps.bom.command.comparebom.staticcompare;

import java.text.NumberFormat;

import e3ps.bom.command.comparebom.AbstractComparePanel;
import e3ps.bom.command.comparebom.AbstractCompareResultTableModel;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.component.BOMDesignatorComponent;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.common.message.KETMessageService;
import ext.ket.part.util.PartUtil;

/**
 * class Name  : NewModelListTableModel
 * Description : BasicModel의 list를 보여주는 table의 TableModel
 */
public class StaticCompareResultTableModel extends AbstractCompareResultTableModel
{
    private static final long serialVersionUID = 1L;

    public StaticCompareResultTableModel(AbstractComparePanel parentPanel) throws Exception
    {
        super(parentPanel);

        try
        {
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    public StaticCompareResultData getComponent(int nRow)
    {
        if (nRow < 0 || nRow >= getRowCount())
        {
            return null;
        }
        return (StaticCompareResultData)m_vector.elementAt(nRow);
    }

    public boolean isCellEditable(int nRow, int nCol)
    {
        return false;
    }

    public Object getValueAt(int nRow, int nCol)
    {
        if (nRow < 0 || nRow >= getRowCount())
        {
            return "";
        }

        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumFractionDigits(3);

        StaticCompareResultData rowData = (StaticCompareResultData)m_vector.elementAt(nRow);
        BOMAssyComponent sourceAssyComponent = rowData.getSourceAssyComponent();
        BOMAssyComponent targetAssyComponent = rowData.getTargetAssyComponent();
        BOMDesignatorComponent sourceDesignatorComponent = rowData.getSourceDesignatorComponent();
        BOMDesignatorComponent targetDesignatorComponent = rowData.getTargetDesignatorComponent();

        KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();

        if (rowData == null)
        {
            return "";
        }

        if (rowData.getCode().equals("N"))
        {
            if (!rowData.getFirstFlag().equals(""))
            {
                /*if(getColumnName(nCol).equalsIgnoreCase("Code"))
                {
                    return rowData.getCode();
                }
                else*/

                if(getColumnName(nCol).equalsIgnoreCase("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00152")/*모부품번호*/))
                {
                    return sourceAssyComponent.getParentItemCodeStr();
                }
                else if(getColumnName(nCol).equalsIgnoreCase("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/))
                {
                    return rowData.getItemCode();
                }
                else if(getColumnName(nCol).equalsIgnoreCase("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/))
                {
                    return sourceAssyComponent.getDescStr();
                }
                else if(getColumnName(nCol).equalsIgnoreCase("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/))
                {
                    if (rowData.getDesignatorNo().equals(""))
                    {
//                        return sourceAssyComponent.getQuantityDbl().toString();

                        String quantity = sourceAssyComponent.getQuantityDbl() + "";
                        if( !quantity.equals("") && quantity.indexOf(".") >= 0 )
                        {
                            quantity = quantity.substring(0, quantity.indexOf("."));
                        }

                        if( PartUtil.isProductType(sourceAssyComponent.getIBAPartType()))
                        {
                            return format.format(sourceAssyComponent.getQuantityDbl());
                        }
                        else
                        {
                            return quantity;
                        }

                    } else
                    {
                        return sourceDesignatorComponent.getQuantityDbl().toString();
                    }
                }
                else if(getColumnName(nCol).equalsIgnoreCase("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00126")/*단위*/))
                {
                    return bean.getUnitDisplayValue( sourceAssyComponent.getUitStr());
                }

                else if(getColumnName(nCol).equalsIgnoreCase("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00304")/*재질*/))
                {
                    return  sourceAssyComponent.getMaterialStr();
                }
                else if(getColumnName(nCol).equalsIgnoreCase("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00082")/*경도(From)*/))
                {
                    return  sourceAssyComponent.getHardnessFrom();
                }
                else if(getColumnName(nCol).equalsIgnoreCase("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00084")/*경도(To)*/))
                {
                    return  sourceAssyComponent.getHardnessTo();
                }
                else if(getColumnName(nCol).equalsIgnoreCase("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00226")/*설계일자*/))
                {
                    return  sourceAssyComponent.getDesignDate();
                }

            }
            else if (!rowData.getSecondFlag().equals(""))
            {
                /*if(getColumnName(nCol).equalsIgnoreCase("Code"))
                {
                    return rowData.getCode();
                }
                else*/

                if(getColumnName(nCol).equalsIgnoreCase("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00152")/*모부품번호*/))
                {
                    return targetAssyComponent.getParentItemCodeStr();
                }
                else if(getColumnName(nCol).equalsIgnoreCase("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/))
                {
                    return rowData.getItemCode();
                }
                else if(getColumnName(nCol).equalsIgnoreCase("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/))
                {
                    return targetAssyComponent.getDescStr();
                }
                else if(getColumnName(nCol).equalsIgnoreCase("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/))
                {
                    if (rowData.getDesignatorNo().equals(""))
                    {
//                        return targetAssyComponent.getQuantityDbl().toString();

                        String quantity = targetAssyComponent.getQuantityDbl() + "";
                        if( !quantity.equals("") && quantity.indexOf(".") >= 0 )
                        {
                            quantity = quantity.substring(0, quantity.indexOf("."));
                        }

                        if( PartUtil.isProductType(targetAssyComponent.getIBAPartType()) )
                        {
                            return format.format(targetAssyComponent.getQuantityDbl());
                        }
                        else
                        {
                            return quantity;
                        }


                    } else
                    {
                        return targetDesignatorComponent.getQuantityDbl().toString();
                    }
                }
                else if(getColumnName(nCol).equalsIgnoreCase("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00126")/*단위*/))
                {
                    return bean.getUnitDisplayValue( targetAssyComponent.getUitStr());
                }

                else if(getColumnName(nCol).equalsIgnoreCase("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00304")/*재질*/))
                {
                    return  targetAssyComponent.getMaterialStr();
                }
                else if(getColumnName(nCol).equalsIgnoreCase("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00082")/*경도(From)*/))
                {
                    return  targetAssyComponent.getHardnessFrom();
                }
                else if(getColumnName(nCol).equalsIgnoreCase("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00084")/*경도(To)*/))
                {
                    return  targetAssyComponent.getHardnessTo();
                }
                else if(getColumnName(nCol).equalsIgnoreCase("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00226")/*설계일자*/))
                {
                    return  targetAssyComponent.getDesignDate();
                }
            }
        }
        else
        {
            /*if(getColumnName(nCol).equalsIgnoreCase("Code"))
            {
                return rowData.getCode();
            }*/

            if(getColumnName(nCol).equalsIgnoreCase("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/))
            {
                return rowData.getItemCode();
            }
            else if(getColumnName(nCol).equalsIgnoreCase("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00184")/*부품번호*/))
            {
                return rowData.getItemCode();
            }
            else if(getColumnName(nCol).equalsIgnoreCase("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00152")/*모부품번호*/))
            {
                return sourceAssyComponent.getParentItemCodeStr();
            }
            else if(getColumnName(nCol).equalsIgnoreCase("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00152")/*모부품번호*/))
            {
                return targetAssyComponent.getParentItemCodeStr();
            }
            else if(getColumnName(nCol).equalsIgnoreCase("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/))
            {
                return sourceAssyComponent.getDescStr();
            }
            else if(getColumnName(nCol).equalsIgnoreCase("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00183")/*부품명*/))
            {
                return targetAssyComponent.getDescStr();
            }
            else if(getColumnName(nCol).equalsIgnoreCase("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/))
            {
                if (rowData.getDesignatorNo().equals(""))
                {
//                    return sourceAssyComponent.getQuantityDbl().toString();

                    String quantity = sourceAssyComponent.getQuantityDbl() + "";
                    if( !quantity.equals("") && quantity.indexOf(".") >= 0 )
                    {
                        quantity = quantity.substring(0, quantity.indexOf("."));
                    }

                    if( PartUtil.isProductType(sourceAssyComponent.getIBAPartType()) )
                    {
                        return format.format(sourceAssyComponent.getQuantityDbl());
                    }
                    else
                    {
                        return quantity;
                    }
                }
                else
                {
                    return sourceDesignatorComponent.getQuantityDbl().toString();
                }
            }
            else if(getColumnName(nCol).equalsIgnoreCase("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00230")/*수량*/))
            {
                if (rowData.getDesignatorNo().equals(""))
                {
//                    return targetAssyComponent.getQuantityDbl().toString();

                    String quantity = targetAssyComponent.getQuantityDbl() + "";
                    if( !quantity.equals("") && quantity.indexOf(".") >= 0 )
                    {
                        quantity = quantity.substring(0, quantity.indexOf("."));
                    }

                    if( PartUtil.isProductType(targetAssyComponent.getIBAPartType()))
                    {
                        return format.format(targetAssyComponent.getQuantityDbl());
                    }
                    else
                    {
                        return quantity;
                    }
                }
                else
                {
                    return targetDesignatorComponent.getQuantityDbl().toString();
                }
            }
            else if(getColumnName(nCol).equalsIgnoreCase("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00126")/*단위*/))
            {
                return bean.getUnitDisplayValue( sourceAssyComponent.getUitStr());
            }
            else if(getColumnName(nCol).equalsIgnoreCase("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00126")/*단위*/))
            {
                return bean.getUnitDisplayValue( targetAssyComponent.getUitStr());
            }

            else if(getColumnName(nCol).equalsIgnoreCase("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00304")/*재질*/))
            {
                return  sourceAssyComponent.getMaterialStr();
            }
            else if(getColumnName(nCol).equalsIgnoreCase("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00082")/*경도(From)*/))
            {
                return  sourceAssyComponent.getHardnessFrom();
            }
            else if(getColumnName(nCol).equalsIgnoreCase("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00084")/*경도(To)*/))
            {
                return  sourceAssyComponent.getHardnessTo();
            }
            else if(getColumnName(nCol).equalsIgnoreCase("A: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00226")/*설계일자*/))
            {
                return  sourceAssyComponent.getDesignDate();
            }
            else if(getColumnName(nCol).equalsIgnoreCase("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00304")/*재질*/))
            {
                return  targetAssyComponent.getMaterialStr();
            }
            else if(getColumnName(nCol).equalsIgnoreCase("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00082")/*경도(From)*/))
            {
                return  targetAssyComponent.getHardnessFrom();
            }
            else if(getColumnName(nCol).equalsIgnoreCase("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00084")/*경도(To)*/))
            {
                return  targetAssyComponent.getHardnessTo();
            }
            else if(getColumnName(nCol).equalsIgnoreCase("B: " + KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00226")/*설계일자*/))
            {
                return  targetAssyComponent.getDesignDate();
            }

        }

        return "";
    }

    public void setValueAt(Object value, int nRow, int nCol)
    {
    }

    public void insert(StaticCompareResultData resultData)
    {
        m_vector.addElement(resultData);
    }
}
