package Utility;

public class Area {
    private Integer left;
    private Integer top;
    private Integer right;
    private Integer bottom;

    public Area (Integer left, Integer top, Integer right, Integer bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public Boolean hit(Integer x, Integer y){
        return x >= left && x <= right && y <= top && y >= bottom;
    }

    public Boolean missed(Integer x, Integer y){
        return x > right || y < bottom;
    }

    public Integer right() {
        return right;
    }

    public Integer bottom() {
        return bottom;
    }
}
