package networkex.client;

import networkex.common.MessageImpl;
import networkex.common.ConnectionListener;
import networkex.common.Connection;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.*;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.net.*;
import java.io.*;
import javax.swing.*;
import networkex.homework.Message;

public class ClientMainInt extends JFrame implements ConnectionListener {
    JPanel contentPane;
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JTextField AddrField = new JTextField();
    JLabel jLabel1 = new JLabel();
    JButton ConnectBut = new JButton();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTextArea OutArea = new JTextArea();
    JTextField MessField = new JTextField();
    JButton SendBut = new JButton();
    private Connection conn = null;
    JButton DisconnectBut = new JButton();
    private boolean doExit = false;

    public ClientMainInt() {
        try {
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            jbInit();
            setState(false);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void setState(boolean connected){
        AddrField.setEnabled(!connected);
        ConnectBut.setEnabled(!connected);
        DisconnectBut.setEnabled(connected);
        SendBut.setEnabled(connected);
        MessField.setEnabled(connected);
    }

    /**
     * Component initialization.
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(gridBagLayout1);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(new Dimension(400, 300));
        setTitle("Network Client");
        this.addWindowListener(new cliMainInt_this_windowAdapter(this));
        jLabel1.setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));
        jLabel1.setText("IP:");
        ConnectBut.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        ConnectBut.setText("Connect");
        ConnectBut.addActionListener(new cliMainInt_ConnectBut_actionAdapter(this));
        OutArea.setBackground(Color.black);
        OutArea.setFont(new java.awt.Font("SansSerif", Font.PLAIN, 11));
        OutArea.setForeground(Color.green);
        OutArea.setEditable(false);
        SendBut.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        SendBut.setForeground(Color.blue);
        SendBut.setText(">>>");
        SendBut.addActionListener(new cliMainInt_SendBut_actionAdapter(this));
        AddrField.setText("127.0.0.1");
        AddrField.setHorizontalAlignment(SwingConstants.CENTER);
        DisconnectBut.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
        DisconnectBut.setForeground(Color.red);
        DisconnectBut.setText("Stop");
        DisconnectBut.addActionListener(new cliMainInt_jButton1_actionAdapter(this));
        jScrollPane1.getViewport().add(OutArea);
        contentPane.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(5, 5, 5, 5), 0, 0));
        contentPane.add(AddrField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 5, 5), 0, 0));
        contentPane.add(jScrollPane1,
                        new GridBagConstraints(0, 1, 5, 1, 1.0, 1.0
                                               , GridBagConstraints.CENTER,
                                               GridBagConstraints.BOTH,
                                               new Insets(5, 5, 5, 5), 0, 0));
        contentPane.add(SendBut, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(5, 5, 5, 5), 0, 0));
        contentPane.add(ConnectBut, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(5, 5, 5, 0), 0, 0));
        contentPane.add(DisconnectBut, new GridBagConstraints(3, 0, 2, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(5, 5, 5, 5), 0, 0));
        contentPane.add(MessField, new GridBagConstraints(0, 2, 4, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 5, 5), 0, 0));
    }

    public void println(String msg){
        OutArea.append(msg+"\n");
    }

    public void connect() {
        try{
            /*Socket s = new Socket();
            s.connect(new InetSocketAddress(InetAddress.getByName(AddrField.getText()), Connection.PORT));*/
            Socket s = new Socket(AddrField.getText(), Connection.PORT);
            new Connection(s, this);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    void fireExit(){
        doExit = true;
    }

    public void closeConnection() {
        //setState(false);
        if (conn!=null){
            try {
                Message mess = new MessageImpl(conn.getName(), Message.CLOSE, null);
                conn.send(mess);
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                conn.close();
            }
        } else {
            System.exit(0);
        }
    }

    public void connectionCreated(Connection conn) {
        setState(true);
        this.conn = conn;
    }

    public void connectionClosed(Connection conn) {
        setState(false);
        if (doExit) System.exit(0);
    }

    public void connectionError(Connection conn, Exception ex) {
        System.out.println(ex);
    }

    public void messageReceived(Connection conn, Message mess) {
        switch (mess.getType()){
        case Message.TEXT:
            println(mess.getNick()+": "+(String)mess.getContent());
            break;
        case Message.CLOSE:
            conn.close();
            break;
        }

    }

    public void sendMess() {
        String mess = MessField.getText().trim();
        if (mess.length()>0){
            try {
                println(mess);               
                Message m = new MessageImpl(conn.getName(), Message.TEXT, mess);
                conn.send(m);
                MessField.setText("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}


class cliMainInt_jButton1_actionAdapter implements ActionListener {
    private ClientMainInt adaptee;
    cliMainInt_jButton1_actionAdapter(ClientMainInt adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.closeConnection();
    }
}


class cliMainInt_SendBut_actionAdapter implements ActionListener {
    private ClientMainInt adaptee;
    cliMainInt_SendBut_actionAdapter(ClientMainInt adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.sendMess();
    }
}


class cliMainInt_this_windowAdapter extends WindowAdapter {
    private ClientMainInt adaptee;
    cliMainInt_this_windowAdapter(ClientMainInt adaptee) {
        this.adaptee = adaptee;
    }

    public void windowClosing(WindowEvent e) {
        adaptee.fireExit();
        adaptee.closeConnection();
    }
}


class cliMainInt_ConnectBut_actionAdapter implements ActionListener {
    private ClientMainInt adaptee;
    cliMainInt_ConnectBut_actionAdapter(ClientMainInt adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.connect();
    }
}
