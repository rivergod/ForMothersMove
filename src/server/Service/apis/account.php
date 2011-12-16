<?php
set_include_path(get_include_path().':/home/mothers');
require_once "../../Zend/Loader.php";

Zend_Loader::loadClass('Zend_Db');
Zend_Loader::loadClass('Zend_Json');
	
$_req_args["userId"] = isset($_REQUEST["userId"]) ? $_REQUEST["userId"] : "";

$_res["result"] = "fail";

$db = Zend_Db::factory('Mysqli', array(
    'host'     => '127.0.0.1',
    'username' => 'mothers',
    'password' => 'mothersdb',
    'dbname'   => 'db_mothers'
));

$db->getConnection();

$db->setFetchMode(Zend_Db::FETCH_OBJ);
$result = $db->fetchRow('SELECT * FROM accounts WHERE userId = ?', array($_req_args["userId"]));

if ( $result != null ) {
	$_res["result"] = "ok";
	
	$_res["userid"] = $result->userid;
	$_res["favor0"] = $result->favor0;
	$_res["favor1"] = $result->favor1;
	$_res["favor2"] = $result->favor2;
	$_res["favor3"] = $result->favor3;
}
else {
	$_res["result"] = "fail";

	$_res["userid"] = "";
	$_res["favor0"] = 0;
	$_res["favor1"] = 0;
	$_res["favor2"] = 0;
	$_res["favor3"] = 0;
}

$db->closeConnection();

echo Zend_Json::encode($_res);
?>
