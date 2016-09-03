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
package com.wrq.rearranger.rearrangement;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.wrq.rearranger.entry.ClassContentsEntry;
import com.wrq.rearranger.entry.ClassEntry;
import com.wrq.rearranger.entry.PsiFileEntry;
import com.wrq.rearranger.entry.RangeEntry;
import com.wrq.rearranger.settings.RearrangerSettings;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Creates a list of entries for classes and class members by parsing the Java file.
 */
public final class Parser
{
    private static final Logger logger = Logger.getLogger("com.wrq.rearranger.rearrangement.Parser");
    private final Project project;
    private final RearrangerSettings settings;
    private final PsiFile psiFile;

    public Parser(final Project project,
                  final RearrangerSettings settings,
                  final PsiFile psiFile)
    {
        this.project = project;
        this.settings = settings;
        this.psiFile = psiFile;
    }

    public List<ClassContentsEntry> parseOuterLevel()
    {
        /**
         * Parse the top level contents of the PsiFile here.
         */
        PsiFileEntry fileEntry = new PsiFileEntry(settings);
        return fileEntry.parseFile(project, psiFile, settings.getClassOrderAttributeList());

    }

    private void dumpOuterClasses(final List<? extends ClassEntry> outerClasses)
    {
        logger.debug("Outer class entries:");
        for (ClassEntry classEntry : outerClasses)
        {
            logger.debug(classEntry.toString());
            if (classEntry.getContents() == null) {
                // this is a header or trailer, and isn't really a class.
                continue;
            }
            logger.debug("   === class contents:");
            for (RangeEntry rangeEntry :  classEntry.getContents())
            {
                logger.debug(rangeEntry.toString());
            }
            logger.debug("   === end class contents:");
        }
    }


}
