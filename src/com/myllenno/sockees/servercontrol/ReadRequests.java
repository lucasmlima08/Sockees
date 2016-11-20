package com.myllenno.sockees.servercontrol;

import com.myllenno.sockees.management.User;
import com.myllenno.sockees.report.HandlerDialog;

import java.util.ArrayList;
import java.util.logging.Handler;

public class ReadRequests {

    /**
     * Classe de comunica��o do evento ocorrido.
     */
    private HandlerDialog handlerDialog;
    
    /**
     * Lista de requisi��es para envio.
     */
    private ArrayList<Object> listRequests;

    /**
     * M�todo construtor.
     * 
     * @param handler
     */
    public ReadRequests(Handler handler){
        handlerDialog = new HandlerDialog(handler);
    }

    /**
     * Retorna a lista de todas as requisi��es que ser�o enviadas.
     * Limpa a lista de requisi��es que ser�o enviadas.
     *
     * @return
     */
    public ArrayList<Object> getAllRequests() {
        return listRequests;
    }

    /**
     * Remove todas as requisi��es de envio da lista.
     *
     * @return
     */
    public void clearAllRequests() {
    	listRequests.clear();
    }
    
    /**
     * Recebe uma requisi��o de um cliente.
     * Pode se usado em um thread.
     *
     * @param user
     * @param objectType
     * @return
     */
    public Object read(User user, Object objectType) {
    	// Primeiro passo: Chama o recebimento de requisi��es do cliente.
    	user.receiveRequests(objectType);
    	// Verifica se a lista possui requisi��es.
    	if (user.getAllRequestsReceived().size() > 0){
    		// Segundo passo: Pega a primeira requisi��o enviada.
    		Object request = user.getAllRequestsReceived().get(0);
    		// Terceiro passo: Remove a requisi��o recebida.
    		// ------- PROGRAMAR...
    		return request;
    	}
        return null;
    }
    
    /**
     * Recebe as requisi��es de todos os clientes conectados.
     * Pode ser usado em um thread.
     */
    public void readAll(ArrayList<User> listUsers, Object objectType) {
    	// Primeiro passo: Percorre a lista de clientes.
        for (int i = 0; i < listUsers.size(); i++) {
        	// Segundo passo: Verifica se o usu�rio est� dispon�vel.
        	if (listUsers.get(i).isAvailable()){
        		// Terceiro passo: Chama o recebimento de requisi��es do cliente.
        		listUsers.get(i).receiveRequests(objectType);
        		// Quarto passo: Adiciona todas as requisi��es a lista de requisi��es.
        		listRequests.addAll(listUsers.get(i).getAllRequestsReceived());
            }
        }
        handlerDialog.publishInfo(handlerDialog.REQUESTS_RECEIVED_ALL);
    }
}
