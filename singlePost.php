<?php

	session_start();
	
$db_username = "dbu309sg3";
$db_password = "NDNlZjIzYTE2";
$db_server = "mysql.cs.iastate.edu";
$db_name = "db309sg3";
$conn = new mysqli($db_server, $db_username, $db_password, $db_name);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}	
if($_SERVER['REQUEST_METHOD']=='POST'){
	$PostName = $_GET['selected'];
	$query = "SELECT * FROM Posts where PostName = '$PostName'";
	$result = $conn->query($query) or die("error making request");
	$data = array();
	while ($row = mysqli_fetch_assoc($result)) {
		$data[] = $row['PostName'];
		$data[] = $row['PostContent'];
	}
	//$data[] = "does this work";
	if($result){
		echo json_encode($data);
	}else{
		echo "failed";
	}

}

?>