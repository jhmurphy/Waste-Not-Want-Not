<?php

session_start();
	
define ('HOST','mysql.cs.iastate.edu');
define ('USER','dbu309sg3');
define ('PASS','NDNlZjIzYTE2');
define ('DB','db309sg3');
	
if($_SERVER['REQUEST_METHOD']=='POST'){
	$con = mysqli_connect(HOST, USER, PASS, DB) or die('unable to connect');
	
	$user = $_POST['username'];
	$pass = $_POST['password'];

	$sql = "SELECT * FROM Users WHERE username = '$user' AND password = '$pass'";
	$result = mysqli_query($con,$sql);
	$num = mysqli_num_rows($result);
	
	if($num == 1){
		echo "Login successful";
	}
	else if($num == 0) {
		echo "No user with that information";
	}
	else{
		echo "Login failed";
	}
	
}
else {
	echo "not a post request";
}

?>