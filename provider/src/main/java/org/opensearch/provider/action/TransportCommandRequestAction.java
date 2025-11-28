/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.provider.action;

import com.wazuh.common.transport.CommandRequest;
import com.wazuh.common.transport.CommandRequestAction;
import com.wazuh.common.transport.CommandResponse;
import org.opensearch.action.ActionRequest;
import org.opensearch.action.support.ActionFilters;
import org.opensearch.action.support.HandledTransportAction;
import org.opensearch.common.inject.Inject;
import org.opensearch.core.action.ActionListener;
import org.opensearch.core.common.io.stream.Writeable.Reader;
import org.opensearch.tasks.Task;
import org.opensearch.transport.TransportService;
import org.opensearch.transport.client.Client;

public class TransportCommandRequestAction extends HandledTransportAction<ActionRequest, CommandResponse> {

  @Inject
  public TransportCommandRequestAction(
      TransportService transportService,
      ActionFilters actionFilters
      ) {
    super(CommandRequestAction.NAME, transportService, actionFilters, CommandRequest::new);
  }

  @Override
  protected void doExecute(Task task, ActionRequest request,
      ActionListener<CommandResponse> actionListener) {
      logger.info("Command Received from consumer endpoint");
  }
}
