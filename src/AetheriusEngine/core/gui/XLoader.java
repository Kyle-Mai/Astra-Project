package AetheriusEngine.core.gui;

/*
Lolita's Revenge
June 29 2017

Handles background loading during a loading screen.
 */

import javax.swing.*;

public abstract class XLoader extends SwingWorker<Void, Void> {

    @Override
    protected Void doInBackground() {
        int progress;

        for (int i = 1; i <= 100; i++) {
            loadOperation(i);
            progress = i;
            setProgress(Math.min(progress, 100));
        }

        setProgress(100);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected abstract void done(); //Determines what the loader will do when the operation is complete.

    protected abstract void loadOperation(int percent); //What the loader does every time it ticks up a percent

    //------------------------------------------------------------------------------------------------------------------

}
