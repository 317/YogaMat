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

    $app->get('/test2', function() use ($app) {
        echo (($app->adapter->query("PUT","tasks/{task_id}",["task_id"=>"83782271739352"], ["notes"=>"GENERATION OK 2"])));
        //echo (($app->adapter->post("tasks/83782271739352/addTag?tag=testtag")));
    });
    
    $app->handle();

} catch (Exception $e) {
     echo "Exception: ", $e->getMessage();
}
