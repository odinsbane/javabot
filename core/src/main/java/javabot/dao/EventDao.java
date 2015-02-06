package javabot.dao;

import javabot.model.AdminEvent;
import javabot.model.AdminEvent.State;
import javabot.criteria.AdminEventCriteria;

public class EventDao extends BaseDao<AdminEvent> {
  protected EventDao() {
    super(AdminEvent.class);
  }

  public AdminEvent findUnprocessed() {
    AdminEventCriteria criteria = new AdminEventCriteria(ds);
    criteria.state().equal(State.NEW);
    criteria.query().order("requestedOn");
    return criteria.query().get();
  }
}