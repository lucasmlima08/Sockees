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

import com.myllenno.sockees.servercontrol.ConnectionServer;
import com.myllenno.sockees.servercontrol.AuthenticateUsers;
import com.myllenno.sockees.servercontrol.ReceiveUsers;
import com.myllenno.sockees.servercontrol.ControlUsers;
import com.myllenno.sockees.servercontrol.WriteRequests;
import com.myllenno.sockees.servercontrol.ReadRequests;

import com.myllenno.sockees.management.User;
import com.myllenno.sockees.requests.RequestSimple;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.logging.Handler;

public class Server {

	private Handler handler;
	private ConnectionServer connectionServer;
	private AuthenticateUsers authenticateUsers;
	private ReceiveUsers receiveUsers;
	private ControlUsers controlUsers;
	private WriteRequests writeRequests;
	private ReadRequests readRequests;

	public Server(Handler handler) {
		connectionServer = new ConnectionServer(handler);
		controlUsers = new ControlUsers(handler);
		this.handler = handler;
	}
	
	public ArrayList<User> getAllUsersAuthenticated() {									// Retorna a lista de usuários autenticados.
		return controlUsers.getAllUsers();
	}
	
	public boolean isAvailable() {														// Verifica se o servidor está disponível
		return connectionServer.isAvailable();
	}
	
	public void openConnection(int port) {												// Abre a conexão do servidor.
		connectionServer.openConnection(port);
	}

	public Callable<User> receiveUsers() {												// Retorna a classe de recebimento de usuários.
		receiveUsers = new ReceiveUsers(handler, connectionServer.getServer());
		return receiveUsers;
	}

	public Callable<User> authenticateUser(User user, int timeToAuthentication) {		// Retorna a classe de autenticação de usuários.
		authenticateUsers = new AuthenticateUsers(handler, user);
		return authenticateUsers;
	}
	
	public ControlUsers controlUsers() {												// Retorna a classe de controle de usuários.
		return controlUsers;
	}
	
	public void startCommunicationFlowWithUser(User userAuthenticated) {				// Inicia o envio/recebimento de requisições do usuário.
		controlUsers.startCommunicationFlowWithUser(userAuthenticated);
		controlUsers.addUserToControl(userAuthenticated);
	}
	
	public void stopCommunicationFlowWithUser(User userAuthenticated) {					// Finaliza o envio/recebimento de requisições do usuário.
		controlUsers.stopCommunicationFlowWithUser(userAuthenticated);
	}

	public Runnable readRequests() {													// Retorna a classe de leitura de requisições.
		readRequests = new ReadRequests(handler, controlUsers);
		return readRequests;
	}

	public void writeRequestAllUsers(RequestSimple request) {							// Escreve uma requisição para todos os usuários.
		writeRequests.writeToAllUsers(controlUsers.getAllUsers(), request);
	}

	public Runnable writeRequests() {													// Retorna a classe de escrita de requisições.
		writeRequests = new WriteRequests(handler, controlUsers, readRequests);
		return writeRequests;
	}

	public void closeConnection() {														// Fecha a conexão do servidor.
		controlUsers.closeAllUsers();
		connectionServer.closeConnection();
	}
}
