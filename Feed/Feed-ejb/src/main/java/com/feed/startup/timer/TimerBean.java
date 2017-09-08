/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feed.startup.timer;

import com.feed.processor.Processor;
import com.feed.startup.Constants;
import com.feed.startup.timer.dao.FeedConfig;
import com.feed.startup.timer.dao.TimerDAO;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Singleton
@Startup
@DeclareRoles({"role"})
@RunAs(value = "role")
@TransactionManagement(TransactionManagementType.BEAN)
public class TimerBean {

    @Resource
    private TimerService timerService;    
    @EJB
    private TimerDAO timerDAO;
    @Inject
    private Event<String> textEvent;
    @Inject
    private Processor gssProcessor;

    @PostConstruct
    public void init() {
        initTimer(timerDAO.getTimerConfig());
    }

    private void initTimer(FeedConfig feedConfig) {
        ScheduleExpression se = initScheduleExpression(feedConfig);
        TimerConfig tc = new TimerConfig(feedConfig, false);
        timerService.createCalendarTimer(se, tc);
        textEvent.fire(String.format("Timers were created %s", se));
    }

    @Schedule(persistent = false, hour = "*", minute = "*/10", second = "0")
    private void timerUpdator() {
        boolean needToUpdateTimer = false;
        for (Timer t : timerService.getTimers()) {
            if (t.getInfo() instanceof FeedConfig) {
                FeedConfig currentConfig = (FeedConfig) t.getInfo();
                FeedConfig actualConfig = timerDAO.getTimerConfig();
                if (!actualConfig.isNotUpdatedFromDatabase() && !actualConfig.equals(currentConfig)) {
                    t.cancel();
                    needToUpdateTimer = true;
                }
            }
        }
        if (needToUpdateTimer) {
            init();
        }
    }

    @Timeout
    private void dailyTask(Timer t) {
        textEvent.fire("dailyTask.start." + t.getSchedule());
        if (t.getInfo() instanceof FeedConfig) {
            FeedConfig currentConfig = (FeedConfig) t.getInfo();
            if (currentConfig.isEnabled() && isTargetedToNode(currentConfig)) {
                textEvent.fire("@Before.Task");
                gssProcessor.processFeedStaging();
                textEvent.fire("@After.Task");
            }
        }
    }

    private ScheduleExpression initScheduleExpression(FeedConfig feedConfig) {
        ScheduleExpression se = new ScheduleExpression();
        se.dayOfWeek(feedConfig.getDayOfWeek());
        se.dayOfMonth(feedConfig.getDayOfMonth());
        se.hour(feedConfig.getHour());
        se.minute(feedConfig.getMinute());
        se.second(feedConfig.getSecond());
        se.timezone(Constants.TIMER_TIMEZONE_ID);
        return se;
    }

    private boolean isTargetedToNode(FeedConfig feedConfig) {
        String nodeName = feedConfig.getTargetNodeName();
        String actualNodeName = System.getenv(Constants.SERVER_NAME);
        boolean res = nodeName != null && nodeName.equalsIgnoreCase(actualNodeName);
        textEvent.fire(String.format("Feed isTargetedToNode=%s", res));
        return res;
    }

    @PreDestroy
    public void destroy() {
        Collection<Timer> timers = timerService.getTimers();
        for (Timer t : timers) {
            t.cancel();
        }
    }
}
