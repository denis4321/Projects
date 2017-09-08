package networkex.homework;

import java.io.*;

public interface Message extends Serializable {

    public static final int TEXT = 1;
    public static final int CLOSE = 2;
    public static final int FILE = 3;

    public String getNick();

    public int getType();

    public Object getContent();

    public String getTo();
}
