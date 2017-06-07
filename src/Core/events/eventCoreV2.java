package Core.events;

import Core.SFX.audioRepository;

/**
 * KM
 * June 06 2017
 * Revamp of the event core - designed to handle the building and management of events.
 */

public class eventCoreV2 implements eventConstants {

    public eventCoreV2() {

    }

    //tutorial event that plays at the start of a new game
    eventBuilder evt_tut_01 = new eventBuilder("", EVENT_GREEN, "") {
        @Override
        public void eventOpen() {
            audioRepository.tutorial_01(); //play tutorial audio
        }

        @Override
        public void eventTrigger() {

        }
    };

    eventBuilder evt_tut_02 = new eventBuilder("", EVENT_GREEN, "") {
        @Override
        public void eventOpen() {

        }

        @Override
        public void eventTrigger() {

        }
    };



}
