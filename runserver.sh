start_server() {
    echo "Starting Server..."
    mvn exec:java -Dexec.mainClass="za.co.wethinkcode.RobotWorlds.ActivateServer"
}

kill_server() {
    echo "Killing Server..."
    lsof -ti:5000 | (read pid; kill -9 $pid )
}

case $1 in
    start)
        start_server
        ;;
    stop)
        kill_server
        ;;
    *)
        echo "Usage: ./runserver.sh [start|stop]"
        exit 1
        ;;
esac

exit 0