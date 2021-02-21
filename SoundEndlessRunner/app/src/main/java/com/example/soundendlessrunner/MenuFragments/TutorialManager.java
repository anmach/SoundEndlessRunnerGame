package com.example.soundendlessrunner.MenuFragments;

import android.os.Handler;

import com.example.soundendlessrunner.GameManager;
import com.example.soundendlessrunner.R;
import com.example.soundendlessrunner.TutorialActivity;

public class TutorialManager extends GameManager {
    private int tutorialPart = 0;
    private boolean withStop;

    public TutorialManager(int noOfTracks, int noOfObjects, TutorialActivity tutorialActivity, Handler handler, boolean withStop){
        super(noOfTracks, noOfObjects, tutorialActivity, handler);
        this.withStop = withStop;
    }

    @Override
    public void continueGame() {
        if(tutorialPart == 1 && !((TutorialActivity)gameActivity).isTtsSpeaking()) {
            tutorialPart++;
            ((TutorialActivity)gameActivity).speakPart2("");
        }
    }

    @Override
    public void moveLeftIfPossible() {
        if(!((TutorialActivity)gameActivity).isTtsSpeaking()){
            if(tutorialPart == 2){
                tutorialPart++;
                soundManager.stopPlayingSound();
                gameData.moveLeftIfPossible();
                ((TutorialActivity)gameActivity).speakPart3();
            }
            else if(tutorialPart == 3){
                gameData.moveLeftIfPossible();
                adjustVolume();
            }
        }
    }

    @Override
    public void moveRightIfPossible() {
        if(!((TutorialActivity)gameActivity).isTtsSpeaking()) {
            if(tutorialPart == 0){
                tutorialPart++;
                soundManager.stopPlayingSound();
                gameData.moveRightIfPossible();
                if(withStop){
                    ((TutorialActivity)gameActivity).speakPart1();
                }
                else{
                    tutorialPart++;
                    ((TutorialActivity)gameActivity).speakPart2(gameActivity.getString(R.string.tut_part0_end));
                }
            }
            else if(tutorialPart == 3){
                gameData.moveRightIfPossible();
                if(gameData.getDifferenceBetweenPlayerAndObjectTrack() == 0){
                    soundManager.stopPlayingSound();
                    tutorialPart++;
                    ((TutorialActivity)gameActivity).speakEnding();
                }
                else {
                    adjustVolume();
                }
            }
        }
    }

    public void continueTutorial(){
        //TODO Sounds and tracks
        switch (tutorialPart){
            case 0:
                gameData.setNoOfObjectTrack(2);
                soundManager.playObstacleSound();
                break;
            case 1:
                break;
            case 2:
                gameData.setNoOfObjectTrack(1);
                soundManager.playPointSound();
                break;
            case 3:
                gameData.setNoOfObjectTrack(3);
                soundManager.playHeartSound();
                break;
            default:
                gameActivity.endGame();
        }
    }
}
