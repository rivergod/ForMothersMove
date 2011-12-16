<?php
header('Content-Type: text/plain; charset=UTF-8');

set_include_path(get_include_path().':/home/mothers');
require_once "../../Zend/Loader.php";

Zend_Loader::loadClass('Zend_Db');
Zend_Loader::loadClass('Zend_Json');
	
$_req_args["userId"] = isset($_REQUEST["userId"]) ? $_REQUEST["userId"] : "";
$_req_args["address"] = isset($_REQUEST["address"]) ? $_REQUEST["address"] : "";
$_req_args["lat"] = isset($_REQUEST["lat"]) ? $_REQUEST["lat"] : "0";
$_req_args["lng"] = isset($_REQUEST["lng"]) ? $_REQUEST["lng"] : "0";

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
$db->query('INSERT INTO attentions (`userId`, `address`, `lat`, `lng`) values ( ?, ?, ?, ? ) ', array($_req_args["userId"], $_req_args["address"], $_req_args["lat"], $_req_args["lng"]));

//$db->insert('accounts', array( 'userId' => $_req_args["userId"], 'passwd' => $_req_args["passwd"], 'favor0' => $_req_args["favor0"], 'favor1' => $_req_args["favor1"], 'favor2' => $_req_args["favor2"], 'favor3' => $_req_args["favor3"]));

$result = $db->fetchRow('SELECT * FROM attentions WHERE userId = ? and address = ? and lat = ? and lng = ?', array($_req_args["userId"], $_req_args["address"], $_req_args["lat"], $_req_args["lng"]));

if ( $result != null && count($result) > 0 ) {
	$_res["result"] = "ok";
}
else {
	$_res["result"] = "fail";
}

$db->closeConnection();

echo Zend_Json::encode($_res);
?>
