#!/bin/sh
#cd /
cd /opt/tomcat/apache-tomee-plus-1.7.1/bin/
./shutdown.sh
rm -rf /opt/tomcat/apache-tomee-plus-1.7.1/webapps/codedeploy/
rm  /opt/tomcat/apache-tomee-plus-1.7.1/webapps/vpa-saas-app.war
rm -rf /opt/tomcat/apache-tomee-plus-1.7.1/webapps/vpa-saas-app/