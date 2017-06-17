package Core.GUI.SwingEX;

import javax.swing.*;

/**
 * KM
 * June 16 2017
 * Handles background loading.
 */

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

    protected abstract void done();

    protected abstract void loadOperation(int percent); //what the loader does every time it ticks up a percent


}
