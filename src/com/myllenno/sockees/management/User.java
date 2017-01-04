package com.myllenno.sockees.management;

import com.myllenno.sockees.usercontrol.ConnectionUser;
import com.myllenno.sockees.usercontrol.Authenticate;
import com.myllenno.sockees.usercontrol.ReceiveRequest;
import com.myllenno.sockees.usercontrol.SendRequest;

import com.myllenno.sockees.requests.RequestSimple;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Handler;

public class User {
	
	private ConnectionUser connectionUser;
	private Authenticate authenticate;
	private ReceiveRequest receiveRequest;
	private SendRequest sendRequest;
        
    /**
     * Instância do identificador do cliente no servidor.
     */
    private int idUser;

    /**
     * Instância responsável por controlar a conexão de socket do cliente.
     */
	private Socket user;
    
    /**
     * Método construtor.
     * 
     * @param user
     */
    public User(Socket user){
        this.user = user;
        start(null);
    }
    
    /**
     * Método construtor.
     * 
     * @param client
     * @param idUser
     */
    public User(Socket user, int idUser){
    	this.user = user;
    	this.idUser = idUser;
        start(null);
    }
    
    /**
     * Método construtor.
     * 
     * @param idUser
     * @param handler
     */
    public User(int idUser, Handler handler){
    	this.idUser = idUser;
    	start(handler);
    }
    
    /**
     * Inicia as classes de interações.
     * 
     * @param handler
     */
    private void start(Handler handler){
    	// Responsável pelo controle da conexão do usuário.
    	connectionUser = new ConnectionUser(handler);
    	// Responsável pela autenticação do usuário com o servidor.
    	authenticate = new Authenticate(handler);
    	// Responsável pelo recebimento de requisições.
    	receiveRequest = new ReceiveRequest(handler);
    	// Responsável pelo envio de requisições.
    	sendRequest = new SendRequest(handler);
    }
    
    /**
     * Retorna o socket do usuário.
     *
     * @return
     */
    public Socket getUser(){
    	return user;
    }
    
    /**
     * Retorna o identificador do usuário.
     *
     * @return
     */
    public int getId(){
    	return idUser;
    }
    
    /**
     * Retorna a classe de leitura do usuário.
     * 
     * @return
     */
    public InputStream getInputStream(){
    	return connectionUser.getInputStream();
    }
    
    /**
     * Retorna a classe de escrita do usuário.
     * 
     * @return
     */
    public OutputStream getOutputStream(){
    	return connectionUser.getOutputStream();
    }
    
    /**
     * Retorna a lista com todas as requisições recebidas.
     *
     * @return
     */
    public ArrayList<RequestSimple> getAllRequestsReceived(){
    	return receiveRequest.getAllRequestsReceived();
    }

    /**
     * Retorna a lista com todas as requisições que não puderam ser enviadas.
     *
     * @return
     */
    public ArrayList<Object> getAllRequestsNotSend(){
    	return sendRequest.getAllRequestsNotSend();
    }
    
    /**
     * Adiciona uma requisição para envio ao servidor.
     * 
     * @param request
     */
    public void addRequestSend(Object request){
    	sendRequest.addRequestSend(request);
    }

    /**
     * Abre a conexão com o socket.
     * 
     * @param ipServer
     * @param portServer
     */
    public void openConnection(String ipServer, int portServer) {
        connectionUser.open(ipServer, portServer);
    }

    /**
     * Verifica se o cliente está disponível e conectado.
     *
     * @return
     */
    public boolean isAvailable(){
        boolean status = connectionUser.isAvailable();
        return status;
    }
    
    /**
     * Realiza a autenticação e recebe a resposta.
     * 
     * @param idUser
     * @return
     */
    public boolean authenticate(int idUser) {
    	boolean status = authenticate.authenticate(idUser, 
    			connectionUser.getInputStream(), 
    			connectionUser.getOutputStream());
    	return status;
    }
        
    /**
     * Recebe um objeto para envio ao cliente.
     * Codifica para JSON e em seguida converte para bytes.
     * Escreve os bytes no socket do cliente.
     *
     * @param object
     * @return
     */
    public boolean sendRequest(Object request) {
    	boolean status = sendRequest.send(connectionUser.getOutputStream(), request);
    	return status;
    }
    
    /**
     * Envia todas as requisições da lista de requisições.
     * Pode ser usado em um thread.
     */
    public void sendRequestsAll() {
        sendRequest.sendAll(connectionUser.getOutputStream());
    }

    /**
     * Recebe o tipo de objeto que o usuário deseja decodificar.
     * Faz a leitura do socket do cliente.
     * Decodifica a leitura do JSON para o tipo de objeto especificado.
     * Retorna o objeto lido.
     *
     * Pode ser utilizado em um Thread ou um método de loop enquanto estiver conectado.
     *
     * @param objectType
     */
    public void receiveRequests(Object objectType) {
        receiveRequest.receive(connectionUser.getInputStream(), objectType);
    }
    
    /**
     * Remove todas as requisições.
     */
    public void clearAll(){
    	receiveRequest.clearAllRequestsReceived();
    	sendRequest.clearAllRequestNotSend();
    }

    /**
     * Fecha a conexão com o cliente.
     */
    public void closeConnection() {
        connectionUser.close();
    }
}
