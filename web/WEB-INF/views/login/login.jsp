<!DOCTYPE html>
<html lang="FR" style="display: flex; align-items: center; justify-content: center;">
<head>
    <meta charset="UTF-8">
    <title>Identification</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link href="../assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/dist/js/font-awesome-4.4.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/dist/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/dist/css/stylecustom.css" rel="stylesheet" type="text/css" />
    <link href="../assets/plugins/iCheck/square/blue.css" rel="stylesheet" type="text/css" />
</head>
<body class="login-page">
    <div class="login-box loginBody">
        <div class="login-box-body">
            <div class="login-logo">
                <a href="index.jsp">
                    <img style="width: 148px; height: 148px;" src="../assets/img/logo_sisal-rmbg.png"/>
                </a>
            </div>
            <p class="login-box-msg" style="font-weight: bold; font-size: 20px;">Se connecter</p>

            <% 
                String message = (String) request.getAttribute("message");
                if (message != null && !message.isEmpty()) { 
            %>
                <div class="alert alert-danger" role="alert">
                    <strong>Erreur!</strong> <%= message %>
                </div>
            <% 
                } 
            %>

            <form action="LoginController" method="post" style="width: 100%; max-width: 300px;">
                <input type="hidden" name="action" value="login"/>
                <div class="input-group" style="margin-bottom: 10px;">
                    <span class="input-group-addon"><i class="fa fa-user"></i></span>
                    <input type="text" value="user" name="username" class="form-control" placeholder="Nom d'utilisateur" required/>
                </div>
                <div class="input-group" style="margin-bottom: 10px;">
                    <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                    <input type="password" value="user" name="password" class="form-control" placeholder="Mot de passe" required/>
                </div>
                <div class="row">
                    <div class="col-xs-5">
                        <input type="submit" class="btn btn-success btn-block btn-flat" value="Se connecter">
                    </div>
                </div>
            </form>
        </div>
    </div>

    <script src="../assets/plugins/jQuery/jQuery-2.1.4.min.js" type="text/javascript"></script>
    <script src="../assets/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="../assets/plugins/iCheck/icheck.min.js" type="text/javascript"></script>
</body>
</html>
