public class SignUp extends Sign {
    public static final String TYPE = "SignUp";

    public SignUp(int x, int y){
        super("res/images/up.png", TYPE, x, y);
    }

    @Override
    public void update(){
        this.render();
    }
}