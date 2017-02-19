/*
 * Copyright (C) Lucas Myllenno S M Lima. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.myllenno.sockees.servercontrol;

import com.myllenno.sockees.management.User;
import com.myllenno.sockees.report.HandlerDialog;
import com.myllenno.sockees.requests.Authentication;
import com.myllenno.sockees.usercontrol.ReceiveRequest;
import com.myllenno.sockees.usercontrol.SendRequest;

import java.util.concurrent.Callable;
import java.util.logging.Handler;

public class AuthenticateUsers implements Callable<User> {

	private HandlerDialog handlerDialog;
	private User user;

	public AuthenticateUsers(Handler handler, User user) {
		handlerDialog = new HandlerDialog(handler);
		this.user = user;
	}
	
	private void sendRequestToAuthenticate(Authentication authentication) throws Exception {	// Envia a requisição de autenticação para o servidor.
		SendRequest sendRequest = new SendRequest(null, user.getConnectionUser());
		sendRequest.sendRequest(authentication);
	}
	
	private Authentication receiveRequestToAuthenticate() throws Exception {					// Recebe a resposta do servidor para a autenticação.
		ReceiveRequest receiveRequest = new ReceiveRequest(
				null, user.getConnectionUser(), Authentication.class);
		Authentication authenticate = (Authentication) receiveRequest.receiveRequestObject();	
		return authenticate;
	}
	
	/**
	 * Autentica o usuário e retorna o usuário autenticado.
	 */
	@Override
	public User call() throws Exception {
		User userAuthenticated = null;
		Authentication authenticateResponse = null;
		try {
			while (authenticateResponse == null) {
				authenticateResponse = receiveRequestToAuthenticate();						// Recebe a requisição de autenticação.
				if (authenticateResponse != null) {											// Verifica se recebeu a requisição.
					if (authenticateResponse.getStatus().equals(handlerDialog.REQUIRED)) {	// Verifica se solicitou a requisição.
						authenticateResponse.refreshStatus(handlerDialog.ACCEPTED); 			// Atualiza o status da requisição.
						user.setId(authenticateResponse.getIdUser());							// Guarda o ID do usuário.
						handlerDialog.publishInfo(handlerDialog.USER_AUTHENTICATED);			
						userAuthenticated = user;												// Informa o usuário autenticado.
					}
				}
			}
			sendRequestToAuthenticate(authenticateResponse);								// Envia a resposta de autenticação.
			return userAuthenticated;														// Retorna o usuário autenticado.
		} catch (Exception e) {
			handlerDialog.publishInfo(handlerDialog.USER_NOT_AUTHENTICATED);
			handlerDialog.publishSevere(e.toString());
			e.printStackTrace();
		}
		return userAuthenticated;
	}
}
