from pwn import *
import socket

HOST = "127.0.0.1"
PORT = 9992


s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((HOST, PORT))

intPayload = 0
payload = intPayload.to_bytes(4, 'big')
key = 'privateKeyTest'
payload += len(key).to_bytes(4, 'big')
intPayload = 1024
payload += intPayload.to_bytes(4, 'big')
payload += key.encode()
s.sendall(payload)
payload = s.recv(1024)
s.close()
print(payload)
