package things.exceptions;

/**
 * @author: Markus Binsteiner
 */
public class ActionException extends Exception {

    private final String actionName;

    public ActionException(String s, String actionname) {
        super(s);
        this.actionName = actionname;
    }

    public ActionException(String s) {
        super(s);
        this.actionName = null;
    }

    public String getActionName() {
        return actionName;
    }
}
