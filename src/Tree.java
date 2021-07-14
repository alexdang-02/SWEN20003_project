public class Tree extends FruitContainer {

    public static final String TYPE = "Tree";

    public Tree(int x, int y){
        super("res/images/tree.png", TYPE, x, y);
        this.numFruit = TREE_INITIAL_FRUIT;
    }

    @Override
    public boolean hasFruit() {
        return numFruit > 0;
    }

    @Override
    public void update(){
        this.drawNumFruit();
        this.render();
    }
}
