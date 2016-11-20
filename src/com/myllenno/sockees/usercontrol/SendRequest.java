package com.myllenno.sockees.usercontrol;

import com.google.gson.Gson;
import com.myllenno.sockees.report.HandlerDialog;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Handler;

public class SendRequest {

    /**
     * Classe de comunica��o do evento ocorrido.
     */
    private HandlerDialog handlerDialog;
    
    /**
     * Lista de requisi��es para envio.
     */
    private ArrayList<Object> listRequestsSend;

    /**
     * Lista de requisi��es que n�o puder�o ser enviadas.
     */
    private ArrayList<Object> listRequestsNotSend;
    
    /**
     * M�todo construtor.
     * 
     * @param handler
     */
    public SendRequest(Handler handler){
        listRequestsSend = new ArrayList<>();
        listRequestsNotSend = new ArrayList<>();
        handlerDialog = new HandlerDialog(handler);
    }

    /**
     * Retorna a lista com todas as requisi��es que n�o puderam ser enviadas.
     *
     * @return
     */
    public ArrayList<Object> getAllRequestsNotSend(){
    	return listRequestsNotSend;
    }

    /**
     * Remove todas as requisi��es n�o enviadas da lista.
     *
     * @return
     */
    public void clearAllRequestNotSend(){
    	listRequestsNotSend.clear();
    }
    
    /**
     * Adiciona uma requisi��o para envio ao servidor.
     * 
     * @param request
     */
    public void addRequestSend(Object request){
    	listRequestsSend.add(request);
    }
    
    /**
     * Recebe um objeto para envio ao cliente.
     * Codifica para JSON e em seguida converte para bytes.
     * Escreve os bytes no socket do cliente.
     *
     * @param object
     */
    public boolean send(OutputStream outputStream, Object request) {
    	boolean status = false;
        try {
        	// Primeiro passo: Convers�o do objeto para string JSON.
            Gson gson = new Gson();
            String json = gson.toJson(request);
            // Segundo passo: Convers�o da string JSON para para array de bytes.
            byte[] bytes = json.getBytes();
            // Terceiro passo: Escrever o array de bytes no socket do cliente.
            outputStream.write(bytes);
            status = true;
            handlerDialog.publishInfo(handlerDialog.DATA_SENT);
        } catch (Exception e){
        	handlerDialog.publishSevere(e.toString());
            e.printStackTrace();
        }
        return status;
    }
    
    /**
     * Envia todas as requisi��es da lista de requisi��es.
     * Pode ser usado em um thread.
     */
    public void sendAll(OutputStream outputStream) {
        // Primeiro passo: Percorre a lista de requisi��es enquanto houver requisi��o.
        while (!listRequestsSend.isEmpty()) {
        	send(outputStream, listRequestsSend.get(0));
        	listRequestsSend.remove(0);
        }
        handlerDialog.publishInfo(handlerDialog.REQUESTS_SENT_ALL);
    }
}
