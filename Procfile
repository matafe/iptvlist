web: java -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n -Xms64m -Xmx128m -Dfile.encoding=UTF-8 -XX:MaxMetaspaceSize=128m -Djava.net.preferIPv4Stack=true -Dswarm.http.port=$PORT -jar target/iptvlist-thorntail.jar
log: tail -f logs/iptv.log