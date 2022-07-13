/*
Copyright (c) 2009, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.net/yui/license.txt
version: 3.0.0
build: 1549
*/
YUI.add("widget-position-ext",function(A){var H=A.Lang,C="align",E="bindUI",B="syncUI",D="offsetWidth",I="offsetHeight",K="viewportRegion",G="region",J="alignChange";function F(L){if(!this._posNode){A.error("WidgetPosition needs to be added to the Widget, before WidgetPositionExt is added");}A.after(this._syncUIPosExtras,this,B);A.after(this._bindUIPosExtras,this,E);}F.ATTRS={align:{value:null},centered:{setter:function(L){return this._setAlignCenter(L);},lazyAdd:false,value:false}};F.TL="tl";F.TR="tr";F.BL="bl";F.BR="br";F.TC="tc";F.RC="rc";F.BC="bc";F.LC="lc";F.CC="cc";F.prototype={_syncUIPosExtras:function(){var L=this.get(C);if(L){this._uiSetAlign(L.node,L.points);}},_bindUIPosExtras:function(){this.after(J,this._afterAlignChange);},_setAlignCenter:function(L){if(L){this.set(C,{node:L===true?null:L,points:[F.CC,F.CC]});}return L;},_afterAlignChange:function(L){if(L.newVal){this._uiSetAlign(L.newVal.node,L.newVal.points);}},_uiSetAlign:function(O,N){if(!H.isArray(N)||N.length!=2){A.error("align: Invalid Points Arguments");return;}var M,L,P,Q;if(!O){M=this._posNode.get(K);}else{O=A.Node.get(O);if(O){M=O.get(G);}}if(M){M.width=M.width||M.right-M.left;M.height=M.height||M.bottom-M.top;L=N[0];P=N[1];switch(P){case F.TL:Q=[M.left,M.top];break;case F.TR:Q=[M.right,M.top];break;case F.BL:Q=[M.left,M.bottom];break;case F.BR:Q=[M.right,M.bottom];break;case F.TC:Q=[M.left+Math.floor(M.width/2),M.top];break;case F.BC:Q=[M.left+Math.floor(M.width/2),M.bottom];break;case F.LC:Q=[M.left,M.top+Math.floor(M.height/2)];break;case F.RC:Q=[M.right,M.top+Math.floor(M.height/2),L];break;case F.CC:Q=[M.left+Math.floor(M.width/2),M.top+Math.floor(M.height/2),L];break;default:break;}if(Q){this._doAlign(L,Q[0],Q[1]);}}},_doAlign:function(M,L,P){var O=this._posNode,N;switch(M){case F.TL:N=[L,P];break;case F.TR:N=[L-O.get(D),P];break;case F.BL:N=[L,P-O.get(I)];break;case F.BR:N=[L-O.get(D),P-O.get(I)];break;case F.TC:N=[L-(O.get(D)/2),P];break;case F.BC:N=[L-(O.get(D)/2),P-O.get(I)];break;case F.LC:N=[L,P-(O.get(I)/2)];break;case F.RC:N=[(L-O.get(D)),P-(O.get(I)/2)];break;case F.CC:N=[L-(O.get(D)/2),P-(O.get(I)/2)];break;default:break;}if(N){this.move(N);}},align:function(M,L){this.set(C,{node:M,points:L});},centered:function(L){this.align(L,[F.CC,F.CC]);}};A.WidgetPositionExt=F;},"3.0.0",{requires:["widget","widget-position"]});
