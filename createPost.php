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
	$thread = $_POST['thread'];

	$query = "SELECT ThreadID FROM Threads where ThreadName = '$thread'";
	$result = $conn->query($query) or die("error making request");
	$row = mysqli_fetch_row($result);
	$num = (int) $row[0];
	$threadID = $num;

	$query = "SELECT Username FROM Threads where ThreadName = '$thread'";
	$result = $conn->query($query) or die("error making request");
	$row = mysqli_fetch_row($result);
	$threadUser = $row[0];

	$query = "INSERT INTO Posts (ThreadID, Username, PostName, PostContent) VALUES ($threadID, '$user', '$title', '$desc')";
	$result = $conn->query($query) or die("error making request");

	if($result){
		echo "post successful";
	}else{
		echo "post failed";
	}

}

?>