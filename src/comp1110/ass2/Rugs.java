package comp1110.ass2;

public class Rugs {

    /**normal rug string, must be something like "p014445"
     *Abbreviated Rug Strings, must be something like "p01"
     */
    //rug string information
    private char color; //'c', 'y', 'r', or 'p'
    private int id; //rug id, unique (01-60)
    private int x1, y1, x2, y2; //x and y coordinates of both squares covered by the rug

    public Rugs(char color, int id, int x1, int y1, int x2, int y2) {
        this.color = color;
        this.id = id;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    //+ getRugState(): String, must be something like "p014445"
    public String getRugState() {
        return color + String.format("%02d", id) + x1 + y1 + x2 + y2;
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    //+ getAbbreviatedRugState(): String
    public String getAbbreviatedRugState() {
        return color + String.format("%02d", id);
    }

// TODO: nice to have, find rugs string at 15th tile
    public static void main(String[] args) {
        Rugs newRug = new Rugs('y', 15, 4, 4, 4, 5);
        System.out.println(newRug.getRugState());
        System.out.println(newRug.getAbbreviatedRugState());
    }







}
