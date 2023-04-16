package tracker.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tracker.commons.exceptions.IllegalValueException;
import tracker.model.book.*;
import tracker.model.tag.Tag;

/**
 * Json-friendly version of {@link Book}.
 */
class JsonAdaptedBook {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Book's %s field is missing!";

    private final String title;
    private final String author;
    private final String note;
    private final String category;
    private final String progress;
    private final String dateAdded;
    private final String rating;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedBook} with the given book details.
     */
    @JsonCreator
    public JsonAdaptedBook(@JsonProperty("title") String title, @JsonProperty("author") String author,
                           @JsonProperty("note") String note, @JsonProperty("category") String category,
                           @JsonProperty("progress") String progress, @JsonProperty("dateAdded") String dateAdded,
                           @JsonProperty("rating") String rating,
                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.title = title;
        this.author = author;
        this.note = note;
        this.category = category;
        this.progress = progress;
        this.dateAdded = dateAdded;
        this.rating = rating;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Book} into this class for Json use.
     */
    public JsonAdaptedBook(Book source) {
        title = source.getTitle().fullTitle;
        author = source.getAuthor().value;
        note = source.getNote().value;
        category = source.getCategory().value;
        progress = source.getProgress().value;
        dateAdded = source.getDateAdded().value;
        rating = source.getRating().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Json-friendly adapted book object into the model's {@code Book} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted book.
     */
    public Book toModelType() throws IllegalValueException {
        final List<Tag> bookTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            bookTags.add(tag.toModelType());
        }
        // TODO: 9/4/2023 change all variable names

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelName = new Title(title);

        if (author == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Author.class.getSimpleName()));
        }
        if (!Author.isValidAuthor(author)) {
            throw new IllegalValueException(Author.MESSAGE_CONSTRAINTS);
        }
        final Author modelPhone = new Author(author);

        if (note == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Note.class.getSimpleName()));
        }
        if (!Note.isValidNote(note)) {
            throw new IllegalValueException(Note.MESSAGE_CONSTRAINTS);
        }
        final Note modelEmail = new Note(note);

        if (category == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Category.class.getSimpleName()));
        }
        if (!Category.isValidCategory(category)) {
            throw new IllegalValueException(Category.MESSAGE_CONSTRAINTS);
        }
        final Category modelAddress = new Category(category);

        if (progress == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Progress.class.getSimpleName()));
        }

        if (!Progress.isValidProgress(progress)) {
            throw new IllegalValueException(Progress.MESSAGE_CONSTRAINTS);
        }

        final Progress modelCompany = new Progress(progress);

        if (dateAdded == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateAdded.class.getSimpleName()));
        }

        if (!DateAdded.isValidDate(dateAdded)) {
            throw new IllegalValueException("DateAdded.MESSAGE_CONSTRAINTS");
        }

        final DateAdded modelDateAdded = new DateAdded(dateAdded);

        if (!Rating.isValidRating(rating)) {
            throw new IllegalValueException(Rating.MESSAGE_CONSTRAINTS);
        }

        final Rating modelRating = new Rating(rating);


        final Set<Tag> modelTags = new HashSet<>(bookTags);
        return new Book(modelName, modelPhone, modelEmail, modelAddress, modelCompany,
                modelDateAdded, modelRating, modelTags);
    }

}
