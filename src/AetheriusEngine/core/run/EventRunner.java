package AetheriusEngine.core.run;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Lolita's Revenge
 * August 07 2017
 * Designed to run events on a 'tick' basis, with the ability to schedule different events to run on different ticks.
 */

public final class EventRunner implements Runnable, EventConstants {

    /*------------------------------------------------------------------------------------------------------------------
     Variables.
     Define the function of the EventRunner.
     */

    private ArrayList<ScheduledEvent> events_one = new ArrayList<>();
    private ArrayList<ScheduledEvent> events_ten = new ArrayList<>();
    private ArrayList<ScheduledEvent> events_hundred = new ArrayList<>();
    private ArrayList<ScheduledEvent> events_thousand = new ArrayList<>();
    private ArrayList<ScheduledEvent> removed = new ArrayList<>();
    private List<ScheduledEvent> eventPool = new LinkedList<>();

    private ExecutorService threadPool = Executors.newCachedThreadPool();

    private boolean isPaused = false;
    private boolean isInterrupted = false;
    private boolean isRunning = false;

    private AtomicLong currentCycle = new AtomicLong(0); //keeps track of the current cycle
    private AtomicLong currentTick = new AtomicLong(0); //keeps track of the current tick
    private final int cycleDuration = 1000; //how many ticks will pass before the cycle increases

    private double tickDuration = 0.0167; //approximately 60 ticks per second by default

    /*------------------------------------------------------------------------------------------------------------------
     Constructors.
     Used to construct instances of the EventRunner.
     */

    public EventRunner() {}

    /*------------------------------------------------------------------------------------------------------------------
     Accessible methods.
     Can be accessed outside of the EventRunner to edit values.
     */

    public double getTickDuration() { return tickDuration; }
    public void setTickDuration(double d) { tickDuration = d; }

    public long getCurrentCycle() { return currentCycle.get(); }
    public long getCurrentTick() { return currentTick.get(); }

    public int getCycleDuration() { return cycleDuration; }

    public boolean isRunning() { return isRunning; }
    public boolean isInterrupted() { return isInterrupted; }
    public boolean isPaused() { return isPaused; }

    public void reset() {
        currentTick.set(0);
        currentCycle.set(0);
    }

    @Override
    public void run() {
        try {
            eventCycle();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //EventRunner finished all processes!
    }

    public void addEvent(int frequency, ScheduledEvent event) { //TODO: Make less shite.
        switch (frequency) {
            case frequency_10: // = 10
                events_ten.add(event);
                break;
            case frequency_100: // = 100
                events_hundred.add(event);
                break;
            case frequency_1000: // = 1000
                events_thousand.add(event);
                break;
            default: // = 1
                events_one.add(event);
            break;
        }
    }

    public void dump(boolean preserveCycle) {
        events_one.clear();
        events_ten.clear();
        events_hundred.clear();
        events_thousand.clear();
        removed.clear();
        if (!preserveCycle) {
            reset();
        }
    }

    public void dispose() {
        dump(false);
        try {
            threadPool.shutdown(); //shuts down the threading used by the EventRunner
            threadPool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("Thread pool shutdown sequence interrupted prematurely.");
        } finally {
            threadPool.shutdownNow(); //forces the thread pool to shutdown, even if tasks are still running
            System.gc(); //prompt garbage collector to clean up any remnants
        }
    }

    /*------------------------------------------------------------------------------------------------------------------
     Core methods.
     Methods used by the EventRunner after run() has been called. These should NOT be touched or accessed individually.
     */

    //private long cyclestart, cycleend;

    private void eventCycle() throws InterruptedException {
        isRunning = true;

        while (!isInterrupted) {
            if (!isPaused) {
                switch ((int) currentTick.get()) {
                    case cycleDuration:
                        currentCycle.incrementAndGet();
                        currentTick.set(0);
                        //System.out.println("Cycle " + currentCycle);
                        System.gc(); //tells the garbage collector to consider cleaning, just to ensure there's no major memory overflow between cycles
                    default:
                        currentTick.incrementAndGet();
                }

                //cyclestart = System.currentTimeMillis();

                eventPool.clear();

                checkEventConditions(events_one);
                if (currentTick.get() % 10 == 0 && currentTick.get() != 0) {
                    checkEventConditions(events_ten);
                }
                if (currentTick.get() % 100 == 0 && currentTick.get() != 0) {
                    checkEventConditions(events_hundred);
                }
                if (currentTick.get() % 1000 == 0 && currentTick.get() != 0) {
                    checkEventConditions(events_thousand);
                }

                threadPool.invokeAll(eventPool).stream().map(booleanFuture -> { //run the events
                    try {
                        return booleanFuture.get();
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                }).forEach(System.out::println); //TODO: Not properly printing results..

                //cycleend = System.currentTimeMillis();
                //System.out.println("Tick " + currentTick + " completed in " + (cycleend - cyclestart) + "ms");

                // pauses the thread for the duration of the tick
                try {
                    Thread.sleep((long) (tickDuration * 1000));
                    //TimeUnit.SECONDS.sleep((long) tickDuration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                //event runner is paused, keep checking for re-activation every 20ms
                try {
                    Thread.sleep(20);
                    //TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
            //all actions performed, repeat the process
        }

        //event runner was interrupted artificially, was this done by an error - or by the user?
        isRunning = false;
        isInterrupted = false;
        throw new InterruptedException("EventRunner cycle interrupted prematurely -- was this intentional?");
    }

    private void checkEventConditions(ArrayList<ScheduledEvent> events) { //checks and runs events

        if (events.size() > 0) {
            for (ScheduledEvent e : events) { //check the event roster
                try {
                    if (e.triggerConditionsMet()) {
                        //conditions met, run the event
                        //System.out.println("Conditions met for event " + e.toString());

                        if (e.isThreaded()) {
                            //event is to be given its own private thread (harder on CPU, faster)
                            eventPool.add(e);
                            //threadPool.submit(e);
                        } else {
                            //run event in main EventRunner thread (better on CPU, slower)
                            e.runEvent();
                        }

                        if (e.isRemovedOnTriggered()) {
                            //if the event is set to be removed once it has been triggered, remove it from the queue
                            removed.add(e);
                        }
                    }
                } catch (Exception p) {
                    p.printStackTrace();
                    removed.add(e); //remove the faulty event from the queue
                }
                //nothing loaded in the queue to be triggered
            }
        }

        if (!removed.isEmpty()) {
            events.removeAll(removed);
            removed.clear();
        }
    }

    //------------------------------------------------------------------------------------------------------------------

}
