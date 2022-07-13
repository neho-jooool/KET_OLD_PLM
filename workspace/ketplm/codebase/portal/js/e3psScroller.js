var sayScrollId        = 1;
var sayScrollMouseOver = new Array();
var sayScrollObjects   = new Array();

function e3psScroll(rollingHeight)/*{{{*/
{
  // parameters
  this.id             = sayScrollId++;
  this.rollingHeight  = rollingHeight;
  this.obj            = null;

  // configuration
  this.speed          = 50;
  this.delay          = 2000;
  this.ignoreMouse    = false;

  // status variables
  this.status         = false;
  this.amount         = 0;
  this.curIndex       = 0;
  this.itemCnt        = 0;
  this.itemList       = new Array();

  // methods
  this.add            = sayScroll_Add;
  this.init           = sayScroll_Init;
  this.setSpeed       = sayScroll_SetSpeed;
  this.setDelay       = sayScroll_SetDelay;
  this.setIgnoreMouse = sayScroll_SetIgnoreMouse;
  this.start          = sayScroll_Start;

  sayScrollMouseOver[this.id] = false;
  sayScrollObjects[this.id]   = this;

  this.add("&nbsp;");
}/*}}}*/

function sayScroll_Add(val) /*{{{*/
{
  this.itemList[this.itemCnt] = "<table border=0 cellspacing=0 cellpadding=0 width=100% height="+this.rollingHeight+" style='table-layout:fixed;' onmouseover='sayScrollMouseOver["+this.id+"] = true;' onmouseout='sayScrollMouseOver["+this.id+"] = false;'><tr><td nowrap>"+val+"</td></tr></table>";
  this.itemCnt++;
}/*}}}*/

function sayScroll_Init(obj) /*{{{*/
{
  this.obj = obj;
  this.start();
}/*}}}*/

function sayScroll_SetSpeed(speed) /*{{{*/
{
  this.speed = speed;
}/*}}}*/

function sayScroll_SetDelay(delay) /*{{{*/
{
  this.delay = delay;
}/*}}}*/

function sayScroll_SetIgnoreMouse() /*{{{*/
{
  this.ignoreMouse = true;
}/*}}}*/

function sayScroll_Start()/*{{{*/
{
  if (sayScrollMouseOver[this.id] && !this.ignoreMouse)
  {
    setTimeout("sayScrollObjects["+this.id+"].start();",this.speed);
    return;
  }

  if (this.status) 
  {
    this.obj.scrollTop++;
    this.amount++;

    if (this.amount+"px" == this.obj.style.height) 
    {
      this.amount = 0;
      this.curIndex++;
      if(this.curIndex >= this.itemCnt-1) 
        this.status = false;
      setTimeout("sayScrollObjects["+this.id+"].start();",this.delay);
    } 
    else 
    {
      setTimeout("sayScrollObjects["+this.id+"].start();",this.speed);
    }		
  } 
  else 
  {
    if (this.obj.innerHTML) 
    {
      this.obj.innerHTML = this.itemList[this.itemCnt-1];
      this.obj.scrollTop = 0;

      for(i=1; i<this.itemCnt; i++) 
        this.obj.innerHTML += this.itemList[i];
      this.curIndex = 0;
    } 
    else 
    {
      for(i=0;i<this.itemCnt;i++) 
        this.obj.innerHTML += this.itemList[i];
      this.curIndex = 0;
    }

    this.status = true;
    this.amount = 0;
    setTimeout("sayScrollObjects["+this.id+"].start();",this.speed);
  }
}/*}}}*/
