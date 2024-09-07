/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.tasks.model;

import org.opensearch.core.xcontent.ToXContent;
import org.opensearch.core.xcontent.XContentBuilder;
import org.opensearch.core.xcontent.XContentParser;
import org.opensearch.core.xcontent.ToXContentObject;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

import static org.opensearch.core.xcontent.XContentParserUtils.ensureExpectedToken;

/**
 * This class represents a Task model
 */
public class Task implements ToXContentObject  {

    /**
     * Enum for the different statuses of a task
     */
    public enum TaskStatus {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }

    // Constants
    public static final String TASK_INDEX = "task";
    public static final String TITLE_FIELD = "title";
    public static final String DESCRIPTION_FIELD = "description";
    public static final String STATUS_FIELD = "status";

    // Fields
    private String title;
    private String description;
    private TaskStatus status;

    // Constructors

    /**
     * Default constructor
     */
    public Task() {}

    /**
     * Constructor with title, description, and status.
     * @param title The title of the task.
     * @param description The description of the task. Default is empty string.
     * @param status The status of the task. Default is PENDING.
     */
    public Task(String title, String description, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    /**
     * Copy constructor.
     * @param copyTask The Task object to copy.
     */
    public Task(final Task copyTask) {
        this(copyTask.title, copyTask.description, copyTask.status);
    }

    // Getters and Setters

    /**
     * Gets the title of the task.
     * @return The title of the task.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the task.
     * @param title The title of the task.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the task.
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the task.
     * @param description The description of the task.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the status of the task.
     * @return The status of the task.
     */
    public TaskStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the task.
     * @param status The status of the task.
     */
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    // Overridden methods

    /**
     * Checks if this task is equal to another object.
     * @param o The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task that = (Task) o;
        return Objects.equals(title, that.title) && status == that.status;
    }

    /**
     * Generates a hash code for this task.
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, description, status);
    }

    /**
     * Returns a string representation of this task.
     * @return The string representation.
     */
    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    /**
     * Serializes this task to XContent.
     * @param builder The XContentBuilder to serialize to.
     * @param params The parameters for serialization.
     * @return The XContentBuilder with the serialized task.
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
     * Parses a Task object from XContentParser.
     * @param parser The XContentParser to parse from.
     * @return The parsed Task object.
     * @throws IOException If an I/O error occurs.
     */
    public static Task parse(XContentParser parser) throws IOException {
        String title = null;
        String description = null;
        TaskStatus status = null;

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
                    status = TaskStatus.valueOf(parser.text().toUpperCase(Locale.ROOT));
                    break;
                default:
                    parser.skipChildren();
                    break;
            }
        }

        return new Task(title, description, status);
    }

    /**
     * Creates a Task object from XContentParser.
     * @param parser The XContentParser to parse from.
     * @return The created Task object.
     * @throws IOException If an I/O error occurs.
     */
    public static Task fromXContent(XContentParser parser) throws IOException {
        return parse(parser);
    }
}
