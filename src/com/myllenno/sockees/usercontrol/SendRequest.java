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

public class SendRequest implements Runnable {

	private boolean status;
    private HandlerDialog handlerDialog;
    private ConnectionUser connectionUser;
    private ArrayList<Object> listRequestsToSend;

    public SendRequest(Handler handler, ConnectionUser connectionUser){
    	status = false;
        handlerDialog = new HandlerDialog(handler);
        this.connectionUser = connectionUser;
        listRequestsToSend = new ArrayList<>();
    }
    
    public void addRequestToSend(Object request) {
    	listRequestsToSend.add(request);
    }
    
    public void setStatus(boolean status) {
		this.status = status;
	}
	
	public boolean getStatus() {
		return status;
	}
    
	public void clearAllRequestsToSend() {
		listRequestsToSend.clear();
	}
	
	public void sendRequest(Object request) throws Exception { 		// Envia a requisição para o servidor em formato JSON.
		String json = objectToJson(request);
		byte[] bytes = json.getBytes();
        connectionUser.getOutputStream().write(bytes);
	}
    
    public void sendRequest(String json) throws Exception { 		// Envia a requisição para o servidor em formato JSON.
    	byte[] bytes = json.getBytes();
        connectionUser.getOutputStream().write(bytes);
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
	private String objectToJson(Object objectRequest) throws Exception {
		Gson gson = new Gson();
        String json = gson.toJson(objectRequest);
		return json;
	}

	/**
	 * Envia as requisições da lista de requisições para envio.
	 */
	@Override
	public void run() {
		while (status) {
			if (!listRequestsToSend.isEmpty()) {
	            Object firstRequest = listRequestsToSend.get(0);
	            try {
	            	sendRequest(firstRequest);
	            	listRequestsToSend.remove(0);
	                handlerDialog.publishInfo(handlerDialog.DATA_SENT);
	            } catch (Exception e) {
					handlerDialog.publishSevere(e.toString());
					e.printStackTrace();
	            }
	        }
		}
		status = false;
	}
}
