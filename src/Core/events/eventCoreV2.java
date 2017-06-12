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
        events.add(new eventBuilder("???", EVENT_ORANGE, false, "/sentient_AI.png", "What the fuck did you just fucking say about me, you little bitch? I’ll have you know I graduated top of my class in the Navy Seals, and I’ve been involved in numerous secret raids on Al-Quaeda, and I have over 300 confirmed kills. I am trained in gorilla warfare and I’m the top sniper in the entire US armed forces. You are nothing to me but just another target. I will wipe you the fuck out with precision the likes of which has never been seen before on this Earth, mark my fucking words. You think you can get away with saying that shit to me over the Internet? Think again, fucker. As we speak I am contacting my secret network of spies across the USA and your IP is being traced right now so you better prepare for the storm, maggot. The storm that wipes out the pathetic little thing you call your life. You’re fucking dead, kid. I can be anywhere, anytime, and I can kill you in over seven hundred ways, and that’s just with my bare hands. Not only am I extensively trained in unarmed combat, but I have access to the entire arsenal of the United States Marine Corps and I will use it to its full extent to wipe your miserable ass off the face of the continent, you little shit. If only you could have known what unholy retribution your little “clever” comment was about to bring down upon you, maybe you would have held your fucking tongue. But you couldn’t, you didn’t, and now you’re paying the price, you goddamn idiot. I will shit fury all over you and you will drown in it. You’re fucking dead, kiddo.") { //TODO: Change text from Navy Seal copypasta to something serious.
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

        events.add(new eventBuilder("Message from Vir", EVENT_ORANGE, false, "/sentient_AI.png", "We should look into expanding our empire's technological prowess. Our first step should be our research sector though funding and supporting specific areas of research. <br> <br> A good place to start would be the <b>New Worlds Protocol</b> technology, as it will open up the possibility of colonization across the galaxy. <br> But first, we must research the <b>String Drive Prototype</b> in order to even reach those systems in a reasonable time frame! <br> <br> Select the Tech Tree view button at the top left-hand corner of the screen to view our research policies.") { //TODO: Change text from Navy Seal copypasta to something serious.
            @Override public void loadOptions() {
                addOption(new Option("Understood.", "") {
                    @Override public void clickButton() {
                    }
                });
            }
            @Override public boolean eventTrigger() {
                return (gameSettings.currentDate >= 7 && gameSettings.tutorialEnabled);
            }

            @Override public void eventOpen() {
                audioRepository.tutorial_03();
                gameSettings.gameIsPaused = true;
            }
        });

        events.add(new eventBuilder("Message from Vir", EVENT_ORANGE, false, "/sentient_AI.png", "Excellent work, the research put into these new technologies should put us in a strong position to begin colonizing nearby stars. <br> <br> Before we do so, however, it may be wise to do an in-depth survey of the nearby star systems with FTL-equipped surveyor ships. By doing so, we can narrow down colonization candidates and ensure our inter-stellar colonies have the greatest chance of success. <br> <br> <b>TASK: Survey nearby star systems.</b>") { //TODO: Change text from Navy Seal copypasta to something serious.
            @Override public void loadOptions() {
                addOption(new Option("Understood.", "") {
                    @Override public void clickButton() {
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
