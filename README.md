LedTicker
=========

Java LedTicker.

RXTX [documentation](https://github.com/rxtx/rxtx)

OS X [setup](http://rxtx.qbang.org/wiki/index.php/Installation_on_MacOS_X)

OS X [fat binary](http://blog.iharder.net/2009/08/18/rxtx-java-6-and-librxtxserial-jnilib-on-intel-mac-os-x/)

Also, for OS X, make sure you are running the app against JRE 1.8. TLS support against GitHub requires 1.2 which is avaialble with JDK 1.8 by default.

Command line program arguments: useGui=true to turn on GUI otherwise the command line will default.
```
java LedTicker useGui=true
```

Build and package as you would for a normal maven project.
```
mvn clean package
```

Run the application with:
```
java -jar target/LedTicker-jar-with-dependencies.jar
```

How to keep processes running on unix after ssh logout: https://askubuntu.com/questions/8653/how-to-keep-processes-running-after-ending-ssh-session

RXTX Raspberry PI: http://angryelectron.com/rxtx-on-raspbian/
And then run the ticker via:
```
java.library.path=/usr/lib/jni -jar target/LedTicker-jar-with-dependencies.jar
```
