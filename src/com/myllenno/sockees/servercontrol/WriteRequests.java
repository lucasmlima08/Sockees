package com.myllenno.sockees.servercontrol;

import com.myllenno.sockees.management.User;
import com.myllenno.sockees.report.HandlerDialog;
import com.myllenno.sockees.requests.RequestGroup;
import com.myllenno.sockees.requests.RequestSimple;

import java.util.ArrayList;
import java.util.logging.Handler;

public class WriteRequests {
	
	/**
     * Classe de comunicação do evento ocorrido.
     */
    private HandlerDialog handlerDialog;
    	
	/**
     * Lista de requisições que não puderão ser enviadas.
     */
    private ArrayList<Object> listRequestsNotSend;
        
    /**
     * Retorna a lista com todas as requisições não enviadas.
     * Remove as requisições da lista de requisições não enviadas.
     *
     * @return
     */
    public ArrayList<Object> getAllRequestsNotSend() {
        return listRequestsNotSend;
    }
    
    /**
     * Método construtor.
     * 
     * @param handler
     */
    public WriteRequests(Handler handler){
    	listRequestsNotSend = new ArrayList<>();
    	handlerDialog = new HandlerDialog(handler);
    }
    
    /**
     * Remove todas as requisições não enviadas da lista.
     *
     * @return
     */
    public void clearAllRequestsNotSend() {
        listRequestsNotSend.clear();
    }
	
	/**
     * Envia apenas uma requisição para uma lista de clientes.
     * Pode ser usado em um thread.
     *
     * @param request
     */
    public boolean write(Object request) {
    	boolean status = false;

        // Verifica se é uma requisição simples.
        if (request instanceof RequestSimple) {
            RequestSimple requestSimple = (RequestSimple) request;
            // Primeiro passo: Verifica se o cliente está disponível.
            if (requestSimple.getUser().isAvailable()) {
                // Segundo passo: Envia a requisição.
                requestSimple.getUser().sendRequest(requestSimple.getObject());
                status = true;
            // Caso o cliente não esteja disponível.
            } else {
                // Guarda a requisição não enviada.
                listRequestsNotSend.add(requestSimple);
                handlerDialog.publishInfo(handlerDialog.DATA_NOT_SEND);
            }

        // Verifica se é uma requisição em grupo.
        } else if (request instanceof RequestGroup) {

            RequestGroup requestGroup = (RequestGroup) request;
            // Primeiro passo: Percorre a lista de clientes do grupo.
            for (int i = 0; i < requestGroup.getListUsers().size(); i++) {
                // Segundo passo: Verifica se o cliente está disponível.
                if (requestGroup.getListUsers().get(i).isAvailable()) {
                    // Terceiro passo: Envia a requisição.
                    requestGroup.getListUsers().get(i).sendRequest(requestGroup.getObject());
                    status = true;
                // Caso o cliente não esteja disponível.
                } else {
                    // Guarda a requisição não enviada.
                    RequestSimple requestSimple = new RequestSimple(requestGroup.getListUsers().get(i), requestGroup.getObject());
                    listRequestsNotSend.add(requestSimple);
                    handlerDialog.publishInfo(handlerDialog.DATA_NOT_SEND);
                }
            }
        // Verifica se é uma requisição desconhecida.
        } else {
        	handlerDialog.publishInfo(handlerDialog.REQUESTS_UNKNOWN);
        }
        return status;
    }

    /**
     * Envia as requisições para todos os clientes da lista de clientes.
     * Pode ser usado em um thread.
     * 
     * @param listRequests
     */
    public void writeAll(ArrayList<Object> listRequests) {
        // Primeiro passo: Percorre a lista de requisições enquanto houver requisição.
        while (!listRequests.isEmpty()) {
            try {
                // Segundo passo: Envia para o método de envio da requisição.
                write(listRequests.get(0));
                // Terceiro passo: Remove da lista a requisição enviada.
                listRequests.remove(0);
            } catch (Exception e) {
            	handlerDialog.publishSevere(e.toString());
                e.printStackTrace();
            }
        }
        handlerDialog.publishInfo(handlerDialog.REQUESTS_SENT_ALL);
    }
    
    /**
     * Envia um objeto para todos os clientes conectados.
     * 
     * @param request
     */
    public void writeDataAllUsers(ArrayList<User> listUsers, Object object){
    	// Primeiro passo: Percorre a lista de gerenciamento de clientes.
    	for (int i=0 ; i < listUsers.size(); i++){
    		// Segundo passo: Envia a requisição para cada cliente.
    		listUsers.get(i).sendRequest(object);
    	}
    }
}
