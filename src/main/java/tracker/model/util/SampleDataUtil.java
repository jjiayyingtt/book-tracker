package tracker.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import tracker.model.BookTracker;
import tracker.model.ReadOnlyBookTracker;
import tracker.model.book.*;
import tracker.model.tag.Tag;

/**
 * Contains utility methods for populating {@code BookTracker} with sample data.
 */
public class SampleDataUtil {
    public static Book[] getSampleBooks() {
        return new Book[] {
            new Book(new Title("Book"), new Author("Me"), new Note("masterpiece!"),
                new Category("READ"),
                    new Progress("100"), new DateAdded("2020/12/23"), new DateStarted("2020/12/23"),
                    new DateFinished("2020/12/23"), new Rating("3.5"),
                getTagSet("nonfiction")),
        };
    }

    public static ReadOnlyBookTracker getSampleBookTracker() {
        BookTracker sampleBT = new BookTracker();
        for (Book sampleBook : getSampleBooks()) {
            sampleBT.addBook(sampleBook);
        }
        return sampleBT;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
