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
		$email = $_POST['email'];
		$token = $_POST['fcm_token'];
		$role = "user";
		
		$sql = "SELECT * FROM Users WHERE username = '$user'";
		
		$result = mysqli_query($con,$sql);
		$num = mysqli_num_rows($result);
		if($num == 0){
			$sql = "INSERT INTO Users (username,password,email,role,fcm_token) VALUES ('$user', '$pass', '$email', '$role', '$token')";
			if(mysqli_query($con, $sql)){
				echo "Signup Successful";
			}else{
				echo "a database error has occurred";
			}
		}else{
			echo "User already created";
		}
	}else{
		echo "a server error has occurred";
	}
?>
