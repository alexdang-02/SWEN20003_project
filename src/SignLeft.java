public class SignLeft extends Sign{
    public static final String TYPE = "SignLeft";

    public SignLeft(int x, int y){
        super("res/images/left.png", TYPE, x, y);
    }

    @Override
    public void update(){
        this.render();
    }
}