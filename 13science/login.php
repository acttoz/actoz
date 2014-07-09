 <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
 <?
 
             if(!$class || !$num || !$name ) //제목, 글내용, 이름 입력체크
               {   echo(" <script>
                   window.alert('이름, 제목, 내용을 입력해 주세요 !')
                   history.go(-1)
                   </script>
                   "); exit;
             }
              
         if(!$pwd) //비밀번호 입력체크
           {   echo(" <script>
              window.alert('\\n이글을 고치거나 지우기 위해서는 \\n반드시 암호가 필요합니다 ')
               history.go(-1)
               </script>
               "); exit;
         }
          
         include("../dbconn.php"); //DB연결파일 인클루드
          
         $sql="select max(num) from board" ;
         $result=mysql_query($sql,$connect ); //쿼리전송
          
         $row=mysql_fetch_array($result); //레코드를 배열로 저장
          
         //글번호 증가
        if($row[0]) {
            $number=$row[0]+1;
         }
         else{
            $number=1;
         }
          
         //=============저장 ==============================
         $writeday = time();
         $sql = "insert into board (name, email, home, title, content,
         pwd, num, writeday, readnum, ip)
         values('$name','$email','$home','$title','$content',
        '$pwd',$number,$writeday,0,'$REMOTE_ADDR')";
         $result=mysql_query($sql, $connect);
          
         if($result)
            { 
              Header("Location:list.php");  // list.php 로 이동
            }
         else  
           {
               echo("<script language=javascript>  
                       window.alert('저장중 오류');
                      history.go(-1);
               </script>");
            exit;         
           }
      ?>