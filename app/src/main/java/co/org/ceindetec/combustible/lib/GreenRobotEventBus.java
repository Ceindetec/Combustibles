package co.org.ceindetec.combustible.lib;
/**
 * Created by Ceindetec02 on 12/07/2016.
 */
public class GreenRobotEventBus implements EventBus {

    //Implementacion del objeto Green Robot para el uso de Event Bus
    org.greenrobot.eventbus.EventBus greenRobotEventBus;

    /**
     * Metodo que asigna el valor de la instancia
     */
    private static class SingletonHolder {
        private static final GreenRobotEventBus INSTANCE = new GreenRobotEventBus();
    }

    /**
     * Metodo por el cual se obtiene la instancia unica de la clase
     *
     * @return
     */
    public static GreenRobotEventBus getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Declaracion del constructor de la clase
     */
    public GreenRobotEventBus() {
        this.greenRobotEventBus = org.greenrobot.eventbus.EventBus.getDefault();
    }

    @Override
    public void register(Object subscriber) {
        greenRobotEventBus.register(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        greenRobotEventBus.unregister(subscriber);
    }

    @Override
    public void post(Object event) {
        greenRobotEventBus.post(event);
    }
}
