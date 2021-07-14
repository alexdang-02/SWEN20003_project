public class Thief extends MovingActor implements FruitInteracted {
    public static final String TYPE = "Thief";
    public boolean consuming = false;


    public Thief(int x, int y){
        super("res/images/thief.png", TYPE, x, y);
        this.direction = new Direction(Direction.UP);
    }

    /**
     * Logic of Thief when interacting with object contain fruits
     * @param containFruit An object of type FruitContainer
     */
    @Override
    public void interactWithStorage(FruitContainer containFruit) {
        if(containFruit instanceof Hoard){
            if(this.consuming){
                consuming = false;
                if(!carrying){
                    if(containFruit.hasFruit()){
                        this.carrying = true;
                        containFruit.numFruit -= 1;
                    }else{
                        this.direction.rotateDirection(90);
                    }
                }
            }else if(this.carrying){
                this.carrying = false;
                containFruit.numFruit +=1;
                this.direction.rotateDirection(90);
            }
        }

        if(containFruit instanceof Stockpile ){
            if(!this.carrying){
                if(containFruit.hasFruit()){
                    this.carrying = true;
                    this.consuming = false;
                    containFruit.numFruit -= 1;
                    this.direction.rotateDirection(90);
                }
            }else{
                this.direction.rotateDirection(90);
            }
        }
    }

    public void copy(Thief thief){
        this.active = thief.active;
        this.carrying = thief.carrying;
        this.direction.copy(thief.direction);
        this.location.copy(thief.location);
    }

    @Override
    public void update(){
        this.render();
    }
}