package com.myllenno.sockees.management;

import com.myllenno.sockees.servercontrol.ConnectionServer;
import com.myllenno.sockees.servercontrol.AuthenticateUsers;
import com.myllenno.sockees.servercontrol.ReceiveUsers;
import com.myllenno.sockees.servercontrol.ControlUsers;
import com.myllenno.sockees.servercontrol.WriteRequests;
import com.myllenno.sockees.servercontrol.ReadRequests;

import com.myllenno.sockees.management.User;

import java.net.ServerSocket;
import java.util.logging.Handler;

public class Server {
	
	private ConnectionServer connectionServer;
	private AuthenticateUsers authenticateUsers;
	private ReceiveUsers receiveUsers;
	private ControlUsers controlUsers;
	private WriteRequests writeRequests;
	private ReadRequests readRequests;
	    
    /**
     * Instância responsável por controlar a conexão de socket do servidor.
     */
    private ServerSocket server;
    
    /**
     * Método construtor.
     * 
     * @param port
     * @param handler
     */
    public Server(Handler handler){
        // Responsável pelo controle da conexão do servidor.
        connectionServer = new ConnectionServer(handler);
        // Responsável pelo recebimento dos usuários.
        receiveUsers = new ReceiveUsers(handler);
        // Responsável pela autenticação do usuário.
        authenticateUsers = new AuthenticateUsers(handler);
        // Responsável por escrever as requisições dos usuários.
        writeRequests = new WriteRequests(handler);
        // Responsável por ler as requisições dos usuários.
        readRequests = new ReadRequests(handler);
        // Responsável por controlar os usuários.
        controlUsers = new ControlUsers(handler);
    }
    
    /**
     * Retorna a instância do socket do servidor.
     *
     * @return
     */
    public ServerSocket getServerSocket(){
        return server;
    }
    
    /**
     * Abre a conexão do servidor.
     * 
     * @param port
     */
    public void openConnection(int port) {
    	connectionServer.open(port);
    }

    /**
     * Verifica se o servidor está aberto e disponível.
     *
     * @return
     */
    public boolean isAvailable(){
        boolean status = connectionServer.isAvailable();
        return status;
    }
        
    /**
     * Recebe novos clientes que venham a se conectar.
     *
     * Pode ser usado em um thread para permitir que o servidor possa receba
     * um ou mais de um clientes para controlar a sua conexão.
     *
     * @return
     */
    public User receiveUsers() {
    	User user = receiveUsers.receive(connectionServer.getServer());
    	return user;
    }
    
    /**
     * Realiza a autenticação do cliente no servidor.
     * 
     * @param user
     * @param timeToAuthentication
     * @return
     */
    public User authenticateUser(User user, int timeToAuthentication){
    	User userAuthenticated = authenticateUsers.authentication(user, timeToAuthentication);
    	return userAuthenticated;
	}
    
    /**
     * Recebe uma requisição de um cliente.
     * Pode se usado em um thread.
     *
     * @param user
     * @param objectType
     * @return
     */
    public Object receiveRequest(User user, Object objectType) {
        Object object = readRequests.read(user, objectType);
        return object;
    }

    /**
     * Recebe as requisições de todos os clientes conectados.
     * Pode ser usado em um thread.
     * 
     * @param objectType
     */
    public void readRequests(Object objectType) {
    	readRequests.readAll(controlUsers.getAllUsers(), objectType);
    }
    
    /**
     * Envia apenas uma requisição para uma lista de clientes.
     * Pode ser usado em um thread.
     *
     * @param request
     * @return
     */
    public boolean writeRequest(Object request) {
    	boolean status = writeRequests.write(request);
    	return status;
    }

    /**
     * Envia as requisições para todos os clientes da lista de clientes.
     * Pode ser usado em um thread.
     */
    public void writeAllRequests() {
    	writeRequests.writeAll(readRequests.getAllRequests());
    }
    
    /**
     * Envia um objeto para todos os clientes conectados.
     * 
     * @param request
     */
    public void writeDataAllUsers(Object object){
    	writeRequests.writeDataAllUsers(controlUsers.getAllUsers(), object);
    }

    /**
     * Apaga todos os clientes e as listas de requisições dos clientes.
     */
    public void clearAll() {
    	readRequests.clearAllRequests();
    	writeRequests.clearAllRequestsNotSend();
    	controlUsers.clearAllUsers();
    }
    
    /**
     * Fecha a conexão do servidor.
     */
    public void closeConnection() {
    	connectionServer.close();
    }
}
