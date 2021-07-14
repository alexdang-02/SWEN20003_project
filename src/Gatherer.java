public class Gatherer extends MovingActor implements FruitInteracted {
    public static final String TYPE = "Gatherer";

    public Gatherer(int x, int y){
        super("res/images/gatherer.png", TYPE, x, y);
        this.direction = new Direction(Direction.LEFT);
    }

    /**
     * Logic of Gatherer when interacting with object contain fruits
     * @param containFruit An object of type FruitContainer
     */
    @Override
    public void interactWithStorage(FruitContainer containFruit){
        if(this.carrying && (containFruit instanceof Hoard ||
        containFruit instanceof Stockpile)){
            containFruit.numFruit +=1;
            this.carrying = false;
        }
    }

    public void copy(Gatherer gatherer){
        this.active = gatherer.active;
        this.carrying = gatherer.carrying;
        this.direction.copy(gatherer.direction);
        this.location.copy(gatherer.location);
    }

    @Override
    public void update(){
        this.render();
    }
}