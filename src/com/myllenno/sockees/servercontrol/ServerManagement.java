package com.myllenno.sockees.servercontrol;

import com.myllenno.sockees.usercontrol.UserManagement;

import java.net.ServerSocket;
import java.util.logging.Handler;

public class ServerManagement {
	
	private ConnectionServer connectionServer;
	private AuthenticateUsers authenticateUsers;
	private ReceiveUsers receiveUsers;
	private ControlUsers controlUsers;
	private WriteRequests writeRequests;
	private ReadRequests readRequests;
	    
    /**
     * Inst�ncia respons�vel por controlar a conex�o de socket do servidor.
     */
    private ServerSocket server;
    
    /**
     * M�todo construtor.
     * 
     * @param port
     * @param handler
     */
    public ServerManagement(Handler handler){
        // Respons�vel pelo controle da conex�o do servidor.
        connectionServer = new ConnectionServer(handler);
        // Respons�vel pelo recebimento dos usu�rios.
        receiveUsers = new ReceiveUsers(handler);
        // Respons�vel pela autentica��o do usu�rio.
        authenticateUsers = new AuthenticateUsers(handler);
        // Respons�vel por escrever as requisi��es dos usu�rios.
        writeRequests = new WriteRequests(handler);
        // Respons�vel por ler as requisi��es dos usu�rios.
        readRequests = new ReadRequests(handler);
        // Respons�vel por controlar os usu�rios.
        controlUsers = new ControlUsers(handler);
    }
    
    /**
     * Retorna a inst�ncia do socket do servidor.
     *
     * @return
     */
    public ServerSocket getServerSocket(){
        return server;
    }
    
    /**
     * Abre a conex�o do servidor.
     * 
     * @param port
     */
    public void openConnection(int port) {
    	connectionServer.open(port);
    }

    /**
     * Verifica se o servidor est� aberto e dispon�vel.
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
     * um ou mais de um clientes para controlar a sua conex�o.
     *
     * @return
     */
    public UserManagement receiveUsers() {
    	UserManagement userManagement = receiveUsers.receive(connectionServer.getServer());
    	return userManagement;
    }
    
    /**
     * Realiza a autentica��o do cliente no servidor.
     * 
     * @param userManagement
     * @param timeToAuthentication
     * @return
     */
    public UserManagement authenticateUser(UserManagement userManagement, int timeToAuthentication){
    	UserManagement userAuthenticated = authenticateUsers.authentication(userManagement, timeToAuthentication);
    	return userAuthenticated;
	}
    
    /**
     * Recebe uma requisi��o de um cliente.
     * Pode se usado em um thread.
     *
     * @param user
     * @param objectType
     * @return
     */
    public Object receiveRequest(UserManagement user, Object objectType) {
        Object object = readRequests.read(user, objectType);
        return object;
    }

    /**
     * Recebe as requisi��es de todos os clientes conectados.
     * Pode ser usado em um thread.
     * 
     * @param objectType
     */
    public void readRequests(Object objectType) {
    	readRequests.readAll(controlUsers.getAllUsers(), objectType);
    }
    
    /**
     * Envia apenas uma requisi��o para uma lista de clientes.
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
     * Envia as requisi��es para todos os clientes da lista de clientes.
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
     * Apaga todos os clientes e as listas de requisi��es dos clientes.
     */
    public void clearAll() {
    	readRequests.clearAllRequests();
    	writeRequests.clearAllRequestsNotSend();
    	controlUsers.clearAllUsers();
    }
    
    /**
     * Fecha a conex�o do servidor.
     */
    public void closeConnection() {
    	connectionServer.close();
    }
}
