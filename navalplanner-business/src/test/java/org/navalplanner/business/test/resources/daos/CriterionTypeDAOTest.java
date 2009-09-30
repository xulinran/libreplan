package org.navalplanner.business.test.resources.daos;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;
import static org.navalplanner.business.BusinessGlobalNames.BUSINESS_SPRING_CONFIG_FILE;
import static org.navalplanner.business.test.BusinessGlobalNames.BUSINESS_SPRING_CONFIG_TEST_FILE;

import java.util.List;
import java.util.UUID;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.navalplanner.business.common.exceptions.InstanceNotFoundException;
import org.navalplanner.business.common.exceptions.ValidationException;
import org.navalplanner.business.resources.daos.ICriterionTypeDAO;
import org.navalplanner.business.resources.entities.CriterionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Diego Pino García <dpino@igalia.com>
 */

/**
 * Test cases for CriterionTypeDAO <br />
 * @author Diego Pino García <dpino@igalia.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { BUSINESS_SPRING_CONFIG_FILE,
        BUSINESS_SPRING_CONFIG_TEST_FILE })
@Transactional
public class CriterionTypeDAOTest {

    @Autowired
    private ICriterionTypeDAO criterionTypeDAO;

    public static final String DEFAULT_CRITERION_TYPE = "TEST_DEFAULT";

    public static CriterionType createValidCriterionType(String name,String description) {
        return CriterionType.create(name,description);
    }

    public static CriterionType createValidCriterionType() {
        String unique = UUID.randomUUID().toString();
        String description = "";
        return createValidCriterionType(unique,description);
    }

    @Test
    public void testSaveCriterionType() throws Exception {
        CriterionType criterionType = createValidCriterionType();
        criterionTypeDAO.save(criterionType);
        assertTrue(criterionTypeDAO.exists(criterionType.getId()));
    }

    @Test
    public void testCriterionTypeCanBeSavedTwice() throws ValidationException {
        CriterionType criterionType = createValidCriterionType();
        criterionTypeDAO.save(criterionType);
        criterionTypeDAO.save(criterionType);
        assertTrue(criterionTypeDAO.exists(criterionType.getId())
                || criterionTypeDAO.existsByName(criterionType));
    }

    @Test
    public void testCannotSaveTwoDifferentCriterionTypesWithTheSameName()
            throws ValidationException {
         try {
            CriterionType criterionType = createValidCriterionType("bla","");
            criterionTypeDAO.save(criterionType);
            criterionType = createValidCriterionType("bla","");
            criterionTypeDAO.save(criterionType);
            criterionTypeDAO.flush();
            fail("must send exception since thereis a duplicated criterion type");
        } catch (ConstraintViolationException c) {
            // This exception is raised in postgresql
        } catch (DataIntegrityViolationException d) {
            // This exception is raised in HSQL
        }
    }

    @Test
    public void testRemove() throws InstanceNotFoundException {
        CriterionType criterionType = createValidCriterionType();
        criterionTypeDAO.save(criterionType);
        criterionTypeDAO.remove(criterionType.getId());
        assertFalse(criterionTypeDAO.exists(criterionType.getId()));
    }

    @Test
    public void testList() {
        int previous = criterionTypeDAO.list(CriterionType.class).size();
        CriterionType criterion1 = createValidCriterionType();
        CriterionType criterion2 = createValidCriterionType();
        criterionTypeDAO.save(criterion1);
        criterionTypeDAO.save(criterion2);
        List<CriterionType> list = criterionTypeDAO.list(CriterionType.class);
        assertEquals(previous + 2, list.size());
    }

    @Test
    public void testGetCriterionTypes() {
        int previous = criterionTypeDAO.list(CriterionType.class).size();
        CriterionType criterion1 = createValidCriterionType();
        CriterionType criterion2 = createValidCriterionType();
        criterionTypeDAO.save(criterion1);
        criterionTypeDAO.save(criterion2);
        List<CriterionType> list = criterionTypeDAO.getCriterionTypes();
        assertEquals(previous + 2, list.size());
    }

}
