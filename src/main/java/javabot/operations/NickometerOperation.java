package javabot.operations;

import javabot.BotEvent;
import javabot.Message;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ricky_clarkson
 * @janitor joed
 */
public class NickometerOperation implements BotOperation {
    private static final Log log = LogFactory.getLog(NickometerOperation.class);

    /**
     * @see BotOperation#handleMessage(BotEvent)
     */
    public List<Message> handleMessage(BotEvent event) {

        List<Message> messages = new ArrayList<Message>();
        String message = event.getMessage();
        String[] messageParts = message.split(" ");

        log.debug("Nickometer :" + message);

        List<String> words2 = new ArrayList<String>();

        for (String word1 : messageParts) {
            if (!("".equals(word1) || " ".equals(word1))) {
                words2.add(word1);
            }
        }

        if (!("nickometer".equals(words2.get(0)) && (words2.size() > 1))) {
            return messages;
        } else {
            String nick = words2.get(1);
            if (nick.length() == 0) {
                return messages;
            }
            int lameness = 0;
            if (nick.length() > 0 && Character.isUpperCase(nick.charAt(0))) {
                lameness--;
            }
            for (int a = 0; a < nick.length(); a++) {
                if (!Character.isLowerCase(nick.charAt(a))) {
                    lameness++;
                }
            }
            double tempLameness = (double) lameness / nick.length();
            tempLameness = Math.sqrt(tempLameness);
            lameness = (int) (tempLameness * 100);
            messages.add(new Message(event.getChannel(), "The nick " + nick + " is " + lameness + "% lame.", false));
        }
        return messages;
    }

    public List<Message> handleChannelMessage(BotEvent event) {
        return new ArrayList<Message>();
    }
}
