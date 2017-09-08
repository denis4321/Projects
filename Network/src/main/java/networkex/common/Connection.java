package networkex.common;

import java.net.*;
import java.io.*;
import networkex.homework.Message;

public class Connection implements Runnable{
    private Socket sock;
    private DataOutputStream out;
    private boolean doRun = true;
    private ConnectionListener l;
    public static final int PORT = 3334;

    public Connection(Socket s, ConnectionListener l)  throws IOException {
        this.sock = s;
        this.l = l;
        out = new DataOutputStream(s.getOutputStream());
        Thread t = new Thread(this);
        t.setPriority(Thread.NORM_PRIORITY);
        t.start();
    }

    public void send(Message mess) throws IOException {
        /*out.writeInt(mess.length());
        out.writeBoolean(false);
        out.write(mess.getBytes());*/
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(buf);
        objOut.writeObject(mess);
        objOut.close();
        byte[] data = buf.toByteArray();
        out.writeInt(data.length);
        out.write(data);
    }

    public void run() {
        try{
            l.connectionCreated(this);
            DataInputStream in = new DataInputStream(sock.getInputStream());
            byte[] buf = null;
            int index = 0;
            while (doRun) {
                if (in.available() > 0) {
                    if (buf == null) {
                        int size = in.readInt();
                        buf = new byte[size];
                        index = 0;
                    } else {
                        index+= in.read(buf, index, buf.length-index);
                        if (index>=buf.length){
                            ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(buf));
                            Message mess = (Message) objIn.readObject();
                            objIn.close();
                            l.messageReceived(this, mess);
                            buf = null;
                        }
                    }
                } else {
                    Thread.sleep(200);
                }
            }
            sock.close();
        } catch (Exception ex){
            l.connectionError(this, ex);
        } finally {
            l.connectionClosed(this);
        }

    }

    public void close(){
        doRun = false;
    }

    public String getName(){
        return sock.getInetAddress().toString();
    }

}
