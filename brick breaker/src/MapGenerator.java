import java.awt.*;

public class MapGenerator {
    int[][] map;
    int Brickwidth;
    int Brickheight;
    public MapGenerator(int row, int column){
        map = new int[row][column];
        for (int i=0;i<map.length;i++){
            for (int j =0;j<map[0].length;j++){
                map[i][j] = 1;
            }
        }
        Brickwidth = 540/column;
        Brickheight = 150/row;
    }
    public void draw(Graphics2D g){
        for (int i=0;i<map.length;i++){
            for (int j =0;j<map[0].length;j++){
                if (map[i][j] > 0){
                    g.setColor(Color.blue);
                    g.fillRect(j*Brickwidth+80,i*Brickheight+50,Brickwidth,Brickheight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j*Brickwidth+80,i*Brickheight+50,Brickwidth,Brickheight);
                }
            }
        }
    }
    public void SetBrickVal(int val,int row,int col){
        map[row][col] = val;
    }
}
