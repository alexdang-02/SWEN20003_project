public class SignRight extends Sign {
    public static final String TYPE = "SignRight";

    public SignRight(int x, int y){
        super("res/images/right.png", TYPE, x, y);
    }

    @Override
    public void update(){
        this.render();
    }
}