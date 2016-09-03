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

import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiFile;
import com.wrq.rearranger.ruleinstance.IRuleInstance;

import java.util.List;

/**
 * Emits a new document from the rearranged entries.
 */
public final class Emitter
{
    private final PsiFile psiFile;
    private final List<IRuleInstance> resultRuleInstances;
    private final Document document;
    private StringBuffer stringBuffer;

    public Emitter(final PsiFile psiFile, final List<IRuleInstance> resultRuleInstances, final Document document)
    {
        this.psiFile = psiFile;
        this.resultRuleInstances = resultRuleInstances;
        this.document = document;
        stringBuffer = new StringBuffer(psiFile.getText().length() + 100); // room for inserted blank lines
    }

    public Document getDocument()
    {
        return document;
    }

    public StringBuffer getStringBuffer()
    {
        return stringBuffer;
    }

    public void emitRearrangedDocument()
    {
        emitRuleInstances(resultRuleInstances);
        document.replaceString(
                psiFile.getTextRange().getStartOffset(),
                psiFile.getTextRange().getEndOffset(),
                stringBuffer.toString()
        );
    }

    public void emitRuleInstances(List<IRuleInstance> resultRuleInstances)
    {
        if (resultRuleInstances == null)
        {
            return;
        }
        for (IRuleInstance ruleInstance : resultRuleInstances)
        {
            ruleInstance.emit(this);
        }
    }
}
