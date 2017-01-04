package com.myllenno.sockees.usercontrol;

import com.google.gson.Gson;
import com.myllenno.sockees.report.HandlerDialog;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Handler;

public class SendRequest {

    /**
     * Classe de comunicação do evento ocorrido.
     */
    private HandlerDialog handlerDialog;
    
    /**
     * Lista de requisições para envio.
     */
    private ArrayList<Object> listRequestsSend;

    /**
     * Lista de requisições que não puderão ser enviadas.
     */
    private ArrayList<Object> listRequestsNotSend;
    
    /**
     * Método construtor.
     * 
     * @param handler
     */
    public SendRequest(Handler handler){
        listRequestsSend = new ArrayList<>();
        listRequestsNotSend = new ArrayList<>();
        handlerDialog = new HandlerDialog(handler);
    }

    /**
     * Retorna a lista com todas as requisições que não puderam ser enviadas.
     *
     * @return
     */
    public ArrayList<Object> getAllRequestsNotSend(){
    	return listRequestsNotSend;
    }

    /**
     * Remove todas as requisições não enviadas da lista.
     */
    public void clearAllRequestNotSend(){
    	listRequestsNotSend.clear();
    }
    
    /**
     * Adiciona uma requisição para envio ao servidor.
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
     * @param outputStream
     * @param request
     */
    public boolean send(OutputStream outputStream, Object request) {
    	boolean status = false;
        try {
        	// Primeiro passo: Conversão do objeto para string JSON.
            Gson gson = new Gson();
            String json = gson.toJson(request);
            // Segundo passo: Conversão da string JSON para para array de bytes.
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
     * Envia todas as requisições da lista de requisições.
     * Pode ser usado em um thread.
     * 
     * @param outputStream
     */
    public void sendAll(OutputStream outputStream) {
        // Primeiro passo: Percorre a lista de requisições enquanto houver requisição.
        while (!listRequestsSend.isEmpty()) {
        	send(outputStream, listRequestsSend.get(0));
        	listRequestsSend.remove(0);
        }
        handlerDialog.publishInfo(handlerDialog.REQUESTS_SENT_ALL);
    }
}
