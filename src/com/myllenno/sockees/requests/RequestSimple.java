package com.myllenno.sockees.requests;

import com.myllenno.sockees.management.User;

public class RequestSimple {
	
	/**
     * Cliente que enviou a requisi��o.
     */
    private User user;

    /**
     * Clientes que receber�o a requisi��o.
     */
    private Object object;

    /**
     * M�todo construtor.
     * 
     * @param user
     * @param object
     */
    public RequestSimple(User user, Object object){
    	this.user = user;
        this.object = object;
    }
    
    /**
     * Retorna o objeto que foi recebido.
     *
     * @return
     */
    public User getUser(){
        return user;
    }

    /**
     * Retorna o objeto que foi recebido.
     *
     * @return
     */
    public Object getObject(){
        return object;
    }
}
