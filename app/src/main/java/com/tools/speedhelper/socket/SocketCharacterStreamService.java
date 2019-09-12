package com.tools.speedhelper.socket;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;


/**
 * Created by zhuyinan on 2016/4/13.
 */
public abstract class SocketCharacterStreamService extends BaseSocketService implements Runnable {


    private DataInputStream in;
    private PrintWriter printWriter;


    @Override
    public void onStop() {
        runFlag = false;
        try {
            socket.shutdownInput();
            socket.close();
            socket = null;
            in.close();
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        new Thread(this).start();
    }

    @Override
    public boolean onSend(String msg) {
        try {
            printWriter.println(msg);
            printWriter.flush();
            return true;
        } catch (Exception e) {

        }
        return false;
    }


    private PrintStream out;

    private void seed(String msg) {
        out.println(msg);
        out.flush();
    }
    @Override
    public void run() {
        try {
            in = new DataInputStream(socket.getInputStream());
            printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            runFlag = true;
            while (runFlag) {
                try {
                    if (in.available() > 0) {
                        int length = in.available();
                        byte[] msg = new byte[length];
                        in.read(msg, 0, length);
                        this.onReceive(new String(msg, "UTF-8").trim());
                    }
                } catch (Exception e) {

                }
            }

        } catch (IOException e) {
            runFlag = false;
            e.printStackTrace();
        }
    }
}
