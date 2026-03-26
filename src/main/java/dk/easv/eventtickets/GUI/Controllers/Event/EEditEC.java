package dk.easv.eventtickets.GUI.Controllers.Event;

import dk.easv.eventtickets.BLL.EventManager;
import javafx.event.ActionEvent;

public class EEditEC {
    private EventManager eMan;
    private


    public void onbtnDelete(ActionEvent actionEvent) {
        try {
            uMod.deleteUser(currentUser);
            uMod.getUserToBeViewed().remove(currentUser); // fjerner fra UI
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
