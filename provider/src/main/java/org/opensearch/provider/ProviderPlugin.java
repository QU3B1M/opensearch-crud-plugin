/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.provider;

import com.wazuh.common.transport.CommandRequest;
import com.wazuh.common.transport.CommandRequestAction;
import com.wazuh.common.transport.CommandResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opensearch.action.ActionRequest;
import org.opensearch.cluster.metadata.IndexNameExpressionResolver;
import org.opensearch.cluster.node.DiscoveryNode;
import org.opensearch.common.settings.ClusterSettings;
import org.opensearch.common.settings.IndexScopedSettings;
import org.opensearch.common.settings.Settings;
import org.opensearch.common.settings.SettingsFilter;
import org.opensearch.core.action.ActionResponse;
import org.opensearch.plugins.ActionPlugin;
import org.opensearch.plugins.ClusterPlugin;
import org.opensearch.plugins.Plugin;
import org.opensearch.provider.action.TransportCommandRequestAction;
import org.opensearch.rest.RestController;
import org.opensearch.rest.RestHandler;

import java.util.List;
import java.util.function.Supplier;

import static java.util.Collections.singletonList;

/**
 * The TasksPlugin class registers REST endpoints for OpenSearch.
 */
public class ProviderPlugin extends Plugin implements ActionPlugin, ClusterPlugin {

    private static final Logger log = LogManager.getLogger(ProviderPlugin.class);

  @Override
  public List<ActionHandler<? extends ActionRequest, ? extends ActionResponse>> getActions() {
    log.error("CommandRequestAction instance: {}", CommandRequestAction.INSTANCE);
    return List.of(
        new ActionHandler<>(CommandRequestAction.INSTANCE, TransportCommandRequestAction.class)
    );
  }

  @Override
    public void onNodeStarted(DiscoveryNode localNode) {
    }

}
