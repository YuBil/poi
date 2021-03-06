/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package org.apache.poi.hssf.record.chart;


import static org.apache.poi.hssf.record.TestcaseRecordInputStream.confirmRecordEncoding;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.apache.poi.hssf.record.TestcaseRecordInputStream;
import org.junit.Test;

/**
 * Tests the serialization and deserialization of the BarRecord
 * class works correctly.  Test data taken directly from a real
 * Excel file.
 */
public final class TestBarRecord {
    byte[] data = new byte[] {
        (byte)0x00,(byte)0x00,   // bar space
        (byte)0x96,(byte)0x00,   // category space
        (byte)0x00,(byte)0x00    // format flags
    };

    @Test
    public void testLoad() {
        BarRecord record = new BarRecord(TestcaseRecordInputStream.create(0x1017, data));
        assertEquals( 0, record.getBarSpace());
        assertEquals( 0x96, record.getCategorySpace());
        assertEquals( 0, record.getFormatFlags());
        assertFalse(record.isHorizontal());
        assertFalse(record.isStacked());
        assertFalse(record.isDisplayAsPercentage());
        assertFalse(record.isShadow());

        assertEquals( 10, record.getRecordSize() );
    }

    @SuppressWarnings("squid:S2699")
    @Test
    public void testStore() {
        BarRecord record = new BarRecord();
        record.setBarSpace( (short)0 );
        record.setCategorySpace( (short)0x96 );
        record.setHorizontal( false );
        record.setStacked( false );
        record.setDisplayAsPercentage( false );
        record.setShadow( false );


        byte [] recordBytes = record.serialize();
        confirmRecordEncoding(BarRecord.sid, data, recordBytes);
    }
}
