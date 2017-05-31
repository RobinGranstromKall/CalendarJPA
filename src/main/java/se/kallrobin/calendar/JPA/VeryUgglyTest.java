package se.kallrobin.calendar.JPA;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.*;

import se.kallrobin.calendar.domain.*;

/**
 * Ett initialt ganska (eller snarare mycket) fult test bara för att se att allt lirar
 */
public class VeryUgglyTest {
    private EntityManager manager;

    public VeryUgglyTest(EntityManager manager) {
        this.manager = manager;
    }

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
        EntityManager manager = factory.createEntityManager();
        VeryUgglyTest test = new VeryUgglyTest(manager);

        clear(manager);

        test2(manager, test);

        System.out.println(".. done");
    }

    /**
     * Vi rensar lite. I alla fall shoppinglistan.
     * (Har inte hittat något smidigt sätt att nollställa databasen annat än att ta bort hela katalogen)
     *
     * @param manager
     */
    private static void clear(EntityManager manager) {
        clearCalendar(manager);
        //clearEvents(manager); //Items ska ryka då vi tar bort shoppinglistan
    }

    private static void clearEvents(EntityManager manager) {
        EntityTransaction tx = manager.getTransaction();
        List<Event> resultList = manager.createQuery("Select e From Event e", Event.class).getResultList();
        System.out.println("Items size: " + resultList.size());
        tx.begin();
        try {
            for (Event next : resultList) {
                manager.remove(next);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
    }

    private static void clearCalendar(EntityManager manager) {
        EntityTransaction tx = manager.getTransaction();
        List<Calendar> resultList = manager.createQuery("Select a From Calendar a", Calendar.class).getResultList();
        System.out.println("Calendar size: " + resultList.size());
        tx.begin();
        try {
            for (Calendar next : resultList) {
                manager.remove(next);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
    }

    private static void test2(EntityManager manager, VeryUgglyTest test) {
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            test.createCalendar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();
        test.listEvents();
    }

    private void createCalendar() {
        Calendar calendar = new Calendar("Veckans affärer");
        EventType type = new EventType("Hobby");
        Date start = new GregorianCalendar(2018,8,5,12,0).getTime();
        Date stop = new GregorianCalendar(2018,8,5,12,0).getTime();
        calendar.addEvent(new Event("Köpa Båt", "Åka långt, minst en timme innan.", start, stop, calendar, type));
        manager.persist(calendar);
        System.out.println(calendar);
    }


    private void listEvents() {
        List<Event> resultList = manager.createQuery("select e from Event e", Event.class).getResultList();
        for (Event next : resultList){
            System.out.println("Event: " + next);
        }
    }
}
