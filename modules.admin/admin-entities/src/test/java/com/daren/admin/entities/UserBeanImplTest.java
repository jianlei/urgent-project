package com.daren.admin.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 * UserBeanImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>һ�� 29, 2014</pre>
 */
public class UserBeanImplTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getName()
     */
    @Test
    public void testGetName() throws Exception {

    }

    /**
     * 测试jaxb导出xml
     *
     * @throws Exception
     */
    @Test
    public void testJaxb() throws Exception {
        UserBeanImpl user = new UserBeanImpl();
        user.setName("name");
        user.setPassword("password");

        JAXBContext jc = JAXBContext.newInstance(UserBeanImpl.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(user, System.out);
    }


    /**
     * Method: setName(String name)
     */
    @Test
    public void testSetName() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getPassword()
     */
    @Test
    public void testGetPassword() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: setPassword(String password)
     */
    @Test
    public void testSetPassword() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: main(String[] args)
     */
    @Test
    public void testMain() throws Exception {
//TODO: Test goes here... 
    }


} 
