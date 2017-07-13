<?php
$username="root";
$password="";
$database="reaching_hands";
$server= "127.0.0.1";
$data_handle=mysql_connect($server,$username,$password);
$db_con=mysql_select_db($database,$data_handle);

$work=$_POST['work'];
if($db_con){
	if($work == "retrieve"){
		$tabNum=$_POST['tabNum'];
		if($tabNum == "1"){
			$retrieve="SELECT * FROM inventory WHERE item_type='Boys'";
			$res=mysql_query($retrieve);
			if($res)
			{
				while($row=mysql_fetch_assoc($res)){
					echo $row['item_name'];
					echo "&";
					echo $row['current_count'];
					echo "&";
					
				}
			}
		}
		else if($tabNum == "2"){
			$retrieve="SELECT * FROM inventory WHERE item_type='Girls'";
			$res=mysql_query($retrieve);
			if($res)
			{
				while($row=mysql_fetch_assoc($res)){
					echo $row['item_name'];
					echo "&";
					echo $row['current_count'];
					echo "&";
					
				}
			}
		}
		else{
			$retrieve="SELECT * FROM inventory WHERE item_type='Kitchen'";
			$res=mysql_query($retrieve);
			if($res)
			{
				while($row=mysql_fetch_assoc($res)){
					echo $row['item_name'];
					echo "&";
					echo $row['current_count'];
					echo "&";
					
				}
			}
		}
	}
	else if($work == "retrieveApprovedNeeded"){
		$retrieve="SELECT * FROM approval";
		$res=mysql_query($retrieve);
		if($res)
		{
			while($row=mysql_fetch_assoc($res)){
				echo $row['Items'];
				echo '&';
				echo $row['Flag'];
				echo '&';
			}
		}
	}
	//echo $work;
	else if($work == "updateApprovedItems"){
		$name=$_POST['name'];
		echo "$name";
		$changes = "UPDATE approval SET Flag='Yes' WHERE Items='$name'";
		$res=mysql_query($changes);
		if($res){
			echo "abc";
		}
		else{
			echo "$name";
		}
	}
	else if($work == "deleteApproved"){
		$name=$_POST['name'];
		$deletes="DELETE FROM approval WHERE Items='$name'";
		$res=mysql_query($deletes);
		if($res){
			echo "abc";
		}
		else{
			echo "$name";
		}
		//echo $work;
	}
	
	else if($work=="billing")
	{
		$amnt=$_POST['amount'];
		$amntt=(int)$amnt;
		$rete="select * from billing";
		$res=mysql_query($rete);
		if($res){
			while($row=mysql_fetch_assoc($res)){
				$billl=$row['spent']+$amntt;
				
			}
		}
		echo "$amt";
		$quee="UPDATE billing SET spent='$billl'";
		$ress=mysql_query($quee);
		if($ress){
			echo "done";
		}
		
		
	}
}

?>