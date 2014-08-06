package com.daren.accident.persist.openjpa;

import com.daren.accident.entities.FileBean;
import com.daren.core.impl.persistence.GenericOpenJpaDao;
import com.daren.accident.api.dao.IFileBeanDao;


/**
 * Created by dell on 14-1-16.
 */
public class FileBeanDaoOpenjpa extends GenericOpenJpaDao<FileBean, Long> implements IFileBeanDao {

}
