**How to run tests**

Username and password can be found in
Heroku -> Resources -> Heroku Postgre -> Settings -> Database Credentials

`mvn -DargLine="-DJDBC_USERNAME={username} -DJDBC_PASSWORD={password} test`