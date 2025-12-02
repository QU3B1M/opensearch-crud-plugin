/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package com.wazuh.provider;

import com.carrotsearch.randomizedtesting.annotations.ThreadLeakScope;
import org.opensearch.test.OpenSearchIntegTestCase;

@ThreadLeakScope(ThreadLeakScope.Scope.NONE)
@OpenSearchIntegTestCase.ClusterScope(scope = OpenSearchIntegTestCase.Scope.SUITE)
public class ProviderPluginIT extends OpenSearchIntegTestCase {

}
