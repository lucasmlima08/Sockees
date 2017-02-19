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

package com.myllenno.sockees.requests;

import java.util.ArrayList;

public class RequestGroup {

	/**
	 * Status da requisição.
	 * SENDING: Requisição enviada pelo remetente.
	 * PROCESSING: Requisição recebida pelo servidor.
	 * RECEIVED: Requisição recebida pelo receptor.
	 * USER OFFLINE: Usuário offline.
	 */
	private String status;
	
	private int idUserSender;						// IDs do usuários remetentes.
	private ArrayList<Integer> idsUsersReceivers; 	// IDs do usuários receptores.
	private String classTypeRequestString; 			// Tipo da classe da requisição em string.
	private String requestStringJson; 				// Objeto da requisição em string json.

	public RequestGroup(int idUserSender, ArrayList<Integer> idsUsersReceivers, String classTypeRequestString, String requestStringJson, String status) {
		this.idUserSender = idUserSender;
		this.idsUsersReceivers = idsUsersReceivers;
		this.classTypeRequestString = classTypeRequestString;
		this.requestStringJson = requestStringJson;
		this.status = status;
	}

	public void refreshStatus(String status) {
		this.status = status;
	}

	public int getIdUserSender() {
		return idUserSender;
	}

	public ArrayList<Integer> getIdsUsersReceivers() {
		return idsUsersReceivers;
	}

	public String getClassTypeRequestString() {
		return classTypeRequestString;
	}
	
	public String getRequestStringJson() {
		return requestStringJson;
	}

	public String getStatus() {
		return status;
	}
}
