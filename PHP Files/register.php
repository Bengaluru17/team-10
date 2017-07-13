<?php
require "init.php";

extract($_POST);
	
$item_name=$_POST["item_name"];

echo $item_name;

//$sql_query=

if(mysqli_query($con,"insert into approval(Items,Flag) VALUES('$item_name','Yes')"))
{
	echo "Hello";
}
else
{
	echo "Data insertion error ..".mysqli_error($con);
}
mysqli_close($con);
?>