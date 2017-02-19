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
import com.myllenno.sockees.requests.RequestGroup;
import com.myllenno.sockees.requests.RequestSimple;

import java.util.ArrayList;
import java.util.logging.Handler;

public class WriteRequests implements Runnable {

	private boolean status;
	private HandlerDialog handlerDialog;
	private ControlUsers controlUsers;
	private ReadRequests readRequests;

	public WriteRequests(Handler handler) {
		status = false;
		handlerDialog = new HandlerDialog(handler);
	}
	
	public WriteRequests(Handler handler, ControlUsers controlUsers, ReadRequests readRequests) {
		status = false;
		handlerDialog = new HandlerDialog(handler);
		this.controlUsers = controlUsers;
		this.readRequests = readRequests;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public boolean getStatus() {
		return status;
	}

	/**
	 * Escreve uma requisi��o para envio ao usu�rio requisitado.
	 * 
	 * @param listUsers
	 * @param request
	 */
	public void writeRequest(ArrayList<User> listUsers, Object request) {
		if (request instanceof RequestSimple) { 							// Requisi��o do Simples.
			RequestSimple requestSimple = (RequestSimple) request;
			writeRequestSimple(listUsers, requestSimple);
		} else if (request instanceof RequestGroup) { 						// Requisi��o em Grupo.
			RequestGroup requestGroup = (RequestGroup) request;
			writeRequestGroup(listUsers, requestGroup);
		} else {
			handlerDialog.publishInfo(handlerDialog.REQUEST_UNKNOWN);
		}
	}
	
	private void writeRequestSimple(ArrayList<User> listUsers, RequestSimple requestSimple) {	// Escreve a requisi��o simples no socket de todos os usu�rios requisitados.
		for (User user : listUsers) { 										// Percorre a lista de clientes conectado.
			if (user != null && user.isAvailable()) { 						// Verifica se o cliente est� dispon�vel.
				if (user.getId() == requestSimple.getIdUserReceiver()) { 	// Verifica se � o cliente requisitado.
					user.addRequestToSend(requestSimple); 					// Adiciona a requisi��o para o envio ao cliente.
				}
			}
		}
	}
	
	private void writeRequestGroup(ArrayList<User> listUsers, RequestGroup requestGroup) {		// Escreve a requisi��o em grupo no socket de todos os usu�rios requisitados.
		for (User user : listUsers) { 										// Percorre a lista de clientes conectado.
			if (user != null && user.isAvailable()) { 						// Verifica se o cliente est� dispon�vel.
				for (int id : requestGroup.getIdsUsersReceivers()) { 		// Percorre a lista de clientes requisitados.
					if (user.getId() == id) { 								// Verifica se � um cliente requisitado.
						RequestSimple requestSimple = new RequestSimple( 	// Cria uma requisi��o simples para envio.
								requestGroup.getIdUserSender(), 				// ID do usu�rio remetente.
								user.getId(), 									// ID do usu�rio receptor.
								requestGroup.getClassTypeRequestString(),		// Tipo da classe da requisi��o em string.
								requestGroup.getRequestStringJson(), 			// Objeto da requisi��o em string json.
								requestGroup.getStatus());						// Status da requisi��o.
						user.addRequestToSend(requestSimple); 				// Adiciona a requisi��o para o envio ao cliente.
						break;
					}
				}
			}
		}
	}

	public void writeToAllUsers(ArrayList<User> listUsers, RequestSimple requestSimple) {			// Escreve a requisi��o no socket de todos os usu�rios autenticados.
		for (User user : listUsers) {
			if (user != null && user.isAvailable()) {
				user.addRequestToSend(requestSimple);
			}
		}
	}

	/**
	 * Faz a escrita de todas as requisi��es no socket dos receptores.
	 */
	@Override
	public void run() {
		while (status == true) {
			Object request = readRequests.getFirstRequestRead();
			if (request != null) {
				writeRequest(controlUsers.getAllUsers(), request);
			}
		}
	}
}
