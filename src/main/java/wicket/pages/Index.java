package wicket.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.resources.StyleSheetReference;
import wicket.core.JavabotPage;
import wicket.panels.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// User: joed
// Date: May 17, 2007
// Time: 2:37:26 PM

public class Index extends JavabotPage {

    public Index(final PageParameters parameters) {
        add(new StyleSheetReference("stylesheet", getClass(), "css/style.css"));

        String year = parameters.getString("0");
        String month = parameters.getString("1");
        String day = parameters.getString("2");
        String channel = parameters.getString("3");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date();

        ChannelBox channelBox = new ChannelBox("ChannelBox");
        add(channelBox);

        if (channel != null && year != null) {
            try {
                date = sdf.parse(year + "-" + month + "-" + day);
            } catch (ParseException e) {
                //log.error("Failed parsing date");
            }
            NavigationPanel navigation = new NavigationPanel("navigation", date, channel);
            add(navigation);
            ChannelLog channelLog = new ChannelLog("ChannelLog", channel, date);
            add(channelLog);
            WelcomePanel welcome = new WelcomePanel("Welcome");
            add(welcome);
            welcome.setVisible(false);

        } else {
            NavigationPanel navigation = new NavigationPanel("navigation", date, channel);
            add(navigation);
            navigation.setVisible(false);
            ChannelLog channelLog = new ChannelLog("ChannelLog", "", new Date());
            add(channelLog);
            channelLog.setVisible(false);
            WelcomePanel welcome = new WelcomePanel("Welcome");
            add(welcome);
        }

        JavabotInfo javabotInfo = new JavabotInfo("info");
        add(javabotInfo);

        Credits credits = new Credits("credits");
        add(credits);
    }

}
