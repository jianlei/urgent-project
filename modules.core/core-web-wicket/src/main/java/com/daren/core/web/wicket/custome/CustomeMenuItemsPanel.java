package com.daren.core.web.wicket.custome;

import com.daren.core.web.api.module.IMenuItemsModule;
import com.daren.core.web.api.module.IMenuModule;
import com.daren.core.web.api.module.IModule;
import com.daren.core.web.wicket.manager.MenuModuleManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.AjaxLazyLoadPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 14-3-23.
 */
public class CustomeMenuItemsPanel extends Panel {
    static boolean isload = false;
    IMenuItemsModule menuItemsModule;

    public CustomeMenuItemsPanel(String id, final IMenuModule menus, final WebMarkupContainer wmc, final int index) {
        super(id);
        Label labelName = new Label("name", menus.getName());
        add(labelName);

        //获取目录下面的子菜单
        List<IMenuItemsModule> submenus = MenuModuleManager.getInstall().findMenusByTag(menus.getTargetTag());
        //权限检查
        List<IMenuItemsModule> permitedMenu=new ArrayList<>();
        Subject currentUser = SecurityUtils.getSubject();
        for (IMenuItemsModule menu : submenus){
            if(menu.isPermissionAvaliable()){//权限检查
                if(currentUser.isPermitted(menu.getPermissionName())){
                    permitedMenu.add(menu);
                }
            }
            else
            {
                permitedMenu.add(menu);
            }
        }

        ListView<IModule> listView = new ListView<IModule>("menuList", permitedMenu) {
            @Override
            protected void populateItem(final ListItem<IModule> item) {
                AjaxLink submenusLink = new AjaxLink("menus") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        wmc.removeAll();
                        //使用延迟加载panel
                        AjaxLazyLoadPanel components = new AjaxLazyLoadPanel("panel") {
                            @Override
                            public Component getLazyLoadComponent(String markupId) {
                                menuItemsModule = ((IMenuItemsModule) item.getModelObject());
                                return menuItemsModule.getPanel(markupId, wmc);
                            }
                        };
                        wmc.addOrReplace(components);
                        //                         setResponsePage();
                        /*if(menuItemsModule.getName().equals("GIS地图")){
                            if(isload){
                                target.appendJavaScript("window.location.reload()");
                                isload=true;
                            }
                        }*/
                        target.add(wmc);
                    }
                   /* @Override
                    public void onClick() {
                        setResponsePage(((IMenuItemsModule) item.getModelObject()).getPageClass());
                    }*/
                };

                submenusLink.setOutputMarkupId(true);
                submenusLink.add(new Label("linkName", item.getModelObject().getName()).setRenderBodyOnly(true).setEscapeModelStrings(false));
                item.add(submenusLink);
            }
        };
        add(listView);
    }

}


