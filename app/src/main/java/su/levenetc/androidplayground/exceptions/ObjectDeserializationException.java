package su.levenetc.androidplayground.exceptions;

import java.io.IOException;

/**
 * Created by eugene.levenetc on 24/12/2016.
 */

public class ObjectDeserializationException extends Exception {

    public ObjectDeserializationException(Exception e) {
        super(e);
    }
}
