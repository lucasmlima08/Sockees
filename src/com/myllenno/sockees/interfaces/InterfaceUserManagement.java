package com.myllenno.sockees.interfaces;

import com.myllenno.sockees.requests.RequestSimple;

import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Handler;

public interface InterfaceUserManagement {
	
	/**
     * Retorna a instância do socket do cliente.
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
     * Retorna a lista com todas as requisições recebidas.
     *
     * @return
     */
    public ArrayList<RequestSimple> getAllRequestsReceived();

    /**
     * Remove todas as requisições recebidas da lista.
     *
     * @return
     */
    public void clearAllRequestsReceived();

    /**
     * Retorna a lista com todas as requisições que não puderam ser enviadas.
     *
     * @return
     */
    public ArrayList<Object> getAllRequestsNotSend();

    /**
     * Remove todas as requisições não enviadas da lista.
     *
     * @return
     */
    public void clearAllRequestNotSend();
    
    /**
     * Adiciona uma requisição para envio ao servidor.
     * 
     * @param request
     */
    public void addRequestSend(Object request);
    
    /**
     * Abre a conexão com o socket.
     * 
     * @param ipServer
     * @param portServer
     */
    public void openConnection(String ipServer, int portServer);

    /**
     * Verifica se o cliente está disponível e conectado.
     *
     * @return
     */
    public boolean isAvailable();
    
    /**
     * Realiza a autenticação e recebe a resposta do servidor.
     * 
     * @param idClient
     * @return
     */
    public boolean authenticate();
    
    /**
     * Adiciona a classe de eventos para receber informações quando ocorrer um evento.
     *
     * @param handler
     */
    public void setHandler(Handler handler);
    
    /**
     * Recebe uma requisição para envio ao servidor.
     * Codifica para JSON e em seguida converte para bytes.
     * Escreve os bytes no socket do cliente.
     *
     * Pode ser utilizado em um Thread.
     *
     * @param object
     */
    public boolean sendRequest(Object request);
    
    /**
     * Envia apenas uma requisição.
     * Retorna um valor booleano que indica se a requisição foi enviada.
     */
    public void sendRequests(); // Um thread.

    /**
     * Recebe as requisições com o tipo de objeto que o usuário deseja decodificar.
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
     * Fecha a conexão do cliente.
     */
    public void closeConnection();
}
