package org.opensearch.tasks.model;

import org.opensearch.core.xcontent.ToXContent;
import org.opensearch.core.xcontent.XContentBuilder;
import org.opensearch.core.xcontent.XContentParser;
import org.opensearch.core.xcontent.ToXContentObject;

import java.io.IOException;
import java.util.Objects;

import static org.opensearch.core.xcontent.XContentParserUtils.ensureExpectedToken;


public class TaskDetails implements ToXContentObject  {

    public enum TaskStatus {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }

    public static final String INDEX = "task_index";
    private String title;
    private String description;
    private TaskStatus status;

    public TaskDetails() {}

    public TaskDetails(String title, String description) {
        this(title, description, TaskStatus.PENDING);
    }

    public TaskDetails(String title, String description, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public TaskDetails(final TaskDetails copyTaskDetails) {
        this(
                copyTaskDetails.title,
                copyTaskDetails.description,
                copyTaskDetails.status
        );
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDetails that = (TaskDetails) o;
        return Objects.equals(title, that.title) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, status);
    }

    @Override
    public String toString() {
        return "TaskDetails{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    public static TaskDetails parse(XContentParser parser) throws IOException {
        String title = null;
        String description = null;
        TaskStatus status = null;

        ensureExpectedToken(XContentParser.Token.START_OBJECT, parser.currentToken(), parser);
        while (parser.nextToken() != XContentParser.Token.END_OBJECT) {
            String fieldName = parser.currentName();
            parser.nextToken();
            switch (fieldName) {
                case "title":
                    title = parser.text();
                    break;
                case "description":
                    description = parser.text();
                    break;
                case "status":
                    status = TaskStatus.valueOf(parser.text());
                    break;
                default:
                    parser.skipChildren();
                    break;
            }
        }

        return new TaskDetails(title, description, status);
    }

    public XContentBuilder toXContent(XContentBuilder builder, ToXContent.Params params) throws IOException {
        XContentBuilder xContentBuilder = builder.startObject();
        if (title != null) {
            xContentBuilder.field("title", title);
        }
        if (description != null) {
            xContentBuilder.field("description", description);
        }
        if (status != null) {
            xContentBuilder.field("status", status);
        }
        return xContentBuilder.endObject();
    }

    public static TaskDetails fromXContent(XContentParser parser) throws IOException {
        return parse(parser);
    }

    public static TaskDetails fromXContent(XContentParser parser, TaskDetails taskDetails) throws IOException {
        return new TaskDetails(taskDetails);
    }

}

