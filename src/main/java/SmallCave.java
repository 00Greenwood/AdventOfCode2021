public class SmallCave extends Cave{
    public SmallCave(String id) {
        super(id);
    }

    @Override
    public Boolean isSmall() {
        return true;
    }
}
