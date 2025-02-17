public class Library {
    private static Library instance;

    public static Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

}
