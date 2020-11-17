package com.example.soundendlessrunner;

import com.example.soundendlessrunner.Enums.ObjectType;

import java.util.Random;

/**
 * Class containing game data
 *
 * @author Anna Mach
 */
public class GameData {
    private float timePlaying = 0;
    private float startTime;

    private int points = 0;
    private int lives = 3;
    private int noOfPlayerTrack = 0;
    private int noOfObjectTrack = 0;

    private int noOfTracks;

    private ObjectType objectType;
    private int objectsPerMinute;
    private long timeBetweenObjects; //in ms

    Random generator = new Random();

    /**
     * Constructor of class GameData
     *
     * @param noOfTracks       number of tracks, where player can move
     * @param objectsPerMinute objects(obstacles and points) appearing per minute
     */
    public GameData(int noOfTracks, int objectsPerMinute) {
        this.noOfTracks = noOfTracks - 1;
        this.objectsPerMinute = objectsPerMinute;
        this.timeBetweenObjects = 60000 / objectsPerMinute;
    }

    /**
     * Changing player track to one to right (number higher by 1) if it is not already last track
     */
    public void moveRightIfPossible() {
        if (noOfPlayerTrack < noOfTracks) {
            noOfPlayerTrack++;
        }
    }

    /**
     * Changing player track to one to left (number lower by 1) if it is not already first track
     */
    public void moveLeftIfPossible() {
        if (noOfPlayerTrack > 0) {
            noOfPlayerTrack--;
        }
    }

    public void drawObject() {
        noOfObjectTrack = generator.nextInt(noOfTracks + 1);

        int drawed = generator.nextInt(100);
        if(drawed<10){
            objectType = ObjectType.Life;
        }
        if(drawed < 30){
            objectType = ObjectType.Point;
        }
        else{
            objectType = ObjectType.Obstacle;
        }

    }

    public boolean didWeDied() {
        if (getDifferenceBetweenPlayerAndObjectTrack() == 0) {
            if(objectType == ObjectType.Life){
                lives++;
            }
            else if(objectType == ObjectType.Point) {
                points++;
            }
            else{
                //TODO remove life
                return true;
            }
        }
        return false;
    }

    /**
     * Adding points to already owned by player
     *
     * @param pointsToAdd number of points to add
     */
    public void addPoints(int pointsToAdd) {
        points += pointsToAdd;
    }

    /**
     * Adding lives to already owned by player
     *
     * @param livesToAdd number of lives to add
     */
    public void addLives(int livesToAdd) {
        lives += livesToAdd;
    }

    /**
     * Getter for number of player track
     *
     * @return no of player track
     */
    public int getNoOfPlayerTrack() {
        return noOfPlayerTrack + 1;
    }

    /**
     * Getter for number of object track
     *
     * @return no of object track
     */
    public int getNoOfObjectTrack() {
        return noOfObjectTrack;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public int getDifferenceBetweenPlayerAndObjectTrack() {
        return noOfPlayerTrack - noOfObjectTrack;
    }

    /**
     * Getter for time between objects appearance
     *
     * @return time between objects appearance
     */
    public long getTimeBetweenObjects() {
        return timeBetweenObjects;
    }
}
