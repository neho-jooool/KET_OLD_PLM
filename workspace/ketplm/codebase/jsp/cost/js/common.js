function bluring(){
	if(event.srcElement.tagName=="A"||event.srcElement.tagName=="IMG")document.body.focus();
}
document.onfocusin=bluring

function deptCodeChangePC(deptCode){
    if(deptCode='11721'){deptCode = 1;   
    }else if(deptCode='11722'){deptCode = 11;
    }else if(deptCode='11723'){deptCode = 12;
    }else if(deptCode='11681'){deptCode = 13;
    }else if(deptCode='11724'){deptCode = 22;
    }else if(deptCode='11725'){deptCode = 23;
    }else if(deptCode='11726'){deptCode = 24;
    }else if(deptCode='11737'){deptCode = 3;
    }else if(deptCode='11738'){deptCode = 4;
    }else if(deptCode='11740'){deptCode = 6;
    }else if(deptCode='11741'){deptCode = 21;
    }else if(deptCode='11734'){deptCode = 7;
    }
    return deptCode;
}

function deptCodeChangeCP(deptCode){
    if(team == "1"){team = "11721";//커넥터연구개발1팀
    }else if(team == "11"){team = "11722";//커넥터연구개발2팀
    }else if(team == "12"){team = "11723";//커넥터연구개발3팀
    }else if(team == "13"){team = "11681";//커넥터연구개발센타
    }else if(team == "22"){team = "11724";//전장모듈연구개발1팀
    }else if(team == "23"){team = "11725";//전장모듈연구개발2팀
    }else if(team == "24"){team = "11726";//전장모듈연구개발3팀
    }else if(team == "3"){team = "11737";//연구개발3팀
    }else if(team == "4"){team = "11738";//연구개발4팀
    }else if(team == "6"){team = "11740";//연구개발6팀
    }else if(team == "21"){team = "11741";//시작개발1팀
    }else if(team == "7"){team = "11734";//전장부품개발팀
    }
    return deptCode;
}

