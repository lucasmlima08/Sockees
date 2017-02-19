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

package com.myllenno.sockees.report;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class HandlerDialog {

	public final String ACCEPTED = "ACCEPTED";
	public final String CONNECTION_CLOSED = "CONNECTION CLOSED";
	public final String CONNECTION_OPENED = "CONNECTION OPENED";
	public final String DATA_EMPTY = "DATA EMPTY";
	public final String DATA_NOT_SEND = "DATA NOT SEND";
	public final String DATA_RECEIVED = "DATA RECEIVED";
	public final String DATA_SENT = "DATA SENT";
	public final String EMPTY = "EMPTY";
	public final String PROCESSING = "PROCESSING";
	public final String RECEIVED = "RECEIVED";
	public final String REJECTED = "REJECTED";
	public final String REQUESTS_RECEIVED_ALL = "REQUESTS RECEIVED ALL";
	public final String REQUESTS_SENT_ALL = "REQUESTS SENT ALL";
	public final String REQUEST_UNKNOWN = "REQUESTS UNKNOWN";
	public final String REQUIRED = "REQUIRED";
	public final String SENDING = "SENDING";
	public final String SERVER_AVAILABLE = "SERVER AVAILABLE";
	public final String SERVER_UNAVAILABLE = "SERVER UNAVAILABLE";
	public final String TIMEOUT = "TIMEOUT";
	public final String TIMEOUT_FOR_AUTHENTICATION = "TIMEOUT FOR AUTHENTICATION";
	public final String UNACCEPTED = "UNACCEPTED";
	public final String UNAVAILABLE = "UNAVAILABLE";
	public final String USER_AUTHENTICATED = "USER AUTHENTICATED";
	public final String USER_NOT_AUTHENTICATED = "USER NOT AUTHENTICATED";
	public final String USER_OFFLINE = "USER OFFLINE";
	public final String USER_RECEIVED = "USER RECEIVED";
	public final String USER_REFUSED = "USER REFUSED";
	public final String USER_REMOVED = "USER REMOVED";
	public final String USER_UNAVAILABLE = "USER UNAVAILABLE";

	private Handler handler;

	public HandlerDialog(Handler handler) {
		this.handler = handler;
	}

	public void publishInfo(String info) {
		if (handler != null) {
			handler.publish(new LogRecord(Level.INFO, info));
		}
	}

	public void publishSevere(String severe) {
		if (handler != null) {
			handler.publish(new LogRecord(Level.SEVERE, severe));
		}
	}

	public void publishWarning(String warning) {
		if (handler != null) {
			handler.publish(new LogRecord(Level.WARNING, warning));
		}
	}

	public void publishConfig(String config) {
		if (handler != null) {
			handler.publish(new LogRecord(Level.CONFIG, config));
		}
	}
}
