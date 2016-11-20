package com.myllenno.sockees.requests;

import com.myllenno.sockees.management.User;

import java.util.ArrayList;

public class RequestGroup {
	
	/**
     * Clientes que receberão a requisição.
     */
    private ArrayList<User> listUsers;

    /**
     * Objeto que foi recebido.
     */
    private Object object;

    /**
     * Método construtor.
     * 
     * @param listClients
     * @param object
     */
    public RequestGroup(ArrayList<User> listUsers, Object object){
        this.object = object;
        this.listUsers = listUsers;
    }
    
    /**
     * Retorna a lista de clientes que receberão a requisição.
     *
     * @return
     */
    public ArrayList<User> getListUsers(){
        return listUsers;
    }

    /**
     * Retorna o objeto que será enviado.
     *
     * @return
     */
    public Object getObject(){
        return object;
    }
}
