public class Hoard extends FruitContainer{
    public static final String TYPE = "Hoard";

    public Hoard(int x, int y){
        super("res/images/hoard.png", TYPE, x, y);
        this.numFruit = STORAGE_INITIAL_FRUIT;
    }

    @Override
    public boolean hasFruit() {
        return this.numFruit > 0;
    }

    @Override
    public void update(){
        this.drawNumFruit();
        this.render();
    }
}

