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

import com.google.gson.Gson;
import com.myllenno.sockees.report.HandlerDialog;

import java.util.ArrayList;
import java.util.logging.Handler;

public class ReceiveRequest implements Runnable {

	private boolean status;
	private HandlerDialog handlerDialog;
	private ConnectionUser connectionUser;
	private Object requestType;
	private ArrayList<Object> listRequestsReceived;

	public ReceiveRequest(Handler handler, ConnectionUser connectionUser, Object requestType) {
		status = false;
		handlerDialog = new HandlerDialog(handler);
		this.connectionUser = connectionUser;
		this.requestType = requestType;
		listRequestsReceived = new ArrayList<>();
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public boolean getStatus() {
		return status;
	}

	public Object getFirstRequestReceived() {
		if (!listRequestsReceived.isEmpty()) {
			Object request = listRequestsReceived.get(0);
			listRequestsReceived.remove(0);
			return request;
		}
		return null;
	}

	public void clearRequestsReceived() {
		listRequestsReceived.clear();
	}

	/**
	 * Recebe a requisição do servidor em formato JSON.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String receiveRequestJson() throws Exception {											// Recebe a requisição em formato JSON.
		byte[] bytesReader = new byte[2000];
		int reader = 2000;
		StringBuilder stringBuilder = new StringBuilder();
		while (reader >= 1010) {
			reader = connectionUser.getInputStream().read(bytesReader, 0, bytesReader.length);
			stringBuilder.append(new String(bytesReader, 0, reader, "UTF-8"));
		}
		return stringBuilder.toString();
	}
	
	public Object receiveRequestObject() throws Exception {											// Recebe a requisição em formato Object.
		String json = receiveRequestJson();
		Object request = jsonToObject(json);
		return request;
	}
	
	/*
     * Copyright (C) 2008 Google Inc.
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     * http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     * 
     * https://github.com/google/gson
     */
	private Object jsonToObject(String json) throws Exception {
		Gson gson = new Gson();
		Object object = gson.fromJson(json, requestType.getClass());
		return object;
	}

	/**
	 * Recebe as requisições e inclui na lista de requisições recebidas.
	 */
	@Override
	public void run() {
		while (status) {
			try {
				Object objectRequest = receiveRequestObject();
				if (objectRequest != null) {
					listRequestsReceived.add(objectRequest);
					handlerDialog.publishInfo(handlerDialog.DATA_RECEIVED);
				}
			} catch (Exception e) {
				handlerDialog.publishInfo(handlerDialog.DATA_EMPTY);
				handlerDialog.publishSevere(e.toString());
				e.printStackTrace();
			}
		}
		status = false;
	}
}
