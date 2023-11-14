package travels;

public class InvalidEmail extends RuntimeException {
    InvalidEmail(String msg)
    {
        super(msg);
    }
}
