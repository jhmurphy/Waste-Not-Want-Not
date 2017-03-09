<?php

	session_start();
	
	define ('HOST','mysql.cs.iastate.edu');
	define ('USER','dbu309sg3');
	define ('PASS','NDNlZjIzYTE2');
	define ('DB','db309sg3');
	
		
		$user = $_SESSION['username'];
		$title = $_POST['name'];
		$desc = $_POST['description'];
		
		$con = mysqli_connect(HOST, USER, PASS, DB) or die('unable to connect');
		
		$sql = "INSERT INTO Threads (ThreadName, ThreadDescription, Username) VALUES ('$title', '$desc', '$user')";
		
		if(mysqli_query($con, $sql)){
			echo "thread successful";
		}else{
			echo "thread failed";
		}
	

?>