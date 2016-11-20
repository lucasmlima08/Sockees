package com.myllenno.sockees.servercontrol;

import com.myllenno.sockees.management.User;
import com.myllenno.sockees.report.HandlerDialog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.logging.Handler;

public class AuthenticateUsers {

    /**
     * Classe de comunica��o do evento ocorrido.
     */
    private HandlerDialog handlerDialog;
    
    /**
     * M�todo construtor.
     * 
     * @param handler
     */
    public AuthenticateUsers(Handler handler){
        handlerDialog = new HandlerDialog(handler);
    }
    
    /**
     * Realiza a autentica��o do cliente no servidor.
     * 
     * @param user
     * @param timeToAuthentication
     * @return
     */
    public User authentication(User user, int timeToAuthentication){
    	boolean available = false;
		long startTime = System.currentTimeMillis();
		try {
			PrintStream printStream = new PrintStream(user.getOutputStream());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(user.getInputStream(), "UTF-8"));
			// Faz a leitura do identificador do cliente.
			while (!available) {
				String read = bufferedReader.readLine();
				// Verifica se o cliente n�o tem autoriza��o.
				if (read.equals("blocked")){
					printStream.println("blocked");
					available = true;
					handlerDialog.publishInfo(handlerDialog.USER_NOT_AUTHENTICATED);
					return null;
				}
				// Verifica se leu o id do cliente. (Se leu ele ser� autenticado).
				if (!read.equals("")){
					//int idClient = Integer.parseInt(read);
					printStream.println("accepted");
					available = true;
					handlerDialog.publishInfo(handlerDialog.USER_AUTHENTICATED);
					return user;
				}
				// verifica se esgotou o tempo de verifica��o da autentica��o.
				if ((System.currentTimeMillis() - startTime) >= timeToAuthentication){
					printStream.println("timeout");
					available = true;
					handlerDialog.publishInfo(handlerDialog.TIMEOUT_FOR_AUTHENTICATION);
					return null;
				}
			}
		} catch (Exception e){
			handlerDialog.publishSevere(e.toString());
			e.printStackTrace();
		}
		return null;
	}
}
