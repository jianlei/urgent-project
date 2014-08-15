package com.daren.core.web.component.table;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.IPageableItems;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.io.IClusterable;

/**
 * 项目名称:  urgent-project
 * 类描述:
 * 创建人:    sunlf
 * 创建时间:  2014/8/8 0:33
 * 修改人:    sunlf
 * 修改时间:  2014/8/8 0:33
 * 修改备注:  [说明本次修改内容]
 */
public class IrisNavigatorLabel extends Label {
    /**
     * Construct.
     *
     * @param id
     * @param pageable
     */
    public IrisNavigatorLabel(String id, IPageableItems pageable) {
        super(id, pageable);
        setDefaultModel(new StringResourceModel("NavigatorLabel", this,
                new Model<LabelModelObject>(new LabelModelObject(pageable))));
    }

    private static class LabelModelObject implements IClusterable {
        private static final long serialVersionUID = 1L;
        private final IPageableItems pageable;

        /**
         * Construct.
         *
         * @param table
         */
        public LabelModelObject(final IPageableItems table) {
            pageable = table;
        }

        /**
         * @return "z" in "Showing x to y of z"
         */
        public long getOf() {
            return pageable.getItemCount();
        }

        /**
         * @return "x" in "Showing x to y of z"
         */
        public long getFrom() {
            if (getOf() == 0) {
                return 0;
            }
            return pageable.getCurrentPage() * pageable.getItemsPerPage() + 1;
        }

        /**
         * @return "y" in "Showing x to y of z"
         */
        public long getTo() {
            if (getOf() == 0) {
                return 0;
            }
            return Math.min(getOf(), getFrom() + pageable.getItemsPerPage() - 1);
        }
    }
}
