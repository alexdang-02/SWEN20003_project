public class Location {
    public int x;
    public int y;

    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean equals(Location location){
        return this.x == location.x && this.y == location.y;
    }

    public void copy(Location location){
        this.x = location.x;
        this.y = location.y;
    }

    /**
     * change coordinate of location
     * @param deltaX change in value of x
     * @param deltaY change in value of y
     */
    public void changeLocation(int deltaX, int deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }
}
