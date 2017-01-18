import socket, sys, readline

HOST = "roboRIO-246-FRC.local"
PORT = 8080

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.connect((HOST, PORT))

while(True):
    
    msg = raw_input("> ")
    sock.sendall(msg + "\n")
    
    if(msg == "exit"):
        sys.exit()

    data = sock.recv(1024)
    print data



