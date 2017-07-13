<?php

$host='127.0.0.1';
$username='root';
$pwd='';
$db='reaching_hands';

$con=mysqli_connect($host,$username,$pwd,$db) or die('unable to connect');

if(mysqli_connect_error($con))
{
	echo "Failed to connect";
}

$query=mysqli_query($con,"SELECT * FROM inventory");

if($query)
{
	while($row=mysqli_fetch_array($query))
	{
		$flag[]=$row;
	}
	print(json_encode($flag));
}

mysqli_close($con);
?>