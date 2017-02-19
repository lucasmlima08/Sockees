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
import com.myllenno.sockees.requests.RequestSimple;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;

public class ControlUsers {

	private HandlerDialog handlerDialog;
	private ArrayList<User> listUsers;
	private ExecutorService executorService;

	public ControlUsers(Handler handler) {
		handlerDialog = new HandlerDialog(handler);
		listUsers = new ArrayList<>();
		executorService = Executors.newSingleThreadExecutor();
	}

	public void addUserToControl(User user) { 								// Adiciona um usuário para a lista de controle.
		listUsers.add(user);
	}

	public ArrayList<User> getAllUsers() {
		return listUsers;
	}

	public void startCommunicationFlowWithUser(User user) { 				// Inicia o envio/recebimento de requisições do usuário.
		executorService.submit(user.receiveRequests(RequestSimple.class));
		executorService.submit(user.sendRequests());
	}
	
	public void stopCommunicationFlowWithUser(User user) { 					// Finaliza o envio/recebimento de requisições do usuário.
		user.closeConnection();
	}

	public void refreshUsers() { 											// Atualiza a lista de usuários disponíveis.
		for (int i = 0; i < listUsers.size(); i++) {
			if (!listUsers.get(i).isAvailable()) {
				removeUserByIndex(i);
				i--;
			}
		}
	}

	public void removeUserById(int id) { 									// Remove o usuário pelo identificador.
		int index = -1;
		for (int i = 0; i < listUsers.size(); i++) {
			if (listUsers.get(i).getId() == id) {
				index = i;
				break;
			}
		}
		if (index != -1) {
			removeUserByIndex(index);
		}
	}

	private void removeUserByIndex(int index) { 							// Remove o usuário pela posição da lista.
		if (listUsers.size() > index) {
			listUsers.remove(index);
			handlerDialog.publishInfo(handlerDialog.USER_REMOVED);
		}
	}

	public void closeAllUsers() { 												// Fecha a conexão com todos os usuários e apaga a lista.
		for (int i = 0; i < listUsers.size(); i++) {
			listUsers.get(i).closeConnection();
		}
		listUsers.clear();
	}
}
