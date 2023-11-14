package travels;

public class NoServiceAvailable extends RuntimeException{
    NoServiceAvailable(String msg)
    {
        super(msg);
    }
}
