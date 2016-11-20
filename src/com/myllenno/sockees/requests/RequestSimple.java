package com.myllenno.sockees.requests;

import com.myllenno.sockees.management.User;

public class RequestSimple {
	
	/**
     * Cliente que enviou a requisição.
     */
    private User user;

    /**
     * Clientes que receberão a requisição.
     */
    private Object object;

    /**
     * Método construtor.
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
