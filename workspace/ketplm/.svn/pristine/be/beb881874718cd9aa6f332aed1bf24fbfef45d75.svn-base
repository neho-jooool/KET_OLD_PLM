/*
 * @(#) ValueObject.java  Create on 2004. 9. 8.
 * Copyright (c) e3ps. All rights reserverd
 */
package e3ps.common.util;

import java.io.Serializable;
import java.util.Hashtable;

import wt.folder.Folder;
import wt.inf.container.WTContainer;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.util.WTException;
import ext.ket.shared.log.Kogger;

/**
 * 
 * @author Choi Seunghwan, skyprda@e3ps.com
 * @version 1.00, 2004. 9. 8.
 * @since 1.4
 */
public class ValueObject implements Serializable
{
    private String number;
    private String name;
    private String title;
    private String description;
    private String lifecycle;
    private WTContainer container;
    private WTContainerRef containerRef;
    private Folder folder;
    private String lifecycleState;
    private String version;
    private Hashtable attrs;

    public ValueObject()
    {
        title = "";
        description = "";
        lifecycle = "Default";
        try
        {
            containerRef = WTContainerHelper.getClassicRef();
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
    }

    /**
     * @return Returns the lifecycleState.
     */
    public String getLifecycleState()
    {
        return lifecycleState;
    }

    /**
     * @param lifecycleState
     *                The lifecycleState to set.
     */
    public void setLifecycleState(String lifecycleState)
    {
        this.lifecycleState = lifecycleState;
    }

    /**
     * @return Returns the version.
     */
    public String getVersion()
    {
        return version;
    }

    /**
     * @param version
     *                The version to set.
     */
    public void setVersion(String version)
    {
        this.version = version;
    }

    /**
     * @return Returns the lifecycle.
     */
    public String getLifecycle()
    {
        return lifecycle;
    }

    /**
     * @param lifecycle
     *                The lifecycle to set.
     */
    public void setLifecycle(String lifecycle)
    {
        this.lifecycle = lifecycle;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *                The description to set.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *                The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return Returns the number.
     */
    public String getNumber()
    {
        return number;
    }

    /**
     * @param number
     *                The number to set.
     */
    public void setNumber(String number)
    {
        this.number = number;
    }

    /**
     * @return Returns the title.
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title
     *                The title to set.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return Returns the container.
     */
    public WTContainer getContainer()
    {
        return container;
    }

    /**
     * @param container
     *                The container to set.
     */
    public void setContainer(WTContainer container)
    {
        this.container = container;
    }

    /**
     * @return Returns the folder.
     */
    public Folder getFolder()
    {
        return folder;
    }

    /**
     * @param folder
     *                The folder to set.
     */
    public void setFolder(Folder folder)
    {
        this.folder = folder;
    }

    public void setFolder(String folderOid)
    {
        this.folder = (Folder) CommonUtil.getObject(folderOid);
    }

    /**
     * @return Returns the containerRef.
     */
    public WTContainerRef getContainerRef()
    {
        return containerRef;
    }

    /**
     * @param containerRef
     *                The containerRef to set.
     */
    public void setContainerRef(WTContainerRef containerRef)
    {
        this.containerRef = containerRef;
    }

    /**
     * @return Returns the attrs.
     */
    public Hashtable getAttrs()
    {
        return attrs;
    }

    /**
     * @param attrs
     *                The attrs to set.
     */
    public void setAttrs(Hashtable attrs)
    {
        this.attrs = attrs;
    }
}
