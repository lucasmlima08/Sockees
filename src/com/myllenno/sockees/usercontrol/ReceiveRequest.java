package com.myllenno.sockees.usercontrol;

import com.google.gson.Gson;
import com.myllenno.sockees.report.HandlerDialog;
import com.myllenno.sockees.requests.RequestSimple;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Handler;

public class ReceiveRequest {

    /**
     * Classe de comunicação do evento ocorrido.
     */
    private HandlerDialog handlerDialog;

    /**
     * Lista de requisições recebidas.
     */
    private ArrayList<RequestSimple> listRequestsReceived;
    
    /**
     * Método construtor.
     * 
     * @param handler
     */
    public ReceiveRequest(Handler handler){
        listRequestsReceived = new ArrayList<>();
        handlerDialog = new HandlerDialog(handler);
    }
        
    /**
     * Retorna a lista com todas as requisições recebidas.
     *
     * @return
     */
    public ArrayList<RequestSimple> getAllRequestsReceived(){
    	return listRequestsReceived;
    }

    /**
     * Remove todas as requisições recebidas da lista.
     *
     * @return
     */
    public void clearAllRequestsReceived(){
    	listRequestsReceived.clear();
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
     * @return
     */
    public void receive(InputStream inputStream, Object objectType) {
        try {
        	// Primeiro passo: Faz a leitura completa do socket por bytes e guarda como string.
            byte[] bytesReader = new byte[20000];
            int reader = 20000;
            StringBuilder stringBuilder = new StringBuilder();
            while (reader >= 1010) {
                reader = inputStream.read(bytesReader, 0, bytesReader.length);
                stringBuilder.append(new String(bytesReader, 0, reader, "UTF-8"));
            }
            // Segundo passo: Verifica se algo foi recebido.
            if (stringBuilder.length() > 0) {
                // Terceiro passo: Converte a string de JSON para o objeto especificado.
                Gson gson = new Gson();
                RequestSimple requestSimple = gson.fromJson(stringBuilder.toString(), objectType.getClass());
                // Quarto passo: Adiciona a requisição a lista de requisições recebidas.
                listRequestsReceived.add(requestSimple);
                handlerDialog.publishInfo(handlerDialog.DATA_RECEIVED);
            // Caso não haja requisição recebida.
            } else {
            	handlerDialog.publishInfo(handlerDialog.DATA_EMPTY);
            }
        } catch (Exception e){
        	handlerDialog.publishSevere(e.toString());
            e.printStackTrace();
        }
    }
}
