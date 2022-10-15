import socket

HOST = "127.0.0.1"
PORT = 9992


# def proses(conn=socket, addr):
#     print(addr)
#     payload = conn.recv(1024)

#     print('type ', int(payload[0:4].hex(), 16))
#     print('len ', int(payload[4:8].hex(), 16))
#     print('port ', int(payload[8:12].hex(), 16))

#     type = 1
#     len = 0
#     payload = type.to_bytes(4, 'big')
#     payload += len.to_bytes(4, 'big')

#     conn.sendall(payload)


s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((HOST, PORT))

s.listen()
conn, addr = s.accept()


while True:
    try:
        print(addr)
        payload = conn.recv(1024)
        print(payload)

        print('type ', int(payload[0:4].hex(), 16))
        print('len ', int(payload[4:8].hex(), 16))
        print('port ', int(payload[8:12].hex(), 16))

        type = 1
        len = 0
        payload = type.to_bytes(4, 'big')
        payload += len.to_bytes(4, 'big')

        conn.sendall(payload)
    except:
        conn, addr = s.accept()
