package Core.events;

import Core.GUI.gfxRepository;
import Core.SFX.audioRepository;
import Core.gameSettings;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * KM
 * June 06 2017
 * Revamp of the event core - designed to handle the building and management of events.

 * The weird formatting for this class is to try and minimize the necessary lines per event.
 * Simply for my readability more than anything.
 */

public class eventCoreV2 implements eventConstants {

    public ArrayList<eventBuilder> events = new ArrayList<>();

    public eventCoreV2() { //constructor for the event class
        initializeEvents();
    }

    private void initializeEvents() { //sets up the events
        events.add(new eventBuilder("Test Event", EVENT_RED, false, "owo what's this") {
            @Override public void loadOptions() {
                addOption(new Option("Test", "Test", 0) {
                    @Override public void clickButton() {
                        System.out.println("Event activated successfully!");
                    }
                });
            }
            @Override public boolean eventTrigger() {
                if (gameSettings.currentDate > 2) {
                    return true;
                } else {
                    return false;
                }
            }
            @Override public void eventOpen() {
                System.out.println("Event successfully opened!");

            }
        });



    }

    public BufferedImage getHeader(int header) {
        switch (header) { //get the event header colour
            case EVENT_BLUE:
                return gfxRepository.blueHeader;
            case EVENT_BROWN:
                return gfxRepository.brownHeader;
            case EVENT_CYAN:
                return gfxRepository.cyanHeader;
            case EVENT_GREEN:
                return gfxRepository.greenHeader;
            case EVENT_ORANGE:
                return gfxRepository.orangeHeader;
            case EVENT_GREY:
                return gfxRepository.greyHeader;
            case EVENT_PURPLE:
                return gfxRepository.purpleHeader;
            case EVENT_RED:
                return gfxRepository.redHeader;
            case EVENT_YELLOW:
                return gfxRepository.yellowHeader;
            default:
                return gfxRepository.greyHeader;
        }
    }

    //tutorial event that plays at the start of a new game
    eventBuilder evt_tut_01 = new eventBuilder("???", EVENT_GREEN, false, "???") {
        @Override public void loadOptions() {
            addOption(new Option("", "", 0) {
                @Override public void clickButton() {

                }
            });

        }

        @Override public void eventOpen() {
            audioRepository.tutorial_01(); //play tutorial audio
        }

        @Override public boolean eventTrigger() {
            return false;
        }
    };

    eventBuilder evt_tut_02 = new eventBuilder("", EVENT_GREEN, false, "") {
        @Override public void loadOptions() {


        }

        @Override public void eventOpen() {

        }

        @Override public boolean eventTrigger() {
            return false;
        }
    };



}
