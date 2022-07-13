	function _utf8_decode(utftext) {
        var string = '',
            i = 0,
            c = 0,
            c3 = 0,
            c2 = 0,
            len = utftext.length;

        while (i < len) {
            c = utftext.charCodeAt(i);

            if (c < 128) {
                string += String.fromCharCode(c);
                i++;
            }
            else if((c > 191) && (c < 224)) {
                c2 = utftext.charCodeAt(i+1);
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                i += 2;
            }
            else {
                c2 = utftext.charCodeAt(i+1);
                c3 = utftext.charCodeAt(i+2);
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }
        }

        return string;
    }
	
	
	function decode (input) {
		var _str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
        var output = '',
            chr1, chr2, chr3,
            enc1, enc2, enc3, enc4,
            i = 0;

        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

        var len = input.length;

        while (i < len) {

            enc1 = _str.indexOf(input.charAt(i++));
            enc2 = _str.indexOf(input.charAt(i++));
            enc3 = _str.indexOf(input.charAt(i++));
            enc4 = _str.indexOf(input.charAt(i++));

            chr1 = (enc1 << 2) | (enc2 >> 4);
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
            chr3 = ((enc3 & 3) << 6) | enc4;

            output = output + String.fromCharCode(chr1);

            if (enc3 != 64) {
                output = output + String.fromCharCode(chr2);
            }
            if (enc4 != 64) {
                output = output + String.fromCharCode(chr3);
            }

        }

        output = _utf8_decode(output);

        return output;
    }
	
	
	function fnRunConnectFile(start_con, bat_file, filePath, end_con) {
	      try{
	          var objWSH = new ActiveXObject("WScript.Shell");
	          var retval = objWSH.Run(start_con,0,false); //공유 폴더 연결
	          var fso = new ActiveXObject("Scripting.FileSystemObject");
	          fso.CreateTextFile(bat_file,true);//file create
	          var newFileObject = fso.GetFile(bat_file); //file 객체 get
	          var text = newFileObject.OpenAsTextStream(2,0);
	          text.Write("@Echo off");
	          text.WriteBlankLines(1); //개행문자 1개 삽입
	          var url = "rundll32 url,FileProtocolHandler "+filePath;
	          
	          text.Write(url);
	          text.Close();
	          
	          retval = objWSH.Run(bat_file,0,false); //bat. 실행 command창은 띄우지 않는다
	          wait(1000); //파일 삭제전 파일이 실행될수 있게 1초 기다리자
	          
	          fso.DeleteFile(bat_file); // 로컬 파일 삭제
	          retval = objWSH.Run(end_con,0,false); //공유 폴더 연결 끊기
	          
	      }catch(e){
	          if(e.message == "자동화 서버는 개체를 작성할 수 없습니다."){
	              alert("상호작용 미허용시 파일을 열 수 없습니다.");
	          }else{
	              alert(e.message);
	          }
	          //에러발생시 동작기능안함..때문에 화면을 새로고침한다. 
	          location.reload();
	      }
	  }
	  
	  function wait(msecs)
	  {
	      var start = new Date().getTime();
	      var cur = start;
	      while(cur - start < msecs)
	      {
	        cur = new Date().getTime();
	      }
	  }