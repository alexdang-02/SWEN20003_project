import bagel.Font;

public abstract class FruitContainer extends Actor {
    int STORAGE_INITIAL_FRUIT = 0;
    int TREE_INITIAL_FRUIT = 3;
    Font numberFont = new Font("res/VeraMono.ttf", 24);

    public Integer numFruit;

    public FruitContainer(String filename, String type, int x, int y){
        super(filename, type, x, y);
    }

    /**
     * draw number of fruit to screen
     */
    public void drawNumFruit(){
        numberFont.drawString(numFruit.toString() , this.location.x , this.location.y);
    }

    /**
     * check if object has at least one fruit
     * @return for GoldenTree, return value is always true.
     */
    public abstract boolean hasFruit();

}
