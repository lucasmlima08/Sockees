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

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.logging.Handler;

public class ReceiveUsers implements Callable<User> {

	private HandlerDialog handlerDialog;
	private Handler handler;
	private ServerSocket server;

	public ReceiveUsers(Handler handler, ServerSocket server) {
		handlerDialog = new HandlerDialog(handler);
		this.handler = handler;
		this.server = server;
	}

	/**
	 * Retorna novos clientes que venham a se conectar.
	 *
	 * @return
	 * @exception
	 */
	@Override
	public User call() throws Exception {
		try {
			Socket socket = server.accept();
			User user = new User(handler, socket);
			handlerDialog.publishInfo(handlerDialog.USER_RECEIVED);
			return user;
		} catch (Exception e) {
			handlerDialog.publishSevere(e.toString());
			e.printStackTrace();
		}
		return null;
	}
}
