import java.util.ArrayList;


public abstract class MovingActor extends Actor {
    public static int SPEED = 64;
    public boolean carrying = false;
    public boolean active = true;
    public Direction direction;

    public MovingActor(String filename, String type, int x, int y){
        super(filename, type, x, y);
    }

    /**
     * move actor according to its current direction
     * @param direction an integer
     */
    public void move(int direction){
        switch (direction) {
            case Direction.UP:
                this.location.changeLocation(0, -SPEED);
                break;
            case Direction.DOWN:
                this.location.changeLocation(0, SPEED);
                break;
            case Direction.LEFT:
                this.location.changeLocation(-SPEED, 0);
                break;
            case Direction.RIGHT:
                this.location.changeLocation(SPEED, 0);
                break;
        }
    }

    /**
     * move actor according to its current direction
     * @param direction an object of type Direction
     */
    public void move(Direction direction){
        move(direction.value);
    }

    /**
     * change active status of moving actor when reaching fence
     * @param fences An ArrayList of Fences object
     */
    public void checkFences(ArrayList<Fence> fences){
        for (Fence fence : fences){
            if(this.location.equals(fence.location)){
                this.active = false;
            }
        }
    }

    /**
     * change direction of moving actor when reaching signs
     * @param signs An ArrayList of Sign object
     */
    public void checkSigns(ArrayList<Sign> signs){
        for(Sign sign : signs){
            if(this.location.equals(sign.location)){
                this.direction = sign.DIRECTION;
            }
        }
    }

    /**
     * Perform logics when actor reach trees
     * @param containFruit An object of type FruitContainer
     */
    public void collectFromTree(FruitContainer containFruit) {
        if(!this.carrying && (containFruit instanceof Tree ||
        containFruit instanceof GoldenTree) && containFruit.hasFruit()){
            containFruit.numFruit -= 1;
            this.carrying = true;
            if(containFruit instanceof Tree){
                this.direction.rotateDirection(180);
            }
            }
        }
    }





