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
package com.wrq.rearranger.entry;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiClass;
import com.wrq.rearranger.settings.RearrangerSettings;
import com.wrq.rearranger.ModifierConstants;
import com.wrq.rearranger.popup.RearrangerTreeNode;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;

/**
 * Represents a class initializer.
 */
public class ClassInitializerEntry
        extends ClassContentsEntry
{
    private static final Logger logger = Logger.getLogger("com.wrq.rearranger.entry.ClassInitializerEntry");

    public ClassInitializerEntry(final PsiElement start,
                                 final PsiElement end,
                                 final int modifiers,
                                 final String modifierString,
                                 final String name)
    {
        super(start, end, modifiers, modifierString, name, "");
    }


    public String getTypeIconName()
    {
        final String name = Modifier.isStatic(getModifiers()) ? "nodes/StaticClassInitializer" : "nodes/ClassInitializer";
        return name;
    }

    public String[] getAdditionalIconNames()
    {
        return null;
    }

    public JLabel getPopupEntryText(RearrangerSettings settings)
    {
        return new JLabel(Modifier.isStatic(getModifiers()) ? "<static class initializer>" : "<class initializer>");
    }

    public DefaultMutableTreeNode addToPopupTree(DefaultMutableTreeNode parent, RearrangerSettings settings)
    {
        logger.debug("add class initializer to popup tree");
        DefaultMutableTreeNode node = new RearrangerTreeNode(this, name);
        parent.add(node);
        return node;
    }
}
