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

    //TODO: Add more events.

    public ArrayList<eventBuilder> events = new ArrayList<>();

    public eventCoreV2() { //constructor for the event class
        initializeEvents();
    }

    private void initializeEvents() { //sets up the events
        events.add(new eventBuilder("???", EVENT_ORANGE, false, "sentient_AI.png", "Greetings, Primarch! I am Vir, and I was constructed by the finest minds of our civilization to assist you. <br> <br> If you would like, I can provide instructional missions to help you get accustomed to managing an interstellar empire. <br> <br> Remember to use the <b> SPACE BAR </b> to pause and unpause the game.") { //TODO: Change text from Navy Seal copypasta to something serious.
            @Override public void loadOptions() {
                addOption(new Option("Power down. (No tutorial)", "Disable the tutorial and play without.") {
                    @Override public void clickButton() {
                        audioRepository.tutorial_off();
                        gameSettings.tutorialEnabled = false;
                    }
                });
                addOption(new Option("Your assistance is appreciated. (Full tutorial)", "Enable and play with the full tutorial.") {
                    @Override public void clickButton() {
                        audioRepository.tutorial_02();
                    }
                });
            }
            @Override public boolean eventTrigger() {
                return (gameSettings.currentDate >= 1 && gameSettings.tutorialEnabled);
            }
            @Override public void eventOpen() {
                audioRepository.tutorial_01(); //play tutorial opening audio
                gameSettings.gameIsPaused = true; //pauses the game
            }
        });

        events.add(new eventBuilder("Message from Vir", EVENT_ORANGE, false, "sentient_AI.png", "We should look into expanding our empire's technological prowess. Our first step should be our research sector though funding and supporting specific areas of research. <br> <br> A good place to start would be the <b>New Worlds Protocol</b> technology, as it will open up the possibility of colonization across the galaxy. <br> But first, we must research the <b>String Drive Prototype</b> in order to even reach those systems in a reasonable time frame! <br> <br> Select the Tech Tree view button at the top left-hand corner of the screen to view our research policies.") { //TODO: Change text from Navy Seal copypasta to something serious.
            @Override public void loadOptions() {
                addOption(new Option("Understood.", "") {
                    @Override public void clickButton() {
                    }
                });
            }
            @Override public boolean eventTrigger() {
                return (gameSettings.currentDate >= 9 && gameSettings.tutorialEnabled);
            }
            @Override public void eventOpen() {
                audioRepository.tutorial_03();
                gameSettings.gameIsPaused = true;
            }
        });

        events.add(new eventBuilder("Message from Vir", EVENT_ORANGE, false, "sentient_AI.png", "Excellent work, the research put into these new technologies should put us in a strong position to begin colonizing nearby star systems. <br> <br> Before we do so, however, it may be wise to do an in-depth survey of the nearby star systems with FTL-equipped surveyor ships. By doing so, we can narrow down colonization candidates and ensure our inter-stellar colonies have the greatest chance of success. <br> <br> <b>TASK: Survey nearby star systems.</b>") { //TODO: Change text from Navy Seal copypasta to something serious.
            @Override public void loadOptions() {
                addOption(new Option("Understood.", "") {
                    @Override public void clickButton() {
                        gameSettings.gameIsPaused = false;
                    }
                });
            }
            @Override public boolean eventTrigger() {
                return (gameSettings.FTLenabled && gameSettings.tutorialEnabled);
            }
            @Override public void eventOpen() {
                audioRepository.tutorial_04();
                gameSettings.gameIsPaused = true;
            }
        });

        events.add(new eventBuilder("Stargazers", EVENT_BLUE, false, "news_room.png", "The recent innovations in Faster-Than-Light (FTL) travel has sparked excited chatter throughout our civilization. Online forums and newsreels are abuzz with speculation and optimism for the future. Many have begun comparing this innovation on par with that of the internet and internal combustion engine. <br> <br> We should begin constructing a fleet of interstellar exploration vessels as soon as possible so that we can begin surveying potential candidates for colonization.") { //TODO: Change text from Navy Seal copypasta to something serious.
            @Override public void loadOptions() {
                addOption(new Option("Great news!", "") {
                    @Override public void clickButton() {
                        gameSettings.gameIsPaused = false;
                    }
                });
            }
            @Override public boolean eventTrigger() {
                return (gameSettings.FTLenabled && gameSettings.currentDate > 120);
            }
            @Override public void eventOpen() {
                audioRepository.event_conversation();
                gameSettings.gameIsPaused = true;
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

}
