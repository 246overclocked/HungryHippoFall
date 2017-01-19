import socket, sys, readline

HOST = "roboRIO-246-FRC.local"
PORT = 8080

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.connect((HOST, PORT))

while(True):
    
    msg = raw_input("> ")
    sock.sendall(msg + "\n")
    
    if(msg == "exit"):
        print "Shutting down..."
        sys.exit()

    print "Waiting for data from roboRIO..."
    try:
        data = sock.recv(2048)
    except socket.timeout:
        print "Failed to receive data from roboRIO"
        #continue
    print data



