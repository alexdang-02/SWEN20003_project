public class SignDown extends Sign{
    public static final String TYPE = "SignDown";

    public SignDown(int x, int y){
        super("res/images/down.png", TYPE, x, y);
    }

    @Override
    public void update(){
        this.render();
    }
}