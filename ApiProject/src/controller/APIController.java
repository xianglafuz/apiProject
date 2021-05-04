/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Model;
import model.Province;
import org.json.JSONException;
import view.APIView;

/**
 *
 * @author REMS1DE
 */
public class APIController implements ActionListener {

    private APIView view;
    private Model model;
    private boolean button1Executed, button2Executed, button3Executed, button4Executed, button5Executed;

    public APIController(APIView view, Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Covid Today") && !button1Executed) {
            try {
                view.setTable(model.covidDayInfo(), 1);
                button1Executed = true;
            } catch (ProtocolException ex) {
                Logger.getLogger(APIController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(APIController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(APIController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (command.equals("Most infected in one day") && !button2Executed) {
            try {
                view.setTable(model.mostInfect(), 2);
                button2Executed = true;
            } catch (IOException ex) {
                Logger.getLogger(APIController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(APIController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else if (command.equals("Most death in one day")&& !button3Executed) {
            try {
                view.setTable(model.mostDeath(), 3);
                button3Executed = true;
            } catch (IOException ex) {
                Logger.getLogger(APIController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(APIController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else if (command.equals("Top 5 Most Infected City")&& !button4Executed) {
            try {
                view.setTable2(model.topInfect(), 4);
                button4Executed = true;
            } catch (IOException ex) {
                Logger.getLogger(APIController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(APIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (command.equals("Top 5 Low Infected City")&& !button5Executed) {
            try {
                view.setTable2(model.topInfect(), 5);
                button5Executed = true;
            } catch (IOException ex) {
                Logger.getLogger(APIController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(APIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
