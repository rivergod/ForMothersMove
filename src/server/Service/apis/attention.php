<?php
header('Content-Type: text/plain; charset=UTF-8');

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
    'dbname'   => 'db_mothers',
	'charset' => 'utf8'
));

//http://mothers.krcode.com/apis/apts.php?userId=rivergod

$db->getConnection();

$db->setFetchMode(Zend_Db::FETCH_OBJ);
$result = $db->fetchAll('select address, lat, lng from attentions WHERE userId = ?', array($_req_args["userId"]));

$db->closeConnection();

echo Zend_Json::encode($result);
?>