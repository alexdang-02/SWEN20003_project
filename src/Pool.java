public class Pool extends Actor{
    public static final String TYPE = "Pool";

    public Pool(int x, int y){
        super("res/images/pool.png", TYPE, x, y);
    }

    @Override
    public void update(){
        this.render();
    }
}
