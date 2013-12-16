/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.wikistream.internal.output;

import java.io.IOException;
import java.io.OutputStream;

import org.xwiki.wikistream.output.OutputStreamOutputTarget;

/**
 * @version $Id$
 * @since 5.2M2
 */
public class DefaultOutputStreamOutputTarget implements OutputStreamOutputTarget
{
    private final OutputStream outputStream;

    public DefaultOutputStreamOutputTarget(OutputStream outputStream)
    {
        this.outputStream = outputStream;
    }

    @Override
    public boolean restartSupported()
    {
        return false;
    }

    public OutputStream getOutputStream()
    {
        return this.outputStream;
    }

    @Override
    public void close() throws IOException
    {
        // Closing the stream is the responsibility of the caller
    }

    @Override
    public String toString()
    {
        return this.outputStream.toString();
    }
}
