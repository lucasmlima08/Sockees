package com.myllenno.sockees.interfaces;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Handler;

import com.myllenno.sockees.requests.RequestSimple;
import com.myllenno.sockees.usercontrol.UserManagement;

public interface InterfaceServerManagement {
	
	/**
     * Retorna a inst�ncia do socket do servidor.
     *
     * @return
     */
    public ServerSocket getServerSocket();

	/**
     * Retorna a lista com todos os clientes conectados ao servidor.
     *
     * @return
     */
    public ArrayList<UserManagement> getAllUsers();

    /**
     * Fecha a conex�o com todos os clientes.
     * Remove todos os clientes da lista.
     *
     * @return
     */
    public void clearAllUsers();

    /**
     * Retorna a lista com todas as requisi��es do fluxo de requisi��es.
     *
     * @return
     */
    public ArrayList<Object> getAllRequests();

    /**
     * Remove todas as requisi��es da lista.
     *
     * @return
     */
    public void clearAllRequests();

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
     * Abre a conex�o do servidor.
     */
    public void openConnection();

    /**
     * Verifica se o servidor est� dispon�vel.
     *
     * @return
     */
    public boolean isAvailable();
	
	/**
     * Adiciona a classe de eventos para receber informa��es quando ocorrer um evento.
     *
     * @param handler
     */
    public void setHandler(Handler handler);
    
    /**
     * Atualiza a lista de clientes e remove os indispon�veis.
     */
    public void refreshClients();
    
    /**
     * Fecha a conex�o e remove um cliente da lista de clientes com o id indicado.
     *
     * @param index
     */
    public void removeClientId(int id);
    
    /**
     * Remove um cliente da lista de clientes na posi��o indicada.
     *
     * @param index
     */
    public void removeClient(int index);
    
    /**
     * Define o tipo de objeto que ser� recebido dos clientes.
     *
     * @param objectType
     */
    public void setObjectReaderType(Object objectType);
    
    /**
     * Recebe novos clientes que venham a se conectar.
     * Pode ser usado em um thread.
     *
     * @return
     */
    public UserManagement receiveClient(); // Um thread.
    
    /**
     * Realiza a autentica��o do cliente e o inclui na lista de clientes.
     * 
     * @param socket
     * @param timeToAuthentication
     * @return
     */
    public UserManagement authenticationClient(Socket socket, int timeToAuthentication); // Um thread.
    
    /**
     * Retorna uma requisi��o recebida de um cliente definido.
     *
     * @param client
     * @return
     */
    public RequestSimple receiveRequest(UserManagement client);

    /**
     * Recebe as requisi��es de todos os clientes conectados.
     * Pode ser usado em um thread.
     */
    public void receiveRequests(); // Um thread.

    /**
     * Envia apenas uma requisi��o.
     * Retorna um valor booleano que indica se a requisi��o foi enviada.
     * 
     * @param request
     * @return
     */
    public boolean sendRequest(Object request);
    
    /**
     * Envia as requisi��es para todos os clientes da lista de clientes.
     * Pode ser usado em um thread.
     */
    public void sendRequests(); // Um thread.
    
    /**
     * Envia um objeto para todos os clientes conectados.
     * 
     * @param request
     */
    public void sendDataAllClients(Object object);

    /**
     * Fecha a conex�o com todos os clientes e apaga as listas de requisi��es.
     */
    public void clearAll();
    
    /**
     * Fecha a conex�o do servidor.
     */
    public void closeConnection();
}
