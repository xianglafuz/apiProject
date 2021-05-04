/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apiproject;

import controller.APIController;
import model.Model;
import view.APIView;

/**
 *
 * @author REMS1DE
 */
public class ApiProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        APIView view = new APIView();
        Model model = new Model();
        view.setVisible(true);
        APIController controller = new APIController(view,model);
        view.registerListener(controller);
    }
    
}
