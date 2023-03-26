package org.myProject.obj;

import java.awt.*;

public class BgObj extends GameObj {
    //Rewrite constructor and paintself method

    public BgObj() {
        super();
    }

    public BgObj(Image img, int x, int y, double speed) {
        super(img, x, y, speed);
    }

    @Override
    public void paintself(Graphics gImage) {
        super.paintself(gImage);
        y+=speed;
        if(y>=0){
            y=-400;
        }
    }
}
