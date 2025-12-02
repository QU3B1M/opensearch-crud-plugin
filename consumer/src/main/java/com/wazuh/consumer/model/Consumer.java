/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package com.wazuh.consumer.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opensearch.core.xcontent.ToXContent;
import org.opensearch.core.xcontent.XContentBuilder;
import org.opensearch.core.xcontent.XContentParser;
import org.opensearch.core.xcontent.ToXContentObject;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

import static org.opensearch.core.xcontent.XContentParserUtils.ensureExpectedToken;

/**
 * This class represents a Consumer model
 */
public class Consumer implements ToXContentObject  {
    private static final Logger log = LogManager.getLogger(Consumer.class);

    /**
     * Enum for the different statuses of a consumer
     */
    public enum ConsumerStatus {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }

    // Constants
    public static final String TASK_INDEX = "consumer";
    public static final String TITLE_FIELD = "title";
    public static final String DESCRIPTION_FIELD = "description";
    public static final String STATUS_FIELD = "status";

    // Fields
    private String title;
    private String description;
    private ConsumerStatus status;

    // Constructors

    /**
     * Default constructor
     */
    public Consumer() {}

    /**
     * Constructor with title, description, and status.
     * @param title The title of the consumer.
     * @param description The description of the consumer. Default is empty string.
     * @param status The status of the consumer. Default is PENDING.
     */
    public Consumer(String title, String description, ConsumerStatus status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    /**
     * Copy constructor.
     * @param copyConsumer The Consumer object to copy.
     */
    public Consumer(final Consumer copyConsumer) {
        this(copyConsumer.title, copyConsumer.description, copyConsumer.status);
    }

    // Getters and Setters

    /**
     * Gets the title of the consumer.
     * @return The title of the consumer.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the consumer.
     * @param title The title of the consumer.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the consumer.
     * @return The description of the consumer.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the consumer.
     * @param description The description of the consumer.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the status of the consumer.
     * @return The status of the consumer.
     */
    public ConsumerStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the consumer.
     * @param status The status of the consumer.
     */
    public void setStatus(ConsumerStatus status) {
        this.status = status;
    }

    // Overridden methods

    /**
     * Checks if this consumer is equal to another object.
     * @param o The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consumer that = (Consumer) o;
        return Objects.equals(title, that.title) && status == that.status;
    }

    /**
     * Generates a hash code for this consumer.
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, description, status);
    }

    /**
     * Returns a string representation of this consumer.
     * @return The string representation.
     */
    @Override
    public String toString() {
        return "Consumer{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    /**
     * Serializes this consumer to XContent.
     * @param builder The XContentBuilder to serialize to.
     * @param params The parameters for serialization.
     * @return The XContentBuilder with the serialized consumer.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public XContentBuilder toXContent(XContentBuilder builder, ToXContent.Params params) throws IOException {
        XContentBuilder xContentBuilder = builder.startObject();
        if (title != null) {
            xContentBuilder.field(TITLE_FIELD, title);
        }
        if (description != null) {
            xContentBuilder.field(DESCRIPTION_FIELD, description);
        }
        if (status != null) {
            xContentBuilder.field(STATUS_FIELD, status);
        }
        return xContentBuilder.endObject();
    }

    // Static methods

    /**
     * Parses a Consumer object from XContentParser.
     * @param parser The XContentParser to parse from.
     * @return The parsed Consumer object.
     * @throws IOException If an I/O error occurs.
     */
    public static Consumer parse(XContentParser parser) throws IOException {
        String title = null;
        String description = null;
        ConsumerStatus status = null;
        log.info(testStatic());

        ensureExpectedToken(XContentParser.Token.START_OBJECT, parser.currentToken(), parser);
        while (parser.nextToken() != XContentParser.Token.END_OBJECT) {
            String fieldName = parser.currentName();
            parser.nextToken();
            switch (fieldName) {
                case TITLE_FIELD:
                    title = parser.text();
                    break;
                case DESCRIPTION_FIELD:
                    description = parser.text();
                    break;
                case STATUS_FIELD:
                    status = ConsumerStatus.valueOf(parser.text().toUpperCase(Locale.ROOT));
                    break;
                default:
                    parser.skipChildren();
                    break;
            }
        }

        return new Consumer(title, description, status);
    }

    /**
     * Creates a Consumer object from XContentParser.
     * @param parser The XContentParser to parse from.
     * @return The created Consumer object.
     * @throws IOException If an I/O error occurs.
     */
    public static Consumer fromXContent(XContentParser parser) throws IOException {
        return parse(parser);
    }

    public static String testStatic() {
        return "NOPE";
    }
}
