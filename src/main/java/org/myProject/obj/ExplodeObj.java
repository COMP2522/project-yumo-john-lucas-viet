package org.myProject.obj;

import java.awt.*;

public class ExplodeObj extends GameObj {

    static Image[] pic=new Image[16];
    //The exploded view is only shown once
    int explodeCount=0;
    static {
        for (int i=0;i<pic.length;i++){
            pic[i]=Toolkit.getDefaultToolkit().getImage("image/explode/e"+(i+1)+".gif");
        }
    }
    public ExplodeObj(int x, int y) {
        super(x, y);
    }

    @Override
    public void paintself(Graphics gImage) {

        if(explodeCount<16){
            img=pic[explodeCount];
            super.paintself(gImage);
            explodeCount++;
        }
    }
}