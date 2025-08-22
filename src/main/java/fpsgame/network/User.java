package fpsgame.network;

public class User {
    public int playerId;
    public String username;
    public String email;
    public Long score;

    public User(int playerId, String username, String email, Long score) {
        this.playerId = playerId;
        this.username = username;
        this.email = email;
        this.score = score;
    }

    @Override
    public String toString() {
        return "User{" +
                "playerId=" + playerId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
