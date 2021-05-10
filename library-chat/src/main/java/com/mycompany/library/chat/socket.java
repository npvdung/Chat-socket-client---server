/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.library.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 *
 * @author npvdung
 */
public class socket {
    private Socket socket;
    private JTextPane txpMessageBoard;
    private PrintWriter out;
    private BufferedReader reader;
    private String sender = "Manager" ;
    private String receiver ;

    public socket(Socket socket, String sender, JTextPane txpMessageBoard) throws IOException {
        this.socket = socket;
        this.txpMessageBoard = txpMessageBoard;
        this.sender = sender;              
        
        out=new PrintWriter(socket.getOutputStream());
        reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        receive();
    }        
    
    public socket(Socket socket,String sender,String receiver, JTextPane txpMessageBoard) throws IOException {
        this.socket = socket;
        this.txpMessageBoard = txpMessageBoard;
        this.sender = sender;
        this.receiver = receiver;
        
        out=new PrintWriter(socket.getOutputStream());
        reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        receive();
    }


    private void receive(){
        Thread th = new Thread(){
            public void run(){
                while (true) {                    
                    try {
                        String line = reader.readLine();
                        if (line!=null) {
                            txpMessageBoard.setText(txpMessageBoard.getText() + receiver + ": " + line + "\n");
                        }
                    } catch (Exception e) {
                    }
                    
                }
            }                   
        };
        th.start();
    }
    public void send(String mag){
        String current = txpMessageBoard.getText();
        txpMessageBoard.setText(current + sender + ": " + mag + "\n");
        out.println(mag);
        out.flush();
    }    
        
    public void close(){
        try {
            out.close();
            reader.close();
            socket.close();
        } catch (Exception e) {
        }
    }    
}

