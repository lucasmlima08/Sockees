package com.myllenno.sockees.servercontrol;

import com.myllenno.sockees.management.User;
import com.myllenno.sockees.report.HandlerDialog;

import java.util.ArrayList;
import java.util.logging.Handler;

public class ReadRequests {

    /**
     * Classe de comunicação do evento ocorrido.
     */
    private HandlerDialog handlerDialog;
    
    /**
     * Lista de requisições para envio.
     */
    private ArrayList<Object> listRequests;

    /**
     * Método construtor.
     * 
     * @param handler
     */
    public ReadRequests(Handler handler){
        handlerDialog = new HandlerDialog(handler);
    }

    /**
     * Retorna a lista de todas as requisições que serão enviadas.
     * Limpa a lista de requisições que serão enviadas.
     *
     * @return
     */
    public ArrayList<Object> getAllRequests() {
        return listRequests;
    }

    /**
     * Remove todas as requisições de envio da lista.
     *
     * @return
     */
    public void clearAllRequests() {
    	listRequests.clear();
    }
    
    /**
     * Recebe uma requisição de um cliente.
     * Pode se usado em um thread.
     *
     * @param user
     * @param objectType
     * @return
     */
    public Object read(User user, Object objectType) {
    	// Primeiro passo: Chama o recebimento de requisições do cliente.
    	user.receiveRequests(objectType);
    	// Verifica se a lista possui requisições.
    	if (user.getAllRequestsReceived().size() > 0){
    		// Segundo passo: Pega a primeira requisição enviada.
    		Object request = user.getAllRequestsReceived().get(0);
    		// Terceiro passo: Remove a requisição recebida.
    		// ------- PROGRAMAR...
    		return request;
    	}
        return null;
    }
    
    /**
     * Recebe as requisições de todos os clientes conectados.
     * Pode ser usado em um thread.
     */
    public void readAll(ArrayList<User> listUsers, Object objectType) {
    	// Primeiro passo: Percorre a lista de clientes.
    	for (User user: listUsers){
        	// Segundo passo: Verifica se o usuário está disponível.
        	if (user.isAvailable()){
        		// Terceiro passo: Chama o recebimento de requisições do cliente.
        		user.receiveRequests(objectType);
        		// Quarto passo: Adiciona todas as requisições a lista de requisições.
        		listRequests.addAll(user.getAllRequestsReceived());
            }
        }
        handlerDialog.publishInfo(handlerDialog.REQUESTS_RECEIVED_ALL);
    }
}
