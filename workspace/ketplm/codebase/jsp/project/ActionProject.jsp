<%@page import="java.text.DecimalFormat"%>
<%@page import="e3ps.project.beans.E3PSProjectData"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.Hashtable"%>
<%@page import="e3ps.common.util.*"%>
<%@page import="e3ps.project.*"%>
<%@page import="java.util.Vector"%>
<%@page import="wt.project.Role"%>
<%@page import="wt.team.TeamTemplate"%>
<%@page import="wt.team.TeamHelper"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.groupware.company.PeopleData"%>
<%@page import="wt.org.WTUser"%>
<%@page import="wt.session.*"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<%
    String msg = "";
    String oid = request.getParameter("oid");
    boolean isCreate = false;
    try {
		String command = request.getParameter("command");
		if (command == null) {
		    command = "";
		}

		if (command != null && command.equals("Create")) {
		    String drNumber = request.getParameter("drNumber");
		    String drKeyOid = request.getParameter("drKeyOid");
            
		    String productTypeLevel2 = request.getParameter("productTypeLevel2");      //제품구분 Level2
		    String projectName = request.getParameter("projectName");
		    String planStartDate = request.getParameter("planStartDate");
		    String planEndDate = "";
		    String projectTypeName = request.getParameter("projectTypeName");
		    String developenttype = request.getParameter("developenttype");
		    if("-".equals(developenttype)){
			developenttype = null;
		    }
		    String salesUser = StringUtil.checkNull(request.getParameter("salesUser"));
		    String devDeptOid = request.getParameter("devDeptOid");
		    String devUser = request.getParameter("devUser");
            
		    String proposalDate = request.getParameter("proposalDate");
		    String estimateDate = request.getParameter("estimateDate");

		    //Wbs
		    String templateOid = StringUtil.checkNull(request.getParameter("templateOid"));
		    String wbsType = request.getParameter("wbsType");
		    String category[] = request.getParameterValues("category");
		    String pwlinkOid = StringUtil.checkNull(request.getParameter("pwlinkOid"));

		    //기준 정보
		    String rank = request.getParameter("rank");
		    String producttype = request.getParameter("producttype");
		    String assembledtype = request.getParameter("assembledtype");
		    String lifecycle = request.getParameter("lifecycle");
		    //재 작업 필요
		    e3ps.common.jdf.config.Config conf = e3ps.common.jdf.config.ConfigImpl.getInstance();

		    // 참여자
		    String userMemberArr[] = request.getParameterValues("userMember");
		    Vector userMemberVec = new Vector();
		    if (userMemberArr != null && userMemberArr.length > 0) {
    			for (int i = 0; i < userMemberArr.length; i++) {
    			    userMemberVec.add(userMemberArr[i]);
    			}
		    }
		    // 최종사용처
		    String CUSTOMEREVENTOid[] = request.getParameterValues("CUSTOMEREVENTOid");
		    Vector CUSTOMEREVENTOidVec = new Vector();
    		    if (CUSTOMEREVENTOid != null && CUSTOMEREVENTOid.length > 0) {
    			for (int i = 0; i < CUSTOMEREVENTOid.length; i++) {
    			    CUSTOMEREVENTOidVec.add(CUSTOMEREVENTOid[i]);
    			}
		    }
		    // 고객처
		    String SUBCONTRACTOROid[] = request.getParameterValues("sOid");
		    Vector SUBCONTRACTOROidVec = new Vector();
		    if (SUBCONTRACTOROid != null && SUBCONTRACTOROid.length > 0) {
    			for (int i = 0; i < SUBCONTRACTOROid.length; i++) {
    
    			    SUBCONTRACTOROidVec.add(SUBCONTRACTOROid[i]);
    			}
		    }
		    // 납입처 Link
		    String nOid[] = request.getParameterValues("nOid");
		    Vector nOidVec = new Vector();
		    if (nOid != null && nOid.length > 0) {
    			for (int i = 0; i < nOid.length; i++) {
    			    nOidVec.add(nOid[i]);
    			}
		    }
		    
		    String developePurpose1 = request.getParameter("developePurpose1"); //개발목적1레벨
		    String developePurpose2 = request.getParameter("developePurpose2"); //개발목적2레벨

		    // print
		    for (int i = 0; i < nOidVec.size(); i++) {
			    StringTokenizer token = new StringTokenizer((String) nOidVec.get(i), "@");
    			String nnOid[] = new String[token.countTokens()];
    			int t = 0;
    			while (token.hasMoreTokens()) {
    			    nnOid[t] = token.nextToken();
    			    t++;
    			}
    			String masterCode = nnOid[0];
    			String subCode = nnOid[1];
    			NumberCode mnc = (NumberCode) CommonUtil.getObject(masterCode);
    			NumberCode snc = (NumberCode) CommonUtil.getObject(subCode);

		    }

		    // 개발 검토 프로젝트 자동 번호 생성
		    WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();
		    PeopleData pd = new PeopleData(sessionUser);

		    String projectNo = "";
		    String tempDate = DateUtil.getDateString(new Date(), "all");
		    String checkprojectNo = "";

		    if (projectTypeName.equals("자동차") || projectTypeName.equals("자동차사업부")) {
    			if ("전장모듈".equals(pd.departmentName.substring(0, 4))) {
    			    checkprojectNo = "M" + tempDate.substring(2, 4) + "A";
    			} else {
    			    checkprojectNo = "A" + tempDate.substring(2, 4) + "A";
    			}
		    } else {
			    checkprojectNo = "E" + tempDate.substring(2, 4) + "A";
		    }
		    projectNo = ManageSequence.getSeqNo(checkprojectNo, "000", "E3PSProjectMaster", E3PSProjectMaster.PJT_NO);
		    DecimalFormat decimalformat = new DecimalFormat("000");

		    // 50 추가 ???
		    // 기존에 50씩  임영근G가 1씩 올라가는 것이 맞다고 했음
		    projectNo = decimalformat.format(Long.parseLong(projectNo));
		    //projectNo = decimalformat.format(Long.parseLong(projectNo)+50);

		    projectNo = checkprojectNo + projectNo;
		    Hashtable hash = new Hashtable();

		    hash.put("projectType", "1");
		    hash.put("projectName", projectName);
		    hash.put("projectNo", projectNo);
		    hash.put("drNumber", drNumber);
		    hash.put("drKeyOid", drKeyOid);
		    hash.put("planStartDate", planStartDate);
		    hash.put("projectTypeName", projectTypeName);
		    if(developenttype != null)hash.put("developenttype", developenttype); 
		    if (salesUser.length() > 0) {
			    hash.put("salesUser", salesUser);
		    }
		    hash.put("devUser", devUser);
		    hash.put("devDeptOid", "");
		    hash.put("proposalDate", proposalDate);
		    hash.put("estimateDate", estimateDate);
		    //hash.put("producttype", producttype);
		    hash.put("productTypeLevel2", productTypeLevel2);
		    //hash.put("assembledtype", assembledtype);
		    hash.put("rank", rank);
		    hash.put("planEndDate", planEndDate);
		    hash.put("TemplateOID", templateOid);
		    hash.put("category", category);
            if(wbsType != null){
        	   hash.put("wbsType", wbsType);
            }
		    hash.put("pwlinkOid", pwlinkOid);
		    hash.put("USERMEMBER", userMemberVec);
		    hash.put("CUSTOMEREVENT", CUSTOMEREVENTOidVec);
		    hash.put("SUBCONTRACTOR", SUBCONTRACTOROidVec);
		    hash.put("lifecycle", lifecycle);
		    hash.put("nOidVec", nOidVec);

		    // 전자 개발 담당자
		    if (devUser.length() > 0) {
			    hash.put("devUser", devUser);
		    }

		    //제품정보
		    if (request.getParameterValues("rowId") != null) {
    			String rowId[] = request.getParameterValues("rowId");
    			Vector rowIdVec = new Vector();
    			if (rowId != null && rowId.length > 0) {
    			    for (int i = 0; i < rowId.length; i++) {
        				rowIdVec.add((rowId[i]));
        
        				if (request.getParameterValues("count" + rowId[i]) != null) {
        				    String count[] = request.getParameterValues("count" + rowId[i]);
        				    Vector countVec = new Vector();
        				    if (count != null && count.length > 0) {
            					for (int j = 0; j < count.length; j++) {
            					    countVec.add((count[j]));
            					}
        				    }
        				    hash.put("countVec" + rowId[i], countVec);
        				}
        
        				if (request.getParameterValues("optOid" + rowId[i]) != null) {
        				    String optOid[] = request.getParameterValues("optOid" + rowId[i]);
        				    Vector optOidVec = new Vector();
        				    if (optOid != null && optOid.length > 0) {
            					for (int j = 0; j < optOid.length; j++) {
            					    optOidVec.add((optOid[j]));
            					}
        				    }
        				    hash.put("optOidVec" + rowId[i], optOidVec);
        				}
        
        				if (request.getParameterValues("y1" + rowId[i]) != null) {
        				    String y1[] = request.getParameterValues("y1" + rowId[i]);
        				    Vector y1Vec = new Vector();
        				    if (y1 != null && y1.length > 0) {
            					for (int j = 0; j < y1.length; j++) {
            					    y1Vec.add((y1[j]));
            					}
        				    }
        				    hash.put("y1Vec" + rowId[i], y1Vec);
        				}
        
        				if (request.getParameterValues("y2" + rowId[i]) != null) {
        				    String y2[] = request.getParameterValues("y2" + rowId[i]);
        				    Vector y2Vec = new Vector();
        				    if (y2 != null && y2.length > 0) {
            					for (int j = 0; j < y2.length; j++) {
            					    y2Vec.add((y2[j]));
            					}
        				    }
        				    hash.put("y2Vec" + rowId[i], y2Vec);
        				}
        
        				if (request.getParameterValues("y3" + rowId[i]) != null) {
        				    String y3[] = request.getParameterValues("y3" + rowId[i]);
        				    Vector y3Vec = new Vector();
        				    if (y3 != null && y3.length > 0) {
            					for (int j = 0; j < y3.length; j++) {
            					    y3Vec.add((y3[j]));
            					}
        				    }
        				    hash.put("y3Vec" + rowId[i], y3Vec);
        				}
        
        				if (request.getParameterValues("y4" + rowId[i]) != null) {
        				    String y4[] = request.getParameterValues("y4" + rowId[i]);
        				    Vector y4Vec = new Vector();
        				    if (y4 != null && y4.length > 0) {
            					for (int j = 0; j < y4.length; j++) {
            					    y4Vec.add((y4[j]));
            					}
        				    }
        				    hash.put("y4Vec" + rowId[i], y4Vec);
        				}
        
        				if (request.getParameterValues("y5" + rowId[i]) != null) {
        				    String y5[] = request.getParameterValues("y5" + rowId[i]);
        				    Vector y5Vec = new Vector();
        				    if (y5 != null && y5.length > 0) {
            					for (int j = 0; j < y5.length; j++) {
            					    y5Vec.add((y5[j]));
            					}
        				    }
        				    hash.put("y5Vec" + rowId[i], y5Vec);
        				}
        
        				if (request.getParameterValues("y6" + rowId[i]) != null) {
        				    String y6[] = request.getParameterValues("y6" + rowId[i]);
        				    Vector y6Vec = new Vector();
        				    if (y6 != null && y6.length > 0) {
            					for (int j = 0; j < y6.length; j++) {
            					    y6Vec.add((y6[j]));
            					}
        				    }
        				    hash.put("y6Vec" + rowId[i], y6Vec);
        				}
        
        				if (request.getParameterValues("y7" + rowId[i]) != null) {
        				    String y7[] = request.getParameterValues("y7" + rowId[i]);
        				    Vector y7Vec = new Vector();
        				    if (y7 != null && y7.length > 0) {
            					for (int j = 0; j < y7.length; j++) {
            					    y7Vec.add((y7[j]));
            					}
        				    }
        				    hash.put("y7Vec" + rowId[i], y7Vec);
        				}
        
        				if (request.getParameterValues("y8" + rowId[i]) != null) {
        				    String y8[] = request.getParameterValues("y8" + rowId[i]);
        				    Vector y8Vec = new Vector();
        				    if (y8 != null && y8.length > 0) {
            					for (int j = 0; j < y8.length; j++) {
            					    y8Vec.add((y8[j]));
            					}
        				    }
        				    hash.put("y8Vec" + rowId[i], y8Vec);
        				}
        
        				if (request.getParameterValues("y9" + rowId[i]) != null) {
        				    String y9[] = request.getParameterValues("y9" + rowId[i]);
        				    Vector y9Vec = new Vector();
        				    if (y9 != null && y9.length > 0) {
            					for (int j = 0; j < y9.length; j++) {
            					    y9Vec.add((y9[j]));
            					}
        				    }
        				    hash.put("y9Vec" + rowId[i], y9Vec);
        				}
        
        				if (request.getParameterValues("y10" + rowId[i]) != null) {
        				    String y10[] = request.getParameterValues("y10" + rowId[i]);
        				    Vector y10Vec = new Vector();
        				    if (y10 != null && y10.length > 0) {
            					for (int j = 0; j < y10.length; j++) {
            					    y10Vec.add((y10[j]));
            					}
        				    }
        				    hash.put("y10Vec" + rowId[i], y10Vec);
        				}
        
        				if (request.getParameterValues("usage" + rowId[i]) != null) {
        				    String usage[] = request.getParameterValues("usage" + rowId[i]);
        				    Vector usageVec = new Vector();
        				    if (usage != null && usage.length > 0) {
            					for (int j = 0; j < usage.length; j++) {
            					    //
            					    usageVec.add((usage[j]));
            					}
        				    }
        				    hash.put("usageVec" + rowId[i], usageVec);
        				}
        
        				if (request.getParameterValues("optionRate" + rowId[i]) != null) {
        				    String optionRate[] = request.getParameterValues("optionRate" + rowId[i]);
        				    Vector optionRateVec = new Vector();
        				    if (optionRate != null && optionRate.length > 0) {
            					for (int j = 0; j < optionRate.length; j++) {
            					    optionRateVec.add((optionRate[j]));
            					}
        				    }
        				    //hash.put("optionRateVec" + rowId[i], optionRateVec);
        				}
        
        				if (request.getParameter("pOid" + rowId[i]) != null) {
        				    String pOid = request.getParameter("pOid" + rowId[i]);
        				    hash.put("pOidVec" + rowId[i], pOid);
        				}
    			    }
    			}
    			hash.put("rowIdVec", rowIdVec);
    		}

		    if (request.getParameterValues("pNum") != null) {
    			String pNum[] = request.getParameterValues("pNum");
    			Vector pNumVec = new Vector();
    			if (pNum != null && pNum.length > 0) {
    			    for (int i = 0; i < pNum.length; i++) {
    				    pNumVec.add(pNum[i]);
    			    }
    			}
    			hash.put("pNumVec", pNumVec);
		    }

		    if (request.getParameterValues("pName") != null) {
    			String pName[] = request.getParameterValues("pName");
    			Vector pNameVec = new Vector();
    			if (pName != null && pName.length > 0) {
    			    for (int i = 0; i < pName.length; i++) {
    				    pNameVec.add(pName[i]);
    			    }
    			}
    			hash.put("pNameVec", pNameVec);
		    }

		    if (request.getParameterValues("areas") != null) {
    			String areas[] = request.getParameterValues("areas");
    			Vector areasVec = new Vector();
    			if (areas != null && areas.length > 0) {
    			    for (int i = 0; i < areas.length; i++) {
    				    areasVec.add(areas[i]);
    			    }
    			}
    			//hash.put("areasVec", areasVec);
		    }

		    if (request.getParameterValues("carName") != null) {
    			String carName[] = request.getParameterValues("carName");
    			Vector carNameVec = new Vector();
    			if (carName != null && carName.length > 0) {
    			    for (int i = 0; i < carName.length; i++) {
    				    carNameVec.add(carName[i]);
    			    }
    			}
    			hash.put("carNameVec", carNameVec);
		    }
            
		    if (request.getParameterValues("usageT") != null) {
                String usageT[] = request.getParameterValues("usageT");
                Vector usageTVec = new Vector();
                if (usageT != null && usageT.length > 0) {
                    for (int i = 0; i < usageT.length; i++) {
                        usageTVec.add((usageT[i]));
                    }
                }
                hash.put("usageTVec", usageTVec);
            }
            
            //조립처 사내/외주 구분
            if (request.getParameterValues("assemblyPlaceType") != null) {
                String assemblyPlaceType[] = request.getParameterValues("assemblyPlaceType");
                Vector assemblyPlaceTypeVec = new Vector();
                if (assemblyPlaceType != null && assemblyPlaceType.length > 0) {
                    for (int i = 0; i < assemblyPlaceType.length; i++) {
                	   assemblyPlaceTypeVec.add((assemblyPlaceType[i]));
                    }
                }
                hash.put("assemblyPlaceTypeVec", assemblyPlaceTypeVec);
            }
            
            //조립처
		    if (request.getParameterValues("assemblyPlace") != null) {
    			String assemblyPlace[] = request.getParameterValues("assemblyPlace");
    			Vector assemblyPlaceVec = new Vector();
    			if (assemblyPlace != null && assemblyPlace.length > 0) {
    			    for (int i = 0; i < assemblyPlace.length; i++) {
    			        assemblyPlaceVec.add((assemblyPlace[i]));
    			    }
    			}
    			hash.put("assemblyPlaceVec", assemblyPlaceVec);
		    }
            
		    //조립구분
            if (request.getParameterValues("assembledType") != null) {
                String assembledType[] = request.getParameterValues("assembledType");
                Vector assembledTypeVec = new Vector();
                if (assembledType != null && assembledType.length > 0) {
                    for (int i = 0; i < assembledType.length; i++) {
                	   assembledTypeVec.add((assembledType[i]));
                    }
                }
                hash.put("assembledTypeVec", assembledTypeVec);
            }

		    if (request.getParameterValues("price") != null) {
    			String price[] = request.getParameterValues("price");
    			Vector priceVec = new Vector();
    			if (price != null && price.length > 0) {
    			    for (int i = 0; i < price.length; i++) {
    				    priceVec.add((price[i]));
    			    }
    			}
    			//hash.put("priceVec", priceVec);
		    }

		    if (request.getParameterValues("cost") != null) {
    			String cost[] = request.getParameterValues("cost");
    			Vector costVec = new Vector();
    			if (cost != null && cost.length > 0) {
    			    for (int i = 0; i < cost.length; i++) {
    				    costVec.add((cost[i]));
    			    }
    			}
    			//hash.put("costVec", costVec);
		    }

		    if (request.getParameterValues("rate") != null) {
    			String rate[] = request.getParameterValues("rate");
    			Vector rateVec = new Vector();
    			if (rate != null && rate.length > 0) {
    			    for (int i = 0; i < rate.length; i++) {
    				    rateVec.add((rate[i]));
    			    }
    			}
    			//hash.put("rateVec", rateVec);
		    }

		    if (request.getParameterValues("y1T") != null) {
    			String y1T[] = request.getParameterValues("y1T");
    			Vector y1TVec = new Vector();
    			if (y1T != null && y1T.length > 0) {
    			    for (int i = 0; i < y1T.length; i++) {
    				    y1TVec.add((y1T[i]));
    			    }
    			}
    			hash.put("y1TVec", y1TVec);
		    }

		    if (request.getParameterValues("y2T") != null) {
    			String y2T[] = request.getParameterValues("y2T");
    			Vector y2TVec = new Vector();
    			if (y2T != null && y2T.length > 0) {
    			    for (int i = 0; i < y2T.length; i++) {
    				    y2TVec.add((y2T[i]));
    			    }
    			}
    			hash.put("y2TVec", y2TVec);
		    }

		    if (request.getParameterValues("y3T") != null) {
    			String y3T[] = request.getParameterValues("y3T");
    			Vector y3TVec = new Vector();
    			if (y3T != null && y3T.length > 0) {
    			    for (int i = 0; i < y3T.length; i++) {
    				    y3TVec.add((y3T[i]));
    			    }
    			}
    			hash.put("y3TVec", y3TVec);
		    }

		    if (request.getParameterValues("y4T") != null) {
    			String y4T[] = request.getParameterValues("y4T");
    			Vector y4TVec = new Vector();
    			if (y4T != null && y4T.length > 0) {
    			    for (int i = 0; i < y4T.length; i++) {
    				    y4TVec.add((y4T[i]));
    			    }
    			}
    			hash.put("y4TVec", y4TVec);
		    }

		    if (request.getParameterValues("y5T") != null) {
    			String y5T[] = request.getParameterValues("y5T");
    			Vector y5TVec = new Vector();
    			if (y5T != null && y5T.length > 0) {
    			    for (int i = 0; i < y5T.length; i++) {
    				    y5TVec.add((y5T[i]));
    			    }
    			}
    			hash.put("y5TVec", y5TVec);
		    }

		    if (request.getParameterValues("y6T") != null) {
    			String y6T[] = request.getParameterValues("y6T");
    			Vector y6TVec = new Vector();
    			if (y6T != null && y6T.length > 0) {
    			    for (int i = 0; i < y6T.length; i++) {
    				    y6TVec.add((y6T[i]));
    			    }
    			}
    			hash.put("y6TVec", y6TVec);
		    }

		    if (request.getParameterValues("y7T") != null) {
    			String y7T[] = request.getParameterValues("y7T");
    			Vector y7TVec = new Vector();
    			if (y7T != null && y7T.length > 0) {
    			    for (int i = 0; i < y7T.length; i++) {
    				    y7TVec.add((y7T[i]));
    			    }
    			}
    			hash.put("y7TVec", y7TVec);
		    }

		    if (request.getParameterValues("y8T") != null) {
    			String y8T[] = request.getParameterValues("y8T");
    			Vector y8TVec = new Vector();
    			if (y8T != null && y8T.length > 0) {
    			    for (int i = 0; i < y8T.length; i++) {
    				    y8TVec.add((y8T[i]));
    			    }
    			}
    			hash.put("y8TVec", y8TVec);
		    }

		    if (request.getParameterValues("y9T") != null) {
    			String y9T[] = request.getParameterValues("y9T");
    			Vector y9TVec = new Vector();
    			if (y9T != null && y9T.length > 0) {
    			    for (int i = 0; i < y9T.length; i++) {
    				    y9TVec.add((y9T[i]));
    			    }
    			}
    			hash.put("y9TVec", y9TVec);
		    }

		    if (request.getParameterValues("y10T") != null) {
    			String y10T[] = request.getParameterValues("y10T");
    			Vector y10TVec = new Vector();
    			if (y10T != null && y10T.length > 0) {
    			    for (int i = 0; i < y10T.length; i++) {
    				    y10TVec.add((y10T[i]));
    			    }
    			}
    			hash.put("y10TVec", y10TVec);
		    }

		    if (request.getParameter("deletePOid") != null) {
    			String deletePOid = request.getParameter("deletePOid");
    			hash.put("deletePOid", deletePOid);
		    }
		    
		    if(developePurpose1 != null)hash.put("developePurpose1", developePurpose1);
		    if(developePurpose2 != null)hash.put("developePurpose2", developePurpose2);

		    Role role = null;
		    String roleUser = null;

		    TeamTemplate tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Review Project");
		    Vector vecTeamStd = tempTeam.getRoles();
		    for (int i = 0; i < vecTeamStd.size(); i++) {
    			role = (Role) vecTeamStd.get(i);
    			roleUser = request.getParameter(role.toString());
    			if (roleUser == null) {
    			    roleUser = "";
    			}
    			hash.put(role.toString(), roleUser);
		    }

		    E3PSProject project = E3PSProjectHelper.service.createE3PSProject(hash);

		    if (project != null) {
    			msg = messageService.getString("e3ps.message.ket_message", "01315")/*등록 되었습니다*/;
    			isCreate = true;
    			oid = CommonUtil.getOIDString(project);
		    } else {
			 msg = messageService.getString("e3ps.message.ket_message", "01317")/*등록 실패*/;
		    }
		} else if (command.equals("update")) {
		    String cmd = request.getParameter("cmd");
		    if(oid == null){ oid = ""; }
		    if(cmd == null){ cmd = ""; }
		    int pjtType = 1;

		    E3PSProject project = null;
		    E3PSProjectData projectData = null;
		    ReviewProject rproject = null;
		    if(oid != null){
		        rproject =(ReviewProject)CommonUtil.getObject(oid);
		        project = (E3PSProject)CommonUtil.getObject(oid);
		        projectData = new E3PSProjectData(project);
		        if(rproject.getAttr1().equals("자동차")){
		            pjtType = 1;
		        }else{
		            pjtType = 0;
		        }
		    }
		    String projectTypeValue = "";
		    if(rproject.getAttr1().equals("자동차사업부")){
		        projectTypeValue = "자동차";
		    }else if(rproject.getAttr1().equals("자동차")){
		        projectTypeValue = "자동차";
		    }
		    if(rproject.getAttr1().equals("전자사업부")){
		        projectTypeValue = "전자";
		    }else if(rproject.getAttr1().equals("전자")){
		        projectTypeValue = "전자";
		    }
            
		    String drNumber = request.getParameter("drNumber");
	        String drKeyOid = request.getParameter("drKeyOid");
	        //String receiptNumber = request.getParameter("receiptNumber");
	        String projectName = request.getParameter("projectName");
	        //String projectTypeName = request.getParameter("projectTypeName");
	        String salesUser = StringUtil.checkNull( request.getParameter("salesUser") );
	        String devUser = request.getParameter("devUser");
	        String devDeptOid = request.getParameter("devDeptOid");
	        String proposalDate = request.getParameter("proposalDate");
	        String estimateDate = request.getParameter("estimateDate");

	        //기준 정보
	        String developenttype = request.getParameter("developenttype");
	        String rank = request.getParameter("rank");
	        String producttype = request.getParameter("producttype");
	        String productTypeLevel2 = request.getParameter("productTypeLevel2");
	        //Kogger.debug("producttype======>"+producttype);
	        String assembledtype = request.getParameter("assembledtype");

	        String userMemberArr[] = request.getParameterValues("userMember");
	        Vector userMemberVec = new Vector();
	        if(userMemberArr != null && userMemberArr.length > 0) {
	            for(int i = 0; i < userMemberArr.length; i++) {
	                userMemberVec.add(userMemberArr[i]);
	            }
	        }

	        String CUSTOMEREVENTOid[] = request.getParameterValues("CUSTOMEREVENTOid");
	        Vector CUSTOMEREVENTOidVec = new Vector();
	        if(CUSTOMEREVENTOid != null && CUSTOMEREVENTOid.length > 0) {
	            for(int i = 0; i < CUSTOMEREVENTOid.length; i++) {

	                CUSTOMEREVENTOidVec.add(CUSTOMEREVENTOid[i]);
	            }
	        }

	        // 고객처
	        String SUBCONTRACTOROid[] = request.getParameterValues("sOid");
	        Vector SUBCONTRACTOROidVec = new Vector();
	        if(SUBCONTRACTOROid != null && SUBCONTRACTOROid.length > 0) {
	            for(int i = 0; i < SUBCONTRACTOROid.length; i++) {

	                SUBCONTRACTOROidVec.add(SUBCONTRACTOROid[i]);
	            }
	        }
	        // 납입처 Link
	        String nOid[] = request.getParameterValues("nOid");
	        Vector nOidVec = new Vector();
	        if(nOid != null && nOid.length > 0) {
	            for(int i = 0; i < nOid.length; i++) {

	                nOidVec.add(nOid[i]);
	            }
	        }


	        Hashtable hash = new Hashtable();
	        hash.put("oid", oid);
	        hash.put("projectType", "1");
	        hash.put("projectName", projectName);
	        //hash.put("receiptNumber", receiptNumber);
	        //hash.put("projectTypeName", projectTypeName);
	        if(salesUser.length() > 0){
	            hash.put("salesUser", salesUser);
	        }
	        hash.put("devUser", devUser);
	        hash.put("devDeptOid", "");
	        hash.put("proposalDate", proposalDate);
	        hash.put("estimateDate", estimateDate);

	        hash.put("developenttype", developenttype);
	        //hash.put("producttype", producttype);
	        hash.put("productTypeLevel2", productTypeLevel2);
	        //hash.put("assembledtype", assembledtype);
	        hash.put("rank", rank);

	        hash.put("CUSTOMEREVENT", CUSTOMEREVENTOidVec);
	        hash.put("SUBCONTRACTOR", SUBCONTRACTOROidVec);
	        hash.put("nOidVec", nOidVec);
	        
	        hash.put("drNumber", drNumber);
	        hash.put("drKeyOid", drKeyOid);
	        String reviewResult = StringUtil.checkNull(request.getParameter("reviewResult"));
	        
	        hash.put("reviewResult", reviewResult);

            project = E3PSProjectHelper.service.updateE3PSProject(hash);

            if(project != null && cmd.equals("update")){
        	   //oid = request.getParameter("oid");
            }
                    
		} else if (command.equals("ProductInfoUpdate")) {
		    Hashtable hash = new Hashtable();
		    hash.put("oid", oid);
	        hash.put("projectType", "1");
	      //제품 정보 수정 flag
	        hash.put("prodModifyFlag","Y");
		  //제품 정보
	        if(request.getParameterValues("rowId") !=null){
	            String rowId[] = request.getParameterValues("rowId");
	            Vector rowIdVec = new Vector();
	            if(rowId != null && rowId.length > 0) {
	                for(int i = 0; i < rowId.length; i++) {
	                    rowIdVec.add((rowId[i]));

	                    if(request.getParameterValues("count"+rowId[i]) !=null){
	                        String count[] = request.getParameterValues("count"+rowId[i]);
	                        Vector countVec = new Vector();
	                        if(count != null && count.length > 0) {
	                            for(int j = 0; j < count.length; j++) {
	                                countVec.add((count[j]));
	                            }
	                        }
	                        hash.put("countVec"+rowId[i], countVec);
	                    }

	                    if(request.getParameterValues("optOid"+rowId[i]) !=null){
	                        String optOid[] = request.getParameterValues("optOid"+rowId[i]);
	                        Vector optOidVec = new Vector();
	                        if(optOid != null && optOid.length > 0) {
	                            for(int j = 0; j < optOid.length ; j++) {
	                                optOidVec.add((optOid[j]));
	                            }
	                        }
	                        hash.put("optOidVec"+rowId[i], optOidVec);
	                    }

	                    if(request.getParameterValues("y1"+rowId[i]) !=null){
	                        String y1[] = request.getParameterValues("y1"+rowId[i]);
	                        Vector y1Vec = new Vector();
	                        if(y1 != null && y1.length > 0) {
	                            for(int j = 0; j < y1.length; j++) {
	                                y1Vec.add((y1[j]));
	                            }
	                        }
	                        hash.put("y1Vec"+rowId[i], y1Vec);
	                    }

	                    if(request.getParameterValues("y2"+rowId[i]) !=null){
	                        String y2[] = request.getParameterValues("y2"+rowId[i]);
	                        Vector y2Vec = new Vector();
	                        if(y2 != null && y2.length > 0) {
	                            for(int j = 0; j < y2.length; j++) {
	                                y2Vec.add((y2[j]));
	                            }
    	                    }
    	                    hash.put("y2Vec"+rowId[i], y2Vec);
    	                }

    	                if(request.getParameterValues("y3"+rowId[i]) !=null){
    	                    String y3[] = request.getParameterValues("y3"+rowId[i]);
    	                    Vector y3Vec = new Vector();
    	                    if(y3 != null && y3.length > 0) {
    	                        for(int j = 0; j < y3.length; j++) {
    	                            y3Vec.add((y3[j]));
    	                        }
    	                    }
    	                    hash.put("y3Vec"+rowId[i], y3Vec);
    	                }
    
    	                if(request.getParameterValues("y4"+rowId[i]) !=null){
    	                    String y4[] = request.getParameterValues("y4"+rowId[i]);
    	                    Vector y4Vec = new Vector();
    	                    if(y4 != null && y4.length > 0) {
    	                        for(int j = 0; j < y4.length; j++) {
    	                            y4Vec.add((y4[j]));
    	                        }
    	                    }
    	                    hash.put("y4Vec"+rowId[i], y4Vec);
    	                }
    
    	                if(request.getParameterValues("y5"+rowId[i]) !=null){
    	                    String y5[] = request.getParameterValues("y5"+rowId[i]);
    	                    Vector y5Vec = new Vector();
    	                    if(y5 != null && y5.length > 0) {
    	                        for(int j = 0; j < y5.length; j++) {
    	                            y5Vec.add((y5[j]));
    	                        }
    	                    }
    	                    hash.put("y5Vec"+rowId[i], y5Vec);
    	                }
    
    	                if(request.getParameterValues("y6"+rowId[i]) !=null){
    	                    String y6[] = request.getParameterValues("y6"+rowId[i]);
    	                    Vector y6Vec = new Vector();
    	                    if(y6 != null && y6.length > 0) {
    	                        for(int j = 0; j < y6.length; j++) {
    	                            y6Vec.add((y6[j]));
    	                        }
    	                    }
    	                    hash.put("y6Vec"+rowId[i], y6Vec);
    	                }
    
    	                if(request.getParameterValues("y7"+rowId[i]) !=null){
    	                    String y7[] = request.getParameterValues("y7"+rowId[i]);
    	                    Vector y7Vec = new Vector();
    	                    if(y7 != null && y7.length > 0) {
    	                        for(int j = 0; j < y7.length; j++) {
    	                            y7Vec.add((y7[j]));
    	                        }
    	                    }
    	                    hash.put("y7Vec"+rowId[i], y7Vec);
    	                }
    
    	                if(request.getParameterValues("y8"+rowId[i]) !=null){
    	                    String y8[] = request.getParameterValues("y8"+rowId[i]);
    	                    Vector y8Vec = new Vector();
    	                    if(y8 != null && y8.length > 0) {
    	                        for(int j = 0; j < y8.length; j++) {
    	                            y8Vec.add((y8[j]));
    	                        }
    	                    }
    	                    hash.put("y8Vec"+rowId[i], y8Vec);
    	                }
    
    	                if(request.getParameterValues("y9"+rowId[i]) !=null){
    	                    String y9[] = request.getParameterValues("y9"+rowId[i]);
    	                    Vector y9Vec = new Vector();
    	                    if(y9 != null && y9.length > 0) {
    	                        for(int j = 0; j < y9.length; j++) {
    	                            y9Vec.add((y9[j]));
    	                        }
    	                    }
    	                    hash.put("y9Vec"+rowId[i], y9Vec);
    	                }
    
    	                if(request.getParameterValues("y10"+rowId[i]) !=null){
    	                    String y10[] = request.getParameterValues("y10"+rowId[i]);
    	                    Vector y10Vec = new Vector();
    	                    if(y10 != null && y10.length > 0) {
    	                        for(int j = 0; j < y10.length; j++) {
    	                            y10Vec.add((y10[j]));
    	                        }
    	                    }
    	                    hash.put("y10Vec"+rowId[i], y10Vec);
    	                }
    
    	                if(request.getParameterValues("usage"+rowId[i]) !=null){
    	                    String usage[] = request.getParameterValues("usage"+rowId[i]);
    	                    Vector usageVec = new Vector();
    	                    if(usage != null && usage.length > 0) {
    	                        for(int j = 0; j < usage.length; j++) {
    	//
    	                            usageVec.add((usage[j]));
    	                        }
    	                    }
    	                    hash.put("usageVec"+rowId[i], usageVec);
    	                }
                        
    	                //조립처 사내/외주 구분
    	                if (request.getParameterValues("assemblyPlaceType") != null) {
    	                    String assemblyPlaceType[] = request.getParameterValues("assemblyPlaceType");
    	                    Vector assemblyPlaceTypeVec = new Vector();
    	                    if (assemblyPlaceType != null && assemblyPlaceType.length > 0) {
    	                        for (int j = 0; j < assemblyPlaceType.length; j++) {
    	                           assemblyPlaceTypeVec.add((assemblyPlaceType[j]));
    	                        }
    	                    }
    	                    hash.put("assemblyPlaceTypeVec", assemblyPlaceTypeVec);
    	                }
    	                
    	                //조립처
    	                if (request.getParameterValues("assemblyPlace") != null) {
    	                    String assemblyPlace[] = request.getParameterValues("assemblyPlace");
    	                    Vector assemblyPlaceVec = new Vector();
    	                    if (assemblyPlace != null && assemblyPlace.length > 0) {
    	                        for (int j = 0; j < assemblyPlace.length; j++) {
    	                            assemblyPlaceVec.add((assemblyPlace[j]));
    	                        }
    	                    }
    	                    hash.put("assemblyPlaceVec", assemblyPlaceVec);
    	                }
    	                
    	                //조립구분
    	                if (request.getParameterValues("assembledType") != null) {
    	                    String assembledType[] = request.getParameterValues("assembledType");
    	                    Vector assembledTypeVec = new Vector();
    	                    if (assembledType != null && assembledType.length > 0) {
    	                        for (int j = 0; j < assembledType.length; j++) {
    	                           assembledTypeVec.add((assembledType[j]));
    	                        }
    	                    }
    	                    hash.put("assembledTypeVec", assembledTypeVec);
    	                }
    
    	                if(request.getParameterValues("optionRate"+rowId[i]) !=null){
    	                    String optionRate[] = request.getParameterValues("optionRate"+rowId[i]);
    	                    Vector optionRateVec = new Vector();
    	                    if(optionRate != null && optionRate.length > 0) {
    	                        for(int j = 0; j < optionRate.length; j++) {
    	                            optionRateVec.add((optionRate[j]));
    	                        }
    	                    }
    	                    //hash.put("optionRateVec"+rowId[i], optionRateVec);
    	                }
    
                        if(request.getParameter("pOid"+rowId[i]) !=null){
                            String pOid = request.getParameter("pOid"+rowId[i]);
                            hash.put("pOidVec"+rowId[i], pOid);
                        }
	                }
	            }
	            hash.put("rowIdVec", rowIdVec);
	        }

	        if(request.getParameterValues("pNum") !=null){
	            String pNum[] = request.getParameterValues("pNum");
	            Vector pNumVec = new Vector();
	            if(pNum != null && pNum.length > 0) {
	                for(int i = 0; i < pNum.length; i++) {
	                    pNumVec.add(pNum[i]);
	                }
	            }
	            hash.put("pNumVec", pNumVec);
	        }

	        if(request.getParameterValues("pName") !=null){
	            String pName[] = request.getParameterValues("pName");
	            Vector pNameVec = new Vector();
	            if(pName != null && pName.length > 0) {
	                for(int i = 0; i < pName.length; i++) {
	                    pNameVec.add(pName[i]);
	                }
	            }
	            hash.put("pNameVec", pNameVec);
	        }

	        if(request.getParameterValues("areas") !=null){
	            String areas[] = request.getParameterValues("areas");
	            Vector areasVec = new Vector();
	            if(areas != null && areas.length > 0) {
	                for(int i = 0; i < areas.length; i++) {
	                    areasVec.add(areas[i]);
	                }
	            }
	            hash.put("areasVec", areasVec);
	        }

	        if(request.getParameterValues("carName") !=null){
	            String carName[] = request.getParameterValues("carName");
	            Vector carNameVec = new Vector();
	            if(carName != null && carName.length > 0) {
	                for(int i = 0; i < carName.length; i++) {
	                    carNameVec.add(carName[i]);
	                }
	            }
	            hash.put("carNameVec", carNameVec);
	        }

	        if(request.getParameterValues("usageT") !=null){
	            String usageT[] = request.getParameterValues("usageT");
	            Vector usageTVec = new Vector();
	            if(usageT != null && usageT.length > 0) {
	                for(int i = 0; i < usageT.length; i++) {
	                    usageTVec.add((usageT[i]));
	                }
	            }
	            hash.put("usageTVec", usageTVec);
	        }

	        if(request.getParameterValues("price") !=null){
	            String price[] = request.getParameterValues("price");
	            Vector priceVec = new Vector();
	            if(price != null && price.length > 0) {
	                for(int i = 0; i < price.length; i++) {
	                    priceVec.add((price[i]));
	                }
	            }
	            hash.put("priceVec", priceVec);
	        }

	        if(request.getParameterValues("cost") !=null){
	            String cost[] = request.getParameterValues("cost");
	            Vector costVec = new Vector();
	            if(cost != null && cost.length > 0) {
	                for(int i = 0; i < cost.length; i++) {
	                    costVec.add((cost[i]));
	                }
	            }
	            hash.put("costVec", costVec);
	        }

	        if(request.getParameterValues("rate") !=null){
	            String rate[] = request.getParameterValues("rate");
	            Vector rateVec = new Vector();
	            if(rate != null && rate.length > 0) {
	                for(int i = 0; i < rate.length; i++) {
	                    rateVec.add((rate[i]));
	                }
	            }
	            hash.put("rateVec", rateVec);
	        }

	        if(request.getParameterValues("y1T") !=null){
	            String y1T[] = request.getParameterValues("y1T");
	            Vector y1TVec = new Vector();
	            if(y1T != null && y1T.length > 0) {
	                for(int i = 0; i < y1T.length; i++) {
	                    y1TVec.add((y1T[i]));
	                }
	            }
	            hash.put("y1TVec", y1TVec);
	        }

	        if(request.getParameterValues("y2T") !=null){
	            String y2T[] = request.getParameterValues("y2T");
	            Vector y2TVec = new Vector();
	            if(y2T != null && y2T.length > 0) {
	                for(int i = 0; i < y2T.length; i++) {
	                    y2TVec.add((y2T[i]));
	                }
	            }
	            hash.put("y2TVec", y2TVec);
	        }

	        if(request.getParameterValues("y3T") !=null){
	            String y3T[] = request.getParameterValues("y3T");
	            Vector y3TVec = new Vector();
	            if(y3T != null && y3T.length > 0) {
	                for(int i = 0; i < y3T.length; i++) {
	                    y3TVec.add((y3T[i]));
	                }
	            }
	            hash.put("y3TVec", y3TVec);
	        }

	        if(request.getParameterValues("y4T") !=null){
	            String y4T[] = request.getParameterValues("y4T");
	            Vector y4TVec = new Vector();
	            if(y4T != null && y4T.length > 0) {
	                for(int i = 0; i < y4T.length; i++) {
	                    y4TVec.add((y4T[i]));
	                }
	            }
	            hash.put("y4TVec", y4TVec);
	        }

	        if(request.getParameterValues("y5T") !=null){
	            String y5T[] = request.getParameterValues("y5T");
	            Vector y5TVec = new Vector();
	            if(y5T != null && y5T.length > 0) {
	                for(int i = 0; i < y5T.length; i++) {
	                    y5TVec.add((y5T[i]));
	                }
	            }
	            hash.put("y5TVec", y5TVec);
	        }

	        if(request.getParameterValues("y6T") !=null){
	            String y6T[] = request.getParameterValues("y6T");
	            Vector y6TVec = new Vector();
	            if(y6T != null && y6T.length > 0) {
	                for(int i = 0; i < y6T.length; i++) {
	                    y6TVec.add((y6T[i]));
	                }
	            }
	            hash.put("y6TVec", y6TVec);
	        }

	        if(request.getParameterValues("y7T") !=null){
	            String y7T[] = request.getParameterValues("y7T");
	            Vector y7TVec = new Vector();
	            if(y7T != null && y7T.length > 0) {
	                for(int i = 0; i < y7T.length; i++) {
	                    y7TVec.add((y7T[i]));
	                }
	            }
	            hash.put("y7TVec", y7TVec);
	        }

	        if(request.getParameterValues("y8T") !=null){
	            String y8T[] = request.getParameterValues("y8T");
	            Vector y8TVec = new Vector();
	            if(y8T != null && y8T.length > 0) {
	                for(int i = 0; i < y8T.length; i++) {
	                    y8TVec.add((y8T[i]));
	                }
	            }
	            hash.put("y8TVec", y8TVec);
	        }

	        if(request.getParameterValues("y9T") !=null){
	            String y9T[] = request.getParameterValues("y9T");
	            Vector y9TVec = new Vector();
	            if(y9T != null && y9T.length > 0) {
	                for(int i = 0; i < y9T.length; i++) {
	                    y9TVec.add((y9T[i]));
	                }
	            }
	            hash.put("y9TVec", y9TVec);
	        }

	        if(request.getParameterValues("y10T") !=null){
	            String y10T[] = request.getParameterValues("y10T");
	            Vector y10TVec = new Vector();
	            if(y10T != null && y10T.length > 0) {
	                for(int i = 0; i < y10T.length; i++) {
	                    y10TVec.add((y10T[i]));
	                }
	            }
	            hash.put("y10TVec", y10TVec);
	        }

	        if(request.getParameter("deletePOid") !=null){
	            String deletePOid = request.getParameter("deletePOid");
	            hash.put("deletePOid", deletePOid);
	        }
            
	        E3PSProjectHelper.service.updateE3PSProject(hash);
		}
%>
<%=oid%>
<%
}catch(Exception ex){
    throw ex;
}
%>
