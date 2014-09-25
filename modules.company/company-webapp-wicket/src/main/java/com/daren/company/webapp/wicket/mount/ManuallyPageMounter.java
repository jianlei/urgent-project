package com.daren.company.webapp.wicket.mount;

import com.daren.company.webapp.wicket.page.RegisterPage;
import org.apache.wicket.Page;
import org.ops4j.pax.wicket.api.MountPointInfo;
import org.ops4j.pax.wicket.api.PageMounter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is exported as a service via blueprint and automatically recognized by pax-wicket mounting a page manually
 * in the system.
 */
public class ManuallyPageMounter implements PageMounter {

    public void addMountPoint(String path, Class<? extends Page> pageClass) {
        // this method is typically though only for internal use
    }

    public List<MountPointInfo> getMountPoints() {
        List<MountPointInfo> mountPoints = new ArrayList<MountPointInfo>();
        mountPoints.add(new MountPointInfo() {

            public String getPath() {
                return "register";
            }

            public Class<? extends Page> getPage() {
                return RegisterPage.class;
            }
        });
        return mountPoints;
    }
}
