#!/bin/bash
# JAVA_HOME
export JAVA_HOME=/usr/local/jdk1.8.0_171-amd64/
export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin:$JAVA_HOME/jre/bin


export MAVEN_HOME=/usr/local/apache-maven-3.6.3
export PATH=$MAVEN_HOME/bin:$PATH

app_name='baiyao-gateway'

tpid=`ps -ef|grep $app_name.jar |grep -v grep|awk '{print $2}'`
if [ ${tpid} ]; then
    echo 'App is running. Kill Process!'
    kill -15 $tpid
else
    echo 'Stop Success.App is NOT running!'
fi
sleep 10
mkdir -p logs/$app_name/
nohup java -jar   $app_name.jar  --spring.profiles.active=prod -Xms1G -Xmx1G > logs/$app_name/start.log 2>&1 &
echo $!, Start Success!
