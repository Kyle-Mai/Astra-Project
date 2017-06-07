package Core;

/*

*/

public class turnTicker extends Thread {

    public turnTicker() {

    }

    @Override
    public void run() {
        System.out.println("[TT] (run) Let the games begin!");
        runTurn();

    }

    private void runTurn() {
        //TODO: Switch to timer class, maybe?
        if (!gameSettings.gameIsPaused) {
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
        gameSettings.player.tickStats(); //update player info
        gameSettings.ui.turnTick(); //refresh the UI

        for (int i = 0; i < gameSettings.eventhandler.events.size(); i++) {

            if (gameSettings.eventhandler.events.get(i).eventTrigger()) { //event has reached the conditions required to trigger



                if (!gameSettings.eventhandler.events.get(i).isRepeatable()) { //event is not repeatable, make sure it doesn't trigger again
                    gameSettings.eventhandler.events.remove(i); //...by removing it completely from the array!
                }
            }

        }


        runTurn();

    }

}
