package com.myllenno.sockees.interfaces;

import com.myllenno.sockees.requests.RequestSimple;

import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Handler;

public interface InterfaceUserManagement {
	
	/**
     * Retorna a inst�ncia do socket do cliente.
     *
     * @return
     */
    public Socket getClientSocket();
    
    /**
     * Retorna o identificador do cliente.
     *
     * @return
     */
    public int getClientId();
    
    /**
     * Retorna a lista com todas as requisi��es recebidas.
     *
     * @return
     */
    public ArrayList<RequestSimple> getAllRequestsReceived();

    /**
     * Remove todas as requisi��es recebidas da lista.
     *
     * @return
     */
    public void clearAllRequestsReceived();

    /**
     * Retorna a lista com todas as requisi��es que n�o puderam ser enviadas.
     *
     * @return
     */
    public ArrayList<Object> getAllRequestsNotSend();

    /**
     * Remove todas as requisi��es n�o enviadas da lista.
     *
     * @return
     */
    public void clearAllRequestNotSend();
    
    /**
     * Adiciona uma requisi��o para envio ao servidor.
     * 
     * @param request
     */
    public void addRequestSend(Object request);
    
    /**
     * Abre a conex�o com o socket.
     * 
     * @param ipServer
     * @param portServer
     */
    public void openConnection(String ipServer, int portServer);

    /**
     * Verifica se o cliente est� dispon�vel e conectado.
     *
     * @return
     */
    public boolean isAvailable();
    
    /**
     * Realiza a autentica��o e recebe a resposta do servidor.
     * 
     * @param idClient
     * @return
     */
    public boolean authenticate();
    
    /**
     * Adiciona a classe de eventos para receber informa��es quando ocorrer um evento.
     *
     * @param handler
     */
    public void setHandler(Handler handler);
    
    /**
     * Recebe uma requisi��o para envio ao servidor.
     * Codifica para JSON e em seguida converte para bytes.
     * Escreve os bytes no socket do cliente.
     *
     * Pode ser utilizado em um Thread.
     *
     * @param object
     */
    public boolean sendRequest(Object request);
    
    /**
     * Envia apenas uma requisi��o.
     * Retorna um valor booleano que indica se a requisi��o foi enviada.
     */
    public void sendRequests(); // Um thread.

    /**
     * Recebe as requisi��es com o tipo de objeto que o usu�rio deseja decodificar.
     * Faz a leitura do socket do cliente.
     * Decodifica a leitura do JSON para o tipo de objeto especificado.
     * Retorna o objeto lido.
     *
     * Pode ser utilizado em um Thread.
     *
     * @param objectType
     */
    public void receiveRequests(Object objectType); // Um thread
        
    /**
     * Fecha a conex�o do cliente.
     */
    public void closeConnection();
}
