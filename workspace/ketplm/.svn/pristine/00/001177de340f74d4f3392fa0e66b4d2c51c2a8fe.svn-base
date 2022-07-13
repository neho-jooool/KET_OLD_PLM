function isValidCipher(type, obj)
{
	var arr_type = new Array("PROJECT", "ITEM", "CUSTOMER", "PRODUCT", "INSTALL_POSITION", "PARTCODE", "GAUGE", "PATPRODUCT", "PLANT", "PROBLEMTYPE");
//	var arr_cipher = new Array("2", "1", "1", "4", "1", "2", "2", "2", "1", "2");
	var arr_cipher = new Array("5", "5", "5", "5", "5", "5", "5", "5", "5", "5");

	for(var i=0 ; i<arr_type.length ; i++)
	{
		if(type == arr_type[i])
		{
			if( obj.value.length > arr_cipher[i] )
			{
				alert(unescape("%uCF54%uB4DC%20%uC790%uB9BF%uC218%uAC00 "+arr_cipher[i]+"%uC790%uB9AC%uB97C%20%uB118%uC5C8%uC2B5%uB2C8%uB2E4"));
				obj.focus();
				obj.value = "";
				return false;
			}
		}
	}
	return true;
}
