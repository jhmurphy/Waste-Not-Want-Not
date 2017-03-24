<?php

	session_start();
	
$db_username = "dbu309sg3";
$db_password = "NDNlZjIzYTE2";
$db_server = "mysql.cs.iastate.edu";
$db_name = "db309sg3";
$conn = new mysqli($db_server, $db_username, $db_password, $db_name);
$path_to_fcm = "https://fcm.googleapis.com/fcm/send";
$server_key = "AAAAhRKOCo0:APA91bGofPDpw-RTs91YLQT1kR_C9cto0yZTLwEHHYtaGXf9sXicTtrt5EEeHD_cG07ZOBABGYVd2yc2YqTK1ojTbe6b9_jwAbP9VJLsD25ObkWFXknBBa8fmYf7dpBMGfdo3Ei5Bb6N";

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

    $query = "SELECT fcm_token FROM Users WHERE username = '$threadUser'";
    $result = $conn->query($query) or die("error making request");
    $row = mysqli_fetch_row($result);
    $token = $row[0];

    $headers = array(
        'Authorization:key=' . $server_key,
        'Content-Type:application/json'
    );

    $title = "Thread Update";
    $message = $user . " has made a post in your thread";

    $fields = array(
        'to'=>$token,
        'data'=>array('title'=>$title,'body'=>$message)
    );

    $payload = json_encode($fields);

    $curl_session = curl_init();
    curl_setopt($curl_session, CURLOPT_URL, $path_to_fcm);
    curl_setopt($curl_session, CURLOPT_POST, true);
    curl_setopt($curl_session, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($curl_session, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($curl_session, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($curl_session, CURLOPT_IPRESOLVE, CURL_IPRESOLVE_V4);
    curl_setopt($curl_session, CURLOPT_POSTFIELDS, $payload);

    $result2 = curl_exec($curl_session);
    curl_close($curl_session);

	if($result){
		echo "post successful";
	}else{
		echo "post failed";
	}

}

?>