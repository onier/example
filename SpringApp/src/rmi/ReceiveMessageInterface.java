/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.*;

public interface ReceiveMessageInterface extends Remote {

    void receiveMessage(String x) throws RemoteException;
}
