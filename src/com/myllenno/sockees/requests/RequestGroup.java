package com.myllenno.sockees.requests;

import com.myllenno.sockees.management.User;

import java.util.ArrayList;

public class RequestGroup {
	
	/**
     * Clientes que receber�o a requisi��o.
     */
    private ArrayList<User> listUsers;

    /**
     * Objeto que foi recebido.
     */
    private Object object;

    /**
     * M�todo construtor.
     * 
     * @param listClients
     * @param object
     */
    public RequestGroup(ArrayList<User> listUsers, Object object){
        this.object = object;
        this.listUsers = listUsers;
    }
    
    /**
     * Retorna a lista de clientes que receber�o a requisi��o.
     *
     * @return
     */
    public ArrayList<User> getListUsers(){
        return listUsers;
    }

    /**
     * Retorna o objeto que ser� enviado.
     *
     * @return
     */
    public Object getObject(){
        return object;
    }
}
