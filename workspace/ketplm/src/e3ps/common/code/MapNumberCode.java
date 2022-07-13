/*
 * @(#) MapNumberCode.java  Create on 2006. 1. 6.
 * Copyright (c) e3ps. All rights reserverd
 */
package e3ps.common.code;

import java.util.Map;

/**
 * 
 * @author Choi Seunghwan, skyprda@e3ps.com
 * @version 1.00, 2006. 1. 6.
 * @since 1.4
 */
public class MapNumberCode
{
    public final static Map<String, String> ITEM = NumberCodeHelper.manager.getNumberCode("ITEM");
    public final static Map<String, String> PROJECT = NumberCodeHelper.manager.getNumberCode("PROJECT");
}
