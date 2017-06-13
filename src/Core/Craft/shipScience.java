package Core.Craft;

import Core.SFX.audioRepository;
import Core.starClass;

/**
 * KM
 * June 03 2017
 * Science/exploration ship type.
 */



public class shipScience extends craftCore {

    private int surveyTime; //time it takes to survey a system

    public shipScience(String name, double speed, int range, int health, double buildCost, int buildTime, double maintenanceCost, String shipGFXDir, int surveyTime) {
        super(name, speed, range, health, buildCost, buildTime, maintenanceCost, shipGFXDir);
        this.surveyTime = surveyTime;
    }

    @Override
    public void performAction() {
        if (!this.isActive()) {
            this.toggleActive();
            this.setTimeUntilAction(surveyTime);
        }
    }

    @Override
    public void actionComplete() {

    }

    @Override
    public void idleTick() {
        super.idleTick();
    }

    @Override
    public void playAudioMove() {
        audioRepository.shipScienceMove();
    }

    @Override
    public void playAudioSelected() {
        audioRepository.shipScienceSelect();
    }

    public int getSurveyTime() { return this.surveyTime; }


}
