package com.myllenno.sockees.requests;

import com.myllenno.sockees.usercontrol.UserManagement;

public class RequestSimple {

    /**
     * Clientes que receber�o a requisi��o.
     */
    private Object object;

    /**
     * Cliente que enviou a requisi��o.
     */
    private UserManagement client;

    public RequestSimple(UserManagement client, Object object){
    	this.client = client;
        this.object = object;
    }

    /**
     * Retorna o objeto que foi recebido.
     *
     * @return
     */
    public Object getObject(){
        return object;
    }

    /**
     * Retorna o objeto que foi recebido.
     *
     * @return
     */
    public UserManagement getClient(){
        return client;
    }
}
