package com.myllenno.sockees.servercontrol;

import com.myllenno.sockees.management.User;
import com.myllenno.sockees.report.HandlerDialog;

import java.util.ArrayList;
import java.util.logging.Handler;

public class ControlUsers {

    /**
     * Classe de comunicação do evento ocorrido.
     */
    private HandlerDialog handlerDialog;

    /**
     * Lista de usuários conectados.
     */
    private ArrayList<User> listUsers;

    /**
     * Método construtor.
     * 
     * @param handler
     */
    public ControlUsers(Handler handler){
        handlerDialog = new HandlerDialog(handler);
        listUsers = new ArrayList<>();
    }
    
    /**
     * Retorna a lista com todos os usuários conectados.
     *
     * @return
     */
    public ArrayList<User> getAllUsers(){
        return listUsers;
    }
    
    /**
     * Responsável por adicionar um novo usuário a lista de usuários.
     * 
     * @param user
     */
    public void addUser(User user){
    	listUsers.add(user);
    }
    
    /**
     * Atualiza a lista de usuários.
     */
    public void refreshUsers() {
        // Primeiro passo: Percorre a lista de clientes.
        for (int i = 0; i < listUsers.size(); i++) {
            // Segundo passo: Verifica se está indisponível.
            if (!listUsers.get(i).isAvailable()) {
                // Terceiro passo: Remove da lista de clientes.
                removeUserIndex(i);
                i--;
            }
        }
    }
    
    /**
     * Remove um usuários da lista de usuários a partir do seu ID.
     *
     * @param id
     */
    public void removeUserId(int id) {
    	int index = -1;
    	for (int i=0 ; i < listUsers.size(); i++){
    		if (listUsers.get(i).getId() == id){
    			index = i;
    			break;
    		}
    	}
    	if (index != -1){
    		removeUserIndex(index);
    	}
    }
    
    /**
     * Remove um usuários da lista de usuários na posição indicada.
     *
     * @param index
     */
    public void removeUserIndex(int index) {
        if (listUsers.size() > index) {
            listUsers.remove(index);
            handlerDialog.publishInfo(handlerDialog.USER_REMOVED);
        }
    }
    
    /**
     * Fecha a conexão com todos os clientes.
     * Remove todos os usuários da lista.
     *
     * @return
     */
    public void clearAllUsers() {
        for (int i=0; i < listUsers.size(); i++){
            listUsers.get(i).closeConnection();
        }
        listUsers.clear();
    }
}
