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

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Handler;

public class ConnectionUser {

	private boolean status;
	private HandlerDialog handlerDialog;
	private int idUser;
	private Socket user;
	private InputStream inputStream;
	private OutputStream outputStream;
	
	public ConnectionUser(Socket user, Handler handler) {
		defineDataCommunication(user);
		status = false;
		handlerDialog = new HandlerDialog(handler);
	}

	public ConnectionUser(int idUser, Handler handler) {
		user = null;
		status = false;
		handlerDialog = new HandlerDialog(handler);
	}
	
	private void defineDataCommunication(Socket user) {						// Inicia as classes de comunicação com o servidor.
		try {
			this.user = user;
			inputStream = user.getInputStream();
			outputStream = user.getOutputStream();
		} catch (Exception e) {
			handlerDialog.publishSevere(e.toString());
			e.printStackTrace();
		}
	}

	public void setId(int idUser) {
		this.idUser = idUser;
	}

	public int getId() {
		return idUser;
	}
	
	public Socket getUser() {
		return user;
	}

	public InputStream getInputStream() {									// Retorna a classe de recebimento de requisições.
		return inputStream;
	}

	public OutputStream getOutputStream() {									// Retorna a classe de envio de requisições.
		return outputStream;
	}

	public boolean isAvailable() {											// Retorna o status de disponibilidade do usuário.
		if ((user != null) && (user.isConnected()) && status) {
			return true;
		} else {
			handlerDialog.publishInfo(handlerDialog.USER_UNAVAILABLE);
			return false;
		}
	}

	public void openConnection(String ipServer, int portServer) {			// Abre a conexão do usuário.
		try {
			Socket user = new Socket(ipServer, portServer);
			defineDataCommunication(user);
			status = true;
			handlerDialog.publishInfo(handlerDialog.CONNECTION_OPENED);
		} catch (Exception e) {
			handlerDialog.publishSevere(e.toString());
			e.printStackTrace();
		}
	}

	public void closeConnection() {											// Fecha a conexão do usuário.
		try {
			if (isAvailable()) {
				inputStream.close();
				outputStream.close();
				user.close();
				user = null;
			}
			handlerDialog.publishInfo(handlerDialog.CONNECTION_CLOSED);
		} catch (Exception e) {
			handlerDialog.publishSevere(e.toString());
			e.printStackTrace();
		}
		status = false;
	}
}
