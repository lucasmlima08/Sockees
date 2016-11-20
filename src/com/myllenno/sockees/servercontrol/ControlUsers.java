package com.myllenno.sockees.servercontrol;

import com.myllenno.sockees.report.HandlerDialog;
import com.myllenno.sockees.usercontrol.UserManagement;

import java.util.ArrayList;
import java.util.logging.Handler;

public class ControlUsers {

    /**
     * Classe de comunica��o do evento ocorrido.
     */
    private HandlerDialog handlerDialog;

    /**
     * Lista de usu�rios conectados.
     */
    private ArrayList<UserManagement> listUsers;

    /**
     * M�todo construtor.
     * 
     * @param handler
     */
    public ControlUsers(Handler handler){
        handlerDialog = new HandlerDialog(handler);
        listUsers = new ArrayList<>();
    }
    
    /**
     * Retorna a lista com todos os usu�rios conectados.
     *
     * @return
     */
    public ArrayList<UserManagement> getAllUsers(){
        return listUsers;
    }
    
    /**
     * Respons�vel por adicionar um novo usu�rio a lista de usu�rios.
     * 
     * @param userManagement
     */
    public void addUser(UserManagement userManagement){
    	listUsers.add(userManagement);
    }
    
    /**
     * Atualiza a lista de usu�rios.
     */
    public void refreshUsers() {
        // Primeiro passo: Percorre a lista de clientes.
        for (int i = 0; i < listUsers.size(); i++) {
            // Segundo passo: Verifica se est� indispon�vel.
            if (!listUsers.get(i).isAvailable()) {
                // Terceiro passo: Remove da lista de clientes.
                removeUserIndex(i);
                i--;
            }
        }
    }
    
    /**
     * Remove um usu�rios da lista de usu�rios a partir do seu ID.
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
     * Remove um usu�rios da lista de usu�rios na posi��o indicada.
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
     * Fecha a conex�o com todos os clientes.
     * Remove todos os usu�rios da lista.
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
