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
java -Djava.library.path=/usr/lib/jni -jar target/LedTicker-jar-with-dependencies.jar
```


ssh into the remote machine
start tmux by typing ```tmux``` into the shell
start the process you want inside the started tmux session
leave/detach the tmux session by typing ```Ctrl+b and then d```
You can now safely log off from the remote machine, your process will keep running inside tmux. When you come back again and want to check the status of your process you can use ```tmux attach``` to attach to your tmux session.

If you want to have multiple sessions running side-by-side, you should name each session using ```Ctrl+b and $```. You can get a list of the currently running sessions using tmux list-sessions.
