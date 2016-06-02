/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.teradata.tpcds;

import static com.teradata.tpcds.type.Date.fromJulianDays;

public abstract class TableRowWithNulls
        implements TableRow
{
    private long nullBitMap;

    protected TableRowWithNulls(long nullBitMap)
    {
        this.nullBitMap = nullBitMap;
    }

    private boolean isNull(Column firstColumn, Column column)
    {
        long kBitMask = 1 << (column.getGlobalColumnNumber() - firstColumn.getGlobalColumnNumber());
        return (nullBitMap & kBitMask) != 0;
    }

    protected <T> String getStringOrNull(T value, Column firstColumn, Column column)
    {
        return isNull(firstColumn, column) ? null : value.toString();
    }

    protected <T> String getStringOrNullForKey(long value, Column firstColumn, Column column)
    {
        return (isNull(firstColumn, column) || value == -1) ? null : Long.toString(value);
    }

    protected <T> String getDateStringOrNullFromJulianDays(long value, Column firstColumn, Column column)
    {
        return (isNull(firstColumn, column) || value < 0) ? null : fromJulianDays((int) value).toString();
    }
}