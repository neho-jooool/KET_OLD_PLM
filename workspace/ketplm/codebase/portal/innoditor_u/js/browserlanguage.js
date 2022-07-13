/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// browserlanguage.js
//						
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


function cm_bwcheck()
{
	this.agent = navigator.userAgent.toLowerCase();


	this.ff = (this.agent.indexOf("firefox") > -1);

	this.wk = (this.agent.indexOf("webkit") > -1);
	this.cr = false;
	if(this.wk)
	{
		this.cr = (this.agent.indexOf("chrome") > -1);
	}

	this.op = (this.agent.indexOf("opera") > -1);





	if(this.ff || this.wk || this.op){}
	else
	{
		this.ie10 = (this.agent.indexOf("msie 10") > -1);
		this.ie9 = (this.agent.indexOf("msie 9") > -1 && !this.ie10);
		this.ie8 = (this.agent.indexOf("msie 8") > -1 && !this.ie9 && !this.ie10);
		this.ie7 = (this.agent.indexOf("msie 7") > -1 && !this.ie8 && !this.ie9 && !this.ie10);
		this.ie6 = (this.agent.indexOf("msie 6") > -1 && !this.ie7 && !this.ie8 && !this.ie9 && !this.ie10);

		if(this.ie6 || this.ie7 || this.ie8 || this.ie9 || this.ie10){}
		else
		{
			if(this.agent.indexOf("mozilla/5.0") > -1)
			{
				this.ie11 = true;
			}
		}

		this.ie = (this.ie6 || this.ie7 || this.ie8 || this.ie9 || this.ie10 || this.ie11);
	}


	if(this.ie)
	{
		if(this.ie6) { this.verInfo = 6; }
		else if(this.ie7) { this.verInfo = 7; }
		else if(this.ie8) { this.verInfo = 8; }
		else if(this.ie9) { this.verInfo = 9; }
		else if(this.ie10) { this.verInfo = 10; }
		else if(this.ie11) { this.verInfo = 11; }
	}

	if(this.ff)
	{
		this.verInfo = 99;
	}

	this.bw = (this.ie || this.ff || this.wk || this.op);


	if(navigator.userLanguage)
	{
		this.language = fnCheckLanguage(navigator.userLanguage.toLowerCase());
	}
	else if (navigator.language)
	{
		this.language = fnCheckLanguage(navigator.language.toLowerCase());
	}
	else
	{
		this.language = null;
	}

	return this;
}

var g_browserCHK = new cm_bwcheck();


function fnCheckLanguage(strLanguage)
{
	var strReturnLanguage = "";
	switch(strLanguage)
	{
		case "ko":
		case "ko-kr":
			strReturnLanguage = "ko";
			break;

		case "ja":
			strReturnLanguage = "ja";
			break;

		case "zh-cn":
			strReturnLanguage = "zh-cn";
			break;

		case "en":
			strReturnLanguage = "en";
			break;

		default :
			strReturnLanguage = "en";
	}

	return strReturnLanguage;
}
