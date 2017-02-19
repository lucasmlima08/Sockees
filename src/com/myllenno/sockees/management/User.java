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

package com.myllenno.sockees.management;

import com.myllenno.sockees.usercontrol.ConnectionUser;
import com.myllenno.sockees.requests.RequestSimple;
import com.myllenno.sockees.usercontrol.Authenticate;
import com.myllenno.sockees.usercontrol.ReceiveRequest;
import com.myllenno.sockees.usercontrol.SendRequest;

import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.logging.Handler;

public class User {

	private Handler handler;
	private ConnectionUser connectionUser;
	private Authenticate authenticate;
	private ReceiveRequest receiveRequest;
	private SendRequest sendRequest;

	public User(Handler handler, Socket user) { 				// Chamado pelo servidor. (Quando recebe o novo cliente, antes da autenticação)..
		connectionUser = new ConnectionUser(user, handler);
		this.handler = handler;
	}

	public User(Handler handler, int idUser) { 					// Chamado pelo cliente.
		connectionUser = new ConnectionUser(idUser, handler);
		this.handler = handler;
	}

	public void setId(int idUser) {
		connectionUser.setId(idUser);
	}

	public int getId() {
		return connectionUser.getId();
	}

	public boolean isAvailable() {
		return connectionUser.isAvailable();
	}
	
	public void addRequestToSend(RequestSimple requestSimple) {						// Adiciona uma requisição para envio ao usuário.
		sendRequest.addRequestToSend(requestSimple);
	}
	
	public Object getFirstRequestReceived() {										// Retorna a primeira requisição do usuário recebida pelo servidor.
		return receiveRequest.getFirstRequestReceived();
	}
	
	public ConnectionUser getConnectionUser() {										// Retorna a classe de conexão do usuário.
		return connectionUser;
	}
	
	public void openConnection(String ipServer, int portServer) {					// Abre a conexão do usuário.
		connectionUser.openConnection(ipServer, portServer);
	}

	public Callable<Boolean> authenticate(int idUser) {								// Retorna a classe de autenticação.
		authenticate = new Authenticate(handler, connectionUser);
		return authenticate;
	}

	public Runnable sendRequests() {												// Retorna a classe de envio de requisições.
		sendRequest = new SendRequest(handler, connectionUser);
		return sendRequest;
	}

	public Runnable receiveRequests(Object requestType) {							// Retorna a classe de recebimento de requisições.
		receiveRequest = new ReceiveRequest(handler, connectionUser, requestType);
		return receiveRequest;
	}
	
	public void closeConnection() {													// Fecha a conexão do cliente.
		sendRequest.setStatus(false);
		receiveRequest.setStatus(false);
		connectionUser.closeConnection();
	}
}
