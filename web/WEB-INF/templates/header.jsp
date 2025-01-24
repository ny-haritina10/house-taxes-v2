<%@ page import="utils.*" %>

<!DOCTYPE html>
<html>
    <head>
        <!--<meta charset="UTF-8">-->
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-15">
        <title>ERP-Prospection</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
            <!-- jQuery 2.1.4 -->
        <script src="../assets/plugins/jQuery/jQuery-2.1.4.min.js"></script>
        <link href="../assets/plugins/select2/select2.min.css" rel="stylesheet" type="text/css" />
        <!-- Bootstrap 3.3.4 -->
        <link href="../assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="../assets/css/form.css" rel="stylesheet" type="text/css" />

        <!-- FontAwesome 4.3.0 -->
        <!--<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />-->
        <link href="../assets/dist/js/font-awesome-4.4.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <!-- Ionicons 2.0.0 -->
        <link href="../assets/plugins/ionicons-2.0.1/css/ionicons.min.css" rel="stylesheet" type="text/css" />
        <!-- Theme style -->
        <link href="../assets/dist/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
        <!-- AdminLTE Skins. Choose a skin from the css/skins
            folder instead of downloading all of them to reduce the load. -->
        <link href="../assets/dist/css/skins/_all-skins.min.css" rel="stylesheet" type="text/css" id="newskin"/>
        <!-- tabs -->
        <link href="../assets/dist/css/tabs.css" rel="stylesheet" type="text/css" id="tabscss"/>
        <!-- iCheck -->
        <link href="../assets/plugins/iCheck/flat/blue.css" rel="stylesheet" type="text/css" />
        <link href="../assets/plugins/datatables/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
        <!-- Morris chart -->
        <!--<link href="../assets/plugins/morris/morris.css" rel="stylesheet" type="text/css" />-->
        <!-- jvectormap -->
        <!--<link href="../assets/plugins/jvectormap/jquery-jvectormap-1.2.2.css" rel="stylesheet" type="text/css" />-->
        <!-- Date Picker -->
        <link href="../assets/plugins/datepicker/datepicker3.css" rel="stylesheet" type="text/css" />
        <link href="../assets/plugins/timepicker/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css" />
        <!-- Daterange picker -->
        <link href="../assets/plugins/daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css" />
        <!-- bootstrap wysihtml5 - text editor -->
        <link href="../assets/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css" rel="stylesheet" type="text/css" />
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
            <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
        <!-- fichier style a customiser !-->
        <link href="../assets/dist/css/stylecustom.css" rel="stylesheet" type="text/css" />
        <link href="../assets/dist/css/messagestyle.css" rel="stylesheet" type="text/css" />
        <link href="../assets/dist/css/jquery-ui.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="../assets/dist/css/skins/skin-yellow-light.css" >
        <script src="../assets/plugins/select2/select2.full.min.js"></script>
        <!-- -->
    </head>
    <body class="skin-yellow-light sidebar-mini">
        <!-- Site wrapper -->
        <div class="wrapper" style="max-width:none !important;">

            <header class="main-header" style="position: fixed; left: 0; right: 0;">
                <!-- Logo -->
                <a style="background-color:#ffffff; height:50px;" href="MainController" class="logo">
                    <!-- mini logo for sidebar mini 50x50 pixels -->
                    <span class="logo-mini" style="color: #000;font-weight: 600;">Gallois</span>
                    <!-- logo for regular state and mobile devices -->
                    <span class="logo-lg">
                        
                        <img style="width: 50px; height: 48px;" src="../assets/img/logo.png" />
                    </span>
                </a>
                
                <!-- Header Navbar: style can be found in header.less -->
                <nav style="background:#ffffff; height: 10px;" class="navbar navbar-static-top" role="navigation">
                    <!-- Sidebar toggle button-->
                    <a href="#" class="sidebar-toggle" style="color:#000;" data-toggle="offcanvas" role="button">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </a>
                    <div class="navbar-custom-menu">
                        <ul class="nav navbar-nav">
                            <!-- User Account: style can be found in dropdown.less -->
                            <li class="dropdown user user-menu">
                                <a href="#" style="color: red;">
                                    <i class="fa fa-sign-out" style="color: red;"></i> Logout
                                </a>
                                
                            </li>
                        </ul>
                    </div>
                </nav>
            </header>


            <!-- SIDEBAR -->
            <%@ include file="sidebar.jsp" %>
            <!-- SIDEBAR -->


            <div class="modal fade" id="modalSendMessage" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="message-chat-title"></h4>
                        </div>
                        <div class="modal-body clearfix">
                            <div class="message-chat-content clearfix" id="message-chat-content"></div>
                            <br/>
                            <form>
                                <textarea id="messagefrom" class="form-control" rows="3" placeholder="Votre message ici"></textarea>
                                <br/><br/>
                                <input type="button" class="btn btn-primary pull-right" style="margin-left: 5px;" value="Envoyer"/>
                                <input type="reset" class="btn btn-danger pull-right" value="Annuler"/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>