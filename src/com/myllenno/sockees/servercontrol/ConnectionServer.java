package com.myllenno.sockees.servercontrol;

import com.myllenno.sockees.report.HandlerDialog;

import java.net.ServerSocket;
import java.util.logging.Handler;

public class ConnectionServer {

    /**
     * Classe de comunica��o do evento ocorrido.
     */
    private HandlerDialog handlerDialog;

    /**
     * Status do servidor.
     */
    private boolean status;

    /**
     * Inst�ncia respons�vel por controlar a conexão de socket do servidor.
     */
    private ServerSocket server;
    
    /**
     * M�todo construtor.
     *
     * @param port
     * @param handler
     */
    public ConnectionServer(Handler handler){
        status = false;
        handlerDialog = new HandlerDialog(handler);
    }
    
    /**
     * Retorna a inst�ncia do socket do servidor.
     *
     * @return
     */
    public ServerSocket getServer(){
        return server;
    }
    
    /**
     * Verifica se o servidor est� aberto e dispon�vel.
     *
     * @return
     */
    public boolean isAvailable(){
        if (server != null && status) {
            return true;
        } else {
        	handlerDialog.publishInfo(handlerDialog.SERVER_UNAVAILABLE);
        	return false;
        }
    }
    
    /**
     * Abre a conex�o do servidor.
     */
    public void open(int port) {
        try {
            server = new ServerSocket(port);
            status = true;
            handlerDialog.publishInfo(handlerDialog.CONNECTION_OPENED);
        } catch (Exception e){
        	handlerDialog.publishSevere(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Fecha a conex�o do servidor.
     */
    public void close() {
        try {
        	// Primeiro passo: Verifica se o servidor est� aberto e o fecha.
            if (isAvailable()) {
                server.close();
            }
            handlerDialog.publishInfo(handlerDialog.CONNECTION_CLOSED);
        } catch (Exception e){
        	handlerDialog.publishSevere(e.toString());
            e.printStackTrace();
        }
        status = false;
    }
}
