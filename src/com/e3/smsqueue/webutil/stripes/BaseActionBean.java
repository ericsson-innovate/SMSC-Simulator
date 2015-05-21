/**
 * 
 */
/*
 * $HeadURL: $
 * $Id: $
 * Copyright (c) 2010 by Ericsson, all rights reserved.
 */

package com.e3.smsqueue.webutil.stripes;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;

/**
 * @author ezhixwa
 *
 */
public class BaseActionBean implements ActionBean {
    private ActionBeanContext context;

    private int               exceptionCode = GlobalExceptionCode.NO_ERROR; //no error 

    public int getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(int exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.stripes.action.ActionBean#getContext()
     */
    public ActionBeanContext getContext() {
        return context;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.stripes.action.ActionBean#setContext(net.sourceforge.stripes.action.ActionBeanContext)
     */
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

}
