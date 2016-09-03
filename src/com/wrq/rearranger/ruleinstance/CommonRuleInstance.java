/*
 * Copyright (c) 2003, 2010, Dave Kriewall
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 *
 * 1) Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 * disclaimer.
 *
 * 2) Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.wrq.rearranger.ruleinstance;

import com.wrq.rearranger.entry.IPopupTreeRangeEntry;
import com.wrq.rearranger.entry.MethodEntry;
import com.wrq.rearranger.entry.RangeEntry;
import com.wrq.rearranger.entry.ClassContentsEntry;
import com.wrq.rearranger.popup.IFilePopupEntry;
import com.wrq.rearranger.rearrangement.Emitter;
import com.wrq.rearranger.settings.RearrangerSettings;
import com.wrq.rearranger.settings.atomicAttributes.SortOptions;
import com.wrq.rearranger.settings.attributeGroups.AttributeGroup;
import com.wrq.rearranger.settings.attributeGroups.FieldAttributes;
import com.wrq.rearranger.settings.attributeGroups.IRule;
import com.wrq.rearranger.settings.attributeGroups.CommonAttributes;
import com.wrq.rearranger.util.ModifierUtils;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles basic Rule functions.
 */
public abstract class CommonRuleInstance
        implements IRuleInstance,
        IFilePopupEntry
{
    private static final Logger logger = Logger.getLogger("com.wrq.rearranger.ruleinstance.CommonRuleInstance");
    protected final IRule rule;
    protected final List<RangeEntry> matchedItems;

    public CommonRuleInstance(IRule rule)
    {
        this.rule = rule;
        matchedItems = new ArrayList<RangeEntry>();
    }

    public IRule getRule()
    {
        return rule;
    }

    /**
     *  adds entry to list of matching entries, in correct order (order encountered, or alphabetically)
     * @param entry
     */
    public void addEntry(RangeEntry entry)
    {
        if (rule instanceof CommonAttributes)
        {
            SortOptions sortOptions = ((CommonAttributes)rule).getSortAttr();
            insertionSort(entry, sortOptions);
        }
        else
        {
            matchedItems.add(entry);
        }
    }

    public List<RangeEntry> getMatches()
    {
        return matchedItems;
    }

    private void insertionSort(final RangeEntry newEntry, final SortOptions sortOptions)
    {
        boolean inserted = false;
        final String s = sortOptions.generateSortString(newEntry);
        logger.debug("insertionSort: new entry=" + s);
        int index = 0;
        while (index < matchedItems.size())
        {
            final RangeEntry re = matchedItems.get(index);
            final String res = sortOptions.generateSortString(re);
            logger.debug("insertionSort: compare to " + res);
            if (s.compareTo(res) < 0)
            {
                logger.debug("insertionSort: insert at " + index);
                matchedItems.add(index, newEntry);
                inserted = true;
                break;
            }
            index++;
        }
        if (!inserted)
        {
            logger.debug("insertionSort: add " + s + " to end");
            matchedItems.add(newEntry);
        }
    }

    public boolean hasMatches()
    {
        return matchedItems.size() > 0;
    }

    public void emit(Emitter emitter)
    {
        for (RangeEntry rangeEntry : matchedItems)
        {
            rangeEntry.emit(emitter);
        }
    }

    public void rearrangeRuleItems(List<ClassContentsEntry> entries,
                                   RearrangerSettings settings)
    {
        MethodEntry.rearrangeRelatedItems(
                entries,
                this,
                settings.getExtractedMethodsSettings()
        );
    }

    public void addRuleInstanceToPopupTree(DefaultMutableTreeNode node, RearrangerSettings settings)
    {
        /**
         * if we are supposed to show rules, create a node for the rule and put its contents below.
         * Otherwise, delegate to each of the matches.
         */
        DefaultMutableTreeNode top = node;
        if (settings.isShowRules() &&
                (!settings.isShowMatchedRules() || hasMatches()))
        {
            top = new DefaultMutableTreeNode(this);
            node.add(top);
        }
        /**
         * append each of the matches to the top level.
         */
        for (IPopupTreeRangeEntry entry : getMatches())
        {
            entry.addToPopupTree(top, settings);
        }
    }

    public String getTypeIconName()
    {
        return "general/templateGroup";
    }

    public String[] getAdditionalIconNames()
    {
        return null;
    }

    public JLabel getPopupEntryText(RearrangerSettings settings)
    {
        JLabel label = new JLabel(toString());
        Font font = label.getFont().deriveFont(Font.ITALIC);
        label.setFont(font);
        return label;
    }

    public String toString()
    {
        return getRule().toString();
    }
}
