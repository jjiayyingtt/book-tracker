package tracker.model.book;

import static tracker.commons.util.CollectionUtil.requireAllNonNull;

import java.util.*;

import tracker.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Book implements Comparable<Book> {

    // Identity fields
    private final Title title;
    private final Progress progress;
    private final DateAdded dateAdded;

    // Data fields
    private final Note note;

    private final Author author;

    private final Category category;

    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Book(Title title, Author author, Note note, Category category, Progress progress, DateAdded dateAdded,
                  Set<Tag> tags) {
        requireAllNonNull(title, author, note, category, progress, dateAdded, tags);
        this.title = title;
        this.author = author;
        this.note = note;
        this.category = category;
        this.progress = progress;
        this.dateAdded = dateAdded;
        this.tags.addAll(tags);
    }

    public Title getTitle() {
        return title;
    }
    public String getNameString() { return getTitle().fullTitle; }
    public String getStandardisedNameString() { return getNameString().toLowerCase(); }
    public Author getAuthor() {
        return author;
    }
    public Note getNote() {
        return note;
    }
    public Category getCategory() {
        return category;
    }
    public Integer getCategoryInt() { return category.getCategoryValue(); }
    public Progress getProgress() { return progress; }
    public DateAdded getDateAdded() {
        return dateAdded;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameBook(Book otherBook) {
        if (otherBook == this) {
            return true;
        }

        return otherBook != null
                && otherBook.getTitle().equals(getTitle()) && otherBook.getAuthor().equals(getAuthor());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Book)) {
            return false;
        }

        Book otherBook = (Book) other;
        return otherBook.getTitle().equals(getTitle())
                && otherBook.getAuthor().equals(getAuthor())
                && otherBook.getNote().equals(getNote())
                && otherBook.getCategory().equals(getCategory())
                && otherBook.getProgress().equals(getProgress())
                && otherBook.getDateAdded().equals(getDateAdded())
                && otherBook.getTags().equals(getTags());
    }


    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, author, note, category, progress, dateAdded, tags);
    }



    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append("; Author: ")
                .append(getAuthor())
                .append("; Note: ")
                .append(getNote())
                .append("; Category: ")
                .append(getCategory())
                .append("; Progress: ")
                .append(getProgress())
                .append("; Date Added: ")
                .append(getDateAdded());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     *      is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Book o) {
        //return o.getBusinessSizeInt() - this.getBusinessSizeInt();
        return 1;
    }
}
