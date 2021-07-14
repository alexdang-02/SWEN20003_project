public class Direction {
    public static final int LEFT = 1;
    public static final int UP = 2;
    public static final int RIGHT = 3;
    public static final int DOWN = 4;

    public static int QUARTER_DEGREE = 90;
    public static int NUM_DIRECTION = 4;

    public int value;
    
    Direction(int value){
        this.value = value;
    }

    public void copy(Direction direction){
        this.value = direction.value;
    }

    /**
     * rotate direction of moving actor
     * @param degree the desired degree to rotate actor
     */
    public void rotateDirection(int degree){
        int times = degree/ QUARTER_DEGREE;
        int res = times % NUM_DIRECTION;
        int newDirection = this.value;
        
        if(res > 0){
            newDirection += res;
            if(newDirection > NUM_DIRECTION){
                newDirection -= NUM_DIRECTION;
            }
        }else if(res <0){
            newDirection += (NUM_DIRECTION - Math.abs(res));
            if(newDirection > NUM_DIRECTION){
                newDirection -= NUM_DIRECTION;
            }
        }

        this.value = newDirection;
    }

    /**
     * change direction of moving actor
     * @param newDirection An object of class Direction
     */
    public void changeDirection(Direction newDirection){
        this.value = newDirection.value;
    }

}
