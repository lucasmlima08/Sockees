package com.myllenno.sockees.usercontrol;

import com.myllenno.sockees.report.HandlerDialog;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Handler;

public class ConnectionUser {

    /**
     * Classe de comunica��o do evento ocorrido.
     */
    private HandlerDialog handlerDialog;
    
    /**
     * Status da conex�o do usu�rio.
     */
    private boolean status;

    /**
     * Inst�ncia respons�vel por controlar a conex�o de socket do cliente.
     */
	private Socket user;

    /**
     * Fluxo de leitura do socket do client.
     */
    private InputStream inputStream;

    /**
     * Fluxo de escrita no socket do cliente.
     */
    private OutputStream outputStream;
    
    /**
     * M�todo construtor.
     * 
     * @param handler
     */
    public ConnectionUser(Handler handler){
        status = false;
        handlerDialog = new HandlerDialog(handler);
    }
    
    /**
     * Retorna a inst�ncia do socket do cliente.
     *
     * @return
     */
    public Socket getUser(){
        return user;
    }
    
    /**
     * Retorna a inst�ncia da classe de leitura.
     * 
     * @return
     */
    public InputStream getInputStream(){
    	return inputStream;
    }
    
    /**
     * Retorna a inst�ncia da classe de escrita.
     * 
     * @return
     */
    public OutputStream getOutputStream(){
    	return outputStream;
    }
    
    /**
     * Verifica se o usu�rio est� dispon�vel e conectado.
     *
     * @return
     */
    public boolean isAvailable(){
        if (user != null && user.isConnected() && status) {
            return true;
        } else {
        	handlerDialog.publishInfo(handlerDialog.USER_UNAVAILABLE);
        	return false;
        }
    }

    /**
     * Abre a conex�o com o socket.
     */
    public void open(String ipServer, int portServer) {
        try {
        	// Segundo passo: Abre a conex�o com o socket.
        	user = new Socket(ipServer, portServer);
            // Terceiro passo: Inicia as classes de leitura e escrita do socket.
            inputStream = user.getInputStream();
            outputStream = user.getOutputStream();
            status = true;
            handlerDialog.publishInfo(handlerDialog.CONNECTION_OPENED);
        } catch (Exception e){
        	handlerDialog.publishSevere(e.toString());
            e.printStackTrace();
        }
    }
    
    /**
     * Fecha a conex�o com o cliente.
     */
    public void close() {
        try {
            // Primeiro passo: Verifica se o cliente est� conectado.
            if (isAvailable()) {
                // Segundo passo: Fecha as classes de leitura/escrita e a conex�o com o cliente.
                inputStream.close();
                outputStream.close();
                user.close();
                user = null;
            }
            handlerDialog.publishInfo(handlerDialog.CONNECTION_CLOSED);
        } catch (Exception e){
        	handlerDialog.publishSevere(e.toString());
            e.printStackTrace();
        }
        status = false;
    }
}
