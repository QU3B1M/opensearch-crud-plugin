/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.provider.handler;

import org.opensearch.action.delete.DeleteRequest;
import org.opensearch.action.get.GetRequest;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.action.update.UpdateRequest;
import org.opensearch.transport.client.node.NodeClient;
import org.opensearch.rest.BaseRestHandler;
import org.opensearch.rest.BytesRestResponse;
import org.opensearch.rest.RestRequest;
import org.opensearch.rest.RestResponse;
import org.opensearch.rest.action.RestResponseListener;
import org.opensearch.rest.action.RestToXContentListener;
import org.opensearch.core.rest.RestStatus;
import org.opensearch.core.xcontent.ToXContent;
import org.opensearch.provider.action.RestTaskDeleteAction;
import org.opensearch.provider.action.RestTaskGetAction;
import org.opensearch.provider.action.RestTaskIndexAction;
import org.opensearch.provider.action.RestTaskUpdateAction;

import java.util.List;
import java.util.Locale;

import static org.opensearch.rest.RestRequest.Method.*;

/**
 * Handles REST actions for tasks in OpenSearch.
 */
public class RestTaskHandler extends BaseRestHandler {

    public static final String BASE_URI = "/_plugins/tasks";

    /**
     * Returns the name of the handler.
     *
     * @return The name of the handler.
     */
    @Override
    public String getName() {
        return "rest_task_action";
    }

    /**
     * Defines the routes handled by this handler.
     *
     * @return A list of routes.
     */
    @Override
    public List<Route> routes() {
        return List.of(
                new Route(POST, BASE_URI),
                new Route(GET, BASE_URI + "/{id}"),
                new Route(PUT, BASE_URI + "/{id}"),
                new Route(DELETE, BASE_URI + "/{id}")
        );
    }

    /**
     * Prepares the request for execution based on the HTTP method.
     *
     * @param request The RestRequest containing the details of the request.
     * @param client The NodeClient to execute the request.
     * @return A RestChannelConsumer to handle the request.
     */
    @Override
    protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) {
        try {
            switch (request.method()) {
                case POST:
                    IndexRequest indexRequest = RestTaskIndexAction.createIndexRequest(request);
                    return channel -> client.index(indexRequest, new RestResponseListener<>(channel) {
                        @Override
                        public RestResponse buildResponse(org.opensearch.action.index.IndexResponse response) throws Exception {
                            BytesRestResponse restResponse = new BytesRestResponse(
                                RestStatus.CREATED,
                                response.toXContent(channel.newBuilder(), ToXContent.EMPTY_PARAMS)
                            );
                            String location = String.format(Locale.ROOT, "%s/%s", BASE_URI, response.getId());
                            restResponse.addHeader("Location", location);
                            return restResponse;
                        }
                    });
                case GET:
                    GetRequest getRequest = RestTaskGetAction.getRequest(request);
                    return channel -> client.get(getRequest, new RestToXContentListener<>(channel));
                case PUT:
                    UpdateRequest updateRequest = RestTaskUpdateAction.updateRequest(request);
                    return channel -> client.update(updateRequest, new RestResponseListener<>(channel) {
                        @Override
                        public RestResponse buildResponse(org.opensearch.action.update.UpdateResponse response) throws Exception {
                            BytesRestResponse restResponse = new BytesRestResponse(
                                RestStatus.OK,
                                response.toXContent(channel.newBuilder(), ToXContent.EMPTY_PARAMS)
                            );
                            String location = String.format(Locale.ROOT, "%s/%s", BASE_URI, response.getId());
                            restResponse.addHeader("Location", location);
                            return restResponse;
                        }
                    });
                case DELETE:
                    DeleteRequest deleteRequest = RestTaskDeleteAction.deleteRequest(request);
                    return channel -> client.delete(deleteRequest, new RestToXContentListener<>(channel));
                default:
                    throw new IllegalArgumentException("Unsupported method: " + request.method());
            }
        } catch (Exception e) {
            return channel -> channel.sendResponse(new BytesRestResponse(channel, e));
        }
    }
}
