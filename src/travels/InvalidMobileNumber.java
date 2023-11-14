package travels;

public class InvalidMobileNumber extends RuntimeException{
    InvalidMobileNumber(String msg)
    {
        super(msg);
    }
}
