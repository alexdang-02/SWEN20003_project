public abstract class Sign extends Actor{
    public final Direction DIRECTION;

    public Sign(String filename, String type, int x, int y){
        super(filename, type, x, y);

        switch (type) {
            case "SignDown":
                this.DIRECTION = new Direction(Direction.DOWN);
                break;
            case "SignUp":
                this.DIRECTION = new Direction(Direction.UP);
                break;
            case "SignLeft":
                this.DIRECTION = new Direction(Direction.LEFT);
                break;
            case "SignRight":
                this.DIRECTION = new Direction(Direction.RIGHT);
                break;
            default:
                this.DIRECTION = new Direction(0);
                break;
        }
    }
}
