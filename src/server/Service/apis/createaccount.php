<?php
header('Content-Type: text/plain; charset=UTF-8');

set_include_path(get_include_path().':/home/mothers');
require_once "../../Zend/Loader.php";

Zend_Loader::loadClass('Zend_Db');
Zend_Loader::loadClass('Zend_Json');
	
$_req_args["userId"] = isset($_REQUEST["userId"]) ? $_REQUEST["userId"] : "";
$_req_args["passwd"] = isset($_REQUEST["passwd"]) ? $_REQUEST["passwd"] : "";
$_req_args["favor0"] = isset($_REQUEST["favor0"]) ? $_REQUEST["favor0"] : 0;
$_req_args["favor1"] = isset($_REQUEST["favor1"]) ? $_REQUEST["favor1"] : 0;
$_req_args["favor2"] = isset($_REQUEST["favor2"]) ? $_REQUEST["favor2"] : 0;
$_req_args["favor3"] = isset($_REQUEST["favor3"]) ? $_REQUEST["favor3"] : 0;

$_res["result"] = "fail";

$db = Zend_Db::factory('Mysqli', array(
    'host'     => '127.0.0.1',
    'username' => 'mothers',
    'password' => 'mothersdb',
    'dbname'   => 'db_mothers',
    'charset' => 'utf8'
));

$db->getConnection();

$db->setFetchMode(Zend_Db::FETCH_OBJ);
$db->query('INSERT INTO accounts (`userId`, `passwd`, `favor0`, `favor1`, `favor2`, `favor3` ) values ( ?, ?, ?, ?, ?, ? ) ', array($_req_args["userId"], $_req_args["passwd"], $_req_args["favor0"], $_req_args["favor1"], $_req_args["favor2"], $_req_args["favor3"]));

//$db->insert('accounts', array( 'userId' => $_req_args["userId"], 'passwd' => $_req_args["passwd"], 'favor0' => $_req_args["favor0"], 'favor1' => $_req_args["favor1"], 'favor2' => $_req_args["favor2"], 'favor3' => $_req_args["favor3"]));

$result = $db->fetchRow('SELECT * FROM accounts WHERE userId = ? and passwd = ?', array($_req_args["userId"], $_req_args["passwd"]));

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
