package com.myllenno.sockees.interfaces;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Handler;

import com.myllenno.sockees.requests.RequestSimple;
import com.myllenno.sockees.usercontrol.UserManagement;

public interface InterfaceServerManagement {
	
	/**
     * Retorna a instância do socket do servidor.
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
     * Fecha a conexão com todos os clientes.
     * Remove todos os clientes da lista.
     *
     * @return
     */
    public void clearAllUsers();

    /**
     * Retorna a lista com todas as requisições do fluxo de requisições.
     *
     * @return
     */
    public ArrayList<Object> getAllRequests();

    /**
     * Remove todas as requisições da lista.
     *
     * @return
     */
    public void clearAllRequests();

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
     * Abre a conexão do servidor.
     */
    public void openConnection();

    /**
     * Verifica se o servidor está disponível.
     *
     * @return
     */
    public boolean isAvailable();
	
	/**
     * Adiciona a classe de eventos para receber informações quando ocorrer um evento.
     *
     * @param handler
     */
    public void setHandler(Handler handler);
    
    /**
     * Atualiza a lista de clientes e remove os indisponíveis.
     */
    public void refreshClients();
    
    /**
     * Fecha a conexão e remove um cliente da lista de clientes com o id indicado.
     *
     * @param index
     */
    public void removeClientId(int id);
    
    /**
     * Remove um cliente da lista de clientes na posição indicada.
     *
     * @param index
     */
    public void removeClient(int index);
    
    /**
     * Define o tipo de objeto que será recebido dos clientes.
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
     * Realiza a autenticação do cliente e o inclui na lista de clientes.
     * 
     * @param socket
     * @param timeToAuthentication
     * @return
     */
    public UserManagement authenticationClient(Socket socket, int timeToAuthentication); // Um thread.
    
    /**
     * Retorna uma requisição recebida de um cliente definido.
     *
     * @param client
     * @return
     */
    public RequestSimple receiveRequest(UserManagement client);

    /**
     * Recebe as requisições de todos os clientes conectados.
     * Pode ser usado em um thread.
     */
    public void receiveRequests(); // Um thread.

    /**
     * Envia apenas uma requisição.
     * Retorna um valor booleano que indica se a requisição foi enviada.
     * 
     * @param request
     * @return
     */
    public boolean sendRequest(Object request);
    
    /**
     * Envia as requisições para todos os clientes da lista de clientes.
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
     * Fecha a conexão com todos os clientes e apaga as listas de requisições.
     */
    public void clearAll();
    
    /**
     * Fecha a conexão do servidor.
     */
    public void closeConnection();
}
