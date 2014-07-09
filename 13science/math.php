 <?
header("Content-Type: text/html; charset=UTF-8");

$connect = mysql_connect("localhost", "actoz", "0453acac") or die("DB Connect Error!!");

mysql_query("SET NAMES 'utf8'");

$db = mysql_select_db("actoz", $connect) or die("DB Select Error!!");

if ($_REQUEST['select'] == "submit") {
    $sql = "insert into math_test  (class,no,name,period,chapter,time,num1,num2,num3)   values (";
    $sql = $sql . "'" . ($_REQUEST['class'] . "','" . $_REQUEST['no'] . "','" . $_REQUEST['name'] . "','" . $_REQUEST['period'] . "','" . $_REQUEST['chapter'] . "','" . $_REQUEST['time'] . "','" . $_REQUEST['num1'] . "','" . $_REQUEST['num2'] . "','" . $_REQUEST['num3'] . "');");
    
    mysql_query($sql, $connect);
    echo $sql;
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
        $results[] = array(
            'local' => $array2['local'],
            'name' => $array2['name']
        );
        
    }
    $data = array(
        'school_list' => $results
    );
    
    echo json_encode2($data);
    
}

if ($_REQUEST['select'] == "result") {
    $sql = "select * from math_test where class='";
    $sql = $sql . ($_REQUEST['class'] . "' ");
    
    $result2 = mysql_query($sql, $connect);
    
    while ($array2 = mysql_fetch_array($result2)) {
        $results[] = array(
            'no' => $array2['no'],
            'name' => $array2['name'],
            'period' => $array2['period'],
            'chapter' => $array2['chapter'],
            'time' => $array2['time'],
            'num1' => $array2['num1'],
            'num2' => $array2['num2'],
            'num3' => $array2['num3']
        );
        
    }
    $data = array(
        'result' => $results
    );
    
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


function json_encode2($data)
{
    switch (gettype($data)) {
        case 'boolean':
            return $data ? 'true' : 'false';
        case 'integer':
        case 'double':
            return $data;
        case 'string':
            return '"' . strtr($data, array(
                '\\' => '\\\\',
                '"' => '\\"'
            )) . '"';
        case 'array':
            $rel = false; // relative array?
            $key = array_keys($data);
            foreach ($key as $v) {
                if (!is_int($v)) {
                    $rel = true;
                    break;
                }
            }
            
            $arr = array();
            foreach ($data as $k => $v) {
                $arr[] = ($rel ? '"' . strtr($k, array(
                    '\\' => '\\\\',
                    '"' => '\\"'
                )) . '":' : '') . json_encode2($v);
            }
            
            return $rel ? '{' . join(',', $arr) . '}' : '[' . join(',', $arr) . ']';
        default:
            return '""';
    }
}
?>