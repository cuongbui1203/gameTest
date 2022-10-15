import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class App {

    private static String HOST = "127.0.0.1";
    private static int PORT = 1234;

    public static int fromByteArray(byte[] bytes) {
        return bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
    }

    public static final byte[] intToByteArray(int value) {
        byte[] res;
        res = new byte[] { (byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value };
        return res;
    }

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(HOST, PORT));
        Socket socket = null;

        socket = server.accept();

        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        byte[] payload = new byte[1024];

        inputStream.read(payload);

        byte[] bytePort = new byte[4];

        Arrays.copyOfRange(payload, 8, 12);
        final int GAMEPORT = fromByteArray(bytePort);
        int len = fromByteArray(Arrays.copyOfRange(payload, 4, 8));
        final String key = new String(Arrays.copyOfRange(payload, 12, 12 + len));

        AtomicBoolean gameStart = new AtomicBoolean(false);
        AtomicBoolean gameFail = new AtomicBoolean(false);

        Thread threadGame = new Thread() {
            @Override
            public void run() {
                try {
                    ServerSocket gameServerSocket = new ServerSocket();
                    gameServerSocket.bind(new InetSocketAddress(HOST, GAMEPORT));

                    gameStart.set(true);

                    Socket p1 = null;

                    // p1 = gameServerSocket.accept();

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    gameFail.set(true);
                    e.printStackTrace();
                }

            }
        };

        threadGame.start();

        while (!gameStart.get() && !gameFail.get()) {
        }

        payload = new byte[1024];
        if (gameFail.get()) {
            payload[0] = 3;
        } else {
            payload[0] = 1;
        }
        outputStream.write(payload);

    }
}
