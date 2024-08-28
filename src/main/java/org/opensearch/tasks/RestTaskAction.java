/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.tasks;

import org.opensearch.action.get.GetRequest;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.action.update.UpdateRequest;
import org.opensearch.client.node.NodeClient;
import org.opensearch.rest.BaseRestHandler;
import org.opensearch.rest.BytesRestResponse;
import org.opensearch.rest.RestRequest;
import org.opensearch.rest.action.RestStatusToXContentListener;
import org.opensearch.rest.action.RestToXContentListener;
import org.opensearch.tasks.action.RestTaskGetAction;
import org.opensearch.tasks.action.RestTaskIndexAction;
import org.opensearch.tasks.action.RestTaskUpdateAction;

import java.io.IOException;
import java.util.List;

import static org.opensearch.rest.RestRequest.Method.*;


public class RestTaskAction extends BaseRestHandler{

    static final String BASE_URI = "/_plugins/tasks";

    @Override
    public String getName() {
        return "rest_task_action";
    }

    @Override
    public List<Route> routes() {
        return List.of(
            new Route(POST, BASE_URI),
            new Route(GET, BASE_URI + "/{id}"),
            new Route(PUT, BASE_URI + "/{id}"),
            new Route(DELETE, BASE_URI + "/{id}")
        );
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) throws IOException {
        try {

            if (request.method() == POST) {
                IndexRequest indexRequest = RestTaskIndexAction.createIndexRequest(request);
                return channel -> client.index(indexRequest, new RestStatusToXContentListener<>(channel, r -> r.getLocation(indexRequest.routing())));
            } else if (request.method() == GET) {
                GetRequest getRequest = RestTaskGetAction.getRequest(request);
                return channel -> client.get(getRequest, new RestToXContentListener<>(channel));
            } else if (request.method() == PUT) {
                UpdateRequest updateRequest = RestTaskUpdateAction.updateRequest(request);
                return channel -> client.update(updateRequest, new RestStatusToXContentListener<>(channel, r -> r.getLocation(updateRequest.routing())));
            } else if (request.method() == DELETE) {
                // Implement the DELETE method here
                return channel -> channel.sendResponse(new BytesRestResponse(channel, new UnsupportedOperationException("Not yet implemented")));


            } else {
                throw new IllegalArgumentException("Unsupported method: " + request.method());
            }
        } catch (Exception e) {
            return channel -> channel.sendResponse(new BytesRestResponse(channel, e));
        }
    }
}
