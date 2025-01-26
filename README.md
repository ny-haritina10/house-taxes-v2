# How to run the app

1. Update the `_config.conf` file to match with your file path 
2. Update the `WILDFLY_PATH` in the `_start.bat` file
3. Update the `Database.java` by setting `propertiesFileName` attributes
4. Update the `_db.properties` file to match with your `ORACLE` credentials
4. Update the `map.js` file by replacing with your `YOUR_WILDFLY_PORT`
5. Execute all `.sql` file in `/sql`, follow the file number
6. Execute `_deploy.bat` to compile and deploy the project
7. Execute `_start.bat` to start `WILDFLY`
8. Access `http://localhost:YOUR_WILDFLY_PORT/house-taxes-v2/`