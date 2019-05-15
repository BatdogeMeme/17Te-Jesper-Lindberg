
/*

a rowstart is a tile at a start if a lne
takes in 4 variable's
x: The x position
y: the y position
dx: The direction the row goes (right to left)
dy: The direction the row goes (up to down)


*/

package otello;

public class rowStart {

    int x, y, dX, dY;

    public rowStart(int x, int y, int dX, int dY) {

        this.x = x;
        this.y = y;
        this.dX = dX;
        this.dY = dY;
    }
}
