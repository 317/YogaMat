<?php

use Phalcon\Loader;
use Phalcon\Tag;
use Phalcon\Mvc\Url;
use Phalcon\Mvc\View;
use Phalcon\Mvc\Application;
use Phalcon\DI\FactoryDefault;
use Phalcon\Db\Adapter\Pdo\Mysql as DbAdapter;
use Phalcon\Mvc\Collection;
use Phalcon\Mvc\Micro;

try {

    // Register an autoloader
    $loader = new Loader();


    $loader->registerDirs(
        array(
            '../app/',
            '../app/controllers/',
            '../app/models/',
            '../app/rest/'
        )
    )
    ->registerNamespaces(
        [
            'Phalcon' => '../app/Library/Phalcon/'
        ]
    )
    ->register();


    // Create a DI
    $di = new FactoryDefault();

    $di->set("adapter", new AsanaAdapter("0/efb8f1a901f0e05d15f3723817ea94d0"));

    // Setting up the view component
    $di['view'] = function() {
        $view = new View();
        $view->setViewsDir('../app/views/');
        return $view;
    };

    // Setup a base URI so that all generated URIs include the "tutorial" folder
    $di['url'] = function() {
        $url = new Url();
        return $url;
    };

    // Setup the tag helpers
    $di['tag'] = function() {
        return new Tag();
    };

    header('Content-Type: application/json');

    $app = new Micro($di);
    $app->get('/me', function() use ($app) {
        echo (($app->adapter->query("GET", "users/me")));
    });

     $app->get('/projects', function() use ($app) {
        echo (($app->adapter->query("GET", "projects")));
    });

    $app->get('/task/{task_id}', function($task_id) use ($app) {
        echo (($app->adapter->query("GET","tasks/{task_id}",["task_id"=>$task_id])));
        //echo (($app->adapter->post("tasks/83782271739352/addTag?tag=testtag")));
    });

    $app->get("/project/{project_id}", function($project_id) use ($app) {
        echo (($app->adapter->query("GET","projects/{project_id}",["project_id"=>$project_id])));
    });

    $app->get("/project/{project_id}/tasks", function($project_id) use ($app) {
        echo (($app->adapter->query("GET","projects/{project_id}/tasks",["project_id"=>$project_id])));
    });

    $app->get("/project/{project_id}/velocity", function($project_id) use ($app) {
        $tasks = json_decode(($app->adapter->query("GET","projects/{project_id}/tasks",["project_id"=>$project_id])));
        $returner = array();
        $returner["pts_total"] = 0;
        $returner["pts_done"] = 0;
        $returner["completion_list"] = array();
        foreach($tasks->data as $task){
            $task_data = json_decode($app->adapter->query("GET","tasks/{task_id}",["task_id"=>$task->id]));
            $task_data = $task_data->data;
            //echo json_encode($task_data);
            foreach($task_data->tags as $tag){
                if(strpos ($tag->name, " pts") != 0 ){
                    preg_match_all('!\d+!', $tag->name, $matches);
                    $var = implode(' ', $matches[0]); 
                    $returner["pts_total"] += $var;
                    if($task_data->completed == true){
                        $returner["pts_done"] += $var;
                        $completed_at = substr($task_data->completed_at, 0, 10);
                        if(!isset($returner["completion_list"][$completed_at])){
                            $returner["completion_list"][$completed_at] = array();
                            $returner["completion_list"][$completed_at]["pts_done"] = 0;
                        }
                        $returner["completion_list"][$completed_at]["pts_done"] += $var;
                    }
                }
            }
        }

       echo json_encode($returner);
    });
    
    $app->handle();

} catch (Exception $e) {
     echo "Exception: ", $e->getMessage();
}
