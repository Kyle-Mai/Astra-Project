package Core;

/*
KM
June whenever 2017
Handles the turn ticker's main events.
*/

import Core.SFX.audioRepository;

public class turnTicker extends Thread {

    public turnTicker() {

    }

    @Override
    public void run() { //open the thread
        System.out.println("[TT] (run) Let the games begin!");
        runTurn();

    }

    private void runTurn() {

        if (gameSettings.currentDate > 1) {
            gameSettings.player.tickStats(); //update player info
        }

        for (int i = 0; i < gameSettings.eventhandler.events.size(); i++) {

            if (gameSettings.eventhandler.events.get(i).eventTrigger()) { //event has reached the conditions required to trigger

                System.out.println("Event triggered!");
                gameSettings.ui.loadEventWindow(gameSettings.eventhandler.events.get(i)); //load the event

                if (!gameSettings.eventhandler.events.get(i).isRepeatable()) { //event is not repeatable, make sure it doesn't trigger again
                    gameSettings.eventhandler.events.remove(i); //...by removing it completely from the array!
                }
            }

        }

        if (gameSettings.currentDate % 10 == 0) {

            try {
                gameSettings.techtree.currentResearch_1.setProgress(gameSettings.player.getResearchTurn());
                if (gameSettings.techtree.currentResearch_1.getProgress() == gameSettings.techtree.currentResearch_1.getCost()) { //if the research is complete, complete it
                    gameSettings.techtree.currentResearch_1.finishResearch();
                    audioRepository.announce_researchComplete();

                    for (int i = 0; i < gameSettings.techtree.techTree.size(); i++) {
                        if (gameSettings.techtree.currentResearch_1 == gameSettings.techtree.techTree.get(i)) {
                            gameSettings.techtree.techTree.remove(i); //research is done, remove it from the list
                            gameSettings.techtree.currentResearch_1 = null; //reset
                        }
                    }

                }
            } catch (NullPointerException e) {
                //System.out.println("No tech selected for research 1!");
            }

            try {
                gameSettings.techtree.currentResearch_2.setProgress(gameSettings.player.getResearchTurn());
                if (gameSettings.techtree.currentResearch_2.getProgress() == gameSettings.techtree.currentResearch_2.getCost()) {
                    gameSettings.techtree.currentResearch_2.finishResearch();
                    audioRepository.announce_researchComplete();
                }

                for (int i = 0; i < gameSettings.techtree.techTree.size(); i++) {
                    if (gameSettings.techtree.currentResearch_2 == gameSettings.techtree.techTree.get(i)) {
                        gameSettings.techtree.techTree.remove(i); //research is done, remove it from the list
                        gameSettings.techtree.currentResearch_2 = null; //reset
                    }
                }
            } catch (NullPointerException e) {
                //System.out.println("No tech selected for research 2!");
            }

            try {
                gameSettings.techtree.currentResearch_3.setProgress(gameSettings.player.getResearchTurn());
                if (gameSettings.techtree.currentResearch_3.getProgress() == gameSettings.techtree.currentResearch_3.getCost()) {
                    gameSettings.techtree.currentResearch_3.finishResearch();
                    audioRepository.announce_researchComplete();
                }

                for (int i = 0; i < gameSettings.techtree.techTree.size(); i++) {
                    if (gameSettings.techtree.currentResearch_3 == gameSettings.techtree.techTree.get(i)) {
                        gameSettings.techtree.techTree.remove(i); //research is done, remove it from the list
                        gameSettings.techtree.currentResearch_3 = null; //reset
                    }
                }
            } catch (NullPointerException e) {
                //System.out.println("No tech selected for research 3!");
            }

        }

        gameSettings.ui.turnTick(); //refresh the UI

        //TODO: Switch to timer class, maybe?
        if (!gameSettings.gameIsPaused) { //check whether or not the game is paused
            try {
                Thread.sleep((long)gameSettings.timeScale[gameSettings.currentTime]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            while (gameSettings.gameIsPaused) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!gameSettings.gameIsPaused) {
                    break;
                }
            }

        }

        gameSettings.currentDate++;

        gameSettings.ui.turnTick(); //refresh the UI

        runTurn(); //recurse back and run the loop again\
    }

}
