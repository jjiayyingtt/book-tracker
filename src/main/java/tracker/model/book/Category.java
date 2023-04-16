package tracker.model.book;

/**
 * Represents the Category the book is in.
 * Guarantees: is valid as declared in {@link #isValidCategory(String)}
 */
public class Category implements Comparable<Category> {


    public static final String MESSAGE_CONSTRAINTS =
            "Category must be marked as either WANT, READ, OR READING";
    public static final String[] VALIDATION_ARRAY = {"WANT", "READ", "READING"};
    public final String value;

    /**
     * Constructs an {@code Category}.
     *
     * @param category Category of the book.
     */
    public Category(String category) {
        this.value = category;
    }

    /**
     * Checks whether a given string is a priority.
     * @param test The category to be checked.
     */
    public static boolean isValidCategory(String test) {
        for (String str : VALIDATION_ARRAY) {
            if (test.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Maps the priority of the client to an integer value.
     *
     * @return The integer value of the client's priority.
     */
    public Integer getCategoryValue() {
        if (value.equals("WANT")) {
            return 3;
        } else if (value.equals("READ")) {
            return 2;
        } else if (value.equals("READING")) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return value;
    }


    @Override
    public int compareTo(Category otherCategory) {
        return this.getCategoryValue() - otherCategory.getCategoryValue();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Category // instanceof handles nulls
                && value.equals(((Category) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
