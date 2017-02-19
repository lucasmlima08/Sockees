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

package com.myllenno.sockees.usercontrol;

import com.myllenno.sockees.report.HandlerDialog;
import com.myllenno.sockees.requests.Authentication;

import java.util.concurrent.Callable;
import java.util.logging.Handler;

public class Authenticate implements Callable<Boolean> {

	private boolean status;
	private HandlerDialog handlerDialog;
	private ConnectionUser connectionUser;

	public Authenticate(Handler handler, ConnectionUser connectionUser) {
		handlerDialog = new HandlerDialog(handler);
		this.connectionUser = connectionUser;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	private void sendRequestToAuthenticate(Authentication authentication) throws Exception {				// Envia a requisição de autenticação para o servidor.
		SendRequest sendRequest = new SendRequest(null, connectionUser);
		sendRequest.sendRequest(authentication);
	}
	
	private Authentication receiveRequestToAuthenticate() throws Exception {								// Recebe a resposta do servidor para a autenticação.
		ReceiveRequest receiveRequest = new ReceiveRequest(null, connectionUser, Authentication.class);
		Authentication authenticate = (Authentication) receiveRequest.receiveRequestObject();	
		return authenticate;
	}
	
	/**
	 * Realiza a autenticação do usuário e retorna o status da autenticação.
	 * 
	 * @param Boolean
	 * @exception
	 */
	@Override
	public Boolean call() throws Exception {
		try {
			Authentication authentication = new Authentication(						// Cria a classe de autenticação.
					connectionUser.getId(), handlerDialog.REQUIRED);				
			sendRequestToAuthenticate(authentication);								// Envia a requisição de autenticação.
			Authentication authenticateResponse = null;
			while (authenticateResponse == null) {
				authenticateResponse = receiveRequestToAuthenticate();				// Recebe a requisição de autenticação.
				if (authenticateResponse != null) {
					handlerDialog.publishInfo(handlerDialog.USER_AUTHENTICATED);
					handlerDialog.publishInfo(authenticateResponse.getStatus());
					return true;
				}
			}
		} catch (Exception e) {
			handlerDialog.publishInfo(handlerDialog.USER_NOT_AUTHENTICATED);
			handlerDialog.publishSevere(e.toString());
			e.printStackTrace();
		}
		return false;
	}
}
