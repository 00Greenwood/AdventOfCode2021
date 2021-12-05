public class Pair<T, U> {
    public T first;
    public U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public boolean equals(Pair<T, U> pair) {
        return first.equals(pair.first) && second.equals(pair.second);
    }
}
