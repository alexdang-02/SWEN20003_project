public class Stockpile extends FruitContainer {
    public static final String TYPE = "Stockpile";

    public Stockpile(int x, int y){
        super("res/images/cherries.png", TYPE, x, y);
        this.numFruit = STORAGE_INITIAL_FRUIT;
    }

    @Override
    public boolean hasFruit() {
        return this.numFruit >0;
    }

    @Override
    public void update(){
        this.drawNumFruit();
        this.render();
    }
}