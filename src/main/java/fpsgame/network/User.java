package fpsgame.network;

public class User {
    public final int userId;
    public final String username;
    public final String email;
    public final long score;
    
    public User(int userId, String username, String email, long score) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.score = score;
    }
    
    @Override
    public String toString() {
        return String.format("User{id=%d, username='%s', email='%s', score=%d}", 
                           userId, username, email, score);
    }
}
