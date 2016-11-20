package com.myllenno.sockees.usercontrol;

import com.myllenno.sockees.report.HandlerDialog;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.logging.Handler;

public class Authenticate {

    /**
     * Classe de comunicação do evento ocorrido.
     */
    private HandlerDialog handlerDialog;
        
    /**
     * Método construtor.
     * 
     * @param handler
     */
    public Authenticate(Handler handler){
    	handlerDialog = new HandlerDialog(handler);
    }
    
    /**
     * Realiza a autenticação e recebe a resposta.
     * 
     * @param idUser
     * @param inputStream
     * @param outputStream
     * @return
     */
    public boolean authenticate(int idUser, InputStream inputStream, OutputStream outputStream) {
    	try {
	        // Primeiro passo: Solicita uma autenticação ao servidor.
	        PrintStream printStream = new PrintStream(outputStream);
	        printStream.println(idUser);
	        // Segundo passo: Aguarda a resposta do servidor.
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
	        String read = bufferedReader.readLine();
	        // Terceiro passo: Verifica se foi aceito pelo servidor.
	        if (read.equals("accepted")){
	        	handlerDialog.publishInfo(handlerDialog.USER_AUTHENTICATED);
	            return true;
	        } else if (read.equals("timeout")){
	        	handlerDialog.publishInfo(handlerDialog.TIMEOUT_FOR_AUTHENTICATION);
	        } else {
	        	handlerDialog.publishInfo(handlerDialog.USER_NOT_AUTHENTICATED);
	        }
    	} catch (Exception e){
    		handlerDialog.publishSevere(e.toString());
    		e.printStackTrace();
    	}
    	return false;
    }
}
