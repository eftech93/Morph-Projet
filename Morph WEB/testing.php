<?php
require 'class.MorphConn.php';
//$savestring = mysql_real_escape_string($unsavestring)  -> for avoiding sql injection
if(isset($_POST['act'])){
	$act = $_POST['act'];	
	if(is_string($act)){
		if(strlen($act) < 6){		
			$M = MorphConn::getInstance();
			$conn = $M->getConnection();
			if ($conn->connect_error) {
				die("Connection failed: " . $conn->connect_error);
			}
			if($act === "gasf"){ // get all system files
				$sql = "SELECT sfn.idsystemfilesname, sfn.name, sf.version, sf.file, sf.createddate FROM systemfilesname sfn inner join systemfiles sf on sfn.idsystemfilesname = sf.idsystemfilesname WHERE sf.version in (SELECT MAX(sf_.version) FROM systemfiles sf_ where sf_.idsystemfilesname = sfn.idsystemfilesname) ORDER BY sfn.idsystemfilesname  ";
				$result = $conn->query($sql);
				if ($result->num_rows > 0) {
					$output = array();
					while($row = $result->fetch_assoc()) {
						$output[] = $row;
					}
					echo json_encode($output);
				} else {
					echo "";
				}
			}else if($act === "gasfv"){// get all system files version
				$sql = "SELECT sfn.idsystemfilesname, sfn.name, sf.version FROM systemfilesname sfn inner join systemfiles sf on sfn.idsystemfilesname = sf.idsystemfilesname WHERE sf.version in (SELECT MAX(sf_.version) FROM systemfiles sf_ where sf_.idsystemfilesname = sfn.idsystemfilesname) ORDER BY sfn.idsystemfilesname  ";
				$result = $conn->query($sql);
				if ($result->num_rows > 0) {
					$output = array();
					while($row = $result->fetch_assoc()) {
						$output[] = $row;
					}
					echo json_encode($output);
				} else {
					echo "";
				}
			}else if($act === "bssf"){// bring some system files
				if(isset($_POST['bring'])){
					$bring_unprotected = $_POST['bring'];	
						if(is_string($bring_unprotected)){
							//$bring_protected = mysql_real_escape_string($bring_unprotected);
							$sql = "SELECT sfn.idsystemfilesname, sfn.name, sf.version, sf.file, sf.createddate FROM systemfilesname sfn inner join systemfiles sf on sfn.idsystemfilesname = sf.idsystemfilesname WHERE sf.version in (SELECT MAX(sf_.version) FROM systemfiles sf_ where sf_.idsystemfilesname = sfn.idsystemfilesname) and FIND_IN_SET(sfn.name, '" . $bring_unprotected . "') ORDER BY sfn.idsystemfilesname ";

							$result = $conn->query($sql);
							if ($result->num_rows > 0) {
								$output = array();
								while($row = $result->fetch_assoc()) {
									$output[] = $row;
								}
								echo json_encode($output);
							} else {
								echo "";
							}
						}
				}
			}else if($act==="gaaop"){//get all activities of an app
				if(isset($_POST['bring'])){
					$bring_unprotected = $_POST['bring'];	
						if(is_string($bring_unprotected)){
							$sql = "SELECT a.name, af.activityname, af.version, aty.type, af.xml as file FROM app a INNER JOIN activity act ON a.name = act.appname INNER JOIN activitytype aty on act.idactivitytype = aty.idactivitytype RIGHT JOIN activityfile af on af.appname = a.name and af.activityname = act.activityname WHERE af.version in (SELECT MAX(af_.version) from activityfile af_ where af.appname = af_.appname and af.activityname = af_.activityname) and a.name = '" . $bring_unprotected . "' ";
							$result = $conn->query($sql);
							if ($result->num_rows > 0) {
								$output = array();
								while($row = $result->fetch_assoc()) {
									$output[] = $row;
								}
								echo json_encode($output);
							} else {
								echo "";
							}
						}
				}
			}else if($act==="gavaa"){//get all version activities of an app
				if(isset($_POST['bring'])){
					$bring_unprotected = $_POST['bring'];	
						if(is_string($bring_unprotected)){
							$sql = "SELECT a.name, af.activityname, af.version, aty.type FROM app a INNER JOIN activity act ON a.name = act.appname INNER JOIN activitytype aty on act.idactivitytype = aty.idactivitytype RIGHT JOIN activityfile af on af.appname = a.name and af.activityname = act.activityname WHERE af.version in (SELECT MAX(af_.version) from activityfile af_ where af.appname = af_.appname and af.activityname = af_.activityname) and a.name = '" . $bring_unprotected . "' ";
							$result = $conn->query($sql);
							if ($result->num_rows > 0) {
								$output = array();
								while($row = $result->fetch_assoc()) {
									$output[] = $row;
								}
								echo json_encode($output);
							} else {
								echo "";
							}
						}
				}
			}else if ($act=="bsaa") { //bring some activity app 
				//check bssf
			}else if($act==="anvaa"){//add new version of an activty app
				
			}else if($act==="soi"){// Store object information
			}else if($act==="goi"){ // get object information
			}
			$conn->close();
		}
	}
}
?>