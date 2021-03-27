package friendsmatchmakingapp.client;

public class Player {
    private final String name;
    private final String socialMedia;
    private int score = 0;

    public Player(String name, String socialMedia) {
        this.name = name;
        this.socialMedia = socialMedia;
    }

    public String getName() {
        return name;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
