<?php
class MorphConn {
	private $_mc = null;
	private $_conn = null;
	private static $_instance = null;
	
	private function __construct() {
	    $this->_mc = $this->getParameters();
		$this->_conn = null;
	}
	public static function getInstance() {
		if(is_null(self::$_instance)) {
			self::$_instance = new MorphConn();
		}
		return self::$_instance;
	} 

	public function getConnection(){
		if(is_null($_conn)){
			$this->_conn = new mysqli('localhost', $this->_mc['username'], $this->_mc['password'], $this->_mc['dbname']);
		}else if (!is_resource($_conn)){
			$this->_conn = new mysqli('localhost', $this->_mc['username'], $this->_mc['password'], $this->_mc['dbname']);
		}
		return $this->_conn;
	}
	
	private function getParameters(){
		return parse_ini_file('config.ini');	
	}
	
}
?>