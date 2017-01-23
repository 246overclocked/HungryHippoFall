import socket, sys, readline, time

HOST = "roboRIO-246-FRC.local"
PORT = 8080

isConnected = False
sock = None

def connect():
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    print "Connecting to roboRIO..."
    sock.connect((HOST, PORT))
    sock.settimeout(30)
    return sock;

while(True):
    
    if(not isConnected):
        sock = connect();
        isConnected = True

    msg = raw_input("> ")
    sock.sendall(msg + "\n")
    
    if(msg == "exit"):
        print "Shutting down..."
        sys.exit()

    print "Waiting for data from roboRIO..."
    try:
        data = sock.recv(2048)
    except socket.timeout:
        sock.sendall("exit" + "\n")
        sock.close()
        isConnected = False
        print "Failed to receive data from roboRIO, attempting to reconnect...\n" +\
        "If this procedure fails, please redeploy code and attempt to reconnect."
        continue

    print data



