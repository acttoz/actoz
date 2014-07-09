<?
header("Content-Type: text/html; charset=UTF-8");

$connect = mysql_connect("localhost", "actoz", "0453acac") or die("DB Connect Error!!");

mysql_query("SET NAMES 'utf8'");

$db = mysql_select_db("actoz", $connect) or die("DB Select Error!!");

 


if ($_REQUEST['select'] == "submit") {
	$sql = "insert into 13science  (class,ban,num,name,quiz,answer,a1,a2,a3,a4,a5,reason,flag)   values (";
	$sql = $sql . "'" . ($_REQUEST['mClass'] . "','" . $_REQUEST['ban'] . "','" . $_REQUEST['num'] . "','" . $_REQUEST['name'] . "','" . $_REQUEST['quiz'] . "','" . $_REQUEST['answer'] . "','" . $_REQUEST['a1'] . "','". $_REQUEST['a2'] . "','" . $_REQUEST['a3'] . "','" . $_REQUEST['a4']."','" . $_REQUEST['a5'] . "','". $_REQUEST['reason'] ."','". $_REQUEST['flag'] . "');");

	mysql_query($sql, $connect);
	echo $sql;
}


if ($_REQUEST['select'] == "answer_input") {
	$sql = "UPDATE 13science SET result='2' WHERE class ='" . $_REQUEST['mClass'] 
		. "' AND quiz='" . $_REQUEST['quiz']
		. "';";
	mysql_query($sql, $connect);
	$sql2 = "UPDATE 13science SET result='1' WHERE class ='" . $_REQUEST['mClass'] 
		. "' AND quiz='" . $_REQUEST['quiz']
		. "' AND a1='" . $_REQUEST['a1'] 
		. "' AND a2='" . $_REQUEST['a2'] 
		. "' AND a3='" . $_REQUEST['a3'] 
		. "' AND a4='" . $_REQUEST['a4'] 
		. "' AND a5='" . $_REQUEST['a5']  . "'";

	mysql_query($sql2, $connect);
	echo $sql;
	echo $sql2;
}

if ($_REQUEST['select'] == "quiz_input") {
	$sql = "UPDATE 13science SET memo='".$_REQUEST['memo']."' WHERE class ='" . $_REQUEST['mClass'] 
		. "' AND quiz='" . $_REQUEST['quiz']
		. "';";
	mysql_query($sql, $connect);
	 
	echo $sql;
}

if ($_REQUEST['select'] == "signin") {
	$query = mysql_query("SELECT * FROM 13science_teacher_list WHERE class ='" . $_REQUEST['mClass'] . "'");
	if (mysql_num_rows($query) == 0) {
		/*회원가입*/
		$sql = "insert into 13science_teacher_list  (class,password)   values (";
		$sql = $sql . "'" . ($_REQUEST['mClass'] . "','" . $_REQUEST['password'] . "');");
		mysql_query($sql, $connect);
		echo 'success';

	} else {
		echo 'fail';
	}

}

if ($_REQUEST['select'] == "login") {

	/* we query through our database to search for a username that has been entered */
	$query = mysql_query("SELECT * FROM 13science_teacher_list WHERE class ='" . $_REQUEST['mClass'] . "' AND password='" . $_REQUEST['password'] . "'");

	if (mysql_num_rows($query) > 0) {
		/* if there is a match with the database, we select the username and password
		 from the database corresponding to the entered username */
		echo 'success';
	} else {
		echo 'fail';
	}

}

if ($_REQUEST['select'] == "graph") {
	$cols2[]=array('type'=>'string');
	$cols2[]=array('type'=>'number');

	/* we query through our database to search for a username that has been entered */
	$result2 = mysql_query("SELECT DISTINCT quiz FROM 13science WHERE class ='" . $_REQUEST['mClass'] ."' and result<>0 ORDER BY _id ASC");
	
	

	while ($array2 = mysql_fetch_array($result2)) {
		$c2_1[] = array( $array2['quiz']);
	}
	foreach ($c2_1 as $value) {
		 $result3 = mysql_query("SELECT
		 COUNT(CASE WHEN result = '1' and quiz = '".$value[0]."' and class = '".$_REQUEST['mClass']."' THEN 1 END) as rightCount,
		 COUNT(CASE WHEN result = '2' and quiz = '".$value[0]."' and class = '".$_REQUEST['mClass']."' THEN 1 END) as wrongCount
		 FROM 13science");

		 while ($array3 = mysql_fetch_array($result3)) {
			 unset($rightCount);
			 unset($wrongCount);
			$rightCount[] = array( $array3['rightCount']);
			$wrongCount[] = array( $array3['wrongCount']);
		 }
		  
		 $c2_2[]=array(round($rightCount[0][0]/($rightCount[0][0]+$wrongCount[0][0])*100));
	}

	foreach ($c2_1 as $index => $value) {
		unset($c2);
		$c2[] = array('v' => $value[0]);
		$c2[] = array('v' => $c2_2[$index][0]);
		$c1[] = array('c' => $c2);
	}
		
    $data[] = array('cols' => $cols2,'rows' => $c1);


	$str= json_encode2($data);

	$str2=SUBSTR($str,1);
	$str3=SUBSTR($str2,0,-1);

	echo $str3;

}

//문항별 선택 인원 비율
if ($_REQUEST['select'] == "graph2") {
	$result_answer=array();
	 
	$cols2[]=array('label'=>'보기','type'=>'string');
	$cols2[]=array('label'=>'응답률','type'=>'number');
	$cols2[]=array('type'=>'string','p' => array('role' => 'style'));

	$result_right = mysql_query("SELECT a1,a2,a3,a4,a5 FROM 13science WHERE class ='" . $_REQUEST['mClass'] ."' and quiz='".$_REQUEST['quiz']."' and result='1' LIMIT 1");

	while ($array2 = mysql_fetch_array($result_right)) {
		$result_answer=array(0=>$array2['a1'],1=>$array2['a2'],2=>$array2['a3'],3=>$array2['a4'],4=>$array2['a5']);
	}
	
	

//	while ($array2 = mysql_fetch_array($result2)) {
//		$c2_1[] = array( $array2['quiz']);
//	}

	$c2_1 = array('1','2','3','4','5');

	 
		 $result3 = mysql_query("SELECT
		 COUNT(CASE WHEN a1='1' and quiz = '".$_REQUEST['quiz']."' and class = '".$_REQUEST['mClass']."' THEN 1 END) as answer1,
		 COUNT(CASE WHEN a2='1' and  quiz = '".$_REQUEST['quiz']."' and class = '".$_REQUEST['mClass']."' THEN 1 END) as answer2,
		 COUNT(CASE WHEN a3='1' and quiz = '".$_REQUEST['quiz']."' and class = '".$_REQUEST['mClass']."' THEN 1 END) as answer3,
		 COUNT(CASE WHEN a4='1' and quiz = '".$_REQUEST['quiz']."' and class = '".$_REQUEST['mClass']."' THEN 1 END) as answer4,
		 COUNT(CASE WHEN a5='1' and quiz = '".$_REQUEST['quiz']."' and class = '".$_REQUEST['mClass']."' THEN 1 END) as answer5
		 FROM 13science");

		 

		 while ($array3 = mysql_fetch_array($result3)) {
			$a1 = $array3['answer1'];

			$a2 = $array3['answer2'];

			$a3 = $array3['answer3'];

			$a4 = $array3['answer4'];

			$a5 = $array3['answer5'];


		 }
		 $sum=$a1+$a2+$a3+$a4+$a5;

		if($a1>0)
			$a1=($a1*100)/$sum;

		if($a2>0)
			$a2=($a2*100)/$sum;
	
		if($a3>0)
			$a3=($a3*100)/$sum;

		if($a4>0)
			$a4=($a4*100)/$sum;

		if($a5>0)
			$a5=($a5*100)/$sum;


		  
	 $c2_2=array($a1,$a2,$a3,$a4,$a5);

     
	 for ($i=0 ; $i<5;$i++){
		unset($c2);
		$c2[] = array('v' => $c2_1[$i]);
		$c2[] = array('v' => $c2_2[$i]);



		if($result_answer[$i]=='1'){
	    	$c2[] = array('v' => 'red'); 
			//echo 'red';
		}else{
			$c2[] = array('v' => 'blue'); 
			//echo 'blue';
		}


		$c1[] = array('c' => $c2);
     }

		
    $data[] = array('cols' => $cols2,'rows' => $c1);


	$str= json_encode2($data);

	$str2=SUBSTR($str,1);
	$str3=SUBSTR($str2,0,-1);

	echo $str3;

}

if ($_REQUEST['select'] == "list") {

	/* we query through our database to search for a username that has been entered */
	$result2 = mysql_query("SELECT DISTINCT quiz FROM 13science WHERE class ='" . $_REQUEST['mClass'] . "' ORDER BY _id DESC");

	while ($array2 = mysql_fetch_array($result2)) {
		$results[] = array('quiz' => $array2['quiz']);
	}

	$data = array('list' => $results);

	echo json_encode2($data);

}

if ($_REQUEST['select'] == "table") {

	/* we query through our database to search for a username that has been entered */
	$result2 = mysql_query("SELECT ban,num,name,answer,a1,a2,a3,a4,a5,reason,memo FROM 13science WHERE class ='" . $_REQUEST['mClass'] ."' and quiz='".$_REQUEST['quiz']. "' ORDER BY ban ASC, num ASC");

	 

	while ($array2 = mysql_fetch_array($result2)) {
		$results[] = array('ban' => $array2['ban'],'num' => $array2['num'],'name' => $array2['name'],'answer' => $array2['answer'],'a1' => $array2['a1'],'a2' => $array2['a2'],'a3' => $array2['a3'],'a4' => $array2['a4'],'a5' => $array2['a5'],'reason' => $array2['reason'],'memo' => $array2['memo']);

	}

	$data = array('list' => $results);

	echo json_encode2($data);

}
if ($_REQUEST['select'] == "deleteall") {

	/* we query through our database to search for a username that has been entered */
	mysql_query("DELETE FROM 13science WHERE class ='" . $_REQUEST['mClass'] ."' and quiz='".$_REQUEST['quiz']."'" );
}

if ($_REQUEST['select'] == "show") {
	$sql = "select *from recycler_rank order by score asc limit 100";

	$result = mysql_query($sql, $connect);

	while ($array = mysql_fetch_array($result)) {
		echo $array['school'] . "?";
		echo $array['grade'] . "?";
		echo $array['ban'] . "?";
		echo $array['name'] . "?";
		echo $array['score'] . "&";
	}
}

if ($_REQUEST['select'] == "school") {
	$sql = "select * from school_list where name like '%";
	$sql = $sql . ($_REQUEST['keyword'] . "%' ");

	$result2 = mysql_query($sql, $connect);

	while ($array2 = mysql_fetch_array($result2)) {
		$results[] = array('local' => $array2['local'], 'name' => $array2['name']);

	}
	$data = array('school_list' => $results);

	echo json_encode2($data);

}

if ($_REQUEST['select'] == "result") {
	$sql = "select * from math_test where class='";
	$sql = $sql . ($_REQUEST['class'] . "' ");

	$result2 = mysql_query($sql, $connect);

	while ($array2 = mysql_fetch_array($result2)) {
		$results[] = array('no' => $array2['no'], 'name' => $array2['name'], 'period' => $array2['period'], 'chapter' => $array2['chapter'], 'time' => $array2['time'], 'num1' => $array2['num1'], 'num2' => $array2['num2'], 'num3' => $array2['num3']);

	}

	$data = array('result' => $results);

	echo json_encode2($data);

}

if ($_REQUEST['select'] == "CreateTable") {
	$sql = "CREATE TABLE IF NOT EXISTS ";
	$sql = $sql . ($_REQUEST['name'] . " (grade int, ban int, score int, name text,_id char(10) UNIQUE)");

	mysql_query($sql, $connect);

}

if ($_REQUEST['select'] == "delete") {
	$sql = "delete from ";
	$sql = $sql . ($_REQUEST['school'] . " where _id like '%" . $_REQUEST['_id'] . "%'");
	mysql_query($sql, $connect);

}

mysql_close($connect);

function json_encode2($data) {
	switch (gettype($data)) {
		case 'boolean' :
			return $data ? 'true' : 'false';
		case 'integer' :
		case 'double' :
			return $data;
		case 'string' :
			return '"' . strtr($data, array('\\' => '\\\\', '"' => '\\"')) . '"';
		case 'array' :
			$rel = false;
			// relative array?
			$key = array_keys($data);
			foreach ($key as $v) {
				if (!is_int($v)) {
					$rel = true;
					break;
				}
			}

			$arr = array();
			foreach ($data as $k => $v) {
				$arr[] = ($rel ? '"' . strtr($k, array('\\' => '\\\\', '"' => '\\"')) . '":' : '') . json_encode2($v);
			}

			return $rel ? '{' . join(',', $arr) . '}' : '[' . join(',', $arr) . ']';
		default :
			return '""';
	}
}
?>