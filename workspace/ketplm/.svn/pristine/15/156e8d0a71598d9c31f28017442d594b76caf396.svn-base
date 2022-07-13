package e3ps.bom.command.savebom;

import java.awt.Cursor;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.component.BOMSubAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.BOMSaveDao;
import e3ps.bom.dao.BOMSearchDao;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import ext.ket.shared.log.Kogger;

public class SaveBOMCmd extends AbstractAIFCommand
{
	private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JFrame parent;
    AbstractAIFUIApplication app;
	BOMRegisterApplicationPanel bomPanel;
	boolean bomGubunFlag = false;
	String type = "";

    public SaveBOMCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = app.getDesktop();
    }

    public SaveBOMCmd(JFrame frame, AbstractAIFUIApplication app, String type)
    {
        this.app = app;
        parent = app.getDesktop();
		this.type = type;
    }

	public SaveBOMCmd(){}

	protected void executeCommand() throws Exception
    {
		try
		{
			if(type.equals(""))
			{
				int n = JOptionPane.showConfirmDialog(parent, messageRegistry.getString("saveBom"), "Confirm", JOptionPane.YES_NO_OPTION);
				if(n==JOptionPane.YES_OPTION)
				{
					bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();

					bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

					BOMTreeTableModel model = (BOMTreeTableModel)((BOMRegisterApplicationPanel)app.getApplicationPanel()).getTreeTableModel();

					Enumeration enum0 = model.getRootNode().preorderEnumeration();
					Vector bomVec = new Vector();
					while(enum0.hasMoreElements())
					{
						BOMTreeNode node = (BOMTreeNode)enum0.nextElement();
						BOMAssyComponent ac = (BOMAssyComponent)node.getBOMComponent();
						if(!ac.getItemCodeStr().trim().equals(BOMBasicInfoPool.getPublicModelName().trim()))
						{
							bomVec.add(ac);
						}
					}

					bomGubunFlag = Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()).equals("") ? true : false;
					String bomEcoItemCode = Utility.checkNVL(BOMBasicInfoPool.getPublicModelName());

					BOMSaveDao dao = new BOMSaveDao();

					if(!bomGubunFlag)		// ECO Number 가 존재하는 경우 -> 설계변경인 경우
					{
						Vector tmpVec = new Vector();
						Vector bomEcoVec = new Vector();

						BOMSearchDao searchDao = new BOMSearchDao();
						searchDao.getBOMECOTmpData(Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()), bomEcoItemCode);

						tmpVec = searchDao.getResultListVec();

Kogger.debug(getClass(), "@@@@@@@@@@ tmpVec [SaveBOM 1] : " + tmpVec);
Kogger.debug(getClass(), "@@@@@@@@@@ bomVec [SaveBOM 1] : " + bomVec);

						String componentItemCodeStr = "";
						Double componentBoxQuantityDbl;
						Double componentQuantityDbl;
						String componentUnitStr = "";
						String componentMaterialStr = "";
						String componentHardnessFromStr = "";
						String componentHardnessToStr = "";
						String componentStartDateStr = "";
						String componentEndDateStr = "";
//						String componentBomTypeStr = "";
						int componentLevelInt = 0;
						Vector componentSubstituteVec = new Vector();

						String tmpComponentParentItemCodeStr = "";
						String tmpComponentItemCodeStr = "";
						Double tmpComponentBoxQuantityDbl;
						Double tmpComponentQuantityDbl;
						String tmpComponentUnitStr = "";
						String tmpComponentMaterialStr = "";
						String tmpComponentHardnessFromStr = "";
						String tmpComponentHardnessToStr = "";
						String tmpComponentStartDateStr = "";
						String tmpComponentEndDateStr = "";
//						String tmpComponentBomTypeStr = "";
						Vector tmpComponentSubstituteVec = new Vector();

						boolean changeSubstituteActivityFlag = false;

						bomEcoVec.removeAllElements();

						for(int j=0; j<bomVec.size(); j++)
						{
							Vector substituteVec = new Vector();

							componentSubstituteVec.removeAllElements();

							BOMAssyComponent component = (BOMAssyComponent)bomVec.elementAt(j);
							componentItemCodeStr = Utility.checkNVL(component.getItemCodeStr());
							componentBoxQuantityDbl = component.getBoxQuantityDbl();
							componentQuantityDbl = component.getQuantityDbl();
							componentUnitStr = Utility.checkNVL(component.getUitStr());
							componentMaterialStr = Utility.checkNVL(component.getMaterialStr());
							componentHardnessFromStr = Utility.checkNVL(component.getHardnessFrom());
							componentHardnessToStr = Utility.checkNVL(component.getHardnessTo());
							componentStartDateStr = Utility.checkNVL(component.getStartDate());
							componentEndDateStr = Utility.checkNVL(component.getEndDate());
//							componentBomTypeStr = Utility.checkNVL(component.getBomType());
							componentLevelInt = component.getLevelInt().intValue();
							componentSubstituteVec = component.getSubAssyComponent();

							if(tmpVec.toString().indexOf(componentItemCodeStr) == -1 )//&& (componentLevelInt == 1))
							{
								if ( componentLevelInt == 1 )
								{
									component.setEcoCodeStr("Add");
								}
								else
								{
									component.setEcoCodeStr("");
								}
								component.setBeforeQuantityDbl(new Double(0));

								if(componentSubstituteVec.size() > 0)
								{
									for(int k=0; k<componentSubstituteVec.size(); k++)
									{
										BOMSubAssyComponent substituteCmp = (BOMSubAssyComponent)componentSubstituteVec.elementAt(k);
										substituteCmp.setEcoCodeStr("Add");
										substituteVec.addElement(substituteCmp);
									}
									component.setSubAssyComponent(substituteVec);
								}
								bomEcoVec.addElement(component);
							}
							else if(componentLevelInt != 1)
							{
								bomEcoVec.addElement(component);
							}
							else
							{
								for(int i=0; i<tmpVec.size(); i++)
								{
									Vector substituteVec1 = new Vector();

									tmpComponentSubstituteVec.removeAllElements();

									changeSubstituteActivityFlag = false;

									BOMAssyComponent tmpComponent = (BOMAssyComponent)tmpVec.elementAt(i);
									tmpComponentItemCodeStr = Utility.checkNVL(tmpComponent.getItemCodeStr());
									tmpComponentBoxQuantityDbl = tmpComponent.getBoxQuantityDbl();
									tmpComponentQuantityDbl = tmpComponent.getQuantityDbl();
									tmpComponentUnitStr = Utility.checkNVL(tmpComponent.getUitStr());
									tmpComponentMaterialStr = Utility.checkNVL(tmpComponent.getMaterialStr());
									tmpComponentStartDateStr = Utility.checkNVL(tmpComponent.getStartDate());
									tmpComponentEndDateStr = Utility.checkNVL(tmpComponent.getEndDate());
//									tmpComponentBomTypeStr = Utility.checkNVL(tmpComponent.getBomType());
									tmpComponentSubstituteVec = tmpComponent.getSubAssyComponent();

									if(componentItemCodeStr.equals(tmpComponentItemCodeStr))
									{
										for(int idx=0; idx<componentSubstituteVec.size(); idx++)
										{
											BOMSubAssyComponent componentSubstitute = (BOMSubAssyComponent)componentSubstituteVec.elementAt(idx);

											// 기존에 대체부품 정보가 존재하지 않는경우 '추가'
											if(tmpComponentSubstituteVec.toString().indexOf((componentSubstitute.getSubstituteItemCodeStr() == null ? "" : componentSubstitute.getSubstituteItemCodeStr().toString())) == -1)
											{
												componentSubstitute.setEcoCodeStr("Add");
												changeSubstituteActivityFlag = true;

												substituteVec1.addElement(componentSubstitute);
											} else	{
												// 기존 대체부품 정보가 존재하는 경우
												for(int inx=0; inx<tmpComponentSubstituteVec.size(); inx++)
												{
													BOMSubAssyComponent tmpComponentSubstitute = (BOMSubAssyComponent)tmpComponentSubstituteVec.elementAt(inx);

													if(componentSubstitute.getSubstituteItemCodeStr().equals(tmpComponentSubstitute.getSubstituteItemCodeStr().trim()))
													{
														if(!componentSubstitute.getQuantityDbl().equals(tmpComponentSubstitute.getQuantityDbl()) )		// 수량만 비교하면 되나??
														{
															componentSubstitute.setBeforeQuantityDbl(tmpComponentSubstitute.getQuantityDbl());
															componentSubstitute.setBeforeUnitStr(tmpComponentSubstitute.getUitStr());
															componentSubstitute.setBeforeStartDate(tmpComponentSubstitute.getStartDate());
															componentSubstitute.setBeforeEndDate(tmpComponentSubstitute.getEndDate());

															componentSubstitute.setEcoCodeStr("Update");
															changeSubstituteActivityFlag = true;

															substituteVec1.addElement(componentSubstitute);
														}
														else
														{
															componentSubstitute.setEcoCodeStr("");
															substituteVec1.addElement(componentSubstitute);
														}
														break;
													}
												}
											}
										}

										for(int idx=0; idx<tmpComponentSubstituteVec.size(); idx++)
										{
											BOMSubAssyComponent tmpComponentSubstitute = (BOMSubAssyComponent)tmpComponentSubstituteVec.elementAt(idx);

											if(componentSubstituteVec.toString().indexOf((tmpComponentSubstitute.getSubstituteItemCodeStr() == null ? "" : tmpComponentSubstitute.getSubstituteItemCodeStr().toString().trim())) == -1)
											{
												tmpComponentSubstitute.setBeforeQuantityDbl(tmpComponentSubstitute.getQuantityDbl());
												tmpComponentSubstitute.setBeforeUnitStr(tmpComponentSubstitute.getUitStr());
												tmpComponentSubstitute.setBeforeStartDate(tmpComponentSubstitute.getStartDate());
												tmpComponentSubstitute.setBeforeEndDate(tmpComponentSubstitute.getEndDate());

												tmpComponentSubstitute.setQuantityDbl(new Double(0));
												tmpComponentSubstitute.setUitStr("");
												tmpComponentSubstitute.setStartDate("");
												tmpComponentSubstitute.setEndDate("");

												tmpComponentSubstitute.setEcoCodeStr("Remove");
												changeSubstituteActivityFlag = true;

												substituteVec1.addElement(tmpComponentSubstitute);
											}
										}

										component.setSubAssyComponent(substituteVec1);

										// 속성값 들만 변경된 경우
										if( componentBoxQuantityDbl.doubleValue() != tmpComponentBoxQuantityDbl.doubleValue() ||
											componentQuantityDbl.doubleValue() != tmpComponentQuantityDbl.doubleValue() ||
											!(componentUnitStr.equals(tmpComponentUnitStr)) ||
											!(componentMaterialStr.equals(tmpComponentMaterialStr)) ||
											!(componentHardnessFromStr.equals(tmpComponentHardnessFromStr)) ||
											!(componentHardnessToStr.equals(tmpComponentHardnessToStr)) ||
											!(componentStartDateStr.equals(tmpComponentStartDateStr)) ||
											!(componentEndDateStr.equals(tmpComponentEndDateStr)) ||
//											!(componentBomTypeStr.equals(tmpComponentBomTypeStr)) ||
											changeSubstituteActivityFlag)
										{
											component.setBeforeBoxQuantityDbl(tmpComponent.getBoxQuantityDbl());
											component.setBeforeQuantityDbl(tmpComponent.getQuantityDbl());
											component.setBeforeUnitStr(tmpComponent.getUitStr());
											component.setBeforeMaterialStr(tmpComponent.getMaterialStr());
											component.setBeforeHardnessFrom(tmpComponent.getHardnessFrom());
											component.setBeforeHardnessTo(tmpComponent.getHardnessTo());
											component.setBeforeStartDate(tmpComponent.getStartDate());
											component.setBeforeEndDate(tmpComponent.getEndDate());
//											component.setBeforeBomType(tmpComponent.getBomType());
											component.setBoxQuantityDbl(component.getBoxQuantityDbl());
											component.setQuantityDbl(component.getQuantityDbl());
											component.setUitStr(component.getUitStr());
											component.setMaterialStr(component.getMaterialStr());
											component.setStartDate(component.getStartDate());
											component.setEndDate(component.getEndDate());
//											component.setBomType(component.getBomType());
//											component.setItemSeqInt(tmpComponent.getItemSeqInt());

											component.setEcoCodeStr("Update");
											bomEcoVec.addElement(component);
										}
										else		// 변경없음
										{
											component.setEcoCodeStr("");
											bomEcoVec.addElement(component);
										}
										break;
									}
								}
							}
						}

						for(int i=0; i<tmpVec.size(); i++)
						{
							Vector substituteVec2 = new Vector();

							BOMAssyComponent tmpComponent = (BOMAssyComponent)tmpVec.elementAt(i);
							tmpComponentItemCodeStr = Utility.checkNVL(tmpComponent.getItemCodeStr());
							tmpComponentBoxQuantityDbl = tmpComponent.getBoxQuantityDbl();
							tmpComponentQuantityDbl = tmpComponent.getQuantityDbl();
							tmpComponentUnitStr = Utility.checkNVL(tmpComponent.getUitStr());
							tmpComponentMaterialStr = Utility.checkNVL(tmpComponent.getMaterialStr());
							tmpComponentHardnessFromStr = Utility.checkNVL(tmpComponent.getHardnessFrom());
							tmpComponentHardnessToStr = Utility.checkNVL(tmpComponent.getHardnessTo());
							tmpComponentStartDateStr = Utility.checkNVL(tmpComponent.getStartDate());
							tmpComponentEndDateStr = Utility.checkNVL(tmpComponent.getEndDate());
//							tmpComponentBomTypeStr = Utility.checkNVL(tmpComponent.getBomType());
							tmpComponentSubstituteVec = tmpComponent.getSubAssyComponent();

							if(bomVec.toString().indexOf(tmpComponentItemCodeStr.toString()) == -1)
							{
								if ( tmpComponent.getLevelInt() == 1 )
								{
									tmpComponent.setBeforeBoxQuantityDbl(tmpComponent.getBoxQuantityDbl());
									tmpComponent.setBeforeQuantityDbl(tmpComponent.getQuantityDbl());
									tmpComponent.setBeforeUnitStr(tmpComponent.getUitStr());
									tmpComponent.setBeforeMaterialStr(tmpComponent.getMaterialStr());
									tmpComponent.setBeforeHardnessFrom(tmpComponent.getHardnessFrom());
									tmpComponent.setBeforeHardnessTo(tmpComponent.getHardnessTo());
									tmpComponent.setBeforeStartDate(tmpComponent.getStartDate());
									tmpComponent.setBeforeEndDate(tmpComponent.getEndDate());
	//								tmpComponent.setBeforeBomType(tmpComponent.getBomType());
									tmpComponent.setBoxQuantityDbl(new Double(tmpComponent.getBoxQuantityDbl().doubleValue()));
									tmpComponent.setQuantityDbl(new Double(tmpComponent.getQuantityDbl().doubleValue()));
									tmpComponent.setItemSeqInt(tmpComponent.getItemSeqInt());
									tmpComponent.setUitStr("");
									tmpComponent.setMaterialStr("");
									tmpComponent.setHardnessFrom("");
									tmpComponent.setHardnessTo("");
									tmpComponent.setStartDate("");
									tmpComponent.setEndDate("");
	//								tmpComponent.setBomType("");
									tmpComponent.setVersionStr(tmpComponent.getVersionStr());
	
									tmpComponent.setEcoCodeStr("Remove");
	
									if(tmpComponentSubstituteVec.size() > 0)
									{
										for(int k=0; k<tmpComponentSubstituteVec.size(); k++)
										{
											BOMSubAssyComponent substituteCmp = (BOMSubAssyComponent)tmpComponentSubstituteVec.elementAt(k);
											substituteCmp.setBeforeQuantityDbl(substituteCmp.getQuantityDbl());
											substituteCmp.setBeforeUnitStr(substituteCmp.getUitStr());
											substituteCmp.setBeforeStartDate(substituteCmp.getStartDate());
											substituteCmp.setBeforeEndDate(substituteCmp.getEndDate());
											substituteCmp.setQuantityDbl(new Double(0));
											substituteCmp.setUitStr("");
											substituteCmp.setStartDate("");
											substituteCmp.setEndDate("");
											substituteCmp.setEcoCodeStr("Remove");
	
											substituteVec2.addElement(substituteCmp);
										}
										tmpComponent.setSubAssyComponent(substituteVec2);
									}
								}
								bomEcoVec.addElement(tmpComponent);
							}
						}

						dao.saveBomEcoList(Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()), bomEcoVec);
					}

					bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

					MessageBox m = new MessageBox(app.getDesktop(), messageRegistry.getString("saveBom1"), "Information", MessageBox.INFORMATION);
					m.setVisible(true);
					m.setModal(true);
					return;
				}
			}
			else
			{
				bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();

				bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

				BOMTreeTableModel model = (BOMTreeTableModel)((BOMRegisterApplicationPanel)app.getApplicationPanel()).getTreeTableModel();
//Kogger.debug(getClass(), "@@@@@ model.getRootNode() : " +  model.getRootNode());
				Enumeration enum0 = model.getRootNode().preorderEnumeration();
				Vector bomVec = new Vector();
				while(enum0.hasMoreElements())
				{
					BOMTreeNode node = (BOMTreeNode)enum0.nextElement();
					BOMAssyComponent ac = (BOMAssyComponent)node.getBOMComponent();
//Kogger.debug(getClass(), "@@@@@@ ac : " + ac);
//Kogger.debug(getClass(), "@@@@@@ ac.getItemCodeStr() : " + ac.getItemCodeStr());
//Kogger.debug(getClass(), "@@@@@@ BOMBasicInfoPool.getPublicModelName() : " + BOMBasicInfoPool.getPublicModelName());

					if(!ac.getItemCodeStr().trim().equals(BOMBasicInfoPool.getPublicModelName().trim()))
					{
						bomVec.add(ac);
					}
				}

				bomGubunFlag = Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()).equals("") ? true : false;
				String bomEcoItemCode = Utility.checkNVL(BOMBasicInfoPool.getPublicModelName());

				BOMSaveDao dao = new BOMSaveDao();

				if(!bomGubunFlag)			// ECO Number 가 존재하는 경우 -> 설계변경인 경우
				{
					Vector tmpVec = new Vector();
					Vector bomEcoVec = new Vector();

					BOMSearchDao searchDao = new BOMSearchDao();
					searchDao.getBOMECOTmpData(Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()), bomEcoItemCode);

					tmpVec = searchDao.getResultListVec();
Kogger.debug(getClass(), "@@@@@@@@@@ tmpVec [SaveBOM 2] : " + tmpVec);
Kogger.debug(getClass(), "@@@@@@@@@@ bomVec [SaveBOM 2] : " + bomVec);

					String componentItemCodeStr = "";
					Double componentBoxQuantityDbl;
					Double componentQuantityDbl;
					String componentUnitStr = "";
					String componentMaterialStr = "";
					String componentHardnessFromStr = "";
					String componentHardnessToStr = "";
					String componentStartDateStr = "";
					String componentEndDateStr = "";
//					String componentBomTypeStr = "";
					int componentLevelInt = 0;
					Vector componentSubstituteVec = new Vector();

					String tmpComponentParentItemCodeStr = "";
					String tmpComponentItemCodeStr = "";
					Double tmpComponentBoxQuantityDbl;
					Double tmpComponentQuantityDbl;
					String tmpComponentUnitStr = "";
					String tmpComponentMaterialStr = "";
					String tmpComponentHardnessFromStr = "";
					String tmpComponentHardnessToStr = "";
					String tmpComponentStartDateStr = "";
					String tmpComponentEndDateStr = "";
//					String tmpComponentBomTypeStr = "";
					Vector tmpComponentSubstituteVec = new Vector();

					boolean changeSubstituteActivityFlag = false;

					bomEcoVec.removeAllElements();

					for(int j=0; j<bomVec.size(); j++)
					{
						Vector substituteVec = new Vector();

						componentSubstituteVec.removeAllElements();

						BOMAssyComponent component = (BOMAssyComponent)bomVec.elementAt(j);
//Kogger.debug(getClass(), "@@@@@@@@ component [SaveBOM] : " + component.getItemSeqInt() + "" );

						componentItemCodeStr = Utility.checkNVL(component.getItemCodeStr());
						componentBoxQuantityDbl = component.getBoxQuantityDbl();
						componentQuantityDbl = component.getQuantityDbl();
						componentUnitStr = Utility.checkNVL(component.getUitStr());
						componentMaterialStr = Utility.checkNVL(component.getMaterialStr());
						componentHardnessFromStr = Utility.checkNVL(component.getHardnessFrom());
						componentHardnessToStr = Utility.checkNVL(component.getHardnessTo());
						componentStartDateStr = Utility.checkNVL(component.getStartDate());
						componentEndDateStr = Utility.checkNVL(component.getEndDate());
//						componentBomTypeStr = Utility.checkNVL(component.getBomType());
						componentLevelInt = component.getLevelInt().intValue();
						componentSubstituteVec = component.getSubAssyComponent();
//Kogger.debug(getClass(), "@@@@@@@@@ componentItemCodeStr  : " + componentItemCodeStr);
//Kogger.debug(getClass(), "@@@@@@@@@ componentBoxQuantityDbl  : " + componentBoxQuantityDbl);
//Kogger.debug(getClass(), "@@@@@@@@@ componentQuantityDbl  : " + componentQuantityDbl);
//Kogger.debug(getClass(), "@@@@@@@@@ componentUnitStr  : " + componentUnitStr);
//Kogger.debug(getClass(), "@@@@@@@@@ componentStartDateStr  : " + componentStartDateStr);
//Kogger.debug(getClass(), "@@@@@@@@@ componentEndDateStr  : " + componentEndDateStr);
//Kogger.debug(getClass(), "@@@@@@@@@ componentLevelInt  : " + componentLevelInt);

						if(tmpVec.toString().indexOf(componentItemCodeStr) == -1 )//&& (componentLevelInt == 1))
						{
							if ( componentLevelInt == 1 )
							{
								component.setEcoCodeStr("Add");
							} 
							else 
							{
								component.setEcoCodeStr("");
							}
							component.setBeforeQuantityDbl(new Double(0));

							if(componentSubstituteVec.size() > 0)
							{
								for(int k=0; k<componentSubstituteVec.size(); k++)
								{
									BOMSubAssyComponent substituteCmp = (BOMSubAssyComponent)componentSubstituteVec.elementAt(k);
									substituteCmp.setEcoCodeStr("Add");
									substituteVec.addElement(substituteCmp);
								}
								component.setSubAssyComponent(substituteVec);
							}
							bomEcoVec.addElement(component);
						}
//						else if(componentLevelInt != 1)			 // 왜 레벨이 1이 아닌건 무조건 넣는거지?? 그래서 주석처리하니까 제대로 입력된당
//						{
//							bomEcoVec.addElement(component);
//						}
						else
						{
							for(int i=0; i<tmpVec.size(); i++)
							{
								Vector substituteVec1 = new Vector();

								tmpComponentSubstituteVec.removeAllElements();

								changeSubstituteActivityFlag = false;

								BOMAssyComponent tmpComponent = (BOMAssyComponent)tmpVec.elementAt(i);
								tmpComponentItemCodeStr = Utility.checkNVL(tmpComponent.getItemCodeStr());
								tmpComponentBoxQuantityDbl = tmpComponent.getBoxQuantityDbl();
								tmpComponentQuantityDbl = tmpComponent.getQuantityDbl();
								tmpComponentUnitStr = Utility.checkNVL(tmpComponent.getUitStr());
								tmpComponentMaterialStr = Utility.checkNVL(tmpComponent.getMaterialStr());
								tmpComponentHardnessFromStr = Utility.checkNVL(tmpComponent.getHardnessFrom());
								tmpComponentHardnessToStr = Utility.checkNVL(tmpComponent.getHardnessTo());
								tmpComponentStartDateStr = Utility.checkNVL(tmpComponent.getStartDate());
								tmpComponentEndDateStr = Utility.checkNVL(tmpComponent.getEndDate());
//								tmpComponentBomTypeStr = Utility.checkNVL(tmpComponent.getBomType());
								tmpComponentSubstituteVec = tmpComponent.getSubAssyComponent();

//Kogger.debug(getClass(), "@@@@@@@@@@@ tmpComponentItemCodeStr : " + tmpComponentItemCodeStr);
//Kogger.debug(getClass(), "@@@@@@@@@@@ tmpComponentBoxQuantityDbl : " + tmpComponentBoxQuantityDbl);
//Kogger.debug(getClass(), "@@@@@@@@@@@ tmpComponentQuantityDbl : " + tmpComponentQuantityDbl);
//Kogger.debug(getClass(), "@@@@@@@@@@@ tmpComponentUnitStr : " + tmpComponentUnitStr);
//Kogger.debug(getClass(), "@@@@@@@@@@@ tmpComponentStartDateStr : " + tmpComponentStartDateStr);
//Kogger.debug(getClass(), "@@@@@@@@@@@ tmpComponentEndDateStr : " + tmpComponentEndDateStr);
//Kogger.debug(getClass(), "@@@@@@@@@@@ changeSubstituteActivityFlag : " + changeSubstituteActivityFlag);
//Kogger.debug(getClass(), "@@@@@@@@@@@ tmpComponentSubstituteVec : " + tmpComponentSubstituteVec);

								if(componentItemCodeStr.equals(tmpComponentItemCodeStr))
								{
									for(int idx=0; idx<componentSubstituteVec.size(); idx++)
									{
										BOMSubAssyComponent componentSubstitute = (BOMSubAssyComponent)componentSubstituteVec.elementAt(idx);
//Kogger.debug(getClass(), "@@@@@@@@@@@ componentSubstitute : " + componentSubstitute);

										if(tmpComponentSubstituteVec.toString().indexOf((componentSubstitute.getSubstituteItemCodeStr() == null ? "" : componentSubstitute.getSubstituteItemCodeStr().toString())) == -1)
										{
											componentSubstitute.setEcoCodeStr("Add");
											changeSubstituteActivityFlag = true;

											substituteVec1.addElement(componentSubstitute);
										}
										else
										{
											for(int inx=0; inx<tmpComponentSubstituteVec.size(); inx++)
											{
												BOMSubAssyComponent tmpComponentSubstitute = (BOMSubAssyComponent)tmpComponentSubstituteVec.elementAt(inx);
//Kogger.debug(getClass(), "@@@@@@@@@@@ tmpComponentSubstitute : " + tmpComponentSubstitute);

												if(componentSubstitute.getSubstituteItemCodeStr().equals(tmpComponentSubstitute.getSubstituteItemCodeStr().trim()))
												{
													if(!componentSubstitute.getQuantityDbl().equals(tmpComponentSubstitute.getQuantityDbl()) )		// 수량만 비교하면 되나??
													{
														componentSubstitute.setBeforeQuantityDbl(tmpComponentSubstitute.getQuantityDbl());
														componentSubstitute.setBeforeUnitStr(tmpComponentSubstitute.getUitStr());
														componentSubstitute.setBeforeStartDate(tmpComponentSubstitute.getStartDate());
														componentSubstitute.setBeforeEndDate(tmpComponentSubstitute.getEndDate());

														componentSubstitute.setEcoCodeStr("Update");
														changeSubstituteActivityFlag = true;

														substituteVec1.addElement(componentSubstitute);
													}
													else
													{
														componentSubstitute.setEcoCodeStr("");
														substituteVec1.addElement(componentSubstitute);
													}
													break;
												}
											}
										}
									}

									for(int idx=0; idx<tmpComponentSubstituteVec.size(); idx++)
									{
										BOMSubAssyComponent tmpComponentSubstitute = (BOMSubAssyComponent)tmpComponentSubstituteVec.elementAt(idx);

										if(componentSubstituteVec.toString().indexOf((tmpComponentSubstitute.getSubstituteItemCodeStr() == null ? "" : tmpComponentSubstitute.getSubstituteItemCodeStr().toString().trim())) == -1)
										{
											tmpComponentSubstitute.setBeforeQuantityDbl(tmpComponentSubstitute.getQuantityDbl());
											tmpComponentSubstitute.setBeforeUnitStr(tmpComponentSubstitute.getUitStr());
											tmpComponentSubstitute.setBeforeStartDate(tmpComponentSubstitute.getStartDate());
											tmpComponentSubstitute.setBeforeEndDate(tmpComponentSubstitute.getEndDate());

											tmpComponentSubstitute.setQuantityDbl(new Double(0));
											tmpComponentSubstitute.setUitStr("");
											tmpComponentSubstitute.setStartDate("");
											tmpComponentSubstitute.setEndDate("");

											tmpComponentSubstitute.setEcoCodeStr("Remove");
											changeSubstituteActivityFlag = true;

											substituteVec1.addElement(tmpComponentSubstitute);
										}
									}

									component.setSubAssyComponent(substituteVec1);

									if( componentBoxQuantityDbl.doubleValue() != tmpComponentBoxQuantityDbl.doubleValue() ||
										componentQuantityDbl.doubleValue() != tmpComponentQuantityDbl.doubleValue() ||
										!(componentUnitStr.equals(tmpComponentUnitStr)) ||
										!(componentMaterialStr.equals(tmpComponentMaterialStr)) ||
										!(componentHardnessFromStr.equals(tmpComponentHardnessFromStr)) ||
										!(componentHardnessToStr.equals(tmpComponentHardnessToStr)) ||
										!(componentStartDateStr.equals(tmpComponentStartDateStr)) ||
										!(componentEndDateStr.equals(tmpComponentEndDateStr)) ||
//										!(componentBomTypeStr.equals(tmpComponentBomTypeStr)) ||
										changeSubstituteActivityFlag)
									{
//Kogger.debug(getClass(), "=========================================");
//Kogger.debug(getClass(), " >> 0번 : " +   !(componentBoxQuantityDbl.equals(tmpComponentBoxQuantityDbl)) );
//Kogger.debug(getClass(), " >> 1번 : " +   !(componentQuantityDbl.equals(tmpComponentQuantityDbl)) );
//Kogger.debug(getClass(), " >> 2번 : " +   !(componentUnitStr.equals(tmpComponentUnitStr)) );
//Kogger.debug(getClass(), " >> 3번 : " +   !(componentMaterialStr.equals(tmpComponentMaterialStr))  );
//Kogger.debug(getClass(), " >> 4번 : " +   !(componentHardnessFromStr.equals(tmpComponentHardnessFromStr))   );
//Kogger.debug(getClass(), " >> 5번 : " +   !(componentHardnessToStr.equals(tmpComponentHardnessToStr))  );
//Kogger.debug(getClass(), " >> 6번 : " +   !(componentStartDateStr.equals(tmpComponentStartDateStr))  );
//Kogger.debug(getClass(), " >> 7번 : " +   !(componentEndDateStr.equals(tmpComponentEndDateStr))   );
//Kogger.debug(getClass(), " >> 8번 : " +   changeSubstituteActivityFlag );
//Kogger.debug(getClass(), "=========================================");
//Kogger.debug(getClass(), "@@@@@@@@@@ 몇번 들어왔지???? >>>>> " + i);
										component.setBeforeBoxQuantityDbl(tmpComponent.getBoxQuantityDbl());
										component.setBeforeQuantityDbl(tmpComponent.getQuantityDbl());
										component.setBeforeUnitStr(tmpComponent.getUitStr());
										component.setBeforeMaterialStr(tmpComponent.getMaterialStr());
										component.setBeforeHardnessFrom(tmpComponent.getHardnessFrom());
										component.setBeforeHardnessTo(tmpComponent.getHardnessTo());
										component.setBeforeStartDate(tmpComponent.getStartDate());
										component.setBeforeEndDate(tmpComponent.getEndDate());
//										component.setBeforeBomType(tmpComponent.getBomType());
										component.setBoxQuantityDbl(component.getBoxQuantityDbl());
										component.setQuantityDbl(component.getQuantityDbl());
										component.setUitStr(component.getUitStr());
										component.setMaterialStr(component.getMaterialStr());
										component.setHardnessFrom(component.getHardnessFrom());
										component.setHardnessTo(component.getHardnessTo());
										component.setStartDate(component.getStartDate());
										component.setEndDate(component.getEndDate());
//										component.setBomType(component.getBomType());
//										component.setItemSeqInt(tmpComponent.getItemSeqInt());		// tmpComponent.getItemSeqInt() 가 0이라서 ItemSeq 가 모두 0으로 됨

										component.setEcoCodeStr("Update");
										bomEcoVec.addElement(component);
									}
									else
									{
										component.setEcoCodeStr("");
										bomEcoVec.addElement(component);
									}
									break;
								}
							}
						}
					}

					String strTargetParentItemCode = "";
					String strTargetItemCode = "";
					boolean isExst = false;
					for(int i=0; i<tmpVec.size(); i++)
					{
						isExst = false;
						Vector substituteVec2 = new Vector();

						BOMAssyComponent tmpComponent = (BOMAssyComponent)tmpVec.elementAt(i);
						tmpComponentParentItemCodeStr = Utility.checkNVL(tmpComponent.getParentItemCodeStr());
						tmpComponentItemCodeStr = Utility.checkNVL(tmpComponent.getItemCodeStr());
Kogger.debug(getClass(), "???????????? tmpComponentParentItemCodeStr : " + tmpComponentParentItemCodeStr);							
Kogger.debug(getClass(), "???????????? tmpComponentItemCodeStr : " + tmpComponentItemCodeStr);			

						tmpComponentBoxQuantityDbl = tmpComponent.getBoxQuantityDbl();
						tmpComponentQuantityDbl = tmpComponent.getQuantityDbl();
						tmpComponentUnitStr = Utility.checkNVL(tmpComponent.getUitStr());
						tmpComponentMaterialStr = Utility.checkNVL(tmpComponent.getMaterialStr());
						tmpComponentHardnessFromStr = Utility.checkNVL(tmpComponent.getHardnessFrom());
						tmpComponentHardnessToStr = Utility.checkNVL(tmpComponent.getHardnessTo());
						tmpComponentStartDateStr = Utility.checkNVL(tmpComponent.getStartDate());
						tmpComponentEndDateStr = Utility.checkNVL(tmpComponent.getEndDate());
//						tmpComponentBomTypeStr = Utility.checkNVL(tmpComponent.getBomType());
						tmpComponentSubstituteVec = tmpComponent.getSubAssyComponent();

						if(bomVec.toString().indexOf(tmpComponentItemCodeStr.toString()) == -1)
						{
							isExst = false;
						} 
						else
						{  
							for (int jnx = 0; jnx < bomVec.size(); jnx++) 
							{
								BOMAssyComponent targetComponent = (BOMAssyComponent)bomVec.elementAt(jnx);
								strTargetParentItemCode = targetComponent.getParentItemCodeStr();
								strTargetItemCode = targetComponent.getItemCodeStr();
Kogger.debug(getClass(), "???????????? strTargetParentItemCode : " + strTargetParentItemCode);
Kogger.debug(getClass(), "???????????? strTargetItemCode : " + strTargetItemCode);	
	
								// 자부품과 모푸품이 모두 같은지 여부를 확인한다.
								if ( (strTargetItemCode != null && strTargetItemCode.trim().equals(tmpComponentItemCodeStr.trim())) && 
									 (strTargetParentItemCode != null && strTargetParentItemCode.trim().equals(tmpComponentParentItemCodeStr.trim()))  )
								{
									isExst = true;
									break;
								}
							}
						}
Kogger.debug(getClass(), "########## 존재여부 : " + isExst);			

						if ( !isExst )	
						{
							if ( tmpComponent.getLevelInt() == 1 )
							{
								tmpComponent.setBeforeBoxQuantityDbl(tmpComponent.getBoxQuantityDbl());
								tmpComponent.setBeforeQuantityDbl(tmpComponent.getQuantityDbl());
								tmpComponent.setBeforeUnitStr(tmpComponent.getUitStr());
								tmpComponent.setBeforeMaterialStr(tmpComponent.getMaterialStr());
								tmpComponent.setBeforeHardnessFrom(tmpComponent.getHardnessFrom());
								tmpComponent.setBeforeHardnessTo(tmpComponent.getHardnessTo());
								tmpComponent.setBeforeStartDate(tmpComponent.getStartDate());
								tmpComponent.setBeforeEndDate(tmpComponent.getEndDate());
	//							tmpComponent.setBeforeBomType(tmpComponent.getBomType());
								tmpComponent.setBoxQuantityDbl(new Double(tmpComponent.getBoxQuantityDbl().doubleValue()));
								tmpComponent.setQuantityDbl(new Double(tmpComponent.getQuantityDbl().doubleValue()));
								tmpComponent.setItemSeqInt(tmpComponent.getItemSeqInt());
								tmpComponent.setBoxQuantityDbl(0.0);
								tmpComponent.setQuantityDbl(0.0);
								tmpComponent.setUitStr("");
								tmpComponent.setMaterialStr("");
								tmpComponent.setHardnessFrom("");
								tmpComponent.setHardnessTo("");
								tmpComponent.setStartDate("");
								tmpComponent.setEndDate("");
	//							tmpComponent.setBomType("");
								tmpComponent.setVersionStr(tmpComponent.getVersionStr());
	
								tmpComponent.setEcoCodeStr("Remove");
	
								if(tmpComponentSubstituteVec.size() > 0)
								{
									for(int k=0; k<tmpComponentSubstituteVec.size(); k++)
									{
										BOMSubAssyComponent substituteCmp = (BOMSubAssyComponent)tmpComponentSubstituteVec.elementAt(k);
										substituteCmp.setBeforeQuantityDbl(substituteCmp.getQuantityDbl());
										substituteCmp.setBeforeUnitStr(substituteCmp.getUitStr());
										substituteCmp.setBeforeStartDate(substituteCmp.getStartDate());
										substituteCmp.setBeforeEndDate(substituteCmp.getEndDate());
										substituteCmp.setQuantityDbl(new Double(0));
										substituteCmp.setQuantityDbl(0.0);
										substituteCmp.setUitStr("");
										substituteCmp.setStartDate("");
										substituteCmp.setEndDate("");
										substituteCmp.setEcoCodeStr("Remove");
	
										substituteVec2.addElement(substituteCmp);
									}
									tmpComponent.setSubAssyComponent(substituteVec2);
								}
							}
							
							bomEcoVec.addElement(tmpComponent);
						}
					}
					dao.saveBomEcoList(Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()), bomEcoVec);
				}

				bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
        }
		catch (Exception e)
		{
            Kogger.error(getClass(), e);
			bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			MessageBox m = new MessageBox(app.getDesktop(), "DB Error : " + e.toString(), "Error", MessageBox.ERROR);
			m.setVisible(true);
			m.setModal(true);
			return;
        }
    }
}
