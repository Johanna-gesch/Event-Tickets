package dk.easv.eventtickets.GUI.Controllers.Cards;

import dk.easv.eventtickets.BE.Event;

public interface IEventCardListener {

    void onGetTickets(Event event);

    void onUpdateEvent(Event event);
}
