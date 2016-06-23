<!DOCTYPE html>
<html lang="en" ng-app="app">
<head>
    <title>LifeInOrder</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Bank Statistics Application">
    <meta name="author" content="Fragkakis Manos">

    <link href="webjars/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="webjars/bootstrap/3.3.1/css/bootstrap-theme.min.css" rel="stylesheet">
    <link href="webjars/angular-ui-grid/3.1.1/ui-grid.min.css" rel="stylesheet"/>
    <link href="styles/starter.css" rel="stylesheet">


    <link rel="shortcut icon" type="image/ico" href="icons/favicon.ico"/>
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,800,700' rel='stylesheet' type='text/css'>

    <style>
        .my-drop-zone { border: dotted 3px lightgray; }
        .nv-file-over { border: dotted 3px red; } /* Default class applied to drop zones on over */
        .another-file-over-class { border: dotted 3px green; }
        html, body { height: 100%; }
    </style>
</head>

<body role="document" ng-app="app">

<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#"><LifeInOrder></LifeInOrder></a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="#/Home">Home</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="true">Bank<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#/Upload">Data Upload</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#/Transactions">Transactions</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#/Statistics">Statistics</a></li>
                    </ul>
                </li>
                <li><a href="#/Microphone">Microphone</a></li>
            </ul>
            <form class="navbar-form navbar-right" ng-controller="VoiceCtrl">
                <button ng-click="startMic()" class="btn btn-success">Start Mic</button>
                <button ng-click="stopMic()" class="btn btn-danger">Stop Mic</button>
            </form>
        </div>

    </div>
</nav>

<div class="main" ng-view></div>

</body>
<script src="webjars/jquery/2.1.3/jquery.min.js" rel="script"></script>
<script src="webjars/angularjs/1.4.9/angular.js" rel="script"></script>
<script src="webjars/angularjs/1.4.9/angular-touch.min.js" rel="script"></script>
<script src="webjars/angularjs/1.4.9/angular-animate.min.js" rel="script"></script>
<script src="webjars/angularjs/1.4.9/angular-resource.min.js"></script>
<script src="webjars/angularjs/1.4.9/angular-route.js" rel="script"></script>
<script src="webjars/bootstrap/3.3.1/js/bootstrap.min.js"></script>
<script src="webjars/angular-ui-bootstrap/0.12.0/ui-bootstrap-tpls.min.js"></script>
<script src="webjars/angular-ui-grid/3.1.1/ui-grid.min.js" rel="script"></script>
<script src="webjars/smart-table/2.1.3-1/smart-table.js"></script>
<script src="webjars/nervgh-angular-file-upload/2.1.1/angular-file-upload.min.js"></script>

<script src="scripts/annyang.min.js"></script>

<script src="scripts/app.js" rel="2script"></script>
<script src="scripts/config/route.js" rel="2script"></script>

<script src="scripts/controllers/mainCtrl.js" rel="2script"></script>
<script src="scripts/controllers/transactions.js" rel="2script"></script>
<script src="scripts/controllers/statistics.js" rel="2script"></script>
<script src="scripts/controllers/voice.js" rel="2script"></script>
<script src="scripts/controllers/statistics.js" rel="2script"></script>
<script src="scripts/controllers/upload.js" rel="2script"></script>
</html>