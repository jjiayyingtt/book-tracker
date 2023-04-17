package tracker.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_TITLE = new Prefix("ti/");
    public static final Prefix PREFIX_AUTHOR = new Prefix("a/");
    public static final Prefix PREFIX_NOTE = new Prefix("n/");
    public static final Prefix PREFIX_CATEGORY = new Prefix("c/");
    public static final Prefix PREFIX_PROGRESS = new Prefix("p/");
    public static final Prefix PREFIX_DATEADDED = new Prefix("d/");
    public static final Prefix PREFIX_DATESTARTED = new Prefix("ds/");
    public static final Prefix PREFIX_DATEFINISHED = new Prefix("df/");
    public static final Prefix PREFIX_RATING = new Prefix("r/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

}
