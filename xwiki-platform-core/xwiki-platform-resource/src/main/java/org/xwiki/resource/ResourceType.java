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
package org.xwiki.resource;

import org.xwiki.stability.Unstable;

/**
 * Represents a type of XWiki Resource (Entity Resource, skin file Resource, etc).
 * 
 * @version $Id$
 * @since 5.3M1
 */
@Unstable
public enum ResourceType
{
    /**
     * Resource to an Entity (Document, Space, Page, Attachment, etc).
     */
    ENTITY,

    /**
     * Resource to a skin item (vm file, image, js, etc).
     */
    SKIN,

    /**
     * Resource to a template item (vm file).
     */
    TEMPLATE,

    /**
     * Resource to some file on the filesystem (js, images, etc).
     */
    RESOURCE
}
