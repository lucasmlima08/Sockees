package com.myllenno.sockees.servercontrol;

import com.myllenno.sockees.report.HandlerDialog;
import com.myllenno.sockees.usercontrol.UserManagement;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Handler;

public class ReceiveUsers {

    /**
     * Classe de comunica��o do evento ocorrido.
     */
    private HandlerDialog handlerDialog;

    /**
     * M�todo construtor.
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
     * um ou mais de um clientes para controlar a sua conex�o.
     *
     * @return
     */
    public UserManagement receive(ServerSocket serverSocket) {
        try {
        	// Primeiro passo: Inicia a espera por um novo cliente
            Socket client = serverSocket.accept();
            // Segundo passo: Autentica o cliente.
            UserManagement userManagement = new UserManagement(client);
            handlerDialog.publishInfo(handlerDialog.USER_RECEIVED);
            return userManagement;
        } catch (Exception e){
        	handlerDialog.publishSevere(e.toString());
            e.printStackTrace();
        }
        return null;
    }
}