package Utility;

public record Area(Integer left, Integer top, Integer right, Integer bottom) {

    public Boolean hit(Integer x, Integer y) {
        return x >= left && x <= right && y <= top && y >= bottom;
    }

    public Boolean missed(Integer x, Integer y) {
        return x > right || y < bottom;
    }
}
