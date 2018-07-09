import org.apache.zookeeper.*;

public class ZookeeperExample {

    private String zkQurom = "localhost:2181";

    private String lockNameSpace = "/MyNode";

    private String nodeString = lockNameSpace + "/TestNode";

    private ZooKeeper zk;

    public ZookeeperExample() {
        try {
            zk = new ZooKeeper(zkQurom, 6000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("Receive event " + watchedEvent);
                    if (Event.KeeperState.SyncConnected == watchedEvent.getState())
                        System.out.println("connection is established...");
                }
            });


            zk.exists(nodeString, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println(">>>>>Receive event " + event);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void createNode() throws KeeperException, InterruptedException {
        zk.create(nodeString, "Test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public void deleteNode() throws KeeperException, InterruptedException {
        zk.delete(nodeString, 0);
    }


    public static void main(String[] args) throws Exception {
        ZookeeperExample example = new ZookeeperExample();
        example.createNode();
        Thread.sleep(1000);
        example.deleteNode();
    }

}
