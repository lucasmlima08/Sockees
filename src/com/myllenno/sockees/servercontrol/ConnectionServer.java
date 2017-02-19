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

import com.myllenno.sockees.report.HandlerDialog;

import java.net.ServerSocket;
import java.util.logging.Handler;

public class ConnectionServer {

	private boolean status;
	private HandlerDialog handlerDialog;
	private ServerSocket server;

	public ConnectionServer(Handler handler) {
		server = null;
		status = false;
		handlerDialog = new HandlerDialog(handler);
	}

	public ServerSocket getServer() {
		return server;
	}

	public boolean isAvailable() {
		if (server != null && status) {
			handlerDialog.publishInfo(handlerDialog.SERVER_AVAILABLE);
			return true;
		} else {
			handlerDialog.publishInfo(handlerDialog.SERVER_UNAVAILABLE);
			return false;
		}
	}

	public void openConnection(int port) {
		try {
			server = new ServerSocket(port);
			status = true;
			handlerDialog.publishInfo(handlerDialog.CONNECTION_OPENED);
		} catch (Exception e) {
			handlerDialog.publishSevere(e.toString());
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
			if (isAvailable()) {
				server.close();
				server = null;
			}
			handlerDialog.publishInfo(handlerDialog.CONNECTION_CLOSED);
		} catch (Exception e) {
			handlerDialog.publishSevere(e.toString());
			e.printStackTrace();
		}
		status = false;
	}
}
