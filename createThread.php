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
	$user = $_POST['user'];
	$title = $_POST['name'];
	$desc = $_POST['description'];

	$query = "INSERT INTO Threads (ThreadName, ThreadDescription, Username) VALUES ('$title', '$desc', '$user')";
	$result = $conn->query($query) or die("error making request");

	if($result){
		echo "thread successful";
	}else{
		echo "thread failed";
	}

}

?>