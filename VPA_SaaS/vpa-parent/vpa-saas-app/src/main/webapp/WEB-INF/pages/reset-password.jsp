<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Vantage Point Analytics</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/font.css">
    <link rel="stylesheet" href="css/style.css">
  </head>
  <body>
  	<div class="wrapper_login">
        <div class="bl_login_tbl">
            <div class="bl_login_cell">
                <div class="bl_login">
                    <div class="logo"><img src="imgs/vpa-logo-login.png" alt=""></div>
                    <hr>
                    <h2>Reset Password</h2>
                    <div class="bl_white mb20">
                    	<p class="errors"></p>
                    	<form action="" id="frm-reset-pwd">
                        	<label>New Password</label>
                            <div class="bl_login_pwd mb20"><input id="pwd1" type="password" class="form-control txt-pwd" placeholder="Password" tabindex="1" autofocus><input type="button" value="show" class="btn-pwd btn" data-click-state="1"></div>
                            <label>Confirm New Password</label>
                            <div class="bl_login_pwd mb20"><input id="pwd2" type="password" class="form-control txt-pwd" placeholder="Password" tabindex="2"><input type="button" value="show" class="btn-pwd btn" data-click-state="1"></div>
                            <div class="text-center"><input type="submit" class="btn btn-vpa" value="Send" tabindex="3"></div>
                        </form>
                    </div>
                    <div class="text-center forgot_pwd_link"><a href="login.html" tabindex="4">Return to sign in screen</a></div>
                </div>
            </div>
        </div>
    </div>
    <script src="js/jquery-1.11.2.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/common.js"></script>
  </body>
</html>