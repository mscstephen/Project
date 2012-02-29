/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Test;

import H.utility.Cpu;
import H.utility.Teams;
import Util.NewHibernateUtil;
import H.utility.Row;
import H.utility.Schedule;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Ignore;


public class DatabasejUnitTests {
    private Session session;
    private Transaction tran;
    private Teams chg;
    private Cpu cpu;
    private Row row;
    private Integer  act;
    private Schedule sch;

    @Before
    public void setUp() {
        session = NewHibernateUtil.getSessionFactory().openSession();
        tran = session.beginTransaction();
        chg = new Teams();
    }

    @After
    public void tearDown() {
        session.disconnect();
    }
    // Select record
    @Test
    public void testGetTeamName(){
         Query q = session.createQuery("From Teams as team where team.teamId = 1");
          for(Object o : q.list()){
              Teams t = (Teams) o;
              junit.framework.Assert.assertEquals("Team name should be Optimisation","Optimisation", t.getName());
          }
           Query q2 = session.createQuery("From Teams as team where team.teamId = 2");
          for(Object o : q2.list()){
              Teams t = (Teams) o;
              junit.framework.Assert.assertEquals("Team name should be Scheduling","Scheduling", t.getName());
          }
    }
    // Update Record
    @Test
    public void createObject(){
         Query q = session.createQuery("From Teams as team where team.teamId = 1");
          for(Object o : q.list()){
              Teams t = (Teams) o;
              act = t.getActive();
              junit.framework.Assert.assertEquals("Team name should be Optimisation","Optimisation", t.getName());
              junit.framework.Assert.assertEquals("Active  was wrong" , act,t.getActive());
          }

    }
    @Ignore
   @Test
    public void convertObject(){
         Query q = session.createQuery("From Cpu as cpu where cpu.cpuid = 1");
         Query q2 = session.createQuery("From Row as row where row.rowId = 1");
          for(Object o : q.list()){
              Teams t = (Teams) o;
              }

    }
    @Test
    public void testSetTeamToActive() {
         Query q = session.createQuery("From Teams as team where team.teamId = 1");
          for(Object o : q.list()){
              Teams t = (Teams) o;
              act = t.getActive();
              if (act == 1){
                  act = 0;
               t.setActive(0);
              }
 else{
                  act = 1;
                  t.setActive(1);
 }
               chg = t;
               tran.commit();
          }
          session.merge(chg);
          Query q2 = session.createQuery("From Teams as team where team.teamId = 1");
          for(Object o : q2.list()){
              Teams t2 = (Teams) o;
              junit.framework.Assert.assertEquals("Team name should be Active", act, t2 .getActive());
          }


    }

    @Test
    public void testConnectionWasMadeWithDatabase(){
        junit.framework.Assert.assertNotNull("Session should not be null", session);
    }

    @Test
    public void testConnectionWithDatabasePopulatedASession(){
        junit.framework.Assert.assertNotNull("Transaction should not be null", tran);
    }
}

