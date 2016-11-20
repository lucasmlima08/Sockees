package com.myllenno.sockees.requests;

import java.util.ArrayList;

import com.myllenno.sockees.usercontrol.UserManagement;

public class RequestGroup {

    /**
     * Objeto que foi recebido.
     */
    private Object object;

    /**
     * Clientes que receberão a requisição.
     */
    private ArrayList<UserManagement> listClients;

    public RequestGroup(Object object, ArrayList<UserManagement> listClients){
        this.object = object;
        this.listClients = listClients;
    }

    /**
     * Retorna o objeto que será enviado.
     *
     * @return
     */
    public Object getObject(){
        return object;
    }

    /**
     * Retorna a lista de clientes que receberão a requisição.
     *
     * @return
     */
    public ArrayList<UserManagement> getListClients(){
        return listClients;
    }
}
