package tracker.testutil;

import java.util.HashSet;
import java.util.Set;

import tracker.model.book.Note;
import tracker.model.book.Author;
import tracker.model.book.DateAdded;
import tracker.model.book.Title;
import tracker.model.book.Book;
import tracker.model.book.Progress;
import tracker.model.book.Category;
import tracker.model.tag.Tag;
import tracker.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    public static final String DEFAULT_BUSINESSSIZE = "420";

    public static final String DEFAULT_COMPANY = "software engineering is not cs";

    public static final String DEFAULT_PRIORITY = "HIGH";
    public static final String DEFAULT_TRANSACTION_COUNT = "0";

    private Title name;
    private Progress phone;
    private DateAdded email;
    private Note address;
    private BusinessSize businessSize;

    private Author company;

    private Category priority;

    private TransactionCount transactionCount;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Title(DEFAULT_NAME);
        phone = new Progress(DEFAULT_PHONE);
        email = new DateAdded(DEFAULT_EMAIL);
        address = new Note(DEFAULT_ADDRESS);
        businessSize = new BusinessSize(DEFAULT_BUSINESSSIZE);
        company = new Author(DEFAULT_COMPANY);
        priority = new Category(DEFAULT_PRIORITY);
        transactionCount = new TransactionCount(DEFAULT_TRANSACTION_COUNT);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Book personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        businessSize = personToCopy.getBusinessSize();
        company = personToCopy.getCompany();
        priority = personToCopy.getPriority();
        transactionCount = personToCopy.getTransactionCount();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Title(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Note(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Progress(phone);
        return this;
    }

    /**
     * Sets the {@code BusinessSize} of the {@code Person} that we are building.
     */
    public PersonBuilder withBusinessSize(String businessSize) {
        this.businessSize = new BusinessSize(businessSize);
        return this;
    }

    /**
     * Sets the {@code Company} of the {@code Person} that we are building.
     */
    public PersonBuilder withCompany(String company) {
        this.company = new Author(company);
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Person} that we are building.
     */
    public PersonBuilder withPriority(String priority) {
        this.priority = new Category(priority);
        return this;
    }


    /**
     * Sets the {@code TransactionCount} of the {@code Person} that we are building.
     */
    public PersonBuilder withTransactionCount(String transactionCount) {
        this.transactionCount = new TransactionCount(transactionCount);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new DateAdded(email);
        return this;
    }

    /**
     * Build a new person.
     */
    public Book build() {
        return new Book(name, phone, email, address, businessSize, company,
                priority, transactionCount, tags);
    }

}
