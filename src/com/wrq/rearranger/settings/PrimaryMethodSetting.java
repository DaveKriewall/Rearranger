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
package com.wrq.rearranger.settings;

import com.wrq.rearranger.configuration.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Contains settings pertinent to a single related-by-name method setting.   A primary field is
 * specified
 */
public class PrimaryMethodSetting
        implements IListManagerObject
{
    /**
     * Description for a single related-by-name method setting.
     */
    String description;
    /**
     * Regular expression which method name must match.  If the method is to be matched to a field, there must be
     * at least one capture group (parenthesized subexpression) which will be treated as the field.
     * In the case of a traditional getter method, this pattern is "get(\w+)".
     */
    String methodNamePattern;
    /**
     * The number of the capture group in methodNamePattern which corresponds to the field name.  If the
     * methodNamePattern is "get(\w+)", then the fieldCaptureGroupNumber is 1.
     */
    int fieldCaptureGroupNumber;
    /**
     * number of parameters the primary method must have.  In the case of a traditional getter method, this is zero.
     */
    int numParameters;
    /**
     * regular expression pattern of the type which primary method must return.
     * In the case of a traditional getter method, this is the field type.  One substitution is available:
     * <pre>
     * %FT%  field type
     * </pre>
     */
    String methodTypePattern;
    /**
     * regular expression pattern of the attributes of the method.
     * In the case of a traditional getter method, this is ".*public.*" (i.e., any public method).
     */
    String methodAttributePattern;
    /**
     * pattern of method body (excluding comments and newline characters).  Substitutions are available as follows:
     * <pre>
     * %FN%  field name
     * %FT%  field type (if field is valid field, else empty string)
     * %MT%  method return type
     * %P1%  parameter 1 name (%P2%, %P3% etc for additional parameters)
     * %T1%  parameter 1 type name
     * </pre>
     */
    String body;
    /**
     * list of secondary method configurations, e.g. for "set" and "is".
     */
    ArrayList<SecondaryMethodSetting> secondaryMethodList = new ArrayList<SecondaryMethodSetting>();

    public PrimaryMethodSetting()
    {
        methodNamePattern = "get(\\w+)";
        fieldCaptureGroupNumber = 1;
        numParameters = 0;
        methodTypePattern = "%FT%";
        methodAttributePattern = ".*public.*";
        body = "{\\s*return\\s*%fn%;\\s*}";
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getMethodNamePattern()
    {
        return methodNamePattern;
    }

    public void setMethodNamePattern(String methodNamePattern)
    {
        this.methodNamePattern = methodNamePattern;
    }

    public int getFieldCaptureGroupNumber()
    {
        return fieldCaptureGroupNumber;
    }

    public void setFieldCaptureGroupNumber(int fieldCaptureGroupNumber)
    {
        this.fieldCaptureGroupNumber = fieldCaptureGroupNumber;
    }

    public int getNumParameters()
    {
        return numParameters;
    }

    public void setNumParameters(int numParameters)
    {
        this.numParameters = numParameters;
    }

    public String getMethodTypePattern()
    {
        return methodTypePattern;
    }

    public void setMethodTypePattern(String methodTypePattern)
    {
        this.methodTypePattern = methodTypePattern;
    }

    public String getMethodAttributePattern()
    {
        return methodAttributePattern;
    }

    public void setMethodAttributePattern(String methodAttributePattern)
    {
        this.methodAttributePattern = methodAttributePattern;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public ArrayList<SecondaryMethodSetting> getSecondaryMethodList()
    {
        return secondaryMethodList;
    }

    public String toString()
    {
        return description;
    }

    public boolean equals(Object obj)
    {
        if (!(obj instanceof PrimaryMethodSetting)) return false;
        PrimaryMethodSetting pms = (PrimaryMethodSetting) obj;
        if (!pms.description.equals(description)) return false;
        if (!pms.methodNamePattern.equals(methodNamePattern)) return false;
        if (pms.fieldCaptureGroupNumber != fieldCaptureGroupNumber) return false;
        if (pms.numParameters != numParameters) return false;
        if (!pms.methodTypePattern.equals(methodTypePattern)) return false;
        if (!pms.methodAttributePattern.equals(methodAttributePattern)) return false;
        if (!pms.body.equals(body)) return false;
        return true;
    }

    public JPanel getPanel()
    {
        IListManagerObjectFactory lmof = new IListManagerObjectFactory()
        {
            public IListManagerObject create(String name)
            {
                SecondaryMethodSetting sms = new SecondaryMethodSetting();
                sms.setDescription(name);
                return sms;
            }

            public List/*<? extends IListManagerObject>*/ getObjectList()
            {
                return secondaryMethodList;
            }
        };
        ListManager Lmgr = new ListManager(lmof, "Description:");
        JPanel pmsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.0d;
        constraints.weighty = 0.0d;
        constraints.gridy = constraints.gridx = 0;
        constraints.insets = new Insets(3, 3, 3, 3);
        pmsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Primary (\"Getter\") Method Criteria"));
        constraints.gridwidth = 1;
        constraints.gridy++;
        JLabel methodNamePatternLabel = new JLabel("Method name:");
        methodNamePatternLabel.setToolTipText(
                "Regular expression which method name must match.  " +
                "If the method is to be matched to a field, there must be\n" +
                " at least one capture group (parenthesized subexpression) which will be treated as the field.\n" +
                " In the case of a traditional getter method, this pattern is \"get(\\w+)\".");
        pmsPanel.add(methodNamePatternLabel, constraints);
        constraints.gridx++;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = .5;
        StringTextField primaryPrefixText = new StringTextField()
        {
            public void setValue(String value) { setMethodNamePattern(value); }
            public String getValue() { return getMethodNamePattern(); }
        };
        pmsPanel.add(primaryPrefixText, constraints);
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.gridx++;
        JLabel fieldCaptureGroupLabel = new JLabel("Field capture group:");
        fieldCaptureGroupLabel.setToolTipText(
                "The number of the capture group in methodNamePattern which corresponds to the field name.  If the\n" +
                " methodNamePattern is \"get(\\w+)\", then the fieldCaptureGroupNumber is 1."
        );
        pmsPanel.add(fieldCaptureGroupLabel, constraints);
        IntTextField itf = new IntTextField(
                new IntTextField.IGetSet()
                {
                    public void set(int value) { fieldCaptureGroupNumber = value; }
                    public int get() { return fieldCaptureGroupNumber; }
                },
                null, null
        );

        constraints.gridx++;
        pmsPanel.add(itf, constraints);
        constraints.gridx++;
        JLabel nParamLabel = new JLabel("Number of parameters:");
        nParamLabel.setToolTipText(
                "number of parameters the primary method must have.\n" +
                "In the case of a traditional getter method, this is zero.");
        pmsPanel.add(nParamLabel, constraints);
        constraints.gridx++;
        IntTextField nParamText = new IntTextField(
                new IntTextField.IGetSet()
        {
            public void set(int value) { numParameters = value; }
            public int get() { return numParameters; }
        }, null, null);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        pmsPanel.add(nParamText, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        constraints.gridwidth = 1;
        constraints.weightx = 0;
        constraints.fill = GridBagConstraints.NONE;
        JLabel returnTypeLabel = new JLabel("Return type:");
        returnTypeLabel.setToolTipText(
                "regular expression pattern of the type which primary method must return.\n" +
                " In the case of a traditional getter method, this is the field type.  One substitution is available:\n" +
                "     * %FT%  field type");
        pmsPanel.add(returnTypeLabel, constraints);
        constraints.gridx++;
        constraints.weightx = .5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        StringTextField returnTypeField = new StringTextField()
        {
            public void setValue(String value) { methodTypePattern = value; }
            public String getValue() { return methodTypePattern; }
        };
        pmsPanel.add(returnTypeField, constraints);
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx++;
        constraints.weightx = 0;
        JLabel methodAttributeLabel = new JLabel("Method attributes:");
        methodAttributeLabel.setToolTipText("Protection level or other attributes for the method.\n" +
                                            "In the case of a getter method, this is usually \".*public.*\".");
        pmsPanel.add(methodAttributeLabel, constraints);
        StringTextField methodAttributeField = new StringTextField()
        {
            public void setValue(String value) { methodAttributePattern = value; }
            public String getValue() { return methodAttributePattern; }
        };
        constraints.gridx++;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = .5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        pmsPanel.add(methodAttributeField, constraints);
        constraints.gridx++;
        constraints.weightx = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridy++;
        constraints.gridx = 0;
        JLabel bodyLabel = new JLabel("Body:");
        bodyLabel.setToolTipText("regular expression pattern of method body (excluding comments and newline characters).\n" +
                                 "Substitutions are available as follows:\n" +
                                 " %FN%  field name\n" +
                                 " %FT%  field type (if field is valid field, else empty string)\n" +
                                 " %MT%  method return type\n" +
                                 " %P1%  parameter 1 name (%P2%, %P3% etc for additional parameters)\n" +
                                 " %T1%  parameter 1 type name");
        constraints.gridwidth = 1;
        constraints.weightx = 0;
        constraints.fill = GridBagConstraints.NONE;
        pmsPanel.add(bodyLabel, constraints);
        constraints.gridx++;
        StringTextArea bodyField = new StringTextArea()
        {
            public void setValue(String value) { body = value; }
            public String getValue() { return body; }
        };
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1;
        constraints.weighty = .35;
        constraints.fill = GridBagConstraints.BOTH;
        JScrollPane sp = new JScrollPane(bodyField);
        pmsPanel.add(sp, constraints);
        constraints.gridx = 0;
        constraints.gridy++;
        constraints.gridheight = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.NONE;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = .65;
        final JPanel pane = Lmgr.getPane();
        pane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Related Methods"));
        pmsPanel.add(pane, constraints);
        return pmsPanel;
    }

    public IListManagerObject deepcopy()
    {
        PrimaryMethodSetting pms = new PrimaryMethodSetting();
        pms.description = getDescription();
        pms.methodNamePattern = getMethodNamePattern();
        pms.fieldCaptureGroupNumber = getFieldCaptureGroupNumber();
        pms.numParameters = getNumParameters();
        pms.methodTypePattern = getMethodTypePattern();
        pms.methodAttributePattern = getMethodAttributePattern();
        pms.body = getBody();
        pms.secondaryMethodList = new ArrayList<SecondaryMethodSetting>();
        for (SecondaryMethodSetting sms : secondaryMethodList)
        {
            pms.secondaryMethodList.add((SecondaryMethodSetting) sms.deepcopy());
        }
        return pms;
    }

    public static void main(String[] args)
    {
        PrimaryMethodSetting ms = new PrimaryMethodSetting();
        final JDialog frame = new JDialog((Frame) null, "SwingApplication");
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0d;
        constraints.weighty = 1.0d;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.gridx = 0;
        constraints.gridy = 0;
        frame.getContentPane().setLayout(new GridBagLayout());
        frame.getContentPane().add(ms.getPanel(), constraints);
        //Finish setting up the frame, and show it.
        frame.pack();
        frame.setResizable(true);
        frame.setModal(true);
        frame.setVisible(true);

    }

}
