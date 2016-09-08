package co.org.ceindetec.combustible.lib;

/**
 * Created by Ceindetec02 on 12/07/2016.
 */
public interface EventBus {

    void register(Object subscriber);

    void unregister(Object subscriber);

    void post(Object event);

}
