public class GoldenTree extends FruitContainer{
    public static final String TYPE = "GoldenTree";
    
    public GoldenTree(int x, int y){
        super("res/images/gold-tree.png", TYPE, x, y);
        this.numFruit = TREE_INITIAL_FRUIT;
    }

    /**
     * Golden tree has unlimited number of fruits
     * @return always true
     */
    @Override
    public boolean hasFruit() {
        return true;
    }

    @Override
    public void update(){
        this.render();
    }
}
