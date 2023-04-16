package tracker.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tracker.logic.commands.EditCommand.EditBookDescriptor;
import tracker.model.book.*;
import tracker.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditBookDescriptorBuilder {

    private EditBookDescriptor descriptor;

    public EditBookDescriptorBuilder() {
        descriptor = new EditBookDescriptor();
    }

    public EditBookDescriptorBuilder(EditBookDescriptor descriptor) {
        this.descriptor = new EditBookDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditBookDescriptorBuilder(Book book) {
        descriptor = new EditBookDescriptor();
        descriptor.setTitle(book.getTitle());
        descriptor.setAuthor(book.getAuthor());
        descriptor.setNote(book.getNote());
        descriptor.setCategory(book.getCategory());
        descriptor.setProgress(book.getProgress());
        descriptor.setDateAdded(book.getDateAdded());
        descriptor.setTags(book.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withAuthor(String author) {
        descriptor.setAuthor(new Author(author));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withNote(String note) {
        descriptor.setNote(new Note(note));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withCategory(String category) {
        descriptor.setCategory(new Category(category));
        return this;
    }

    /**
     * Sets the {@code BusinessSize} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withProgress(String progress) {
        descriptor.setProgress(new Progress(progress));
        return this;
    }

    /**
     * Sets the {@code Company} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withDateAdded(String dateAdded) {
        descriptor.setDateAdded(new DateAdded(dateAdded));
        return this;
    }


    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditBookDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditBookDescriptor build() {
        return descriptor;
    }
}
