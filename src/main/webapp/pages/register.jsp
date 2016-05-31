<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Form</title>
    <link rel="stylesheet" href="../css/register.css">
</head>
<body>
<div class="login-page">
    <div class="servlet-resp">
        <b> Incorrect username or password! Please, try again or register!</b>
    </div>

    <div class="form">
        <form class="register-form" method="get">
            <input type="text" name="login" placeholder="Username"/>
            <input type="password" name="password" placeholder="Password"/>
            <button type="submit" formaction="/register">create</button>
            <p class="message">Already registered? <a href="#">Sign In</a></p>
        </form>
        <form class="login-form" method="get">
            <input type="text" name="login" placeholder="Username"/>
            <input type="password" name="password" placeholder="Password"/>
            <button type="submit" formaction="/login">login</button>
            <p class="message">Not registered? <a href="#">Create an account</a></p>
        </form>
    </div>
</div>
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

<script src="../js/register.js"></script>
</body>
</html>
