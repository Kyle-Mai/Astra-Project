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

        runTurn();

    }

}
