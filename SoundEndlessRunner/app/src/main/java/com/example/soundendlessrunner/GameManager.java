package com.example.soundendlessrunner;

import android.os.Handler;

import com.example.soundendlessrunner.Enums.ObjectType;

import static java.lang.Thread.sleep;


public class GameManager implements Runnable {
    protected GameData gameData;
    protected SoundManager soundManager;
    protected GameActivity gameActivity;
    private Handler endGameHandler;
    private boolean isGameActive = true;

    protected GameManager(int noOfTracks, int noOfObjects, GameActivity gameActivity, Handler handler){
        this.gameActivity = gameActivity;
        this.endGameHandler = handler;
        gameData = new GameData(noOfTracks, noOfObjects);
        soundManager = new SoundManager(gameActivity, gameData.getTimeBetweenObjects());
    }

    @Override
    public void run() {
        while (isGameActive){
            try {
                sleep(getObjectTime());
                continueGame();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void play(){
        gameData.drawObject();
        playSound();
    }

    public void stop(){
        soundManager.stopPlayingSound();
    }

    public void continueGame(){
        if(gameData.didWeHit()){
            ObjectType type = gameData.getObjectType();
            if(type == ObjectType.Point){
                gameActivity.ttsSpeak(gameActivity.getString(R.string.add_point) + gameData.getPoints());
            }
            else{
                if(gameData.didWeDied()){
                    isGameActive = false;
                    endGameHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            gameActivity.endGame();
                        }
                    });
                }
                else{
                    gameActivity.ttsSpeak(gameActivity.getString(R.string.add_life) + gameData.getLives());
                }
            }
        }

        gameData.drawObject();
        soundManager.stopPlayingSound();
        playSound();
    }

    private void playSound(){
        soundManager.setStartingVolume(gameData.getDifferenceBetweenPlayerAndObjectTrack());
        ObjectType objectType = gameData.getObjectType();
        if(objectType == ObjectType.Life){
            soundManager.playHeartSound();
        }
        else if(objectType == ObjectType.Point) {
            soundManager.playPointSound();
        }
        else{
            soundManager.playObstacleSound();
        }
    }

    protected void adjustVolume(){
        int difference = gameData.getDifferenceBetweenPlayerAndObjectTrack();
        soundManager.adjustVolume(difference);
    }

    public void moveLeftIfPossible(){
        if(gameData.moveLeftIfPossible()){
            adjustVolume();
        }
    }

    public void moveRightIfPossible(){
        if(gameData.moveRightIfPossible()){
            adjustVolume();
        }
    }

    public int getPoints(){
        return gameData.getPoints();
    }

    public long getObjectTime(){
        return gameData.getTimeBetweenObjects();
    }
}
