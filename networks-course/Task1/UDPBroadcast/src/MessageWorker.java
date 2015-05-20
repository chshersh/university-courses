import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class MessageWorker implements Runnable {
    private BlockingQueue<Message> msgs;
    private Map<String, HostPerson> hostByIp;
    private SortedSet<HostPerson> sortedHosts;

    public MessageWorker(BlockingQueue<Message> msgs) {
        this.msgs = msgs;
        this.hostByIp = new HashMap<>();
        this.sortedHosts = new TreeSet<>(new HostComparator());
    }

    @Override
    public void run() {
        while (true) {
            Message msg;
            try {
                msg = msgs.poll(2000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                continue;
            }

            long curTime = System.currentTimeMillis();
            HostPerson host = null;
            if (msg != null) {
                host = hostByIp.get(msg.getIp());
                if (host == null) {
                    host = new HostPerson(msg.getIp(), msg.getMac(), msg.getName(), curTime);
                    hostByIp.put(host.getIp(), host);
                }
            }

            for (HostPerson h : hostByIp.values()) {
                if (host != null && h.getIp().equals(host.getIp())) h.updateHistory(curTime, true);
                else h.updateHistory(curTime, false);
            }
            sortedHosts.clear();
            sortedHosts.addAll(hostByIp.values());

            printTable(curTime);
        }
    }

    private void printTable(long curTime) {
        StringBuilder sb = new StringBuilder(100);
        sb.append("====================================================" +
                  "=====================================================" + "\n");

        Iterator<HostPerson> it = sortedHosts.iterator();
        while (it.hasNext()) {
            HostPerson host = it.next();
            if (curTime - host.getLastReceived() >= 20000 || host.getPacketsLost() == 10) {
                it.remove();
                hostByIp.remove(host.getIp());
            } else {
                sb.append(host.toString() + "\n");
            }
        }
        System.out.println(sb.toString());
    }

    public class HostComparator implements Comparator<HostPerson> {
        @Override
        public int compare(HostPerson h1, HostPerson h2) {
            if (h1.getPacketsLost() != h2.getPacketsLost()) {
                return Integer.compare(h1.getPacketsLost(), h2.getPacketsLost());
            }
            return h1.getIp().compareTo(h2.getIp());
        }
    }
}
