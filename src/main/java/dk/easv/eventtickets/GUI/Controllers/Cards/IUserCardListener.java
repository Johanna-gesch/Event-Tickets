package dk.easv.eventtickets.GUI.Controllers.Cards;

import dk.easv.eventtickets.BE.User;

/**
 * Listener interface for UserCardController. Used to notify ADashController when an edit button is clicked.
 *
 * JavaFX ListCells are reused and shouldn't perform navigation. Instead, the events are exposed using this interface.
 * Now it is the ADashController's responsibility to handle the event.
 */

public interface IUserCardListener {

    void onEditUser(User user);
}
