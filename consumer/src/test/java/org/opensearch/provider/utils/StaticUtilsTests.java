/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */
package org.opensearch.consumer.utils;

import org.opensearch.test.OpenSearchTestCase;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class StaticUtilsTests extends OpenSearchTestCase {

    public void testStaticNoArg() {
        assertEquals(StaticUtils.name(), "TEST");

        try (MockedStatic<StaticUtils> utilities = Mockito.mockStatic(StaticUtils.class)) {
            utilities.when(StaticUtils::name).thenReturn("NOPE");
            assertEquals(StaticUtils.name(), "NOPE");
        }

        assertEquals(StaticUtils.name(), "TEST");
    }
}
