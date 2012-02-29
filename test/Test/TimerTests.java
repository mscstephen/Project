package Test;

import H.utility.Cpu;
import H.utility.Teams;
import Util.NewHibernateUtil;
import H.utility.Row;
import H.utility.Schedule;

import java.util.*;
import org.hibernate.HibernateException;
import org.junit.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TimerTests {
    class DatabaseTask extends TimerTask{
        @Override
        public void run() {
            System.out.println("\nGenerating ballox\n");

            TimerTests x = new TimerTests();
            x.SetActiveMethod();
        }
   }
    /*
     * sch get 4 seconds to change status
     * 2 second buffer
     * 6 seconds to pick up a change on sch or fail
     * power have same
     * turn off listen after status change
     * 4 seconds
     * test that fail after a wait command
     * test cases timers every 1 seconds check which team is active, calls method
     * if test if >10 accert that the test has failed.
     * pass data four times
     */

    private Timer t1 = new Timer();
    private Timer t2;
    private Timer t3;
    private Timer t4;
    private Thread listener1;
    private Session session;
    private Transaction tran;
    private Teams chg;
    private Cpu cpu;
    private Row row;
    private Integer act;
    private Schedule sch;
    String condition = "Not Set";

    @Before
    public void setup() {
        session = NewHibernateUtil.getSessionFactory().openSession();
        tran = session.beginTransaction();
        chg = new Teams();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testTimersSetTeamToActiveAfterFourSeconds() {
        //SetActiveMethod();
        t1.schedule(new DatabaseTask(),1000, 4 * 1000);
        Assert.assertEquals("Active", condition);
    }

    public void SetActiveMethod() {
        Query q = session.createQuery("From Teams as team where team.teamId = 1");
        for (Object o : q.list()) {
            Teams t = (Teams) o;
            act = t.getActive();
            if (act == 1) {
                act = 0;
                t.setActive(0);
            } else {
                act = 1;
                t.setActive(1);
            }
            chg = t;
            tran.commit();
        }
        session.merge(chg);
        Query q2 = session.createQuery("From Teams as team where team.teamId = 1");
        for (Object o : q2.list()) {
            Teams t2 = (Teams) o;
            if (act == t2.getActive()) {
                condition = "Active";
            } else {
                condition = "Not Active";
            }
        }
        //condition = "Not Active";
}
}



