package comp1110.ass2;


public class Gamecaculate {

    int playerScore;
    /**
     * update the state of the playstate
     *  For this method, you should update the player's status
     *  First， you should find the playerState using a String,
     * then, update the number of Rugs or Dirhmas
     *     return a String that about the updated player
     *
     *
     */
    public static String playerStringUpdate(String playerState){
        // implement the function
        return null;
    }
    /**
     * check if player’s numberOfRugs = 0 or not, then count numberOfDirhmas,
     *  The variable zeroRugAmount - Sets leftover rugs to zero should get
     *  the number of Rug in zero
     *  return a boolean to check if someone lose the game
     *
     *
     */
    public static  boolean playerOut(int numberRugs ){
        // implement the function
        return false;

    }
    /**
     * calculate player’s score
     * if someone is out of dirhmas, then you should use the function
     * Notice that you should calculate the numbers of Rugs instead of the String
     * Notice that you only calculate the player who is not out of the game
     *     return the final Score of the player
     *
     *
     */
    public static int calScore(int numberofRugs){
        // implement the function
        return numberofRugs;
    }

    /**
     * compare all players’ score
     * if someone's dirmas is not  equal to another, then you should use this function
     * and notice you should compare the total Score.
     * return a String who wins the game, just like Pr00803i
     *
     *
     */
    public static int  compareScores(int Score){
        // implement the function
        return  Score;
    }

    /**
     * calculate player’s dirhmas
     * if someone's dirmas is equal to another, then you should use this function
     * and notice you should just compare the numbers of dirhmas instead of the score
     *
     *
     */
public static int compareDirhmas(int amountofDirhmas){
    // implement the function
        return amountofDirhmas;
}
    /**
     * Get the final Result
     * if someone wins the game,the gameResult will be display
     * notice the winner should be like this (winner + players’ score from high to low)
     * and all the player should be display
     *
     *
     */
public static void  gameResult(){

}



}
