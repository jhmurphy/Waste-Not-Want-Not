<?php
session_start();

$db_username = "dbu309sg3";
$db_password = "NDNlZjIzYTE2";
$db_server = "mysql.cs.iastate.edu";
$db_name = "db309sg3";
//echo $db_username . " : " . $db_password . "</br>";
$conn = new mysqli($db_server, $db_username, $db_password, $db_name);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if($_SERVER['REQUEST_METHOD']=='POST'){
 	$username = $_POST['username'];
	$query = "SELECT * FROM Threads";
	$result = $conn->query($query) or die("error making request");
	$data = array();
	while ($row = mysqli_fetch_assoc($result)) {
		$data[] = $row['ThreadName'];
	}

	if($result){
		echo json_encode($data);
	}
	else{
		echo "Failed";
	}
 }

?>