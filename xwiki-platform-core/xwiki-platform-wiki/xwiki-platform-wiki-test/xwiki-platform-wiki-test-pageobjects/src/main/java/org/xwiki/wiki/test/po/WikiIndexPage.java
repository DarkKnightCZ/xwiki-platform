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
package org.xwiki.wiki.test.po;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Represents actions that can be done on the WikiManager.WebHome page.
 *
 * @version $Id$
 */
public class WikiIndexPage extends ExtendedViewPage
{
    @FindBy(id = "wikis")
    private WebElement wikisTable;

    @FindBy(xpath = "//table[@id='wikis']//td[@class='wikiprettyname linkfield typetext']/a\n")
    private List<WebElement> wikiPrettyNames;

    /**
     * Opens the home page.
     */
    public static WikiIndexPage gotoPage()
    {
        getUtil().gotoPage(getSpace(), getPage());
        return new WikiIndexPage();
    }

    public static String getSpace()
    {
        return "WikiManager";
    }

    public static String getPage()
    {
        return "WebHome";
    }

    public List<WebElement> getWikiPrettyNames()
    {
        return wikiPrettyNames;
    }
}
