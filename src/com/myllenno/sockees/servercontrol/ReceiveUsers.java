package com.myllenno.sockees.servercontrol;

import com.myllenno.sockees.management.User;
import com.myllenno.sockees.report.HandlerDialog;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Handler;

public class ReceiveUsers {

    /**
     * Classe de comunicação do evento ocorrido.
     */
    private HandlerDialog handlerDialog;

    /**
     * Método construtor.
     * 
     * @param handler
     */
    public ReceiveUsers(Handler handler){
        handlerDialog = new HandlerDialog(handler);
    }

    /**
     * Recebe novos clientes que venham a se conectar.
     *
     * Pode ser usado em um thread para permitir que o servidor possa receba
     * um ou mais de um clientes para controlar a sua conexão.
     *
     * @param serverSocket
     * @return
     */
    public User receive(ServerSocket serverSocket) {
        try {
        	// Primeiro passo: Inicia a espera por um novo cliente
            Socket client = serverSocket.accept();
            // Segundo passo: Autentica o cliente.
            User user = new User(client);
            handlerDialog.publishInfo(handlerDialog.USER_RECEIVED);
            return user;
        } catch (Exception e){
        	handlerDialog.publishSevere(e.toString());
            e.printStackTrace();
        }
        return null;
    }
}
