<?php
use Phalcon\Http\Client\Request;

class AsanaAdapter
{
	private $key;
	private $provider;

	public function __construct($key){
		$this->key = $key;
		$this->provider  = Request::getProvider();
		$this->provider->setBaseUri('https://app.asana.com/api/1.0/');
		$this->provider->header->set('Accept', 'application/json');
	}

	public function query($type, $request_uri, $uri_params=array(), $post_data=array()){
		$request_uri = $this->compileURI($request_uri, $uri_params);
		$post_data["access_token"] = $this->key;
		$response = $this->provider->$type($request_uri, $post_data, false);
		return $response->body;
	}

	public function compileURI($request_uri, $params){
		//str_replace(find,replace,string,count)
		foreach($params as $key=>$param){
			$request_uri = str_replace("{".$key."}", $param, $request_uri);
		}
		return $request_uri;
	}
	
  
}