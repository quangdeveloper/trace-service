<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Xác nhận đăng ký tài khoản hệ thống Tracebility</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>

    <!-- use the font -->
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            font-size: 48px;
        }
    </style>
</head>
<body style="margin: 0; padding: 0;">
<p>Xin chào ${username},</p>
<p>Bạn vừa thực hiện đăng ký tài khoản ${userId} trên hệ thống</p>
<p>Để xác nhận đăng ký tài khoản, xin vui lòng <a href="${serverAddress}#/auth/register-token-authen?token=${token}&userId=${userId}">ấn vào đây</a></p>
<p>Xin cảm ơn!</p>
</body>
</html>