package AetheriusEngine.core.run;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Lolita's Revenge
 * August 07 2017
 * Schedules events for the EventRunner.
 */

public abstract class ScheduledEvent implements Callable<Boolean> {

    private static AtomicInteger threadcount = new AtomicInteger(1);

    private boolean removedOnTriggered; //Whether or not the event will be removed after it has been triggered.
    private boolean threaded;

    public ScheduledEvent() {
        removedOnTriggered = false;
        threaded = false;
    }

    public ScheduledEvent(boolean remove, boolean thread) {
        removedOnTriggered = remove;
        threaded = thread;
    }

    public abstract boolean triggerConditions(); //The conditions that must be met for the event to trigger.
    public abstract void runEvent(); //The event's actions
    public final boolean triggerConditionsMet() { return triggerConditions(); }

    public final void setRemovedOnTriggered(boolean b) { removedOnTriggered = b;}
    public final boolean isRemovedOnTriggered() { return removedOnTriggered; }

    public final void setThreaded(boolean b) { threaded = b; }
    public final boolean isThreaded() { return threaded; }

    public static int getEventCount() { return threadcount.get(); } //gets the most recently executed event's name
    public static void resetEventCount() { threadcount.set(1); } //resets the event counter to the default state (1)

    @Override
    public Boolean call() {
        try {
            Thread.currentThread().setName("Scheduled Event no." + threadcount.getAndIncrement()); //tracks the current event number
            runEvent(); //runs the event designated by the user
        } catch (Exception e) { //event failed to run for some reason
            System.err.println(Thread.currentThread().getName() + " failed to execute:");
            e.printStackTrace();
            return false; //allows the EventRunner the potential to deal with a failed event
        }
        return true; //arbitrary return to signal the event's completion
    }
}
